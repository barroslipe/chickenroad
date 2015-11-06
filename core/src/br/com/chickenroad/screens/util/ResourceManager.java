package br.com.chickenroad.screens.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class ResourceManager {

	private AssetManager assetManager;

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public ResourceManager(){
		assetManager = new AssetManager();
	}

	public void load(){

		//texturas
		assetManager.load(Constantes.URL_BACK_BUTTON, Texture.class);
		assetManager.load(Constantes.URL_BACKGROUND, Texture.class);
		assetManager.load(Constantes.URL_BACKGROUND_SEASON, Texture.class);

		assetManager.load(Constantes.URL_EXIT_BUTTON, Texture.class);
		assetManager.load("fase1.png", Texture.class);
		assetManager.load("faseBloqueada.png", Texture.class);
		assetManager.load(Constantes.URL_PLAY_BUTTON, Texture.class);
		assetManager.load(Constantes.URL_SOUND_OFF_BUTTON, Texture.class);
		assetManager.load(Constantes.URL_SOUND_ON_BUTTON, Texture.class);

		assetManager.load(Constantes.URL_POPUP_YES_BUTTON, Texture.class);
		assetManager.load(Constantes.URL_POPUP_NO_BUTTON, Texture.class);
		assetManager.load(Constantes.URL_POPUP_EXIT_BACKGROUND, Texture.class);

		//sons
		assetManager.load(Constantes.URL_SOUND_CLICK, Music.class);
		assetManager.load(Constantes.URL_SOUND_MENU_BACKGROUND, Music.class);

		assetManager.load("pauseFaseButton.png", Texture.class);
		assetManager.load("playFaseButton.png", Texture.class);
		assetManager.load("restartFaseButton.png", Texture.class);
		assetManager.load("listFaseButton.png", Texture.class);
		
		//player
		assetManager.load(Constantes.URL_PLAYER_AVATAR_RIGHT, Texture.class);
		assetManager.load(Constantes.URL_PLAYER_AVATAR_LEFT, Texture.class);
		assetManager.load(Constantes.URL_PLAYER_AVATAR_JUMP_RIGHT, Texture.class);
		assetManager.load(Constantes.URL_PLAYER_AVATAR_JUMP_LEFT, Texture.class);
		assetManager.load(Constantes.URL_PLAYER_AVATAR_STOP_RIGHT, Texture.class);
		assetManager.load(Constantes.URL_PLAYER_AVATAR_STOP_LEFT, Texture.class);
		
		//life level player
		assetManager.load(Constantes.URL_PLAYER_LIFE_LEVEL_FULL, Texture.class);
		assetManager.load(Constantes.URL_PLAYER_LIFE_LEVEL_HALF, Texture.class);
		assetManager.load(Constantes.URL_PLAYER_LIFE_LEVEL_EMPTY, Texture.class);

	}

	public void dispose(){
		assetManager.dispose();
		assetManager = null;
	}
}
