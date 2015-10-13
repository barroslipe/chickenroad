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
		assetManager.load(Constantes.URL_EXIT_BUTTON, Texture.class);
		assetManager.load("fase1.png", Texture.class);
		assetManager.load("faseBloqueada.png", Texture.class);
		assetManager.load(Constantes.URL_PLAY_BUTTON, Texture.class);
		assetManager.load(Constantes.URL_SOUND_OFF_BUTTON, Texture.class);
		assetManager.load(Constantes.URL_SOUND_ON_BUTTON, Texture.class);
		
		//sons
		assetManager.load(Constantes.URL_SOUND_CLICK, Music.class);
		assetManager.load(Constantes.URL_SOUND_MENU_BACKGROUND, Music.class);
		
	}
	
	public boolean isLoaded(){
		
		while(!assetManager.update()){
			System.out.println("Carregando: "+assetManager.getProgress()*100 + "%");
		}
		return false;
	}
	
	public void dispose(){
		assetManager.dispose();
		assetManager = null;
	}
}
