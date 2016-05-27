package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.Constantes;
import br.com.chickenroad.entities.StateGame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayMenuButtons{

	private Sprite spritePause;
	private Sprite spritePlay;
	private Sprite spriteEggsQtd;
	private Sprite spriteCornQtd;

	//flag para desabilitar os botões
	private boolean flagDisable;


	public PlayMenuButtons(AssetManager assetManager) {

		Texture pauseFase = assetManager.get("pauseFaseButton.png");
		this.spritePause = new Sprite(new TextureRegion(pauseFase));
		this.spritePause.setScale(0.8f);

		Texture EggsQtd = assetManager.get(Constantes.URL_EGGS_SCORE);
		this.spriteEggsQtd = new Sprite(new TextureRegion(EggsQtd));
		this.spriteEggsQtd.setScale(0.8f);

		Texture CornQtd = assetManager.get(Constantes.URL_YELLOW_CORN_SCORE);
		this.spriteCornQtd = new Sprite(new TextureRegion(CornQtd));
		this.spriteCornQtd.setScale(0.8f);

		flagDisable = false;
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

	/**
	 * TODO rever as variaveis de entrada.
	 */
	public void draw(SpriteBatch spriteBatch, StateGame stateGame, int deltaXPositionButtons, int deltaYPositionButtons){

		if(!flagDisable){

			if(stateGame == StateGame.PLAYING){
				spritePause.setPosition(10+deltaXPositionButtons,  430+deltaYPositionButtons);
				spritePause.draw(spriteBatch);

			}

		}

		spriteEggsQtd.setPosition(525+deltaXPositionButtons,  420+deltaYPositionButtons);
		spriteCornQtd.setPosition(575+deltaXPositionButtons,  420+deltaYPositionButtons);
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
		this.spritePause = null;
		this.spritePlay = null;
		this.spriteEggsQtd = null;
		this.spriteCornQtd = null;
	}

}

