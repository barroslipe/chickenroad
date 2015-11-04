package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public abstract class ScreenBase implements Screen{

	protected ChickenRoadGame chickenRoadGame;
	
	public ScreenBase(ChickenRoadGame chickenRoadGame) {
		this.chickenRoadGame = chickenRoadGame;
	}
	public AssetManager getAssetManager(){
		return chickenRoadGame.getResourceManager().getAssetManager();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public abstract void render(float delta);

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
