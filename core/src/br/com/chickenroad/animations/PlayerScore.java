package br.com.chickenroad.animations;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PlayerScore {
	
	
	public static final int CORN_SCORE = 100;
	public static int EGGS_SCORE = 15;
	private int score;
	private int scoreEggs;
	private int scoreCorns;
	
	public BitmapFont defaultFont;

	
	public PlayerScore() {
		this.score = 0;
		this.scoreEggs = 0;
		this.scoreCorns = 0;

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
		score = 0;
		scoreEggs = 0;
		scoreCorns = 0;
		
		defaultFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);	
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScoreEggs() {
		return scoreEggs;
	}

	public void setScoreEggs(int scoreEggs) {
		this.scoreEggs = scoreEggs;
	}

	public int getScoreCorns() {
		return scoreCorns;
	}

	public void setScoreCorns(int scoreCorns) {
		this.scoreCorns = scoreCorns;
	}

	public void draw(Batch batch, int deltaXPositionButtons, int deltaYPositionButtons){
		defaultFont.draw(batch, Integer.toString(score), 310+deltaXPositionButtons, 455+deltaYPositionButtons);
		
		if(scoreCorns<10){
			defaultFont.draw(batch, Integer.toString(scoreEggs), 567+deltaXPositionButtons, 440+deltaYPositionButtons);
		    defaultFont.draw(batch, Integer.toString(scoreCorns), 613+deltaXPositionButtons, 441+deltaYPositionButtons);
		} else {
			defaultFont.draw(batch, Integer.toString(scoreEggs), 562+deltaXPositionButtons, 440+deltaYPositionButtons);
			defaultFont.draw(batch, Integer.toString(scoreCorns), 610+deltaXPositionButtons, 441+deltaYPositionButtons);
		}
}

	public void addScore(int value) {
		this.score += value;
		
	}

	public void minusScoreEggs() {
		this.scoreEggs -=1;
		
	}

	public void minusScoreCorn() {
		this.scoreCorns -= 1;
		
	}
}
