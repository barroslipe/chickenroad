package br.com.chickenroad.screens.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * 
 * Guarda as informações da fase como origem do jogador e as estradas.
 *
 */

public class MyProperties {

	private Properties properties;
	private InputStream inputStream;
	private ArrayList<String> roads;
	private String originPlayer;

	public MyProperties(){
		properties = new Properties();

	}

	public void loadProperties(String file){
		try {
			
			FileHandle fileHandle = Gdx.files.internal(file);
			
			inputStream = fileHandle.read();

			properties.load(inputStream);
			
			getAll();


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void getAll() {
				
		roads = new ArrayList<String>();
		Set<Object> keys = properties.keySet();
		
		for(Object k : keys){
			String param = (String)k;
			
			if(param.equals("originPlayer"))
				originPlayer = properties.getProperty(param);
			else
				roads.add(properties.getProperty((String)k));
		}
	}

	public String getOriginPlayer() {
		return originPlayer;
	}
	
	public ArrayList<String> getRoads(){
		return roads;
	}
}
