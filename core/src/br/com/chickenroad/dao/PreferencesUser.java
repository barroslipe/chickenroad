package br.com.chickenroad.dao;

import java.util.ArrayList;

import br.com.chickenroad.configuration.ApplicationConfig;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

public class PreferencesUser {

	private static Preferences preferences = Gdx.app.getPreferences("Preferences user");
	static{
		saveInitialState();
	}

	/**
	 * Será executado a primeira vez na aplicação
	 */
	private static void saveInitialState(){

		//se já houver algo gravado, não deve adicionar nada
		if(preferences.getBoolean("STATUS")){
			return;
		}

		UserInformation userInformation = new UserInformation();
		save(userInformation);

	}

	public static UserInformation getUserInformation(){
		UserInformation userInformation = 
				new Json().fromJson(UserInformation.class, preferences.getString("INFORMATION", null));
		return userInformation;
	}

	/**
	 * 
	 * @return todas as temporadas e fases abertas
	 */
	public static ArrayList<Season> getSeasons(){
		return getUserInformation().getSeasonList();

	}

	public static ArrayList<Fase> getOpenFases(int seasonId){
		return getSeasons().get(seasonId).getFaseList();
	}

	public static void setSucesso(int seasonId, int faseId, int scoreGame) {

		UserInformation userInformation = getUserInformation();

		//verifico se o score atual da fase é maior que o já registrado, 
		if(scoreGame > userInformation.getSeasonList().get(seasonId).getFaseList().get(faseId).getScore())
			userInformation.getSeasonList().get(seasonId).getFaseList().get(faseId).setScore(scoreGame);

		//se for a ultima fase aberta, deve ser aberta a próxima fase com o sucesso
		if(faseId == (userInformation.getSeasonList().get(seasonId).getFaseList().size()-1)){

			//se estiver na ultima fase da sessão, deve ser aberto a próxima temporada
			if(faseId == (ApplicationConfig.FASES_PER_SEASON-1)){

				//caso não seja a ultima fase da ultima temporada
				if(seasonId < (ApplicationConfig.SEASON_PER_APPLICATION-1))
					userInformation.getSeasonList().add(new Season(seasonId+1));

			}else //abrir apenas a proxima fase da mesma temporada
				userInformation.getSeasonList().get(seasonId).getFaseList().add(new Fase(faseId+1));
		}

		save(userInformation);
	}

	private static void save(UserInformation userInformation) {
		String user = new Json().toJson(userInformation);
		preferences.putString("INFORMATION", user);
		preferences.putBoolean("STATUS",Boolean.TRUE);
		preferences.flush();
	}

}
