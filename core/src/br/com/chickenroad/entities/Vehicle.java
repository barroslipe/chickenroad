package br.com.chickenroad.entities;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * Veículos da estrada
 *
 */

public class Vehicle extends Sprite{
	//private VehicleAnimation vehicleAnimation;
	private RoadFaixa roadFaixa;

	public Vehicle(String sprite, RoadFaixa aRoadFaixa, AssetManager assetManager){
		super(new Texture(sprite));
		setScale(0.7f);
		roadFaixa = aRoadFaixa;

		//		this.vehicleAnimation = new VehicleAnimation(assetManager);
		//		
		//		if(aRoadFaixa.getDirection() == Direction.RIGHT)
		//			vehicleAnimation.setSpriteSheet(Constantes.URL_YELLOW_CAR_RIGHT[0], VehicleTypes.YELLOW_CAR_RIGHT);
		//		else
		//			vehicleAnimation.setSpriteSheet(Constantes.URL_YELLOW_CAR_LEFT[0], VehicleTypes.YELLOW_CAR_LEFT);

	}


	public void dispose(){
		//getTexture().dispose();
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
		//vehicleAnimation.inicializar(x, y);
	}
	
	public Rectangle getBoundingRectangleColision() {
		Rectangle rectangle = new Rectangle();

		rectangle.set(super.getBoundingRectangle());
		rectangle.setHeight(rectangle.height/2);

		return rectangle;
	};

	@Override
	public void draw(Batch batch, float delta){

		//	this.setRegion(vehicleAnimation.getCurrentFrame());

		super.draw(batch);
		//this.vehicleAnimation.draw(batch, delta);	
	}
}
