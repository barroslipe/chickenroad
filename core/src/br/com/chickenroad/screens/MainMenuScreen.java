package br.com.chickenroad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import br.com.chickenroad.ChickenRoadGame;

public class MainMenuScreen implements Screen {

	//TODO buscar uma maneira de trabalhar essas constantes
	private final int WIDTH = 700;
	private final int HEIGHT = 480;
	
	//TODO modificar a figura
	private final String URL_BACKGROUND = "telaInicial.jpg";
	
	private ChickenRoadGame chickenRoadGame;
	
	//background
	private Texture textureBackground;
	private TextureRegion textureRegionBackground;
	private Sprite spriteBackground;
	
	
	private OrthographicCamera orthographicCamera;
	
	public MainMenuScreen(ChickenRoadGame chickenRoadGame) {
		
		this.chickenRoadGame = chickenRoadGame;
		
		textureBackground = new Texture(URL_BACKGROUND);
		textureRegionBackground = new TextureRegion(textureBackground, 0,0,WIDTH,HEIGHT);
		spriteBackground = new Sprite(textureRegionBackground);

		
		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false, WIDTH,HEIGHT);
	
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		orthographicCamera.update();
		chickenRoadGame.getSpriteBatch().setProjectionMatrix(orthographicCamera.combined);
		
		chickenRoadGame.getSpriteBatch().begin();
		

		drawBackground();

		
		chickenRoadGame.getSpriteBatch().end();

	}

	private void drawBackground() {
		
		spriteBackground.draw(chickenRoadGame.getSpriteBatch());
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		
	

	}

}
