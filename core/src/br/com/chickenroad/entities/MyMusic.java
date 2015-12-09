package br.com.chickenroad.entities;

import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

/**
 * Responsável por gerenciar todas as músicas da fase
 *
 */
public class MyMusic {

	private Music soundEggs,soundCorns, soundChickenDemage, soundBackgroundFase, 
	soundCoinEndFase, soundEndFase, soundBackgroundChicken, soundSheep;

	/**
	 * Inicialização dos atributos da classe
	 * @param assetManager referência a classe principal do jogo
	 */
	public MyMusic(AssetManager assetManager){

		this.soundBackgroundChicken = assetManager.get(Constantes.URL_SOUND_BACKGROUND_CHICKEN);
		this.soundEndFase =  assetManager.get(Constantes.URL_SOUND_END_FASE);
		this.soundCoinEndFase =  assetManager.get(Constantes. URL_SOUND_END_FASE_COIN);
		this.soundBackgroundFase = assetManager.get(Constantes.URL_SOUND_BACKGROUND_FASE1);
		this.soundEggs = assetManager.get(Constantes.URL_SOUND_EGGS);
		this.soundCorns = assetManager.get(Constantes.URL_SOUND_CORNS);
		this.soundChickenDemage = assetManager.get(Constantes.URL_SOUND_CHICKEN_DEMAGE);
		this.soundSheep = assetManager.get(Constantes.URL_SOUND_SHEEP);
	}
	/**
	 * Inicializar a classe atribuíndo volume e tocando alguns sons
	 */
	public void init() {
		this.soundBackgroundFase.setVolume(0.2f);
		this.soundBackgroundChicken.setVolume(0.4f);

		this.soundBackgroundFase.play();
		this.soundBackgroundChicken.play();
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

	public Music getSoundBackgroundFase() {
		return soundBackgroundFase;
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
}
