package br.com.chickenroad.entities;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.audio.Music;

public class MyMusic {
	private Music soundEggs, soundCorns, soundChickenDemage, soundBackgroundFase1, 
		soundCoinEndFase, soundEndFase, soundBackgroundChicken, soundSheep, soundMenuBackground;

	public MyMusic(ChickenRoadGame aChickenRoadGame){
		//sons	
		this.soundBackgroundChicken = aChickenRoadGame.getResourceManager().getAssetManager().get(Constantes.URL_SOUND_BACKGROUND_CHICKEN);
		this.soundEndFase =  aChickenRoadGame.getResourceManager().getAssetManager().get(Constantes.URL_SOUND_END_FASE);
		this.soundCoinEndFase =  aChickenRoadGame.getResourceManager().getAssetManager().get(Constantes. URL_SOUND_END_FASE_COIN);
		this.soundBackgroundFase1 = aChickenRoadGame.getResourceManager().getAssetManager().get(Constantes.URL_SOUND_BACKGROUND_FASE1);
		this.soundEggs = aChickenRoadGame.getResourceManager().getAssetManager().get(Constantes.URL_SOUND_EGGS);
		this.soundCorns = aChickenRoadGame.getResourceManager().getAssetManager().get(Constantes.URL_SOUND_CORNS);
		this.soundChickenDemage = aChickenRoadGame.getResourceManager().getAssetManager().get(Constantes.URL_SOUND_CHICKEN_DEMAGE);
		this.soundSheep = aChickenRoadGame.getResourceManager().getAssetManager().get(Constantes.URL_SOUND_SHEEP);
		this.soundMenuBackground = aChickenRoadGame.getResourceManager().getAssetManager().get(Constantes.URL_SOUND_MENU_BACKGROUND);
		
		soundBackgroundFase1.setVolume(0.2f);
		soundBackgroundChicken.setVolume(0.4f);
		soundMenuBackground.setVolume(0.3f);
	}

	public Music getSoundEggs() {
		return soundEggs;
	}

	public Music getSoundCorns() {
		return soundCorns;
	}

	public Music getSoundChickenDemage() {
		return soundChickenDemage;
	}

	public Music getSoundBackgroundFase1() {
		return soundBackgroundFase1;
	}

	public Music getSoundCoinEndFase() {
		return soundCoinEndFase;
	}

	public Music getSoundEndFase() {
		return soundEndFase;
	}

	public Music getSoundBackgroundChicken() {
		return soundBackgroundChicken;
	}

	public Music getSoundSheep() {
		return soundSheep;
	}
	
	public Music getSoundMenuBackground() {
		return soundMenuBackground;
	}
	
	public void dispose() {
		this.soundEggs.dispose();
		this.soundCorns.dispose();
		this.soundChickenDemage.dispose();
		this.soundBackgroundFase1.dispose();
		this.soundCoinEndFase.dispose();
		this.soundEndFase.dispose();
		this.soundBackgroundChicken.dispose();
		this.soundSheep.dispose();
		this.soundMenuBackground.dispose();
	}
}
