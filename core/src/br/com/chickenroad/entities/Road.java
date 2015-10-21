package br.com.chickenroad.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Entidade que modela uma estrada
 * 
 *
 */
public class Road {

	private Vector2 point;
	private float width, height;
	
	public Road(float pointX, float pointY, float width, float height){
		point = new Vector2(pointX, pointY);
		this.width = width;
		this.height = height;
	}

	public Vector2 getPoint() {
		return point;
	}
	
	@Override
	public String toString(){
		return "Ponto (x,y) => ("+point.x+", "+point.y+") / width: "+width +" , height: "+height;
	}
}
