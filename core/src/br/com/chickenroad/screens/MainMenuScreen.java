package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {

	private ChickenRoadGame chickenRoadGame;
	private AssetManager assetManager;


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

	private OrthographicCamera orthographicCamera;
	
	private Music soundMenuBackground, soundClick;

	public MainMenuScreen(ChickenRoadGame chickenRoadGame) {

		this.chickenRoadGame = chickenRoadGame;
		this.assetManager = chickenRoadGame.getResourceManager().getAssetManager();
		
		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false, Constantes.WIDTH_BACKGROUND,Constantes.HEIGHT_BACKGROUND);

		textureBackground = assetManager.get(Constantes.URL_BACKGROUND);
		textureRegionBackground = new TextureRegion(textureBackground, 0,0,Constantes.WIDTH_BACKGROUND,Constantes.HEIGHT_BACKGROUND);
		spriteBackground = new Sprite(textureRegionBackground);

		texturePlay = assetManager.get(Constantes.URL_PLAY_BUTTON);
		textureRegionPlay = new TextureRegion(texturePlay,0,0,Constantes.WIDTH_PLAY_BUTTON,Constantes.HEIGHT_PLAY_BUTTON);
		spritePlay = new Sprite(textureRegionPlay);

		textureExit = assetManager.get(Constantes.URL_EXIT_BUTTON);
		textureRegionExit = new TextureRegion(textureExit,0,0,Constantes.WIDTH_EXIT_BUTTON,Constantes.HEIGHT_EXIT_BUTTON);
		spriteExit = new Sprite(textureRegionExit);

		textureSoundOn = assetManager.get(Constantes.URL_SOUND_ON_BUTTON);
		textureRegionSoundOn = new TextureRegion(textureSoundOn,0,0,Constantes.WIDTH_SOUNDON_BUTTON,Constantes.HEIGHT_SOUNDON_BUTTON);
		spriteSoundOn = new Sprite(textureRegionSoundOn);

		textureSoundOff = assetManager.get(Constantes.URL_SOUND_OFF_BUTTON);
		textureRegionSoundOff = new TextureRegion(textureSoundOff,0,0,Constantes.WIDTH_SOUNDOFF_BUTTON,Constantes.HEIGHT_SOUNDOFF_BUTTON);
		spriteSoundOff = new Sprite(textureRegionSoundOff);
		
		soundMenuBackground = assetManager.get(Constantes.URL_SOUND_MENU_BACKGROUND);
		soundClick = assetManager.get(Constantes.URL_SOUND_CLICK);

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

		if(!soundMenuBackground.isPlaying() && Constantes.SOUND_ON_FLAG) soundMenuBackground.play(); 

		input();
			
	}

	private void input() {

		if(Gdx.input.justTouched()){

			Vector3 touchPoint = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			orthographicCamera.unproject(touchPoint);

			if(spriteExit.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				soundClick.play();
				dispose();
				Gdx.app.exit();
			}else if(spritePlay.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				//TODO liberar tudo
				soundClick.play();
				dispose();
				chickenRoadGame.setScreen(new SeasonScreen(chickenRoadGame));
			}else if(spriteSoundOn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				//TODO tocar o som
				soundClick.play();
				Constantes.SOUND_ON_FLAG = !Constantes.SOUND_ON_FLAG;
				if(!Constantes.SOUND_ON_FLAG) soundMenuBackground.stop();
			}else{
			}
		}
		
	}

	private void drawMenuButtons() {
		
		final int positionXMenu = 48;
		final int positionYMenu = 60;
		

		spritePlay.setPosition(Constantes.WIDTH_BACKGROUND/2-positionXMenu, Constantes.HEIGHT_BACKGROUND/2);
		spriteExit.setPosition(Constantes.WIDTH_BACKGROUND/2-positionXMenu, Constantes.HEIGHT_BACKGROUND/2-positionYMenu);

		spritePlay.draw(chickenRoadGame.getSpriteBatch());
		spriteExit.draw(chickenRoadGame.getSpriteBatch());

		final int positionSound = 20;
		
		if(Constantes.SOUND_ON_FLAG){
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

		//soundMenuBackground.dispose();
		soundMenuBackground.stop();
	}

}
