package br.com.chickenroad.entities;


import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Veículo da estrada
 * @author MacOs
 *
 */
public class Vehicle extends Sprite{
	
	private boolean start;	
	
	//TODO roadFaixa está dentro de road, rever isso
	private RoadFaixa roadFaixa;
	private Road road;
	
	public Vehicle(String sprite, RoadFaixa aRoadFaixa, Road aRoad){
		super(new Texture(sprite), Constantes.WIDTH_TILE, Constantes.HEIGHT_TILE);		
		
		road = aRoad;
		roadFaixa = aRoadFaixa;
		start = false;
	}
	
	public void dispose(){
		getTexture().dispose();
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}
	
	public void walkX(){
		if(getX() < roadFaixa.getInitialPoint().x + road.getWidth())
			setX(getX() + roadFaixa.getSpeed());
		else
			init(roadFaixa.getInitialPoint().x, getY());
	}
	
	public void init(float x, float y) {
		setPosition(x, y);
	}
}
