package br.com.chickenroad.entities;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

/**
 * Entidade que modela uma estrada
 * 
 *
 */
public class Road {

	private Vector2 initialPoint;
	private float width, height;
	private ArrayList<RoadFaixa> roadFaixaList;
	
	public Road(float pointX, float pointY, float width, float height, ArrayList<RoadFaixa> aRoadFaixaList){
		this.initialPoint = new Vector2(pointX, pointY);
		this.width = width;
		this.height = height;
		this.roadFaixaList = aRoadFaixaList;
	}

	public Vector2 getPoint() {
		return initialPoint;
	}
	
	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public ArrayList<RoadFaixa> getRoadFaixaList() {
		return roadFaixaList;
	}

	@Override
	public String toString(){
		return "Ponto (x,y) => ("+initialPoint.x+", "+initialPoint.y+") / width: "+width +" , height: "+height;
	}
}
