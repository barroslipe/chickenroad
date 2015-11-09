package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.entities.MyMap;
import br.com.chickenroad.entities.Player;
import br.com.chickenroad.entities.StateGame;
import br.com.chickenroad.screens.screenparts.FinishPopup;
import br.com.chickenroad.screens.screenparts.PlayMenuButtons;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.PlayCamera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;

/**
 * Responsável pelo controle da fase. Gerencia os componetes de mapa e player para renderizar a fase.
 * 
 *
 */
public class Play extends ScreenBase {

	private PlayMenuButtons playMenuButtons;

	private Player player;
	private MyMap myMap;
	private PortalTeste portalTeste;
	private StateGame stateGame;
	private FinishPopup popupFinish;
	private PlayCamera playCamera;

	public static int deltaXPositionButtons=0, deltaYPositionButtons=0;

	public Play(String urlMap, ChickenRoadGame aChickenRoadGame) {
		super(aChickenRoadGame);

		this.myMap = new MyMap(urlMap);
		this.playMenuButtons = new PlayMenuButtons(getAssetManager());
		this.playCamera = new PlayCamera();

		//TODO parametrizar para iniciar com outro personagem
		this.player = new Player(Constantes.URL_PLAYER_AVATAR, myMap.getWidthTiledMap(), myMap.getHeightTiledMap(), getAssetManager());
		this.portalTeste = new PortalTeste("portal.png");
		

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
	//evento para liberação de toque na tela - quando solta a tela
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
		

		player.movimentar(screenX, screenY, playCamera.getOrthographicCamera());

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

		stateGame = StateGame.PLAYING;
		player.inicializar(this.myMap.getPlayerOrigin());
	}

	private void draw(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1); //cor preta
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		


		playCamera.setPosition(player.getX(), player.getY(), myMap.getWidthTiledMap(), myMap.getHeightTiledMap());

		chickenRoadGame.getSpriteBatch().setProjectionMatrix(playCamera.getOrthographicCamera().combined);		
		myMap.draw(playCamera.getOrthographicCamera());


		chickenRoadGame.getSpriteBatch().begin();
		player.draw(chickenRoadGame.getSpriteBatch(), delta);
		portalTeste.draw(chickenRoadGame.getSpriteBatch());
		myMap.drawVehicles(chickenRoadGame.getSpriteBatch(), stateGame);
		playMenuButtons.draw(chickenRoadGame.getSpriteBatch(), stateGame, deltaXPositionButtons, deltaYPositionButtons);
		
		chickenRoadGame.getSpriteBatch().end();
		
		if(player.getPlayerLife().getLife() <= 0) stateGame = StateGame.GAME_OVER;
		
		if(portalTeste.checkColision(player)){
			stateGame = StateGame.PAUSE;
			popupFinish = new FinishPopup(chickenRoadGame.getResourceManager());
			popupFinish.draw(chickenRoadGame.getSpriteBatch());
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
	}

}
