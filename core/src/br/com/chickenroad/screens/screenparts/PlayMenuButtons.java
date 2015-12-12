package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.entities.StateGame;
import br.com.chickenroad.screens.util.Constantes;

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
	private Sprite spriteEggsQtd;
	private Sprite spriteCornQtd;
	
	public int playerLifeLevel;
	
	//flag para desabilitar os botões
	private boolean flagDisable;

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

		Texture EggsQtd = assetManager.get(Constantes.URL_EGGS_SCORE);
		this.spriteEggsQtd = new Sprite(new TextureRegion(EggsQtd));	

		Texture CornQtd = assetManager.get(Constantes.URL_YELLOW_CORN_SCORE);
		this.spriteCornQtd = new Sprite(new TextureRegion(CornQtd));	

		
		flagDisable = false;
	}


	public boolean checkClickPlayButton(float x, float y) {

		if(flagDisable) return false;

		
		if(spritePlay.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickPauseButton(float x, float y) {

		if(flagDisable) return false;

		
		if(spritePause.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickRestartButton(float x, float y) {

		if(flagDisable) return false;

		
		if(spriteRestart.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickFaseListButton(float x, float y) {

		if(flagDisable) return false;

		
		if(spriteFaseList.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	
	public boolean checkClickEggsQtdButton(float x, float y) {

		if(spriteEggsQtd.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickCornQtdButton(float x, float y) {

		
		if(spriteCornQtd.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	

	/**
	 * TODO rever as variaveis de entrada.
	 */
	public void draw(SpriteBatch spriteBatch, StateGame stateGame, int deltaXPositionButtons, int deltaYPositionButtons){

		if(stateGame == StateGame.PLAYING){
			spritePause.setPosition(10+deltaXPositionButtons,  430+deltaYPositionButtons);
			spritePause.draw(spriteBatch);
			
			
			
		}else{
			spritePlay.setPosition(10+deltaXPositionButtons,  430+deltaYPositionButtons);
			spritePlay.draw(spriteBatch);
		}

		spriteRestart.setPosition(60+deltaXPositionButtons,  430+deltaYPositionButtons);
		spriteFaseList.setPosition(110+deltaXPositionButtons,  430+deltaYPositionButtons);
		spriteEggsQtd.setPosition(525+deltaXPositionButtons,  420+deltaYPositionButtons);
		spriteCornQtd.setPosition(575+deltaXPositionButtons,  420+deltaYPositionButtons);

		
		spriteRestart.draw(spriteBatch);
		spriteFaseList.draw(spriteBatch);
		spriteEggsQtd.draw(spriteBatch);
		spriteCornQtd.draw(spriteBatch);

	}

	/**
	 * Desabilitar os botões
	 */
	public void disable() {
		flagDisable = true;
	}

	/**
	 * Habilitar os botões
	 */
	public void enable() {
		flagDisable = false;	
	}

	
	public void dispose() {
		this.spriteFaseList = null;
		this.spritePause = null;
		this.spritePlay = null;
		this.spriteRestart = null;
		this.spriteEggsQtd = null;
		this.spriteCornQtd = null;
	}

}

