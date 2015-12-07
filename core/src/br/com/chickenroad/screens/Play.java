package br.com.chickenroad.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.animations.PlayerScore;
import br.com.chickenroad.entities.ChickenNest;
import br.com.chickenroad.entities.MyMap;
import br.com.chickenroad.entities.MyMusic;
import br.com.chickenroad.entities.Player;
import br.com.chickenroad.entities.PlayerTypes;
import br.com.chickenroad.entities.StateGame;
import br.com.chickenroad.entities.Supporting;
import br.com.chickenroad.entities.SupportingTypes;
import br.com.chickenroad.entities.TargetPlayer;
import br.com.chickenroad.entities.TargetPlayerTypes;
import br.com.chickenroad.screens.screenparts.FinishPopup;
import br.com.chickenroad.screens.screenparts.PlayMenuButtons;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.PlayCamera;

/**
 * ResponsÃ¡vel pelo controle da fase. Gerencia os componetes de mapa e player para renderizar a fase.
 * 
 *
 */
public class Play extends ScreenBase {

	private MyMusic myMusic;

	private PlayMenuButtons playMenuButtons;
	private ArrayList<TargetPlayer> targetPlayerEggsList, targetPlayerCornsList;
	private TargetPlayer targetPlayerGiftSheep;
	private TextGame textGame[]; 
	private Supporting supporting[];
	private Player player;
	private MyMap myMap;
	private StateGame stateGame;
	private FinishPopup popupFinish;
	private PlayCamera playCamera; 
	private PlayerScore playerScore;

	private int contAmazing, contPow, contPlus15, contPlus100;
	private int numCornCatchedIndex; //recebe o valor da posiï¿½ï¿½o do vetor de milhos encontrados
	private boolean flagPlus15;
	private boolean flagPlus100;
	private boolean catchedGift;

	private ChickenNest chickenNest;

	public static int deltaXPositionButtons=0, deltaYPositionButtons=0;

	public Play(String urlMap, ChickenRoadGame aChickenRoadGame) {
		super(aChickenRoadGame);

		this.myMusic = new MyMusic(aChickenRoadGame);

		this.chickenNest = new ChickenNest(Constantes.URL_CHICKENNEST);
		this.myMap = new MyMap(urlMap);
		this.playMenuButtons = new PlayMenuButtons(getAssetManager());
		this.playCamera = new PlayCamera();
		this.player = new Player(Constantes.URL_PLAYER_AVATAR, getAssetManager(),  myMap.getWidthTiledMap(),
				myMap.getHeightTiledMap());
		this.playerScore = new PlayerScore();
		
		this.numCornCatchedIndex=0;
		this.contAmazing = 0;
		this.contPow = 0;
		this.contPlus15 = 0;
		this.contPlus100 = 0;
		
		this.flagPlus15 = false;
		this.flagPlus100 = false;
		this.catchedGift = false;
		
		this.supporting = new Supporting[PlayConfig.numSupporting];
		this.textGame = new TextGame[PlayConfig.numTexts];	

		this. textGame[0] = new TextGame(Constantes.URL_TEXT_AMAZING, getAssetManager(), TextGameTypes.AMAZING);
		this. textGame[1] = new TextGame(Constantes.URL_TEXT_POW, getAssetManager(), TextGameTypes.POW);
		this. textGame[2] = new TextGame(Constantes.URL_TEXT_PLUS15, getAssetManager(), TextGameTypes.PLUS15);
		this. textGame[3] = new TextGame(Constantes.URL_TEXT_PLUS100, getAssetManager(), TextGameTypes.PLUS100);

		//inicializa vetor de coadjuvantes - personagens secundarios
		this.supporting[0] = new Supporting(Constantes.URL_PIG_STOP_RIGHT, 
				getAssetManager(), SupportingTypes.PIG_AWAKE_RIGHT);
		this.supporting[1] = new Supporting(Constantes.URL_PIG_SLEEPING_RIGHT, 
				getAssetManager(), SupportingTypes.PIG_SLEEPING_RIGHT);
		this.supporting[2] = new Supporting(Constantes.URL_PIG_SLEEPING_LEFT, 
				getAssetManager(), SupportingTypes.PIG_SLEEPING_LEFT);
		
		//inicializa vetor de ovos com velocidades aleatorias
		Random generator = new Random();
		
		this.targetPlayerEggsList = new ArrayList<TargetPlayer>();
		for(int i=0;i<PlayConfig.numEggs;i++)
			this.targetPlayerEggsList.add( new TargetPlayer(Constantes.URL_EGGS, getAssetManager(),TargetPlayerTypes.EGGS, 1f/(float)generator.nextInt(5)));
		
		//inicializa vetor de milhos com velocidades iguais
		this.targetPlayerCornsList = new ArrayList<TargetPlayer>();
		for(int i=0;i<PlayConfig.numCorns;i++)
			this.targetPlayerCornsList.add(new TargetPlayer(Constantes.URL_YELLOW_CORN, getAssetManager(),TargetPlayerTypes.YELLOW_CORN,1f/7f ));
		
		this.targetPlayerGiftSheep = new TargetPlayer(Constantes.URL_GIFT_SHEEP, getAssetManager(), TargetPlayerTypes.SHEEP, 1f/4f);
		
		//inicia fase
		initFase();
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
	//evento para liberaÃ§Ã£o de toque na tela - quando solta a tela
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		Vector3 touchPoint = new Vector3(screenX, screenY, 0);
		playCamera.getOrthographicCamera().unproject(touchPoint);

		if((stateGame != StateGame.PAUSE) && playMenuButtons.checkClickPauseButton(touchPoint.x, touchPoint.y)){
			stateGame = StateGame.PAUSE;
			return true;
		}
		if(playMenuButtons.checkClickPlayButton(touchPoint.x, touchPoint.y)){
			stateGame = StateGame.PLAYING;
			return true;
		}
		if(playMenuButtons.checkClickRestartButton(touchPoint.x, touchPoint.y)){
			stateGame = StateGame.RESTART;
			return true;
		}
		if(playMenuButtons.checkClickFaseListButton(touchPoint.x, touchPoint.y)){
			chickenRoadGame.setScreenWithTransitionFade(new SeasonScreen(chickenRoadGame));
			return true;
		}

		if(popupFinish != null && popupFinish.checkClickBackMenuButton(touchPoint.x, touchPoint.y)){
			chickenRoadGame.setScreenWithTransitionFade(new SeasonScreen(chickenRoadGame));
			return true;
		}
		if(popupFinish != null && popupFinish.checkClickRestartButton(touchPoint.x, touchPoint.y)){
			stateGame = StateGame.RESTART;
			return true;
		}
		if(popupFinish != null && popupFinish.checkClicNextButton(touchPoint.x, touchPoint.y)){
			System.err.println("proxima fase");
			return true;
		}

		//se nao for clicando em nada acima, devo me movimentar
		if(stateGame == StateGame.PLAYING) player.movimentar(touchPoint);

		return false;
	}

	@Override
	public void render(float delta) {

		switch (stateGame) {
		case PLAYING:
			player.updatePlayerPosition(Gdx.graphics.getDeltaTime(), myMap.getTiles(), myMap.getVehicleList());
			break;

		case RESTART:
			initFase();
			break;

		case GAME_OVER:
			break;
		default:
			break;
		}
		draw(delta);
	}

	private void initFase() {
		//muda o estado do jogo para 'PLAYING'
		stateGame = StateGame.PLAYING;

		float x = this.myMap.getPlayerOrigin().x;
		float y = this.myMap.getPlayerOrigin().y;

		//se ainda estiver tocando, pára musica do munu inicial
		if (myMusic.getSoundMenuBackground().isPlaying()) {
			myMusic.getSoundMenuBackground().stop();
		}
		
		player.inicializar(x, y);
		playerScore.inicializar();
		chickenNest.inicializar();
		
		numCornCatchedIndex=0;
		contAmazing = 0;
		contPow = 0;
		contPlus15 = 0;
		contPlus100 = 0;
		
		flagPlus15 = false;
		flagPlus100 = false;
		catchedGift = false;

		Random gerador = new Random();

		//inicializa todos os textos
		for(int i=0;i<PlayConfig.numTexts;i++)
			textGame[i].inicializar(); //exibe texto na posiï¿½ï¿½o do player

		//inicia vetor de coadjuvantes
		for(int i=0;i<PlayConfig.numSupporting;i++){
			int pigPosX=gerador.nextInt(130)+10;
			int pigPosY = gerador.nextInt(150)+160;
			supporting[i].inicializar(pigPosX, pigPosY);
		}

		//gera ovos em posiï¿½ï¿½es aleatorios
		for(int i=0;i<PlayConfig.numEggs;i++){
			targetPlayerEggsList.get(i).inicializar(gerador.nextInt(600), gerador.nextInt(400));
			targetPlayerEggsList.get(i).setVisible(true);
		}

		//gera milhos em posiï¿½ï¿½es aleatorios
		for(int i=0;i<PlayConfig.numCorns;i++){
			targetPlayerCornsList.get(i).inicializar(gerador.nextInt(600), gerador.nextInt(400));
			targetPlayerCornsList.get(i).setVisible(true);
		}
		
		//inicializa presente
		targetPlayerGiftSheep.inicializar(500,  120);
	}

	private void draw(float delta) {		
		Random gerador = new Random();

		Gdx.gl.glClearColor(0, 0, 0, 1); //cor preta
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		

		playCamera.setPosition(player.getX(), player.getY(), myMap.getWidthTiledMap(), myMap.getHeightTiledMap());

		//PROJETA NA MATRIX DO SPRITEBATCH DO MAPA
		chickenRoadGame.getSpriteBatch().setProjectionMatrix(playCamera.getOrthographicCamera().combined);		
		myMap.draw(playCamera.getOrthographicCamera());
		chickenRoadGame.getSpriteBatch().begin();
		chickenNest.draw(chickenRoadGame.getSpriteBatch());	

		for(int i=0;i<PlayConfig.numEggs;i++) {
			targetPlayerEggsList.get(i).draw(chickenRoadGame.getSpriteBatch(), delta);		
		}

		player.draw(chickenRoadGame.getSpriteBatch(), delta);

		//desenha os personagens coadjuvantes
		for(int i=0;i<PlayConfig.numSupporting;i++) {
			supporting[i].draw(chickenRoadGame.getSpriteBatch(), delta);
		}

		myMap.drawVehicles(chickenRoadGame.getSpriteBatch(), stateGame);
		playMenuButtons.draw(chickenRoadGame.getSpriteBatch(), stateGame, deltaXPositionButtons, deltaYPositionButtons);
		playerScore.draw(chickenRoadGame.getSpriteBatch(), deltaXPositionButtons, deltaYPositionButtons);

		
		//exibe presente - se pegar X milhos e Y ovos
		if(playerScore.getCurrentNoCatchedEggs() == 0 && 
				playerScore.getCurrentNoCatchedCorns() <= 49){//PlayConfig.numCorns/2) {
			if(!catchedGift) {
				myMusic.getSoundSheep().play();
				targetPlayerGiftSheep.setPosition(500, 100);
				targetPlayerGiftSheep.draw(chickenRoadGame.getSpriteBatch(), delta);
			}
		}
		else
			targetPlayerGiftSheep.setPosition(-100, -100);
		
		//se pegou todos os ovos, exibe texto animado de fim de fase
		if(playerScore.getCurrentNoCatchedEggs() == 0 && chickenNest.checkColision(player)) {
			if(contAmazing++ < 130) {//este if evitar que a animaï¿½ï¿½o fique infinita

				myMusic.getSoundEndFase().play();

				//mostra no meio da tela aproximadamente
				textGame[0].setPosition(player.getX()-280, playCamera.getOrthographicCamera().viewportHeight/2 -40); //exibe texto na posiï¿½ï¿½o do playe
				textGame[0].draw(chickenRoadGame.getSpriteBatch(), delta);
			}

			myMusic.getSoundBackgroundFase1().pause();
			myMusic.getSoundBackgroundChicken().pause();

			stateGame = StateGame.FINISH;

		}
		else { //else GAMBIARRA TEMPORï¿½RIA - :(

			myMusic.getSoundBackgroundFase1().play();
			myMusic.getSoundBackgroundChicken().play();

			textGame[0].setPosition(-100, -200);
			contAmazing = 0;
		}

		//exibe animaï¿½ï¿½o de colisï¿½o se houve colisï¿½o
		if(player.isColisionVehiclesStatus()) {
			myMusic.getSoundChickenDemage().play();

			if(contPow++ < 55) {
				textGame[1].setPosition(player.getX()-30, player.getY()-30); //exibe texto na posiï¿½ï¿½o do playe
				textGame[1].draw(chickenRoadGame.getSpriteBatch(), delta);
			}
		} else {//else GAMBIARRA TEMPORï¿½RIA - :(
			textGame[1].setPosition(-100, -200);
			contPow = 0;
		}

		//testa colisï¿½o do alvo 
		for(int i=0;i<PlayConfig.numEggs;i++){
			//so pode pegar ovos se ele nao tiver sido pego antes
			if(targetPlayerEggsList.get(i).checkColision(player) && (playerScore.getCurrentNoCatchedEggs() > 0)){ 
				targetPlayerEggsList.get(i).setVisible(false);//apaga o ovo da tela
				
				myMusic.getSoundEggs().play();
				playerScore.addScoreGame(PlayerScore.EGGS_SCORE);
				playerScore.minusCurrentNoCatchedEggs();
				flagPlus15 = true;
			}			
		}

		//testa colisï¿½o do alvo - MILHOS ESCONDIDOS
		for(int i=0;i<PlayConfig.numCorns;i++){
			//so pode pegar ovos se ele nao tiver sido pego antes
			if(targetPlayerCornsList.get(i).checkColision(player) && (playerScore.getCurrentNoCatchedEggs() > 0)){
				targetPlayerCornsList.get(i).setVisible(false); //apaga milho da tela
				targetPlayerCornsList.get(i).setLocker(true);
				myMusic.getSoundCorns().play();
				playerScore.addScoreGame(PlayerScore.CORN_SCORE);
				playerScore.minusCurrentNoCatchedCorn();//decrementa o valor do milho
				flagPlus100 = true;
				numCornCatchedIndex = i;//recebe a posiï¿½ï¿½o do milho pego
			}			
		}

		if(flagPlus15) {
			if(contPlus15++ < 45) {
				textGame[2].setPosition(player.getX()-20, player.getY()+30);
				textGame[2].draw(chickenRoadGame.getSpriteBatch(), delta);
			}else{
				textGame[2].setPosition(-100, -200);
				contPlus15 = 0;
				flagPlus15 = false;
			}
		}

		if(flagPlus100) {
			if(contPlus100++ < 48) {
				//exibe apenas o milho encontrado
				targetPlayerCornsList.get(numCornCatchedIndex).setVisible(true);
				targetPlayerCornsList.get(numCornCatchedIndex).draw(chickenRoadGame.getSpriteBatch(), delta);		
				textGame[3].setPosition(player.getX()-20, player.getY()+30);
				textGame[3].draw(chickenRoadGame.getSpriteBatch(), delta);

			}else{
				textGame[3].setPosition(-100, -200);
				contPlus100 = 0;
				flagPlus100 = false;
				targetPlayerCornsList.get(numCornCatchedIndex).setVisible(false);

				
			}
		}

		//SE O LIFE FOR MENOR QUE ZERO - gameover
		if(player.getPlayerLife().getLife() <= 0) {
			player.getPlayerAnimation().setSpriteSheet(Constantes.URL_PLAYER_AVATAR_DEAD, PlayerTypes.AVATAR_DEAD); //muda para animação de avatar morto
			player.draw(chickenRoadGame.getSpriteBatch(), delta);
			stateGame = StateGame.GAME_OVER;
			
			myMusic.getSoundBackgroundChicken().pause();
			myMusic.getSoundBackgroundFase1().pause();
			
		}
		chickenRoadGame.getSpriteBatch().end();

		

		//se aproximar do ninho, deixa um ovo
		if(chickenNest.checkColision(player)) {
			for(int i=0;i<PlayConfig.numEggs;i++){
				if(!targetPlayerEggsList.get(i).getVisible()){
					targetPlayerEggsList.get(i).inicializar(840+gerador.nextInt(30), 60+gerador.nextInt(10));
					targetPlayerEggsList.get(i).setVisible(true);
					targetPlayerEggsList.get(i).setLocker(true);

				}
			}
		}
		
		//colisao com o presente ganho
		if(targetPlayerGiftSheep.checkColision(player)) {
				catchedGift = true;
				myMusic.getSoundCoinEndFase().play();
				myMusic.getSoundSheep().pause();
		}
	}//end draw()

	@Override
	public void pause() {
		stateGame = StateGame.PAUSE;
	}

	@Override
	public void dispose() {
		this.chickenRoadGame = null;
		this.player.dispose();
		this.playMenuButtons.dispose();
		this.stateGame = null;
		this.myMap.dispose();
		this.playerScore = null;
		this.myMusic.dispose();

		for(int i=0;i<PlayConfig.numTexts;i++)
			this.textGame[i].dispose();

		for(int i=0;i<PlayConfig.numSupporting;i++)
			this.supporting[i].dispose();

		for(int i=0;i<PlayConfig.numEggs;i++)
			this.targetPlayerEggsList.get(i).dispose();

		for(int i=0;i<PlayConfig.numCorns;i++)
			this.targetPlayerCornsList.get(i).dispose();

		this.targetPlayerGiftSheep.dispose();
	}
}
