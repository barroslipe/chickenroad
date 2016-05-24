package br.com.chickenroad.entities;

public enum VehicleTypes {
	
	MOTO("moto"),
	CAR("veiculo"),
	TRACTOR("trator");
	
	public String valor;
	
	private VehicleTypes(String veiculo) {
		this.valor = veiculo;
	}
	
}
