package br.com.chickenroad.screens;

import java.util.ArrayList;
import java.util.List;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.builder.RoadsBuilder;
import br.com.chickenroad.builder.TargetPlayerBuilder;
import br.com.chickenroad.builder.VehiclesBuilder;
import br.com.chickenroad.dao.PreferencesUser;
import br.com.chickenroad.entities.ChickenNest;
import br.com.chickenroad.entities.MyMap;
import br.com.chickenroad.entities.MyPlayMusic;
import br.com.chickenroad.entities.PlayCamera;
import br.com.chickenroad.entities.Player;
import br.com.chickenroad.entities.PlayerScore;
import br.com.chickenroad.entities.RoadFaixa;
import br.com.chickenroad.entities.TextGame;
import br.com.chickenroad.entities.Timer;
import br.com.chickenroad.entities.Vehicle;
import br.com.chickenroad.entities.enums.StateGame;
import br.com.chickenroad.entities.enums.TextGameTypes;
import br.com.chickenroad.screens.screenparts.PlayMenuButtons;
import br.com.chickenroad.screens.screenparts.PopupGameOver;
import br.com.chickenroad.screens.screenparts.PopupPause;
import br.com.chickenroad.screens.screenparts.PopupSuccess;
import br.com.chickenroad.screens.screenparts.PopupTutorial;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.MyProperties;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;

/**
 * Responsável pelo controle da fase
 * 
 *
 */
public class PlayScreen extends ScreenBase {

	//músicas da fase
	private MyPlayMusic myPlayMusic;
	//botões do jogo[menu de contexto]
	private PlayMenuButtons playMenuButtons;
	//ovos e espigas
	private TargetPlayerBuilder targetPlayerBuilder;
	//textos
	private TextGame textGame[];
	//porcos
	//private Supporting supporting[];
	//jogador
	private Player player;
	//score do jogador
	private PlayerScore playerScore;
	//timer
	private Timer timer;
	//ninho
	private ChickenNest chickenNest;
	//mapa
	private MyMap myMap;
	//estado do jogo
	private StateGame stateGame;

	private PopupSuccess popupSuccess;
	private PopupPause popupPause;
	private PopupGameOver popupGameOver;
	private PopupTutorial popupTutorial;

	//camera do jogo
	private PlayCamera playCamera;
	//lista de estradas
	private List<RoadFaixa> roadFaixaList;
	//veiculos
	private ArrayList<Vehicle> vehicleList;
	//propriedades da fase
	private MyProperties myProperties;

	public static int deltaXPositionButtons=0, deltaYPositionButtons=0;

	//parametros
	private int seasonId, faseId;

	private boolean isSaved;

	/**
	 * Inicialização dos atributos da classe
	 * @param urlMap url do mapa
	 * @param aChickenRoadGame referência a classe principal do jogo
	 * @param seasonId identificador da temporada
	 * @param faseId identificador da fase
	 */
	public PlayScreen(ChickenRoadGame aChickenRoadGame, int seasonId, int faseId) {
		super(aChickenRoadGame);

		this.seasonId = seasonId;
		this.faseId = faseId;

		this.myPlayMusic = new MyPlayMusic(getAssetManager());
		this.chickenNest = new ChickenNest(getAssetManager());
		this.myMap = new MyMap(Constantes.URL_MAPS[seasonId][faseId]);
		this.playMenuButtons = new PlayMenuButtons(getAssetManager());
		this.playCamera = new PlayCamera();
		this.player = new Player(getAssetManager());
		this.playerScore = new PlayerScore();
		this.myProperties = new MyProperties();
		this.myProperties.loadProperties(Constantes.URL_MAPS[seasonId][faseId] + ".properties");
		this.timer = new Timer();
		this.targetPlayerBuilder = new TargetPlayerBuilder();

		this.popupTutorial = new PopupTutorial(getAssetManager());
		this.popupGameOver = new PopupGameOver(getAssetManager());
		this.popupPause = new PopupPause(getAssetManager());
		this.popupSuccess = new PopupSuccess(getAssetManager());

		//iniciar fase
		init();
	}

	/**
	 * Inicializa váriaveis de contagem, flags de estado entre outras coisas
	 */
	private void init() {

		isSaved = false;

		player.init(myProperties.getOriginPlayer().split(","));
		playerScore.init(myProperties.getNumberEggs(),myProperties.getNumberCorns());
		timer.setTimer(myProperties.getTimer());
		chickenNest.init(myProperties.getOriginChickenNest().split(","));

		this.textGame = new TextGame[2];	
		this. textGame[0] = new TextGame(Constantes.URL_TEXT_AMAZING, getAssetManager(), TextGameTypes.AMAZING);
		this. textGame[1] = new TextGame(Constantes.URL_TEXT_POW, getAssetManager(), TextGameTypes.POW);

		//inicializa todos os textos
		for(int i=0;i<textGame.length;i++)
			textGame[i].init(); //exibe texto na posi��o do player

		targetPlayerBuilder.init(myMap, myProperties, getAssetManager(), chickenNest);
		roadFaixaList = RoadsBuilder.builder(myProperties);
		vehicleList = VehiclesBuilder.builder(roadFaixaList, getAssetManager());

		//visualizar popup - flag para auxiliar o jogador[tutorial]
		//TODO pausar as animações quando a aplicação estiver em pause[pelo usuário ou por mostrar o popup tutorial]
		if(faseId == 0){

			//muda o estado do jogo para 'PAUSE', pois um popup irá aparecer e não posso ficar rodando a aplicação
			stateGame = StateGame.PAUSE;
			this.popupTutorial.setVisible(true);
			this.playMenuButtons.disable();
		}else{
			stateGame = StateGame.PLAYING;
			playMenuButtons.enable();
		}
	}

	/**
	 * Renderizador principal da classe. Nele há a lógica de estados do jogo.
	 */
	@Override
	public void render(float delta) {

		switch (stateGame) {
		case PLAYING:
			player.updatePlayerPosition(delta, myMap.getTiles(), vehicleList, myMap.getWidthTiledMap(), myMap.getHeightTiledMap());
			checkCollisionEgg();
			checkCollisionCorn();
			leaveEggInNest();
			break;

		case RESTART:
			init();
			break;

		case GAME_OVER:
			break;
		default:
			break;
		}
		//desenhar na tela
		draw(delta);
	}
	/**
	 * Método principal de desenho
	 * @param delta
	 */
	private void draw(float delta) {	

		Gdx.gl.glClearColor(0, 0, 0, 1); //cor preta
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		

		//posição da camera deve acompanhar o jogador
		playCamera.setPosition(player.getX(), player.getY(), myMap.getWidthTiledMap(), myMap.getHeightTiledMap());

		//PROJETA NA MATRIX DO SPRITEBATCH DO MAPA
		chickenRoadGame.getSpriteBatch().setProjectionMatrix(playCamera.getOrthographicCamera().combined);		

		myMap.draw(playCamera.getOrthographicCamera());

		//musicas de fundo em looping
		if((stateGame == StateGame.PAUSE) || (stateGame == StateGame.PLAYING)){
			MyPlayMusic.playSound(myPlayMusic.getSoundBackgroundFase());
			MyPlayMusic.playSound(myPlayMusic.getSoundBackgroundChicken());
		}

		chickenRoadGame.getSpriteBatch().begin();

		//desenhar o ninho
		chickenNest.draw(chickenRoadGame.getSpriteBatch());

		targetPlayerBuilder.drawEggs(chickenRoadGame.getSpriteBatch(), delta);
		drawPlayerAndVehicles(delta);

		chickenRoadGame.getSpriteBatch().end();
		//desenhar camada do mapa depois do player
		myMap.renderLayerColisaoObjetosImoveis();

		chickenRoadGame.getSpriteBatch().begin();

		playMenuButtons.draw(chickenRoadGame.getSpriteBatch(), stateGame, deltaXPositionButtons, deltaYPositionButtons);

		timer.draw(chickenRoadGame.getSpriteBatch(), stateGame, delta, deltaXPositionButtons, deltaYPositionButtons);

		//renderizar o score do player
		playerScore.draw(chickenRoadGame.getSpriteBatch(), deltaXPositionButtons, deltaYPositionButtons);

		player.drawLife(chickenRoadGame.getSpriteBatch());

		targetPlayerBuilder.drawCatchEggs(chickenRoadGame.getSpriteBatch(), player, delta);
		targetPlayerBuilder.drawCatchCorn(chickenRoadGame.getSpriteBatch(), player, delta);

		drawColisionPlayerCar(delta);

		drawGameOver(delta);
		drawPausePopup();
		drawFinishGame(delta);

		popupTutorial.draw(chickenRoadGame.getSpriteBatch());

		chickenRoadGame.getSpriteBatch().end();

	}//end draw()

	private void drawPausePopup() {
		if(stateGame == StateGame.PAUSE && !popupTutorial.isVisible()){
			playMenuButtons.disable();
			this.popupPause.draw(chickenRoadGame.getSpriteBatch());
		}

	}

	private void drawPlayerAndVehicles(float delta) {

		boolean renderPlayer = true;

		for(int i=0;i<vehicleList.size();i++){
			if(stateGame == StateGame.PLAYING)
				vehicleList.get(i).runX();
			//a figura do carro tá estranha, por isso baixei 4 na figura da galinha
			if(Intersector.overlaps(player.getBoundingRectangle(), vehicleList.get(i).getBoundingRectangle())
					&& player.getY() -6 >= vehicleList.get(i).getY()){
				player.draw(chickenRoadGame.getSpriteBatch(), delta);
				vehicleList.get(i).draw(chickenRoadGame.getSpriteBatch());
				renderPlayer = false;
			}else{
				vehicleList.get(i).draw(chickenRoadGame.getSpriteBatch());
			}
			if(stateGame == StateGame.PLAYING && vehicleList.get(i).isNearToHork(player.getBoundingRectangle())){
				MyPlayMusic.playSound(myPlayMusic.getSoundHorn());
			}
		}

		if(renderPlayer) player.draw(chickenRoadGame.getSpriteBatch(), delta);

	}

	private void checkCollisionEgg() {

		if(targetPlayerBuilder.testColissionEgg(player)){
			MyPlayMusic.playSound(myPlayMusic.getSoundEggs());
			playerScore.addToScore(PlayerScore.EGGS_SCORE);
			playerScore.minusCurrentNoCatchedEggs();
		}
	}

	private void checkCollisionCorn() {

		if(targetPlayerBuilder.testColissionCorn(player)){
			MyPlayMusic.playSound(myPlayMusic.getSoundCorns());
			playerScore.addToScore(PlayerScore.CORN_SCORE);
			playerScore.minusCurrentNoCatchedCorn();//decrementa o valor do milho
		}
	}


	private void drawFinishGame(float delta) {
		//se pegou todos os ovos, exibe texto animado de fim de fase
		if(playerScore.getCurrentNoCatchedEggs() == 0 && chickenNest.checkColision(player.getBoundingRectangle())) {

			myPlayMusic.stopBackgroundMusic();

			//TODO aqui será o sucesso da aplicação certo? então, aqui iremos persistir o score e o presente[não há variável do presente por enquanto]
			if(!isSaved){
				playerScore.addToScore(player.getPlayerLife().calculateScoreLife());
				playerScore.addToScore(timer.calculateScoreTimer());
				playMenuButtons.disable();
				PreferencesUser.setSucesso(seasonId, faseId, playerScore.getScoreGame());
				isSaved = true;
			}

			if(stateGame != StateGame.SUCCESS){
				MyPlayMusic.playSound(myPlayMusic.getSoundEndFase());
				//mostra no meio da tela aproximadamente
				textGame[0].setPosition((Constantes.WORLD_WIDTH - 350)/2 + deltaXPositionButtons, 
						(Constantes.WORLD_HEIGHT - 100)/2 + deltaYPositionButtons); //exibe texto na posi��o do playe
			}else{
				textGame[0].draw(chickenRoadGame.getSpriteBatch(), delta);
				if(!myPlayMusic.getSoundEndFase().isPlaying())
					popupSuccess.draw(chickenRoadGame.getSpriteBatch(), playerScore.getScoreGame(), 
							Util.getMaxScoreFase(myProperties.getNumberEggs(), myProperties.getNumberCorns()));

			}

			stateGame = StateGame.SUCCESS;
		}
	}

	private void leaveEggInNest() {
		//se aproximar do ninho, deixa um ovo
		if(chickenNest.checkColision(player.getBoundingRectangle())) {
			targetPlayerBuilder.leaveEggInNest(chickenNest);
		}
	}

	private void drawGameOver(float delta) {
		if(player.isDead() || (timer.possuiTimer() && timer.getTimer() < 1) ){
			player.changeDead();
			stateGame = StateGame.GAME_OVER;
			myPlayMusic.stopBackgroundMusic();
			popupGameOver.draw(chickenRoadGame.getSpriteBatch());
			playMenuButtons.disable();


		}
	}

	private void drawColisionPlayerCar(float delta) {
		//exibe anima��o de colis�o se houve colis�o
		if(player.isColisionVehiclesStatus()) {
			MyPlayMusic.playSound(myPlayMusic.getSoundChickenDemage());
			textGame[1].setPosition(player.getX()-30, player.getY()-30); //exibe texto na posi��o do playe
			textGame[1].draw(chickenRoadGame.getSpriteBatch(), delta);
		}
	}

	@Override
	public boolean keyDown(int keycode) {

		if((keycode == Keys.BACK)|| (keycode == Keys.ESCAPE)){
			if(stateGame == StateGame.PLAYING)
				stateGame = StateGame.PAUSE;
			else
				stateGame = StateGame.PLAYING;

			return true;
		}
		return false;
	}

	@Override
	//evento para liberação de toque na tela - quando solta a tela
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		Vector3 touchPoint = new Vector3(screenX, screenY, 0);
		playCamera.getOrthographicCamera().unproject(touchPoint);

		//clicou em pause
		if(playMenuButtons.checkClickPauseButton(touchPoint.x, touchPoint.y) && (stateGame == StateGame.PLAYING) && !popupTutorial.isVisible()){
			stateGame = StateGame.PAUSE;
			return true;
		}
		//clicou no play
		if(popupPause.checkClickPlayButton(touchPoint.x, touchPoint.y) && (stateGame == StateGame.PAUSE) && !popupTutorial.isVisible()){
			playMenuButtons.enable();
			stateGame = StateGame.PLAYING;
			return true;
		}
		//clicou no restart
		if(((popupGameOver.checkClickRestartButton(touchPoint.x, touchPoint.y) && (stateGame == StateGame.GAME_OVER))
				|| (popupPause.checkClickRestartButton(touchPoint.x, touchPoint.y) && (stateGame == StateGame.PAUSE))
				|| (popupSuccess.checkClickRestartButton(touchPoint.x, touchPoint.y) && (stateGame == StateGame.SUCCESS))) && !popupTutorial.isVisible()){
			stateGame = StateGame.RESTART;
			myPlayMusic.stopBackgroundMusic();
			myPlayMusic.getSoundEndFase().stop();
			return true;
		}
		//clicou para sair da fase e ir a tela de fases
		if(((popupGameOver.checkClickFaseListButton(touchPoint.x, touchPoint.y)  && (stateGame == StateGame.GAME_OVER)) 
				|| (popupPause.checkClickFaseListButton(touchPoint.x, touchPoint.y)  && (stateGame == StateGame.PAUSE))
				|| (popupSuccess.checkClickFaseListButton(touchPoint.x, touchPoint.y) && (stateGame == StateGame.SUCCESS))) && !popupTutorial.isVisible()){
			myPlayMusic.stopBackgroundMusic();
			myPlayMusic.getSoundEndFase().stop();
			chickenRoadGame.setScreen(new FasesScreen(chickenRoadGame, seasonId));
			return true;
		}

		if(popupTutorial.checkClickOkTutorialButton(touchPoint.x, touchPoint.y) && popupTutorial.isVisible()){
			popupTutorial.setVisible(false);
			playMenuButtons.enable();
			stateGame = StateGame.PLAYING;
			return true;
		}

		if(popupPause.checkClickSoundOnButton(touchPoint.x, touchPoint.y) && (stateGame == StateGame.PAUSE) && !popupTutorial.isVisible()){
			Constantes.SOUND_ON_FLAG = !Constantes.SOUND_ON_FLAG;
			if(!Constantes.SOUND_ON_FLAG) myPlayMusic.stopBackgroundMusic();

		}
		if(popupSuccess.checkClickNextButton(touchPoint.x, touchPoint.y) && (stateGame == StateGame.SUCCESS) && !popupTutorial.isVisible()){

			//TODO isso não deve existir quando não for protótipo
			if((faseId+1) != Constantes.MAX_FASES){
				chickenRoadGame.setScreen(new PlayScreen(chickenRoadGame, seasonId, faseId+1));
			}else{
				chickenRoadGame.setScreen(new FasesScreen(chickenRoadGame, seasonId));
			}
		}

		//se nao for clicando em nada acima, devo me movimentar
		if(stateGame == StateGame.PLAYING && !popupTutorial.isVisible()) player.move(touchPoint);

		return false;
	}
	@Override
	public void pause() {
		stateGame = StateGame.PAUSE;
	};


	@Override
	public void dispose() {
		this.chickenRoadGame = null;
		this.playMenuButtons.dispose();
		this.stateGame = null;
		this.myMap.dispose();
		this.playerScore = null;
		this.vehicleList = null;
		this.targetPlayerBuilder.dispose();
		this.targetPlayerBuilder = null;

		for(int i=0;i<textGame.length;i++)
			this.textGame[i].dispose();

	}
}
