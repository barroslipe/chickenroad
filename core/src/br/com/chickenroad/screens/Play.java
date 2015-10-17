package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.entities.MyMap;
import br.com.chickenroad.entities.Player;
import br.com.chickenroad.entities.StateGame;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

/**
 * Responsável pelo controle da fase. Gerencia os componetes de mapa e player para renderizar a fase.
 * 
 *
 */
public class Play implements Screen {

	private ChickenRoadGame chickenRoadGame;

	private Player player;
	private MyMap myMap;
	private StateGame stateGame;

	float deltaXPositionButtons=0, deltaYPositionButtons=0;
	public static OrthographicCamera orthographicCamera;//cria camera

	private int fase;

	private Sprite spritePause, spriteRestart, spritePlay, spriteListFase;

	public Play(String urlMap, ChickenRoadGame aChickenRoadGame, int aFase) {
		this.myMap = new MyMap(urlMap);
		this.chickenRoadGame = aChickenRoadGame;
		this.fase = aFase;

		Texture pauseFase = chickenRoadGame.getResourceManager().getAssetManager().get("pauseFaseButton.png");
		this.spritePause = new Sprite(new TextureRegion(pauseFase));

		Texture playFase = chickenRoadGame.getResourceManager().getAssetManager().get("playFaseButton.png");
		this.spritePlay = new Sprite(new TextureRegion(playFase));

		Texture restartFase = chickenRoadGame.getResourceManager().getAssetManager().get("restartFaseButton.png");
		this.spriteRestart = new Sprite(new TextureRegion(restartFase));

		Texture listFase = chickenRoadGame.getResourceManager().getAssetManager().get("listFaseButton.png");
		this.spriteListFase = new Sprite(new TextureRegion(listFase));

		stateGame = StateGame.PLAYING;

	}

	@Override
	public void show() {

		orthographicCamera = new OrthographicCamera();

		//TODO qual o tamanho da camera?
		orthographicCamera.setToOrtho(false, 640,480);

		//TODO parametrizar para iniciar com outro personagem
		player = new Player(Constantes.URL_PLAYER_AVATAR, chickenRoadGame, myMap.getWidthTiledMap(), myMap.getHeightTiledMap());
		player.inicializar();

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
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {

				Vector3 touchPoint = new Vector3(screenX, screenY, 0);
				orthographicCamera.unproject(touchPoint);

				if((stateGame != StateGame.PAUSE) && spritePause.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
					stateGame = StateGame.PAUSE;
					return true;
				}

				if(spritePlay.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
					stateGame = StateGame.PLAYING;
					return true;
				}
				if(spriteRestart.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
					stateGame = StateGame.RESTART;
					return true;
				}
				if(spriteListFase.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
					chickenRoadGame.setScreen(new SeasonScreen(chickenRoadGame));

					return true;
				}

				player.movimentar(screenX, screenY);

				return false;
			}
		});
	}

	@Override
	public void render(float delta) {

		switch (stateGame) {
		case PLAYING:
			player.update(Gdx.graphics.getDeltaTime(), myMap.getTiles());
			break;

		case RESTART:
			restartFase();
			break;

		case GAME_OVER:
			break;

		default:
			break;
		}
		draw();
	}

	private void restartFase() {

		stateGame = StateGame.PLAYING;
		player.inicializar();

	}

	private void draw() {

		Gdx.gl.glClearColor(0, 0, 0, 1); //cor preta
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//renderiza camera e imagens
		myMap.getOrthogonalTiledMapRenderer().setView(orthographicCamera);
		myMap.getOrthogonalTiledMapRenderer().render();

		//faz a camera seguir o player
		orthographicCamera.position.set(player.getX(), player.getY(), 0);

		chickenRoadGame.getSpriteBatch().begin();

		new BitmapFont().draw(chickenRoadGame.getSpriteBatch(), "Fase "+fase, 580+deltaXPositionButtons, 440+deltaYPositionButtons);

		player.draw(chickenRoadGame.getSpriteBatch());
		drawState();

		chickenRoadGame.getSpriteBatch().end();

		positionCamera();
		orthographicCamera.update();
		chickenRoadGame.getSpriteBatch().setProjectionMatrix(orthographicCamera.combined);		
	}

	private void drawState() {

		System.out.println("x-> "+orthographicCamera.position.x + " , y-> "+orthographicCamera.position.y);


		if(stateGame == StateGame.PLAYING){
			spritePause.setPosition(10+deltaXPositionButtons,  440+deltaYPositionButtons);
			spritePause.draw(chickenRoadGame.getSpriteBatch());
		}else{
			spritePlay.setPosition(10+deltaXPositionButtons,  440+deltaYPositionButtons);
			spritePlay.draw(chickenRoadGame.getSpriteBatch());
		}

		spriteRestart.setPosition(50+deltaXPositionButtons,  440+deltaYPositionButtons);
		spriteRestart.draw(chickenRoadGame.getSpriteBatch());

		spriteListFase.setPosition(90+deltaXPositionButtons,  440+deltaYPositionButtons);
		spriteListFase.draw(chickenRoadGame.getSpriteBatch());

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

		deltaYPositionButtons = (orthographicCamera.position.y - 240 > 0 ? orthographicCamera.position.y - 240 : 0);
		deltaXPositionButtons = (orthographicCamera.position.x - 320 > 0 ? orthographicCamera.position.x - 320 : 0);

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {
		stateGame = StateGame.PAUSE;

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		this.dispose();

	}

	@Override
	public void dispose() {
		player.dispose();
		myMap.dispose();
	}

}
