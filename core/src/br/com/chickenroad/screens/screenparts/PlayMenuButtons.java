package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.entities.StateGame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayMenuButtons{

	private Sprite spritePause;
	private Sprite spriteRestart;
	private Sprite spritePlay;
	private Sprite spriteFaseList;
	public int playerLifeLevel;

	/**
	 * @return the playerLifeLevel
	 */
	public int getPlayerLifeLevel() {
		return playerLifeLevel;
	}


	/**
	 * @param playerLifeLevel the playerLifeLevel to set
	 */
	public void setPlayerLifeLevel(int playerLifeLevel) {
		this.playerLifeLevel = playerLifeLevel;
	}


	public PlayMenuButtons(AssetManager assetManager) {

		Texture pauseFase = assetManager.get("pauseFaseButton.png");
		this.spritePause = new Sprite(new TextureRegion(pauseFase));

		Texture playFase = assetManager.get("playFaseButton.png");
		this.spritePlay = new Sprite(new TextureRegion(playFase));

		Texture restartFase = assetManager.get("restartFaseButton.png");
		this.spriteRestart = new Sprite(new TextureRegion(restartFase));

		Texture listFase = assetManager.get("listFaseButton.png");
		this.spriteFaseList = new Sprite(new TextureRegion(listFase));	


	}


	public boolean checkClickPlayButton(float x, float y) {

		if(spritePlay.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickPauseButton(float x, float y) {

		if(spritePause.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickRestartButton(float x, float y) {

		if(spriteRestart.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickFaseListButton(float x, float y) {

		if(spriteFaseList.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}

	/**
	 * TODO rever as variaveis de entrada.
	 */
	public void draw(SpriteBatch spriteBatch, StateGame stateGame, int deltaXPositionButtons, int deltaYPositionButtons){

		if(stateGame == StateGame.PLAYING){
			spritePause.setPosition(10+deltaXPositionButtons,  440+deltaYPositionButtons);
			spritePause.draw(spriteBatch);
		}else{
			spritePlay.setPosition(10+deltaXPositionButtons,  440+deltaYPositionButtons);
			spritePlay.draw(spriteBatch);
		}

		spriteRestart.setPosition(50+deltaXPositionButtons,  440+deltaYPositionButtons);
		spriteFaseList.setPosition(90+deltaXPositionButtons,  440+deltaYPositionButtons);

		spriteRestart.draw(spriteBatch);
		spriteFaseList.draw(spriteBatch);

	}


	public void dispose() {
		this.spriteFaseList = null;
		this.spritePause = null;
		this.spritePlay = null;
		this.spriteRestart = null;
	}

}

