package br.com.chickenroad.screens;

import java.util.ArrayList;
import java.util.List;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.Constantes;
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
import br.com.chickenroad.entities.Road;
import br.com.chickenroad.entities.StateGame;
import br.com.chickenroad.entities.TextGame;
import br.com.chickenroad.entities.TextGameTypes;
import br.com.chickenroad.entities.Timer;
import br.com.chickenroad.entities.Vehicle;
import br.com.chickenroad.screens.screenparts.PlayMenuButtons;
import br.com.chickenroad.screens.screenparts.PopupGameOver;
import br.com.chickenroad.screens.screenparts.PopupPause;
import br.com.chickenroad.screens.screenparts.PopupTutorial;
import br.com.chickenroad.screens.util.MyProperties;

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
	private PopupPause popupPause;
	private PopupGameOver popupGameOver;
	private PopupTutorial popupTutorial;
	//camera do jogo
	private PlayCamera playCamera;
	//lista de estradas
	private List<Road> roadList;
	//veiculos
	private ArrayList<Vehicle> vehicleList;
	//propriedades da fase
	private MyProperties myProperties;

	//variáveis de controle
	private int contAmazing, contPow;

	public static int deltaXPositionButtons=0, deltaYPositionButtons=0;

	//parametros
	private int seasonId, faseId;
	//flag para visualizar popup
	private boolean flagPopupTutorial;

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

		//iniciar fase
		init();
	}

	/**
	 * Inicializa váriaveis de contagem, flags de estado entre outras coisas
	 */
	private void init() {

		String[] points = myProperties.getOriginPlayer().split(",");
		player.init(Float.parseFloat(points[0])*Constantes.WIDTH_TILE,Float.parseFloat(points[1])*Constantes.HEIGHT_TILE);

		myPlayMusic.init();

		playerScore.init(Integer.parseInt(myProperties.getNumberEggs()), Integer.parseInt(myProperties.getNumberCorns()));

		timer.setTimer(myProperties.getTimer());

		points = myProperties.getOriginChickenNest().split(",");
		chickenNest.init(Float.parseFloat(points[0])*Constantes.WIDTH_TILE,Float.parseFloat(points[1])*Constantes.HEIGHT_TILE);


		//this.supporting = new Supporting[ApplicationConfig.numSupporting];
		this.textGame = new TextGame[2];	

		this. textGame[0] = new TextGame(Constantes.URL_TEXT_AMAZING, getAssetManager(), TextGameTypes.AMAZING);
		this. textGame[1] = new TextGame(Constantes.URL_TEXT_POW, getAssetManager(), TextGameTypes.POW);


		//inicializa todos os textos
		for(int i=0;i<textGame.length;i++)
			textGame[i].init(); //exibe texto na posi��o do player

		//inicializa vetor de coadjuvantes - personagens secundarios
		/*
		 * TODO retirado os porcos, pois ainda não fechamos a real posição deles
		this.supporting[0] = new Supporting(Constantes.URL_PIG_STOP_RIGHT, getAssetManager(), SupportingTypes.PIG_AWAKE_RIGHT);
		this.supporting[1] = new Supporting(Constantes.URL_PIG_SLEEPING_RIGHT, getAssetManager(), SupportingTypes.PIG_SLEEPING_RIGHT);
		this.supporting[2] = new Supporting(Constantes.URL_PIG_SLEEPING_LEFT, getAssetManager(), SupportingTypes.PIG_SLEEPING_LEFT);
		 */


		//inicia vetor de coadjuvantes
		//		for(int i=0;i<supporting.length;i++){
		//			supporting[i].init(generator.nextInt(130)+10, generator.nextInt(150)+160);
		//		}

		targetPlayerBuilder.init(myMap, myProperties, getAssetManager(), chickenNest);
		contAmazing = 0;
		contPow = 0;

		roadList = RoadsBuilder.builder(myProperties);
		vehicleList = VehiclesBuilder.builder(roadList, getAssetManager());

		//muda o estado do jogo para 'PAUSE', pois um popup irá aparecer e não posso ficar rodando a aplicação
		stateGame = StateGame.PAUSE;

		//visualizar popup - flag para auxiliar o jogador[tutorial]
		//TODO pausar as animações quando a aplicação estiver em pause[pelo usuário ou por mostrar o popup tutorial]
		this.flagPopupTutorial = true;
		this.playMenuButtons.disable();

	}

	/**
	 * Renderizador principal da classe. Nele há a lógica de estados do jogo.
	 */
	@Override
	public void render(float delta) {

		switch (stateGame) {
		case PLAYING:
			player.updatePlayerPosition(delta, myMap.getTiles(), vehicleList, myMap.getWidthTiledMap(), myMap.getHeightTiledMap());
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
		if(stateGame != StateGame.PAUSE){
			MyPlayMusic.playSound(myPlayMusic.getSoundBackgroundFase());
			MyPlayMusic.playSound(myPlayMusic.getSoundBackgroundChicken());
		}

		chickenRoadGame.getSpriteBatch().begin();

		//desenhar o ninho
		chickenNest.draw(chickenRoadGame.getSpriteBatch());

		//desenha os personagens coadjuvantes
		//		for(int i=0;i<supporting.length;i++) {
		//			supporting[i].draw(chickenRoadGame.getSpriteBatch(), delta);
		//		}

		targetPlayerBuilder.drawEggs(chickenRoadGame.getSpriteBatch(), delta);
		drawGift(delta);

		drawPlayerAndVehicles(delta);

		testCollisionEgg();
		testCollisionCorn();

		chickenRoadGame.getSpriteBatch().end();
		//desenhar camada do mapa depois do player
		myMap.renderLayerColisaoObjetosImoveis();

		chickenRoadGame.getSpriteBatch().begin();

		//renderizar os botões de play, restart, sair
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

		//popup tutorial
		if(flagPopupTutorial)
			popupTutorial.draw(chickenRoadGame.getSpriteBatch());

		chickenRoadGame.getSpriteBatch().end();

		leaveEggInNest();
		testCollisionGift();

	}//end draw()

	private void drawPausePopup() {
		if(stateGame == StateGame.PAUSE && !flagPopupTutorial){
			playMenuButtons.disable();
			this.popupPause.draw(chickenRoadGame.getSpriteBatch());
		}

	}

	private void drawPlayerAndVehicles(float delta) {

		boolean renderPlayer = true;

		for(int i=0;i<vehicleList.size();i++){
			if(stateGame == StateGame.PLAYING)
				vehicleList.get(i).walkX();
			//a figura do carro tá estranha, por isso baixei 4 na figura da galinha
			if(Intersector.overlaps(player.getBoundingRectangle(), vehicleList.get(i).getBoundingRectangle())
					&& player.getY() -4 >= vehicleList.get(i).getY()){
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

	private void drawGift(float delta) {
		//exibe presente - se pegar Y ovos
		if(playerScore.getCurrentNoCatchedEggs() == 0 && !targetPlayerBuilder.getTargetPlayerGiftSheep().isLocker()){
			targetPlayerBuilder.drawGift(chickenRoadGame.getSpriteBatch(), delta);
			MyPlayMusic.playSound(myPlayMusic.getSoundSheep());
		}else
			targetPlayerBuilder.getTargetPlayerGiftSheep().setVisible(false);

	}

	private void testCollisionEgg() {

		if(targetPlayerBuilder.testColissionEgg(player)){
			MyPlayMusic.playSound(myPlayMusic.getSoundEggs());
			playerScore.addScoreGame(PlayerScore.EGGS_SCORE);
			playerScore.minusCurrentNoCatchedEggs();
		}
	}

	private void testCollisionCorn() {

		if(targetPlayerBuilder.testColissionCorn(player)){
			MyPlayMusic.playSound(myPlayMusic.getSoundCorns());
			playerScore.addScoreGame(PlayerScore.CORN_SCORE);
			playerScore.minusCurrentNoCatchedCorn();//decrementa o valor do milho
		}
	}


	private void drawFinishGame(float delta) {
		//se pegou todos os ovos, exibe texto animado de fim de fase
		if(playerScore.getCurrentNoCatchedEggs() == 0 && chickenNest.checkColision(player.getBoundingRectangle())) {

			myPlayMusic.stopBackgroundMusic();

			//TODO aqui será o sucesso da aplicação certo? então, aqui iremos persistir o score e o presente[não há variável do presente por enquanto]
			if(stateGame != StateGame.SUCCESS){
				playerScore.addScoreGame(player.getPlayerLife().calculateScoreLife());
				playerScore.addScoreGame(timer.calculateScoreTimer());
				playMenuButtons.disable();
				PreferencesUser.setSucesso(seasonId, faseId, playerScore.getScoreGame());
			}

			if(contAmazing++ < 140) {//este if evitar que a anima��o fique infinita

				MyPlayMusic.playSound(myPlayMusic.getSoundEndFase());

				//mostra no meio da tela aproximadamente
				textGame[0].setPosition((Constantes.WORLD_WIDTH - 350)/2 + deltaXPositionButtons, 
						(Constantes.WORLD_HEIGHT - 100)/2 + deltaYPositionButtons); //exibe texto na posi��o do playe
				textGame[0].draw(chickenRoadGame.getSpriteBatch(), delta);
			}else{
				myPlayMusic.getSoundEndFase().stop();

				//TODO isso não deve existir quando não for protótipo
				if((faseId+1) != Constantes.MAX_FASES){
					chickenRoadGame.setScreen(new PlayScreen(chickenRoadGame, seasonId, faseId+1));
				}else{
					chickenRoadGame.setScreen(new FasesScreen(chickenRoadGame, seasonId));
				}
			}

			myPlayMusic.getSoundBackgroundFase().pause();
			myPlayMusic.getSoundBackgroundChicken().pause();

			stateGame = StateGame.SUCCESS;

		}
		else { //else GAMBIARRA TEMPOR�RIA - :(
			contAmazing = 0;
		}
	}

	private void leaveEggInNest() {
		//se aproximar do ninho, deixa um ovo
		if(chickenNest.checkColision(player.getBoundingRectangle())) {
			targetPlayerBuilder.leaveEggInNest(chickenNest);
		}
	}

	private void testCollisionGift() {

		if(targetPlayerBuilder.testColissionGift(player)){
			MyPlayMusic.playSound(myPlayMusic.getSoundCoinEndFase());
			myPlayMusic.getSoundSheep().stop();

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

			if(contPow++ < 55) {
				textGame[1].setPosition(player.getX()-30, player.getY()-30); //exibe texto na posi��o do playe
				textGame[1].draw(chickenRoadGame.getSpriteBatch(), delta);
			}
		} else {//else GAMBIARRA TEMPOR�RIA - :(
			contPow = 0;
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
		if(playMenuButtons.checkClickPauseButton(touchPoint.x, touchPoint.y)){
			stateGame = StateGame.PAUSE;
			return true;
		}
		//clicou no play
		if(popupPause.checkClickPlayButton(touchPoint.x, touchPoint.y) && stateGame == StateGame.PAUSE){
			playMenuButtons.enable();
			stateGame = StateGame.PLAYING;
			return true;
		}
		//clicou no restart
		if((popupGameOver.checkClickRestartButton(touchPoint.x, touchPoint.y) && stateGame == StateGame.GAME_OVER) 
				|| popupPause.checkClickRestartButton(touchPoint.x, touchPoint.y) && stateGame == StateGame.PAUSE){
			stateGame = StateGame.RESTART;
			return true;
		}
		//clicou para sair da fase e ir a tela de fases
		if((popupGameOver.checkClickFaseListButton(touchPoint.x, touchPoint.y)  && stateGame == StateGame.GAME_OVER) 
			|| (popupPause.checkClickFaseListButton(touchPoint.x, touchPoint.y)  && stateGame == StateGame.PAUSE)){
			myPlayMusic.stopBackgroundMusic();
			myPlayMusic.getSoundEndFase().stop();
			chickenRoadGame.setScreenWithTransitionFade(new FasesScreen(chickenRoadGame, seasonId));
			return true;
		}

		if(popupTutorial.checkClickOkTutorialButton(touchPoint.x, touchPoint.y) && flagPopupTutorial){
			flagPopupTutorial = false;
			playMenuButtons.enable();
			stateGame = StateGame.PLAYING;
			return true;
		}

		if(popupPause.checkClickSoundOnButton(touchPoint.x, touchPoint.y) && stateGame == StateGame.PAUSE){
			Constantes.SOUND_ON_FLAG = !Constantes.SOUND_ON_FLAG;
			if(!Constantes.SOUND_ON_FLAG) myPlayMusic.stopBackgroundMusic();

		}

		//se nao for clicando em nada acima, devo me movimentar
		if(stateGame == StateGame.PLAYING) player.movimentar(touchPoint);

		return false;
	}

	@Override
	public void pause() {
		stateGame = StateGame.PAUSE;
	}

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
