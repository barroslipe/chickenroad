package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {

	//TODO buscar uma maneira de trabalhar essas constantes
	private final int WIDTH = 893;
	private final int HEIGHT = 540;

	//TODO modificar as figuras
	private final String URL_BACKGROUND = "backgroundMenu.jpg";
	private final String URL_PLAY_BUTTON = "playButton.jpg";
	private final String URL_QUIT_BUTTON = "exitButton.jpg";
	private final String URL_SOUND_ON_BUTTON = "soundOnButton.jpg";
	private final String URL_SOUND_OFF_BUTTON = "soundOffButton.jpg";

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

	//sound on button
	private Texture textureSoundOn;
	private TextureRegion textureRegionSoundOn;
	private Sprite spriteSoundOn;

	//sound off button
	private Texture textureSoundOff;
	private TextureRegion textureRegionSoundOff;
	private Sprite spriteSoundOff;

	private boolean soundOnFlag = true;

	private OrthographicCamera orthographicCamera;

	public MainMenuScreen(ChickenRoadGame chickenRoadGame) {

		this.chickenRoadGame = chickenRoadGame;

		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false, WIDTH,HEIGHT);

		textureBackground = new Texture(URL_BACKGROUND);
		textureRegionBackground = new TextureRegion(textureBackground, 0,0,WIDTH,HEIGHT);
		spriteBackground = new Sprite(textureRegionBackground);

		texturePlay = new Texture(URL_PLAY_BUTTON);
		textureRegionPlay = new TextureRegion(texturePlay,0,0,96,35);
		spritePlay = new Sprite(textureRegionPlay);

		textureQuit = new Texture(URL_QUIT_BUTTON);
		textureRegionQuit = new TextureRegion(textureQuit,0,0,96,41);
		spriteQuit = new Sprite(textureRegionQuit);

		textureSoundOn = new Texture(URL_SOUND_ON_BUTTON);
		textureRegionSoundOn = new TextureRegion(textureSoundOn,0,0,80,57);
		spriteSoundOn = new Sprite(textureRegionSoundOn);

		textureSoundOff = new Texture(URL_SOUND_OFF_BUTTON);
		textureRegionSoundOff = new TextureRegion(textureSoundOff,0,0,72,57);
		spriteSoundOff = new Sprite(textureRegionSoundOff);


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
				//TODO liberar tudo
				dispose();
				chickenRoadGame.setScreen(new SeasonScreen(chickenRoadGame));
			}else if(spriteSoundOn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				//TODO tocar o som
				soundOnFlag = !soundOnFlag;
			}else{
			}
		}

	}

	private void drawMenuButtons() {

		spritePlay.setPosition(WIDTH/2-48, HEIGHT/2);
		spriteQuit.setPosition(WIDTH/2-48, HEIGHT/2-60);

		spritePlay.draw(chickenRoadGame.getSpriteBatch());
		spriteQuit.draw(chickenRoadGame.getSpriteBatch());

		if(soundOnFlag){
			spriteSoundOn.setPosition(20, 20);
			spriteSoundOn.draw(chickenRoadGame.getSpriteBatch());
		}else{
			spriteSoundOff.setPosition(20, 20);
			spriteSoundOff.draw(chickenRoadGame.getSpriteBatch());
		}
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
