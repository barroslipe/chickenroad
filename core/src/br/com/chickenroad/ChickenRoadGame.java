package br.com.chickenroad;

import br.com.chickenroad.screens.MainMenuScreen;
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
		
		//carregar os arquivos
		resourceManager = new ResourceManager();		
		resourceManager.load();
				
		//menu inicial da tela de abertura 
		if(!resourceManager.isLoaded())
			setScreen(new MainMenuScreen(this));	
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
