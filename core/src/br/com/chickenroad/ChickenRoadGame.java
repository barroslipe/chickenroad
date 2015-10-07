package br.com.chickenroad;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import br.com.chickenroad.screens.MainMenuScreen;

public class ChickenRoadGame extends Game {
	
	private SpriteBatch spriteBatch;
	
	private BitmapFont bitmapFont;
		
	
	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		bitmapFont = new BitmapFont();
		
		//menu inicial da tela de abertura 
		setScreen(new MainMenuScreen(this));	
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void dispose(){
		spriteBatch.dispose();
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public BitmapFont getBitmapFont() {
		return bitmapFont;
	}

}
