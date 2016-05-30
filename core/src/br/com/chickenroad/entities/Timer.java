package br.com.chickenroad.entities;

import br.com.chickenroad.entities.enums.StateGame;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Timer {

	public static final int MAX_SCORE_TIMER = 1000;
	private BitmapFont defaultFont;
	private float TOTAL_TIMER;
	private float timer;
	private boolean possuiTimer;

	public Timer(){

		this.defaultFont = new BitmapFont();
		this.possuiTimer = false;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constantes.URL_FONT_KRAASH_BLACK));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 17;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 2;
		defaultFont = generator.generateFont(parameter);

		generator.dispose();
	}

	public void draw(SpriteBatch spriteBatch, StateGame stateGame, float delta, int deltaXPositionButtons, int deltaYPositionButtons) {

		if(possuiTimer){
			if(stateGame == StateGame.PLAYING && timer > 0)
				timer -= delta;

			String minutos =  ((int)timer/60 > 10 ? ""+(int)timer/60 : "0"+ (int)timer/60);
			String segundos = ((int)timer%60 > 10 ? ""+(int)timer%60 : "0"+(int)timer%60);

			defaultFont.draw(spriteBatch, ""+minutos+":"+segundos, Constantes.WORLD_WIDTH/2 - 50+deltaXPositionButtons, 470+deltaYPositionButtons);
		}

	}

	public void setTimer(String timerGame) {
		if(timerGame != null && !timerGame.equals("")){
			timer = Float.parseFloat(timerGame)*60;
			TOTAL_TIMER = timer;
			possuiTimer = true;
		}
	}

	public float getTimer(){
		return timer;
	}


	public boolean possuiTimer() {
		return possuiTimer;
	}


	public int calculateScoreTimer() {

		if(!possuiTimer) return 0;

		float tempo_decorrido = TOTAL_TIMER - timer;
		if(0.3*TOTAL_TIMER >= tempo_decorrido)
			return MAX_SCORE_TIMER;
		else if(0.6*TOTAL_TIMER >= tempo_decorrido)
			return MAX_SCORE_TIMER/2;
		else
			return MAX_SCORE_TIMER/5;
	}

}
