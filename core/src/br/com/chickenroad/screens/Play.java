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
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Responsável pelo controle da fase. Gerencia os componetes de mapa e player para renderizar a fase.
 * 
 *
 */
public class Play implements Screen {

	private ChickenRoadGame chickenRoadGame;

	private Player player;
	private MyMap myMap;

	public static OrthographicCamera orthographicCamera;//cria camera
	
	private int fase;
	
	private StateGame stateGame;
	

	public Play(String urlMap, ChickenRoadGame aChickenRoadGame, int aFase) {
		this.myMap = new MyMap(urlMap);
		this.chickenRoadGame = aChickenRoadGame;
		this.fase = aFase;
	}

	@Override
	public void show() {

		orthographicCamera = new OrthographicCamera();

		//TODO qual o tamanho da camera?
		orthographicCamera.setToOrtho(false, 640,480);

		//TODO parametrizar para iniciar com outro personagem
		player = new Player(Constantes.URL_PLAYER_AVATAR, chickenRoadGame, myMap.getWidthTiledMap(), myMap.getHeightTiledMap());

		//TODO parametrizar, o ponto de origem do jogador pode mudar de acordo com o mapa a ser apresentado
		player.setX(0);
		player.setY(0);

		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean keyDown(int keycode) {
				
				if((keycode == Keys.BACK)|| (keycode == Keys.ESCAPE)){
					if(stateGame == StateGame.RUN)
						stateGame = StateGame.PAUSE;
					else
						stateGame = StateGame.RUN;
					
					return true;
				}
				return false;
			}
			
			@Override
			//evento para liberação de toque na tela - quando solta a tela
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {

				//para que o sistema não guarde o ponto que o usuario clique no jogo pausado.
				if(stateGame == StateGame.PAUSE)
					return false;
				
				player.movimentar(screenX, screenY);
				
				return false;
			}
		});
		
		stateGame = StateGame.RUN;

	}

	@Override
	public void render(float delta) {

		switch (stateGame) {
		case RUN:
			player.update(Gdx.graphics.getDeltaTime(), myMap.getTiles());
			break;
			
		case PAUSE:
			
			break;
			
		case RESUME:
			
			break;

		default:
			break;
		}
		draw();
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
		
		new BitmapFont().draw(chickenRoadGame.getSpriteBatch(), "Fase "+fase, 100, 100);
		
		player.draw(chickenRoadGame.getSpriteBatch());
		drawState();
		
		chickenRoadGame.getSpriteBatch().end();
	
		positionCamera();
		orthographicCamera.update();
		chickenRoadGame.getSpriteBatch().setProjectionMatrix(orthographicCamera.combined);		
	}

	private void drawState() {
		
		//TODO desenhar alguma figura para indicar pause e poder retornar à fase ou sair dela.
		if(stateGame == StateGame.PAUSE)
			new BitmapFont().draw(chickenRoadGame.getSpriteBatch(), stateGame.toString(), 200, 200);
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
		stateGame = StateGame.RESUME;

	}

	@Override
	public void hide() {
		this.dispose();

	}

	@Override
	public void dispose() {
		player.getTexture().dispose();
		myMap.dispose();
	}

}
