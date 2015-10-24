package br.com.chickenroad;

import br.com.chickenroad.screens.SplashScreen;
import br.com.chickenroad.screens.util.ResourceManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * 
 * Classe principal que inicia a lógica do jogo
 *
 */
public class ChickenRoadGame extends Game {

	private SpriteBatch spriteBatch;
	private ResourceManager resourceManager;

	@Override
	public void create () {

		this.spriteBatch = new SpriteBatch();
		this.resourceManager = new ResourceManager();

		Gdx.input.setCatchBackKey(true);

		this.resourceManager.load();

		setScreen(new SplashScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	public void dispose(){
		this.spriteBatch.dispose();
		this.spriteBatch = null;
		this.resourceManager.dispose();
	}
}
