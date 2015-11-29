package br.com.chickenroad.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.animations.PlayerScore;
import br.com.chickenroad.entities.ChickenNest;
import br.com.chickenroad.entities.MyMap;
import br.com.chickenroad.entities.Player;
import br.com.chickenroad.entities.StateGame;
import br.com.chickenroad.entities.TargetPlayer;
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
	private Player player;
	private MyMap myMap;
	private StateGame stateGame;
	private FinishPopup popupFinish;
	private PlayCamera playCamera; 
	private int NumEggs = 4;
	private boolean catchedEggs[];
	private PlayerScore playerScore;
	private int score;
	private int numCatched;
	private PortalTeste portalTeste;
	private ChickenNest chickenNest;
	
	public static int deltaXPositionButtons=0, deltaYPositionButtons=0;

	public Play(String urlMap, ChickenRoadGame aChickenRoadGame) {
		super(aChickenRoadGame);

		this.chickenNest = new ChickenNest(Constantes.URL_CHICKENNEST);
		this.portalTeste = new PortalTeste(Constantes.URL_PORTAL);
		this.myMap = new MyMap(urlMap);
		this.playMenuButtons = new PlayMenuButtons(getAssetManager());
		this.playCamera = new PlayCamera();

		this.player = new Player(Constantes.URL_PLAYER_AVATAR, myMap.getWidthTiledMap(),
					myMap.getHeightTiledMap(), getAssetManager());

		this.playerScore = new PlayerScore();
		this.score = 0;
		this.numCatched = 0;
		this.catchedEggs = new boolean[NumEggs];
		this.targetPlayer = new TargetPlayer[NumEggs];
		
		for(int i=0;i<NumEggs;i++)
		   this.targetPlayer[i] = new TargetPlayer(Constantes.URL_EGGS, getAssetManager());
		
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
		player.inicializar(this.myMap.getPlayerOrigin());
		playerScore.inicializar();
		chickenNest.setPosition(590, 50);
		score = 0;
		numCatched = 0;
		popupFinish = null;
		//gera ovos em posiÁıes aleatorios
		Random gerador = new Random();
		for(int i=0;i<NumEggs;i++){
		   targetPlayer[i].inicializar(gerador.nextInt(600), gerador.nextInt(400));
		   catchedEggs[i] = false;
		}
	}

	private void draw(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1); //cor preta
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		

		playCamera.setPosition(player.getX(), player.getY(), myMap.getWidthTiledMap(), myMap.getHeightTiledMap());

		//PROJETA NA MATRIX DO SPRITEBATCH DO MAPA
		chickenRoadGame.getSpriteBatch().setProjectionMatrix(playCamera.getOrthographicCamera().combined);		
		myMap.draw(playCamera.getOrthographicCamera());
		
		chickenRoadGame.getSpriteBatch().begin();
		//se pegou todos os ovos, exibe portal de fase

		chickenNest.draw(chickenRoadGame.getSpriteBatch());	
/*
		if(numCatched == NumEggs) {
			portalTeste.setPosition(615, 80);
			portalTeste.draw(chickenRoadGame.getSpriteBatch());
		}
		else //else GAMBIARRA TEMPOR¡RIA - :(
			portalTeste.setPosition(-10, -10);
		
	*/	
		for(int i=0;i<NumEggs;i++) {
			if(!catchedEggs[i])
			  targetPlayer[i].draw(chickenRoadGame.getSpriteBatch(), delta);		
		}

			
		player.draw(chickenRoadGame.getSpriteBatch(), delta);
		myMap.drawVehicles(chickenRoadGame.getSpriteBatch(), stateGame);
		playMenuButtons.draw(chickenRoadGame.getSpriteBatch(), stateGame, deltaXPositionButtons, deltaYPositionButtons);
		playerScore.draw(chickenRoadGame.getSpriteBatch(), deltaXPositionButtons, deltaYPositionButtons);
		

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
			Random gerador = new Random();
			for(int i=0;i<NumEggs;i++){
				if(catchedEggs[i]){
				targetPlayer[i].inicializar(615+gerador.nextInt(30), 75+gerador.nextInt(10));
				catchedEggs[i] = false;//sinaliza como ovo liberado
					if(numCatched == NumEggs)
						stateGame = StateGame.PAUSE;
				}
			}
			
		}
		
		//testa colis„o do alvo 
		for(int i=0;i<NumEggs;i++){
			//so pode pegar ovos se ele nao tiver sido pego antes
			if(!catchedEggs[i] && targetPlayer[i].checkColision(player) && (numCatched < NumEggs)) {
				catchedEggs[i] = true;//marcou como ovo pego
				score+=15;
				numCatched++;
				PlayerScore.setScore(score);
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
		
		for(int i=0;i<NumEggs;i++)
		this.targetPlayer[i].dispose();
	}
}
