package br.com.chickenroad.dao;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Modelar as informações do usuário no banco de dados. 
 * 
 */
public class UserInformation implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Temporadas abertas pelo usuário
	 */
	private ArrayList<Season> seasonList;
	
	/*
	 * Criação inicial com apenas 1 temporada aberta - Padrão para o primeiro acesso
	 */
	public UserInformation(){
		this.seasonList = new ArrayList<Season>();
		
		//abrir a primeira temporada do jogo
		seasonList.add(new Season(0));
	}

	public ArrayList<Season> getSeasonList() {
		return seasonList;
	}

	public void setSeasonList(ArrayList<Season> seasonList) {
		this.seasonList = seasonList;
	}
}
