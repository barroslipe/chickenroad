package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.Constantes;
import br.com.chickenroad.screens.PlayScreen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PopupPause {

	private Sprite spriteBackground;

	private Sprite spriteFaseList;
	private Sprite spriteRestart;
	private Sprite spritePlay;
	private Sprite spriteSoundOn;
	private Sprite spriteSoundOff;


	public PopupPause(AssetManager assetManager) {

		Texture texture = assetManager.get(Constantes.URL_PAUSE_POPUP);
		this.spriteBackground = new Sprite(texture);

		Texture listFase = assetManager.get("listFaseButton.png");
		this.spriteFaseList = new Sprite(listFase);
		this.spriteFaseList.setScale(0.8f);

		Texture playFase = assetManager.get(Constantes.URL_PLAY_BUTTON);
		this.spritePlay = new Sprite(playFase);
		this.spritePlay.setScale(0.8f);

		Texture restartFase = assetManager.get("restartFaseButton.png");
		this.spriteRestart = new Sprite(restartFase);
		this.spriteRestart.setScale(0.8f);

		Texture textureSoundOn = assetManager.get(Constantes.URL_SOUND_ON_BUTTON);
		this.spriteSoundOn = new Sprite(textureSoundOn);
		this.spriteSoundOn.setScale(0.8f);
		
		Texture textureSoundOff = assetManager.get(Constantes.URL_SOUND_OFF_BUTTON);
		this.spriteSoundOff = new Sprite(textureSoundOff);
		this.spriteSoundOff.setScale(0.8f);


	}
	public boolean checkClickRestartButton(float x, float y) {
		if(spriteRestart.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickPlayButton(float x, float y) {

		if(spritePlay.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickFaseListButton(float x, float y) {

		if(spriteFaseList.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickSoundOnButton(float x, float y) {

		if(spriteSoundOn.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	

	public void draw(SpriteBatch spriteBatch){

		spriteBackground.setPosition(Constantes.WORLD_WIDTH/2 - spriteBackground.getWidth()/2 + PlayScreen.deltaXPositionButtons,
				Constantes.WORLD_HEIGHT/2 - spriteBackground.getHeight()/2+PlayScreen.deltaYPositionButtons);		

		spriteSoundOn.setPosition(Constantes.WORLD_WIDTH/2 - 145 +PlayScreen.deltaXPositionButtons,  145+PlayScreen.deltaYPositionButtons);
		spriteSoundOff.setPosition(Constantes.WORLD_WIDTH/2 - 145 +PlayScreen.deltaXPositionButtons,  145+PlayScreen.deltaYPositionButtons);
		
		spriteFaseList.setPosition(Constantes.WORLD_WIDTH/2 - 150 +PlayScreen.deltaXPositionButtons,  35+PlayScreen.deltaYPositionButtons);

		spritePlay.setPosition(Constantes.WORLD_WIDTH/2  -50+ PlayScreen.deltaXPositionButtons,  35+PlayScreen.deltaYPositionButtons);

		spriteRestart.setPosition(Constantes.WORLD_WIDTH/2 +50 +PlayScreen.deltaXPositionButtons,  35+PlayScreen.deltaYPositionButtons);

		spriteBackground.draw(spriteBatch);

		spriteFaseList.draw(spriteBatch);
		spritePlay.draw(spriteBatch);
		spriteRestart.draw(spriteBatch);
		
		if(Constantes.SOUND_ON_FLAG){
			spriteSoundOn.draw(spriteBatch);
		}else{
			spriteSoundOff.draw(spriteBatch);
		}
	}
}
