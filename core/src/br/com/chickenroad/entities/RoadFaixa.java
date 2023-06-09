package br.com.chickenroad.entities;

import br.com.chickenroad.entities.enums.Direction;

import com.badlogic.gdx.math.Vector2;

public class RoadFaixa {

	private float speed;
	private Vector2 initialPoint;
	private int carsBetweenDistance;
	private float width;
	private Direction direction;

	public RoadFaixa(float aSpeed, Vector2 aInitialoint, float aWidth ,int aCarsBetweenDistance,Direction aDirection){
		this.speed = aSpeed;
		this.initialPoint = aInitialoint;
		this.carsBetweenDistance = aCarsBetweenDistance;
		this.width = aWidth;
		this.direction = aDirection;
	}

	public float getSpeed() {
		return speed;
	}

	public Vector2 getInitialPoint() {
		return initialPoint;
	}

	public int getCarsBetweenDistance() {
		return carsBetweenDistance;
	}

	public float getWidth() {
		return width;
	}

	public Direction getDirection() {
		return direction;
	}

}
