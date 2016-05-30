package br.com.chickenroad.entities;


import br.com.chickenroad.entities.enums.Direction;
import br.com.chickenroad.entities.enums.VehicleTypes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * Veículos da estrada
 *
 */

public class Vehicle extends Sprite{

	private RoadFaixa roadFaixa;
	private VehicleTypes vehicleType;

	public Vehicle(String sprite, RoadFaixa aRoadFaixa, AssetManager assetManager){
		super((Texture) assetManager.get(sprite));
		
		this.roadFaixa = aRoadFaixa;
		//TODO para pegar o original
		setOrigin(0, 0);
		setScale(0.7f);
		
		//TODO verificar outra forma de atribuir tipo do veiculo
		vehicleType = (sprite.substring(9, sprite.length()-6).equalsIgnoreCase((VehicleTypes.MOTO).valor) ? VehicleTypes.MOTO : VehicleTypes.CAR);
	}

	public void runX(){

		if(roadFaixa.getDirection() == Direction.RIGHT){

			if(getX() <= roadFaixa.getInitialPoint().x + roadFaixa.getWidth())
				setX(getX() + roadFaixa.getSpeed());
			else
				setPosition(roadFaixa.getInitialPoint().x, getY());
			
		}else if(roadFaixa.getDirection() == Direction.LEFT){

			if(getX() >= roadFaixa.getInitialPoint().x)
				setX(getX() - roadFaixa.getSpeed());
			else
				setPosition(roadFaixa.getWidth(), getY());

		}
	}

	public Rectangle getCollisionBounds() {
		
		int diffX = 75;
		Rectangle rectangle = new Rectangle(getX(), getY(), this.getWidth() - diffX, this.getHeight());
		
		if(vehicleType == VehicleTypes.MOTO){
			rectangle.setHeight(rectangle.height/9);
		}else{
			rectangle.setHeight(rectangle.height/5);
		}
		return rectangle;
	};

	public boolean isNearToHork(Rectangle player) {

		final int DISTANCE_MAX = 40;
		final Rectangle vehicleBounds = getCollisionBounds();

		if(roadFaixa.getDirection() == Direction.RIGHT){

			if( (vehicleBounds.getX() + vehicleBounds.getWidth() < player.x 
					&& vehicleBounds.getX()+vehicleBounds.getWidth()+DISTANCE_MAX >= player.x)
					&& (vehicleBounds.getY() < player.y && player.y <= vehicleBounds.getY()+vehicleBounds.getHeight()) ){
				return true;
			}

		}else if(roadFaixa.getDirection() == Direction.LEFT){
			//adicionei 60 pois esse libgdx é louco!
			if( (vehicleBounds.getX() - DISTANCE_MAX - 60 <= player.x &&  player.x < vehicleBounds.getX())
					&& (vehicleBounds.getY() < player.y && player.y <= vehicleBounds.getY()+vehicleBounds.getHeight()) ){

				return true;
			}
		}

		return false;
	}

	public void dispose(){
		getTexture().dispose();
	}

}
