package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.entities.Player;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Play implements Screen {

	private String url_map;

	private ChickenRoadGame chickenRoadGame;

	private TiledMap tiledMap; //guarda o mapa
	private int widthTiledMap, heightTiledMap;

	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer; //guarda o renderizador do mapa
	public static OrthographicCamera orthographicCamera;//cria camera
	private Player player;

	public Play(String aMap, ChickenRoadGame aChickenRoadGame) {
		this.url_map = aMap;
		this.chickenRoadGame = aChickenRoadGame;
	}

	@Override
	public void show() {

		tiledMap = new TmxMapLoader().load(url_map);
		orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		widthTiledMap = tiledMap.getProperties().get("width", Integer.class)*tiledMap.getProperties().get("tilewidth", Integer.class);
		heightTiledMap = tiledMap.getProperties().get("height", Integer.class)*tiledMap.getProperties().get("tileheight", Integer.class);

		orthographicCamera = new OrthographicCamera();

		//TODO qual o tamanho da camera?
		orthographicCamera.setToOrtho(false, 640,480);

		//TODO parametrizar para iniciar com outro personagem
		player = new Player(Constantes.URL_PLAYER_AVATAR, chickenRoadGame, widthTiledMap, heightTiledMap);

		//TODO parametrizar, o ponto de origem do jogador pode mudar de acordo com o mapa a ser apresentado
		player.setX(0);
		player.setY(0);

		Gdx.input.setInputProcessor(player);

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1); //cor preta
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//renderiza camera e imagens
		orthogonalTiledMapRenderer.setView(orthographicCamera);
		orthogonalTiledMapRenderer.render();

		//faz a camera seguir o player
		orthographicCamera.position.set(player.getX(), player.getY(), 0);


		chickenRoadGame.getSpriteBatch().begin();
		player.draw(chickenRoadGame.getSpriteBatch());
		chickenRoadGame.getSpriteBatch().end();

		positionCamera();

		orthographicCamera.update();
		chickenRoadGame.getSpriteBatch().setProjectionMatrix(orthographicCamera.combined);
	}

	private void positionCamera() {

		// camera .viewportWidth = tamanho da camera
		if(orthographicCamera.position.x < 320) {
			orthographicCamera.position.x = 320;
		}

		if(orthographicCamera.position.y < 240) {
			orthographicCamera.position.y = 240;
		}

		if(orthographicCamera.position.x > widthTiledMap-320) {
			orthographicCamera.position.x = widthTiledMap-320;
		}

		if(orthographicCamera.position.y > heightTiledMap-240) {
			orthographicCamera.position.y = heightTiledMap-240;
		}

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {


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
		tiledMap.dispose();
		orthogonalTiledMapRenderer.dispose();
		player.getTexture().dispose();

	}

}
