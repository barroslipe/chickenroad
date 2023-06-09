package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Componente base de tela que implementa uma screen(encapsula componentes de tela)
 *  e inputProcessor(captura dados do mouse e teclado) 
 * 
 */

public abstract class ScreenBase implements Screen, InputProcessor{

	/*
	 * Referência à tela principal 
	 */
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

	/*
	 * liberar recursos
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	public abstract void dispose();

	/**
	 * Habilita mouse e teclado
	 */
	public void enableInputProcessor(){
		Gdx.input.setInputProcessor(this);
	}

	/**
	 * Desabilita mouse e teclado
	 */
	public void disableInputProcessor(){
		Gdx.input.setInputProcessor(null);		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
