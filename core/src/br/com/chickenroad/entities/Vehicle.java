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

	public Vehicle(String sprite, RoadFaixa aRoadFaixa, AssetManager assetManager){
		super(new Texture(sprite));
		roadFaixa = aRoadFaixa;
		setScale(0.7f);
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
		rectangle.setHeight(rectangle.height/2);

		return rectangle;
	};

	public void dispose(){
		//getTexture().dispose();
	}

}
