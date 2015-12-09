package br.com.chickenroad.entities;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import br.com.chickenroad.animations.VehicleAnimation;
import br.com.chickenroad.screens.util.Constantes;

/**
 * Veículos da estrada
 *
 */

public class Vehicle extends Sprite{
	private VehicleAnimation vehicleAnimation;
 	private RoadFaixa roadFaixa;

	public Vehicle(String sprite, RoadFaixa aRoadFaixa, AssetManager assetManager){
		super(new Texture(sprite));		
		roadFaixa = aRoadFaixa;
	
		this.vehicleAnimation = new VehicleAnimation(assetManager);
	}
		

	public void dispose(){
		getTexture().dispose();
	}

	public void walkX(){

		if(roadFaixa.getDirection() == Direction.RIGHT){
			
			vehicleAnimation.setSpriteSheet(Constantes.URL_YELLOW_CAR_LEFT, VehicleTypes.YELLOW_CAR_LEFT);
			
			if(getX() <= roadFaixa.getInitialPoint().x + roadFaixa.getWidth())
				setX(getX() + roadFaixa.getSpeed());
			else
				init(roadFaixa.getInitialPoint().x, getY());
		}else if(roadFaixa.getDirection() == Direction.LEFT){
			
			vehicleAnimation.setSpriteSheet(Constantes.URL_YELLOW_CAR_RIGHT, VehicleTypes.YELLOW_CAR_RIGHT);
			
			if(getX() >= roadFaixa.getInitialPoint().x)
				setX(getX() - roadFaixa.getSpeed());
			else
				init(roadFaixa.getWidth(), getY());

		}
	}

	public void init(float x, float y) {
		setPosition(x, y);
	}
	
	@Override
	public void draw(Batch batch, float delta){
		super.draw(batch, delta);
		this.vehicleAnimation.draw(batch, delta);	
	}
}
