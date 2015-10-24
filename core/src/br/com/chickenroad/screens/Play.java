package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.entities.MyMap;
import br.com.chickenroad.entities.Player;
import br.com.chickenroad.entities.StateGame;
import br.com.chickenroad.screens.screenparts.PlayMenuButtons;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Responsável pelo controle da fase. Gerencia os componetes de mapa e player para renderizar a fase.
 * 
 *
 */
public class Play extends ScreenAdapter {

	private ChickenRoadGame chickenRoadGame;
	private PlayMenuButtons playMenuButtons;

	private Player player;
	private MyMap myMap;
	private StateGame stateGame;

	private OrthographicCamera orthographicCamera;

	private int deltaXPositionButtons=0, deltaYPositionButtons=0;

	public Play(String urlMap, ChickenRoadGame aChickenRoadGame) {
		this.myMap = new MyMap(urlMap);
		this.chickenRoadGame = aChickenRoadGame;
		this.playMenuButtons = new PlayMenuButtons(chickenRoadGame.getResourceManager().getAssetManager());

		this.orthographicCamera = new OrthographicCamera();
		this.orthographicCamera.setToOrtho(false, 640,480);

		//TODO parametrizar para iniciar com outro personagem
		this.player = new Player(Constantes.URL_PLAYER_AVATAR, myMap.getWidthTiledMap(), myMap.getHeightTiledMap());
	}

	@Override
	public void show() {

		initFase();

		Gdx.input.setInputProcessor(new InputAdapter(){
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
				orthographicCamera.unproject(touchPoint);

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
					chickenRoadGame.setScreen(new SeasonScreen(chickenRoadGame));
					return true;
				}

				player.movimentar(screenX, screenY, orthographicCamera);

				return false;
			}
		});

	}

	@Override
	public void render(float delta) {

		switch (stateGame) {
		case PLAYING:
			player.update(Gdx.graphics.getDeltaTime(), myMap.getTiles(), myMap.getVehicleList());
			break;

		case RESTART:
			initFase();
			break;

		case GAME_OVER:
			break;

		default:
			break;
		}
		draw();
	}

	private void initFase() {

		stateGame = StateGame.PLAYING;
		player.inicializar(this.myMap.getPlayerOrigin());
	}

	private void draw() {

		Gdx.gl.glClearColor(0, 0, 0, 1); //cor preta
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		

		myMap.draw(orthographicCamera);

		//faz a camera seguir o player
		orthographicCamera.position.set(player.getX(), player.getY(), 0);

		positionCamera();
		orthographicCamera.update();
		chickenRoadGame.getSpriteBatch().setProjectionMatrix(orthographicCamera.combined);		

		chickenRoadGame.getSpriteBatch().begin();
		player.draw(chickenRoadGame.getSpriteBatch());
		myMap.drawVehicles(chickenRoadGame.getSpriteBatch());		
		playMenuButtons.draw(chickenRoadGame.getSpriteBatch(), stateGame, deltaXPositionButtons, deltaYPositionButtons);
		chickenRoadGame.getSpriteBatch().end();

	}

	private void positionCamera() {

		// camera .viewportWidth = tamanho da camera
		if(orthographicCamera.position.x < 320) {
			orthographicCamera.position.x = 320;
		}

		if(orthographicCamera.position.y < 240) {
			orthographicCamera.position.y = 240;
		}

		if(orthographicCamera.position.x > myMap.getWidthTiledMap()-320) {
			orthographicCamera.position.x = myMap.getWidthTiledMap()-320;

		}

		if(orthographicCamera.position.y > myMap.getHeightTiledMap()-240) {
			orthographicCamera.position.y = myMap.getHeightTiledMap()-240;
		}

		deltaYPositionButtons = (int)(orthographicCamera.position.y - 240 > 0 ? orthographicCamera.position.y - 240 : 0);
		deltaXPositionButtons = (int)(orthographicCamera.position.x - 320 > 0 ? orthographicCamera.position.x - 320 : 0);

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
