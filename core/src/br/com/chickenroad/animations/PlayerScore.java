package br.com.chickenroad.animations;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PlayerScore {
	private static int score;
	public BitmapFont defaultFont;

	
	public PlayerScore() {
		this.score = 0;
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
	public static void setScore(int score) {
		PlayerScore.score = score;
	}
	
	public void draw(Batch batch, int deltaXPositionButtons, int deltaYPositionButtons){
		defaultFont.draw(batch, Integer.toString(score), 310+deltaXPositionButtons, 455+deltaYPositionButtons);
	}
}
