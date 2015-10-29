package br.com.chickenroad.entities;

import com.badlogic.gdx.math.Vector2;

public class RoadFaixa {

	private float speed;
	private Vector2 initialPoint;
	private int carsDistance;
	private float width;
	
	private Direction direction;


	public RoadFaixa(float aSpeed, Vector2 aInitialoint, float aWidth ,int aCarsDistance, Direction aDirection){
		speed = aSpeed;
		initialPoint = aInitialoint;
		this.carsDistance = aCarsDistance;
		this.width = aWidth;
		this.direction = aDirection;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Vector2 getInitialPoint() {
		return initialPoint;
	}

	public void setInitialPoint(Vector2 initialPoint) {
		this.initialPoint = initialPoint;
	}

	public int getCarsDistance() {
		return carsDistance;
	}

	public float getWidth() {
		return width;
	}

	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction aDirection){
		this.direction = aDirection;
	}
}