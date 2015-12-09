package br.com.chickenroad.animations;

import br.com.chickenroad.screens.PlayConfig;
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

	private BitmapFont defaultFont, scoreFont;


	public PlayerScore() {
		this.scoreGame = 0;
		this.currentNoCatchedEggs = 0;
		this.currentNoCatchedCorns = 0;
		
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

	public void inicializar(){

		scoreGame = 0;

		currentNoCatchedEggs = PlayConfig.numEggs;
		currentNoCatchedCorns = PlayConfig.numCorns;

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
		
		scoreFont.draw(batch, Integer.toString(scoreGame), 310+deltaXPositionButtons, 470+deltaYPositionButtons);

		if(currentNoCatchedCorns<10){
			defaultFont.draw(batch, Integer.toString(currentNoCatchedEggs), 561+deltaXPositionButtons, 442+deltaYPositionButtons);
			defaultFont.draw(batch, Integer.toString(currentNoCatchedCorns), 607+deltaXPositionButtons, 443+deltaYPositionButtons);
		} else {
			defaultFont.draw(batch, Integer.toString(currentNoCatchedEggs), 556+deltaXPositionButtons, 442+deltaYPositionButtons);
			defaultFont.draw(batch, Integer.toString(currentNoCatchedCorns), 604+deltaXPositionButtons, 443+deltaYPositionButtons);
		}
	}

	public void addScoreGame(int value) {
		this.scoreGame += value;
	}

	public void minusCurrentNoCatchedEggs() {
		this.currentNoCatchedEggs -=1;
	}

	public void minusCurrentNoCatchedCorn() {
		this.currentNoCatchedCorns -= 1;
	}
}
