package br.com.chickenroad.entities;

import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;


public class PlayerScore {

	public static final int CORN_SCORE = 100;//100 pontos  para cada milho pego
	public static final int EGGS_SCORE = 15;
	
	private int scoreGame;
	private int currentNoCatchedEggs;
	private int currentNoCatchedCorns;
	private String scoreGameDraw, currentNoCatchedEggsDraw, currentNoCatchedCornsDraw;

	private BitmapFont defaultFont, scoreFont;


	public PlayerScore() {

		this.defaultFont = new BitmapFont();
		//	Usando FreeTypeFont

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constantes.URL_FONT_KRAASH_BLACK));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 10;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 2;
		defaultFont = generator.generateFont(parameter);
		parameter.size = 20;
		scoreFont = generator.generateFont(parameter);
		generator.dispose(); // don't forget to dispose to 

	}

	public void init(int eggs, int corns){

		scoreGame = 0;
		scoreGameDraw = "0";

		currentNoCatchedEggs = eggs;
		currentNoCatchedEggsDraw = Integer.toString(eggs);
		
		currentNoCatchedCorns = corns;
		currentNoCatchedCornsDraw = Integer.toString(corns);

		defaultFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);	
	}
	/**
	 * @return the score
	 */
	public int getScoreGame() {
		return scoreGame;
	}
	/**
	 * @param score the score to set
	 */
	/**
	 * @param score the score to set
	 */
	public void setScoreGame(int score) {
		this.scoreGame = score;
	}

	public int getCurrentNoCatchedEggs() {
		return currentNoCatchedEggs;
	}

	public void setCurrentNoCatchedEggs(int scoreEggs) {
		this.currentNoCatchedEggs = scoreEggs;
	}

	public int getCurrentNoCatchedCorns() {
		return currentNoCatchedCorns;
	}

	public void setCurrentNoCatchedCorns(int scoreCorns) {
		this.currentNoCatchedCorns = scoreCorns;
	}

	public void draw(Batch batch, int deltaXPositionButtons, int deltaYPositionButtons){

		scoreFont.draw(batch, scoreGameDraw, 310+deltaXPositionButtons, 470+deltaYPositionButtons);

		if(currentNoCatchedCorns<10){
			defaultFont.draw(batch, currentNoCatchedEggsDraw, 561+deltaXPositionButtons, 442+deltaYPositionButtons);
			defaultFont.draw(batch, currentNoCatchedCornsDraw, 607+deltaXPositionButtons, 443+deltaYPositionButtons);
		} else {
			defaultFont.draw(batch, currentNoCatchedEggsDraw, 556+deltaXPositionButtons, 442+deltaYPositionButtons);
			defaultFont.draw(batch, currentNoCatchedCornsDraw, 604+deltaXPositionButtons, 443+deltaYPositionButtons);
		}
	}

	public void addScoreGame(int value) {
		this.scoreGame += value;
		this.scoreGameDraw = Integer.toString(scoreGame);

	}

	public void minusCurrentNoCatchedEggs() {
		this.currentNoCatchedEggs -=1;
		this.currentNoCatchedEggsDraw = Integer.toString(currentNoCatchedEggs);
	}

	public void minusCurrentNoCatchedCorn() {
		this.currentNoCatchedCorns -= 1;
		this.currentNoCatchedCornsDraw = Integer.toString(currentNoCatchedCorns);
	}
}
