package br.com.chickenroad.screens.util;

import java.util.ArrayList;

import br.com.chickenroad.configuration.ApplicationConfig;
import br.com.chickenroad.dao.Fase;
import br.com.chickenroad.dao.Season;
import br.com.chickenroad.dao.UserInformation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

public class PreferencesUser {

	private static Preferences preferences = Gdx.app.getPreferences("Preferences user");
	static{
		saveStateInitial();
	}

	/**
	 * Será executado a primeira vez na aplicação
	 */
	private static void saveStateInitial(){

		System.err.println("INICIANDO PERSISTENCIA");

		//se já houver algo gravado, não deve adicionar nada
		if(preferences.getBoolean("STATUS")){
			System.err.println("NÃO SERÁ NECESÁRIO GRAVAR");
			return;
		}

		System.err.println("EXECUTANDO A PERSISTENCIA PELA PRIMEIRA VEZ");

		UserInformation userInformation = new UserInformation();
		save(userInformation);

	}
	/**
	 * 
	 * @return todas as temporadas e fases abertas
	 */
	public static ArrayList<Season> getSeasons(){
		return getUserInformation().getSeasonList();

	}
	public static UserInformation getUserInformation(){
		Json json = new Json();
		UserInformation userInformation = json.fromJson(UserInformation.class, preferences.getString("INFORMATION", null));
		return userInformation;
	}

	public static ArrayList<Fase> getFases(int seasonId){
		return getSeasons().get(seasonId).getFaseList();
	}

	public static void setSucesso(int seasonId, int faseId, int scoreGame) {

		System.err.println("GRAVANDO");
		
		UserInformation userInformation = getUserInformation();

		//verifico se o score atual da fase é maior que o já registrado, 
		//lembrando que o jogador pode jogar a mesma fase quantas vezes quiser
		if(scoreGame > userInformation.getSeasonList().get(seasonId).getFaseList().get(faseId).getScore()){
			userInformation.getSeasonList().get(seasonId).getFaseList().get(faseId).setScore(scoreGame);
			
			System.err.println("Modificando o score da fase atual");

		}else{
			System.err.println("Score da fase atual não modificado");

		}

		//se for a ultima fase aberta, deve ser aberta a próxima fase com o sucesso
		if(faseId == (userInformation.getSeasonList().get(seasonId).getFaseList().size()-1)){

			//se estiver na ultima fase da sessão, deve ser aberto a próxima temporada
			if(faseId == (ApplicationConfig.FASES_PER_SEASON-1)){

				//caso não seja a ultima fase da ultima temporada
				if(seasonId < (ApplicationConfig.SEASON_PER_APPLICATION-1)){ 
					userInformation.getSeasonList().add(new Season(seasonId+1));
					System.err.println("Criando uma temporada");
				}

			}else{ //abrir apenas a proxima fase da mesma temporada
				userInformation.getSeasonList().get(seasonId).getFaseList().add(new Fase(faseId+1));
				System.err.println("Abrindo uma fase da mesma temporada");

			}

		}

		save(userInformation);
	}
	private static void save(UserInformation userInformation) {

		Json json = new Json();

		String user = json.toJson(userInformation);
		preferences.putString("INFORMATION", user);
		preferences.putBoolean("STATUS",Boolean.TRUE);
		preferences.flush();
	}

}
