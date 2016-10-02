package br.com.chickenroad.entities.enums;

public enum DemageTypes {

	VEHICLE_DEMAGE(20),
	ENEMY_DEMAGE(10);
	
	public final int value;
	
	private DemageTypes(int aValue){
		this.value = aValue;
	}
}
