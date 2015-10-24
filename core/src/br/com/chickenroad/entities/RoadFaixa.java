package br.com.chickenroad.entities;

import com.badlogic.gdx.math.Vector2;

public class RoadFaixa {

	private float speed;
	private Vector2 initialPoint;

	public RoadFaixa(float aSpeed, Vector2 aInitialoint){
		speed = aSpeed;
		initialPoint = aInitialoint;
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
}
