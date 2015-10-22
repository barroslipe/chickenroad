package br.com.chickenroad.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Vehicle extends Sprite{
	
	private boolean start;
	private float speed;
	private Road road;
	
	public Vehicle(String sprite, Road aRoad){
		super(new Texture(sprite));		
		start = false;
		
		road = aRoad;
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
		if(getX() < road.getPoint().x + road.getWidth())
			setX(getX()+speed);
		else
			init(road.getPoint().x, getY());
	}
		
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void init(float x, float y) {
		setPosition(x, y);
	}

}
