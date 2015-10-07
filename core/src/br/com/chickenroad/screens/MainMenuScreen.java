package br.com.chickenroad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import br.com.chickenroad.ChickenRoadGame;

public class MainMenuScreen implements Screen {

	//TODO buscar uma maneira de trabalhar essas constantes
	private final int WIDTH = 700;
	private final int HEIGHT = 480;
	
	//TODO modificar as figuras
	private final String URL_BACKGROUND = "telaInicial.jpg";
	private final String URL_PLAY_BUTTON = "play.jpg";
	private final String URL_QUIT_BUTTON = "play.jpg";
	private final String URL_SOUND_BUTTON = "play.jpg";

	private ChickenRoadGame chickenRoadGame;
	
	//background
	private Texture textureBackground;
	private TextureRegion textureRegionBackground;
	private Sprite spriteBackground;
	
	//play button
	private Texture texturePlay;
	private TextureRegion textureRegionPlay;
	private Sprite spritePlay;
	
	//quit button
	private Texture textureQuit;
	private TextureRegion textureRegionQuit;
	private Sprite spriteQuit;
	
	//sound button
	private Texture textureSound;
	private TextureRegion textureRegionSound;
	private Sprite spriteSound;
	
	
	private OrthographicCamera orthographicCamera;
	
	public MainMenuScreen(ChickenRoadGame chickenRoadGame) {
		
		this.chickenRoadGame = chickenRoadGame;
		
		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false, WIDTH,HEIGHT);
		
		textureBackground = new Texture(URL_BACKGROUND);
		textureRegionBackground = new TextureRegion(textureBackground, 0,0,WIDTH,HEIGHT);
		spriteBackground = new Sprite(textureRegionBackground);
		
		texturePlay = new Texture(URL_PLAY_BUTTON);
		textureRegionPlay = new TextureRegion(texturePlay,0,0,100,100);
		spritePlay = new Sprite(textureRegionPlay);
		
		textureQuit = new Texture(URL_QUIT_BUTTON);
		textureRegionQuit = new TextureRegion(textureQuit,0,0,100,100);
		spriteQuit = new Sprite(textureRegionQuit);
		
		textureSound = new Texture(URL_SOUND_BUTTON);
		textureRegionSound = new TextureRegion(textureSound,0,0,100,100);
		spriteSound = new Sprite(textureRegionSound);
		
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
		drawMenuButtons();

		chickenRoadGame.getSpriteBatch().end();
		
		if(Gdx.input.justTouched()){
			
			Vector3 touchPoint = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			orthographicCamera.unproject(touchPoint);

			if(spriteQuit.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				Gdx.app.exit();
			}else if(spritePlay.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				//TODO
				System.out.println("Clicou no play");
			}else if(spriteSound.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				//TODO
				System.out.println("Clicou no som");
			}else{
				System.out.println("Não clicou nos botões");
			}
		}

	}
	
	private void drawMenuButtons() {
		
		spritePlay.setPosition(WIDTH/2-50, HEIGHT/2-100);
		spriteQuit.setPosition(WIDTH/2-50, HEIGHT/2-200);
		spriteSound.setPosition(0, 0);

		spritePlay.draw(chickenRoadGame.getSpriteBatch());
		spriteQuit.draw(chickenRoadGame.getSpriteBatch());
		spriteSound.draw(chickenRoadGame.getSpriteBatch());

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
		// TODO Auto-generated method stub
		
	}

}
