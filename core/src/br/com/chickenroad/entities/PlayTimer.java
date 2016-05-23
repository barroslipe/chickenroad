package br.com.chickenroad.entities;

import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class PlayTimer {

	private BitmapFont defaultFont;
	private float timer;

	public PlayTimer(){

		this.defaultFont = new BitmapFont();

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constantes.URL_FONT_KRAASH_BLACK));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 10;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 2;
		defaultFont = generator.generateFont(parameter);

		generator.dispose();
	}


	public void draw(SpriteBatch spriteBatch, int deltaXPositionButtons, int deltaYPositionButtons) {

		//defaultFont.draw(spriteBatch, ""+timer, 100+deltaXPositionButtons, 100+deltaYPositionButtons);

	}


	public void setTimer(String timerGame) {
		if(timerGame != null && !timerGame.equals("")){
			timer = Float.parseFloat(timerGame);
		}

	}



}
