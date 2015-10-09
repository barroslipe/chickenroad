package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {

	//TODO buscar uma maneira de trabalhar essas constantes
	private final int WIDTH_BACKGROUND = 893;
	private final int HEIGHT_BACKGROUND = 540;
	private final int HEIGHT_SOUNDOFF_BUTTON = 57;
	private final int WIDTH_SOUNDOFF_BUTTON = 72;
	private final int HEIGHT_SOUNDON_BUTTON = 57;
	private final int WIDTH_SOUNDON_BUTTON = 80;
	private final int HEIGHT_EXIT_BUTTON = 41;
	private final int WIDTH_EXIT_BUTTON = 96;
	private final int HEIGHT_PLAY_BUTTON = 35;
	private final int WIDTH_PLAY_BUTTON = 96;

	//TODO modificar as figuras
	private final String URL_BACKGROUND = "backgroundMenu.jpg";
	private final String URL_PLAY_BUTTON = "playButton.jpg";
	private final String URL_EXIT_BUTTON = "exitButton.jpg";
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
	private Texture textureExit;
	private TextureRegion textureRegionExit;
	private Sprite spriteExit;

	//sound on button
	private Texture textureSoundOn;
	private TextureRegion textureRegionSoundOn;
	private Sprite spriteSoundOn;

	//sound off button
	private Texture textureSoundOff;
	private TextureRegion textureRegionSoundOff;
	private Sprite spriteSoundOff;

	public static boolean soundOnFlag = true;

	private OrthographicCamera orthographicCamera;
	
	private Music soundMenuBackground, soundClick;


	public MainMenuScreen(ChickenRoadGame chickenRoadGame) {

		this.chickenRoadGame = chickenRoadGame;

		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false, WIDTH_BACKGROUND,HEIGHT_BACKGROUND);

		textureBackground = new Texture(URL_BACKGROUND);
		textureRegionBackground = new TextureRegion(textureBackground, 0,0,WIDTH_BACKGROUND,HEIGHT_BACKGROUND);
		spriteBackground = new Sprite(textureRegionBackground);

		texturePlay = new Texture(URL_PLAY_BUTTON);
		textureRegionPlay = new TextureRegion(texturePlay,0,0,WIDTH_PLAY_BUTTON,HEIGHT_PLAY_BUTTON);
		spritePlay = new Sprite(textureRegionPlay);

		textureExit = new Texture(URL_EXIT_BUTTON);
		textureRegionExit = new TextureRegion(textureExit,0,0,WIDTH_EXIT_BUTTON,HEIGHT_EXIT_BUTTON);
		spriteExit = new Sprite(textureRegionExit);

		textureSoundOn = new Texture(URL_SOUND_ON_BUTTON);
		textureRegionSoundOn = new TextureRegion(textureSoundOn,0,0,WIDTH_SOUNDON_BUTTON,HEIGHT_SOUNDON_BUTTON);
		spriteSoundOn = new Sprite(textureRegionSoundOn);

		textureSoundOff = new Texture(URL_SOUND_OFF_BUTTON);
		textureRegionSoundOff = new TextureRegion(textureSoundOff,0,0,WIDTH_SOUNDOFF_BUTTON,HEIGHT_SOUNDOFF_BUTTON);
		spriteSoundOff = new Sprite(textureRegionSoundOff);
		
		soundMenuBackground = Gdx.audio.newMusic(Gdx.files.internal("sounds/soundMenuBackground.mp3"));
		soundClick = Gdx.audio.newMusic(Gdx.files.internal("sounds/soundClick.mp3"));

	}

	@Override
	public void show() {
		
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

		if(!soundMenuBackground.isPlaying() && soundOnFlag) soundMenuBackground.play(); 

		input();
			
	}

	private void input() {

		if(Gdx.input.justTouched()){

			Vector3 touchPoint = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			orthographicCamera.unproject(touchPoint);

			if(spriteExit.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				soundClick.play();
				Gdx.app.exit();
			}else if(spritePlay.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				//TODO liberar tudo
				soundClick.play();
				chickenRoadGame.setScreen(new SeasonScreen(chickenRoadGame));
			}else if(spriteSoundOn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				//TODO tocar o som
				soundClick.play();
				soundOnFlag = !soundOnFlag;
				if(!soundOnFlag) soundMenuBackground.stop();
			}else{
			}
		}
		
	}

	private void drawMenuButtons() {
		
		final int positionXMenu = 48;
		final int positionYMenu = 60;
		

		spritePlay.setPosition(WIDTH_BACKGROUND/2-positionXMenu, HEIGHT_BACKGROUND/2);
		spriteExit.setPosition(WIDTH_BACKGROUND/2-positionXMenu, HEIGHT_BACKGROUND/2-positionYMenu);

		spritePlay.draw(chickenRoadGame.getSpriteBatch());
		spriteExit.draw(chickenRoadGame.getSpriteBatch());

		final int positionSound = 20;
		
		if(soundOnFlag){
			spriteSoundOn.setPosition(positionSound, positionSound);
			spriteSoundOn.draw(chickenRoadGame.getSpriteBatch());
		}else{
			spriteSoundOff.setPosition(positionSound, positionSound);
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

		soundMenuBackground.dispose();
	}

}
