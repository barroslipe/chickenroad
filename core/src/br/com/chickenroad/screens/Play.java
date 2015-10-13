package br.com.chickenroad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import br.com.chickenroad.entities.Player;

public class Play implements Screen {
	private TiledMap map; //guarda o mapa
	public OrthogonalTiledMapRenderer renderer; //guarda o renderizador do mapa
	public static OrthographicCamera camera;//cria camera
	private Player player;
	
	@Override
	public void show() {
		player = new Player(new Sprite(new Texture("player/chk1.png")));
		TmxMapLoader loader = new TmxMapLoader();
		map = loader.load("maps/desert.tmx");
		camera = new OrthographicCamera();
		renderer = new OrthogonalTiledMapRenderer(map);
		
		//permite que os eventos de entrada da aplicação vá para o Player tratar
		Gdx.input.setInputProcessor(player);
		
		//posicao inicial do player
		player.setX(map.getProperties().get("width", Integer.class)
				*(map.getProperties().get("tilewidth", Integer.class)/2)-400);
		player.setY(map.getProperties().get("height", Integer.class)
				*(map.getProperties().get("tileheight", Integer.class)/2)-500);
	}

	@Override
	public void render(float delta) {
		//metodo executado a cada frame
		//ajuda a apagar lixo da aplicação
		Gdx.gl.glClearColor(0, 0, 0, 1); //cor preta
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//renderiza camera e imagens
		renderer.setView(camera);
		renderer.render();
		
		player.batch.begin();
		player.draw(new SpriteBatch(1000));
		player.batch.end();
		
		//faz a camera seguir o player
		camera.position.set(player.getX(), player.getY(), 0);
		
		// camera .viewportWidth = tamanho da camera
		if(camera.position.x < camera.viewportWidth/2) {
			camera.position.x = camera.viewportWidth/2;
		}
		
		if(camera.position.y < camera.viewportHeight/2) {
			camera.position.y = camera.viewportHeight/2;
		}
	
		if(camera.position.x > (map.getProperties().get("width", Integer.class)
				*(map.getProperties().get("tilewidth", Integer.class))) - camera.viewportWidth/2) {
			camera.position.x = (map.getProperties().get("width", Integer.class)
					*(map.getProperties().get("tilewidth", Integer.class))) - camera.viewportWidth/2;
		}
		
		if(camera.position.y > (map.getProperties().get("height", Integer.class)
				*(map.getProperties().get("tileheight", Integer.class))) - camera.viewportHeight/2) {
			camera.position.y = (map.getProperties().get("height", Integer.class)
					*(map.getProperties().get("tileheight", Integer.class))) - camera.viewportHeight/2;
		}
		camera.update();
		
		//combina o movimento da camera com a do player
		player.batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void resize(int width, int height) {
	//chamado quando o usuario alterar o tamanho da janela
		camera.viewportWidth = width;
		camera.viewportHeight = height;

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
		map.dispose();
		renderer.dispose();
		player.getTexture().dispose();

	}

}
