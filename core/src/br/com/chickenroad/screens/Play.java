package br.com.chickenroad.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.animations.PlayerScore;
import br.com.chickenroad.entities.ChickenNest;
import br.com.chickenroad.entities.MyMap;
import br.com.chickenroad.entities.Player;
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
 * Respons√°vel pelo controle da fase. Gerencia os componetes de mapa e player para renderizar a fase.
 * 
 *
 */
public class Play extends ScreenBase {

	private PlayMenuButtons playMenuButtons;
	
	private TargetPlayer targetPlayer[];
	private Supporting supporting[];
	private Player player;
	private MyMap myMap;
	private StateGame stateGame;
	private FinishPopup popupFinish;
	private PlayCamera playCamera; 
	private int numEggs = 2;
	private int numSupporting = 3;
	private int numTexts = 3;
	private boolean catchedEggs[];
	private PlayerScore playerScore;
	private int score;
	private int numCatched;
	private int numLeft;
	private int contAmazing, contPow, contPlus15;
	private int pigPosX, pigPosY;
	private boolean flagPlus15 = false;
	
	private PortalTeste portalTeste;
	private ChickenNest chickenNest;
	private TextGame textGame[]; 
	
	public static int deltaXPositionButtons=0, deltaYPositionButtons=0;

	public Play(String urlMap, ChickenRoadGame aChickenRoadGame) {
		super(aChickenRoadGame);

		this.chickenNest = new ChickenNest(Constantes.URL_CHICKENNEST);
		this.portalTeste = new PortalTeste(Constantes.URL_PORTAL);
		this.myMap = new MyMap(urlMap);
		this.playMenuButtons = new PlayMenuButtons(getAssetManager());
		this.playCamera = new PlayCamera();

		this.player = new Player(Constantes.URL_PLAYER_AVATAR, getAssetManager(),  myMap.getWidthTiledMap(),
					myMap.getHeightTiledMap());

		this.playerScore = new PlayerScore();
		this.score = 0;
		this.numCatched = 0;//numero de ovos pegos no cenario
		this.numLeft = 0;//numeros de ovos deixados no ninho
		this.contAmazing = 0;
		this.contPow = 0;
		this.contPlus15 = 0;
		this.catchedEggs = new boolean[numEggs];
		this.targetPlayer = new TargetPlayer[numEggs];
		this.supporting = new Supporting[numSupporting];
		this.textGame = new TextGame[numTexts];	
		
		this. textGame[0] = new TextGame(Constantes.URL_TEXT_AMAZING, getAssetManager(), TextGameTypes.AMAZING);
		this. textGame[1] = new TextGame(Constantes.URL_TEXT_POW, getAssetManager(), TextGameTypes.POW);
		this. textGame[2] = new TextGame(Constantes.URL_TEXT_PLUS15, getAssetManager(), TextGameTypes.PLUS15);
		
		
		//inicializa vetor de coadjuvantes - personagens secundarios
		this.supporting[0] = new Supporting(Constantes.URL_PIG_STOP_RIGHT, 
				    getAssetManager(), SupportingTypes.PIG_AWAKE_RIGHT);
		this.supporting[1] = new Supporting(Constantes.URL_PIG_SLEEPING_RIGHT, 
					getAssetManager(), SupportingTypes.PIG_SLEEPING_RIGHT);
		
		this.supporting[2] = new Supporting(Constantes.URL_PIG_SLEEPING_LEFT, 
				getAssetManager(), SupportingTypes.PIG_SLEEPING_LEFT);
		
		//inicializa vetor de ovos
		for(int i=0;i<numEggs;i++)
		   this.targetPlayer[i] = new TargetPlayer(Constantes.URL_EGGS, getAssetManager(),TargetPlayerTypes.EGGS);
		
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
	//evento para libera√ß√£o de toque na tela - quando solta a tela
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
		
		player.inicializar(x, y);
		playerScore.inicializar();
		chickenNest.setPosition(590, 50);
		contAmazing = 0;
		contPow = 0;
		contPlus15 = 0;
		numLeft = 0;
		score = 0;
		flagPlus15 = false;
		numCatched = 0;
		popupFinish = null;
		
		//inicializa todos os textos
		for(int i=0;i<numTexts;i++)
			textGame[i].inicializar(); //exibe texto na posiÁ„o do player
		
		//inicia vetor de coadjuvantes
		
		for(int i=0;i<numSupporting;i++){
			Random gerador = new Random();
			int pigPosX=gerador.nextInt(130)+10;
			int pigPosY = gerador.nextInt(150)+170;
			supporting[i].inicializar(pigPosX, pigPosY);
		}
		//gera ovos em posiÁıes aleatorios
		for(int i=0;i<numEggs;i++){
		   Random gerador = new Random();
		   targetPlayer[i].inicializar(gerador.nextInt(600), gerador.nextInt(400));
		   catchedEggs[i] = false;
		}
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
	
		
		//se pegou todos os ovos, exibe portal de fase
		/*if(numLeft == NumEggs) {
			portalTeste.setPosition(player.getX(), player.getY());
			portalTeste.draw(chickenRoadGame.getSpriteBatch());
		}
		else //else GAMBIARRA TEMPOR¡RIA - :(
			portalTeste.setPosition(-10, -10);
		*/	
		
		for(int i=0;i<numEggs;i++) {
			if(!catchedEggs[i])//exibe apenas os n„o pegos
			  targetPlayer[i].draw(chickenRoadGame.getSpriteBatch(), delta);		
		}
		
		player.draw(chickenRoadGame.getSpriteBatch(), delta);
		
		//desenha os personagens coadjuvantes
		for(int i=0;i<numSupporting;i++) {
			supporting[i].setPosition(pigPosX, pigPosY);
			supporting[i].draw(chickenRoadGame.getSpriteBatch(), delta);
		}
		
		myMap.drawVehicles(chickenRoadGame.getSpriteBatch(), stateGame);
		playMenuButtons.draw(chickenRoadGame.getSpriteBatch(), stateGame, deltaXPositionButtons, deltaYPositionButtons);
		playerScore.draw(chickenRoadGame.getSpriteBatch(), deltaXPositionButtons, deltaYPositionButtons);

		//se pegou todos os ovos, exibe texto animado de fim de fase
		if(numLeft == numEggs) {
			if(contAmazing++ < 60) {
				//mostra no meio da tela aproximadamente
				textGame[0].setPosition(player.getX()-60, playCamera.getOrthographicCamera().viewportHeight/2 ); //exibe texto na posiÁ„o do playe
				textGame[0].draw(chickenRoadGame.getSpriteBatch(), delta);
			}
			stateGame = StateGame.PAUSE;
			
			//popupFinish = new FinishPopup(chickenRoadGame.getResourceManager());
		   // popupFinish.setPopupInitPositionX((int) (player.getX()-160));
			//popupFinish.setPopupInitPositionY((int) (playCamera.getOrthographicCamera().viewportHeight/2));
			//popupFinish.draw(chickenRoadGame.getSpriteBatch(), delta);

		}
		else { //else GAMBIARRA TEMPOR¡RIA - :(
			textGame[0].setPosition(-100, -200);
			contAmazing = 0;
		}
		
		
		//exibe animaÁ„o de colis„o se houve colis„o
		if(player.isColisionVehiclesStatus()) {
			if(contPow++ < 55) {
				textGame[1].setPosition(player.getX()-30, player.getY()-30); //exibe texto na posiÁ„o do playe
				textGame[1].draw(chickenRoadGame.getSpriteBatch(), delta);
			}
		} else {//else GAMBIARRA TEMPOR¡RIA - :(
			textGame[1].setPosition(-100, -200);
			contPow = 0;
		}
		

		//testa colis„o do alvo 
		for(int i=0;i<numEggs;i++){
			//so pode pegar ovos se ele nao tiver sido pego antes
			if(!catchedEggs[i] && targetPlayer[i].checkColision(player) && (numCatched < numEggs)) {
				catchedEggs[i] = true;//marcou como ovo pego
				score+=15;
				numCatched++;
				PlayerScore.setScore(score);
				flagPlus15 = true;
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
		chickenRoadGame.getSpriteBatch().end();

		//SE O LIFE FOR MENOR QUE ZERO - gameover
		if(player.getPlayerLife().getLife() <= 0) 
			stateGame = StateGame.GAME_OVER;

		//if(portalTeste.checkColision(player)){
			//stateGame = StateGame.PAUSE;
			//popupFinish = new FinishPopup(chickenRoadGame.getResourceManager());
			//popupFinish.draw(chickenRoadGame.getSpriteBatch());
		//}

		//se aproximar do ninho, deixa um ovo
		if(chickenNest.checkColision(player)) {
			for(int i=0;i<numEggs;i++){
				if(catchedEggs[i]){
					targetPlayer[i].inicializar(615+gerador.nextInt(30), 75+gerador.nextInt(10));
					catchedEggs[i] = false;//sinaliza como ovo liberado
					numLeft++;
				}
				
				if(numLeft == numEggs) {
					//contAmazing = 0;
					//contPow = 0;
					//contPlus15 = 0;
					//numCatched=0;
					//numLeft = 0;
					//stateGame = StateGame.PAUSE;
				}
			}
		}
		
	}

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
		
		for(int i=0;i<numTexts;i++)
			this.textGame[i].dispose();
	
		for(int i=0;i<numSupporting;i++)
			this.supporting[i].dispose();
		
		for(int i=0;i<numEggs;i++)
			this.targetPlayer[i].dispose();
	}
}
