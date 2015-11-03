package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainMenuButtons {

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

	public MainMenuButtons(AssetManager assetManager) {

		this.texturePlay = assetManager.get(Constantes.URL_PLAY_BUTTON);
		this.textureRegionPlay = new TextureRegion(texturePlay,0,0,Constantes.WIDTH_PLAY_BUTTON,Constantes.HEIGHT_PLAY_BUTTON);
		this.spritePlay = new Sprite(textureRegionPlay);

		this.textureExit = assetManager.get(Constantes.URL_EXIT_BUTTON);
		this.textureRegionExit = new TextureRegion(textureExit,0,0,Constantes.WIDTH_EXIT_BUTTON,Constantes.HEIGHT_EXIT_BUTTON);
		this.spriteExit = new Sprite(textureRegionExit);

		this.textureSoundOn = assetManager.get(Constantes.URL_SOUND_ON_BUTTON);
		this.textureRegionSoundOn = new TextureRegion(textureSoundOn,0,0,Constantes.WIDTH_SOUNDON_BUTTON,Constantes.HEIGHT_SOUNDON_BUTTON);
		this.spriteSoundOn = new Sprite(textureRegionSoundOn);

		this.textureSoundOff = assetManager.get(Constantes.URL_SOUND_OFF_BUTTON);
		this.textureRegionSoundOff = new TextureRegion(textureSoundOff,0,0,Constantes.WIDTH_SOUNDOFF_BUTTON,Constantes.HEIGHT_SOUNDOFF_BUTTON);
		this.spriteSoundOff = new Sprite(textureRegionSoundOff);
	}

	public void draw(SpriteBatch spriteBatch){

		final int positionXMenu = 68;
		final int positionYMenu = 120;

		spritePlay.setPosition(Constantes.WORLD_WIDTH/2-positionXMenu, Constantes.WORLD_HEIGHT/2);
		spriteExit.setPosition(Constantes.WORLD_WIDTH/2-positionXMenu, Constantes.WORLD_HEIGHT/2-positionYMenu);

		spritePlay.draw(spriteBatch);
		spriteExit.draw(spriteBatch);

		final int positionSound = 20;

		if(Constantes.SOUND_ON_FLAG){
			spriteSoundOn.setPosition(positionSound, positionSound);
			spriteSoundOn.draw(spriteBatch);
		}else{
			spriteSoundOff.setPosition(positionSound, positionSound);
			spriteSoundOff.draw(spriteBatch);
		}
	}

	public boolean checkClickExitButton(float x, float y) {

		if(spriteExit.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickPlayButton(float x, float y) {

		if(spritePlay.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickSoundOnButton(float x, float y) {

		if(spriteSoundOn.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickSoundOffButton(float x, float y) {

		if(spriteSoundOff.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}

	public void dispose(){
		//TODO
	}

}
