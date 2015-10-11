package br.com.chickenroad;

import br.com.chickenroad.screens.SplashScreen;
import br.com.chickenroad.screens.util.ResourceManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChickenRoadGame extends Game {
	
	private SpriteBatch spriteBatch;
	private BitmapFont bitmapFont;	
	private ResourceManager resourceManager;
		
	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		bitmapFont = new BitmapFont();
		resourceManager = new ResourceManager();
		
		//carregar os arquivos
		resourceManager.load();
		
		setScreen(new SplashScreen(this));
		
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void dispose(){
		spriteBatch.dispose();
		resourceManager.dispose();
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public BitmapFont getBitmapFont() {
		return bitmapFont;
	}

}
