package br.com.chickenroad.entities;


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
		super(new Texture(sprite));
		roadFaixa = aRoadFaixa;
		setScale(0.7f);
		//verificar outra forma de atribuir tipo do veiculo
		vehicleType = (sprite.substring(9, sprite.length()-6).equalsIgnoreCase((VehicleTypes.MOTO).valor) ? VehicleTypes.MOTO : VehicleTypes.CAR);
	}

	public void walkX(){

		if(roadFaixa.getDirection() == Direction.RIGHT){

			if(getX() <= roadFaixa.getInitialPoint().x + roadFaixa.getWidth())
				setX(getX() + roadFaixa.getSpeed());
			else
				init(roadFaixa.getInitialPoint().x, getY());
		}else if(roadFaixa.getDirection() == Direction.LEFT){


			if(getX() >= roadFaixa.getInitialPoint().x)
				setX(getX() - roadFaixa.getSpeed());
			else
				init(roadFaixa.getWidth(), getY());

		}
	}

	public void init(float x, float y) {
		setPosition(x, y);
	}

	public Rectangle getBoundingRectangleColision() {
		Rectangle rectangle = new Rectangle();

		rectangle.set(super.getBoundingRectangle());

		if(vehicleType == VehicleTypes.MOTO){
			rectangle.setHeight(rectangle.height/6);
		}else{
			rectangle.setHeight(rectangle.height/3);
		}
		return rectangle;
	};

	public void dispose(){
		//getTexture().dispose();
	}

	public boolean isNearToHork(Rectangle bound) {

		int distance_max = 5;
		float altura = (vehicleType == VehicleTypes.MOTO ? getHeight()/6 : getHeight()/3);

		if(roadFaixa.getDirection() == Direction.RIGHT){

			if( (getX() + getWidth() < bound.x && getX()+getWidth()+distance_max >= bound.x)
					&& (getY() < bound.y && bound.y <= getY()+altura) ){
				return true;
			}

		}else if(roadFaixa.getDirection() == Direction.LEFT){
			//adicionei 60 pois esse libgdx é louco!
			if( (getX() - distance_max - 60 <= bound.x &&  bound.x < getX())
					&&
					(getY() < bound.y && bound.y <= getY()+altura) ){

				return true;
			}
		}

		return false;
	}

}
