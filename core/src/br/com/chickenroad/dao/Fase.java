package br.com.chickenroad.dao;

/**
 * Modelar os dados da fase na base de dados
 *
 */
public class Fase {

	/**
	 * identificador da fase
	 */
	private int id;
	
	/**
	 * score alcançado pelo jogador
	 */
	private int score;
	
	/**
	 * presentes alcançados pelo jogador
	 */
	private int gift;
	
	
	/**
	 * 
	 */
	public Fase(){
		
	}
	/**
	 * Criar uma fase com score zero
	 * @param id identificador da fase que será iniciada
	 */
	public Fase(int id){
		
		this.id = id;
		this.score = 0;
		this.gift = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getGift() {
		return gift;
	}

	public void setGift(int gift) {
		this.gift = gift;
	}
}
