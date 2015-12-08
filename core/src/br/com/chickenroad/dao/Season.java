package br.com.chickenroad.dao;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Modelar os dados de uma temporada na base de dados
 *
 */
public class Season implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * identificador da temporada
	 */
	private int id;
	
	/**
	 * Lista de fases da temporada
	 */
	private ArrayList<Fase> faseList;

	public Season(){
		
	}
	
	/**
	 * Criar uma temporada
	 * @param id identificador da temporada que será iniciada
	 */
	public Season(int id){
		this.id = id;
		this.faseList = new ArrayList<Fase>();

		//abrir a primeira fase da temporada sempre que iniciá-la
		this.faseList.add(new Fase(0));
	}

	public ArrayList<Fase> getFaseList() {
		return faseList;
	}

	public void setFaseList(ArrayList<Fase> faseList) {
		this.faseList = faseList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Calcular a potuação total da temporada
	 * @return potuação total da temporada
	 */
	public int getSeasonTotalScore(){

		int total = 0;

		for(Fase fase : faseList){
			total += fase.getScore();
		}

		return total;
	}
	/**
	 *  Calcular a pontuação dos presentes da temporada
	 * @returna pontuação total dos presentes
	 */
	public int getSeasonTotalGift(){

		int total = 0;

		for(Fase fase : faseList){
			total += fase.getGift();
		}

		return total;
	}

}
