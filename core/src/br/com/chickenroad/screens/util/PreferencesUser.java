package br.com.chickenroad.screens.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesUser {

	private static Preferences preferences = Gdx.app.getPreferences("Preferences user");

	/**
	 * Método para apontar a fase atual do jogador. A medida que o jogador vai passando de fase, este m�todo � chamado
	 * para abrir a fase seguinte.
	 * @param fase
	 */
	public static void setFase(int fase){
		if( fase > preferences.getInteger("Fase", 0)){
			preferences.putInteger("Fase", fase);
			preferences.flush();
		}
	}
	public static int getFase(){
		return preferences.getInteger("Fase", 0);
	}
}
