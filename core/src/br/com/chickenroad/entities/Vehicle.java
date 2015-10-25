package br.com.chickenroad.entities;


import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Veículos da estrada
 *
 */
public class Vehicle extends Sprite{

	private RoadFaixa roadFaixa;

	public Vehicle(String sprite, RoadFaixa aRoadFaixa){
		super(new Texture(sprite), Constantes.WIDTH_TILE, Constantes.HEIGHT_TILE);		
		roadFaixa = aRoadFaixa;
	}

	public void dispose(){
		getTexture().dispose();
	}

	public void walkX(){
		if(getX() < roadFaixa.getInitialPoint().x + roadFaixa.getWidth())
			setX(getX() + roadFaixa.getSpeed());
		else
			init(roadFaixa.getInitialPoint().x, getY());
	}

	public void init(float x, float y) {
		setPosition(x, y);
	}
}
