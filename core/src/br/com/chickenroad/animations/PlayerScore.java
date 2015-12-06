package br.com.chickenroad.animations;

import br.com.chickenroad.screens.PlayConfig;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PlayerScore {
	
	
	public static final int CORN_SCORE = 100;//100 pontos  para cada milho pego
	public static final int EGGS_SCORE = 15;
	private int scoreGame;
	private int currentNoCatchedEggs;
	private int currentNoCatchedCorns;
	
	public BitmapFont defaultFont;

	
	public PlayerScore() {
		this.scoreGame = 0;
		this.currentNoCatchedEggs = 0;
		this.currentNoCatchedCorns = 0;

		this.defaultFont = new BitmapFont();
	/*	Usando FreeTypeFont
	 * FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Kraash Black.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 30;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 2;
		defaultFont = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to 
	*/
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
		defaultFont.draw(batch, Integer.toString(scoreGame), 310+deltaXPositionButtons, 455+deltaYPositionButtons);
		
		if(currentNoCatchedCorns<10){
			defaultFont.draw(batch, Integer.toString(currentNoCatchedEggs), 567+deltaXPositionButtons, 440+deltaYPositionButtons);
		    defaultFont.draw(batch, Integer.toString(currentNoCatchedCorns), 613+deltaXPositionButtons, 441+deltaYPositionButtons);
		} else {
			defaultFont.draw(batch, Integer.toString(currentNoCatchedEggs), 562+deltaXPositionButtons, 440+deltaYPositionButtons);
			defaultFont.draw(batch, Integer.toString(currentNoCatchedCorns), 610+deltaXPositionButtons, 441+deltaYPositionButtons);
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
