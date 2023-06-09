package br.com.chickenroad.builder;

import java.util.ArrayList;
import java.util.List;

import br.com.chickenroad.entities.Player;
import br.com.chickenroad.entities.RoadFaixa;
import br.com.chickenroad.entities.Vehicle;
import br.com.chickenroad.entities.enums.Direction;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;

public class VehiclesBuilder {

	private List<Vehicle> vehiclesList;

	//Deslocando a origem da pista para esquerda, essa variavel vai 
	//evitar que os ve�culos vindos da esquerda para direita, surjam "do nada" na tela
	private int DESLOC_INIT_X_ROAD = 200;

	public void init(List<RoadFaixa> roadFaixaList, AssetManager assetManager) {

		this.vehiclesList = builder(roadFaixaList, assetManager);

	}

	private ArrayList<Vehicle> builder(List<RoadFaixa> roadFaixaList, AssetManager assetManager) {

		ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();

		Vehicle vehicle;
		float positionY;


		for(int j=roadFaixaList.size()-1;j>=0;j--){
			RoadFaixa faixa = roadFaixaList.get(j);

			positionY = faixa.getInitialPoint().y;
			float positionX =  faixa.getInitialPoint().x;
			do {
				//escolher carro aleatoriamente
				String picture = (faixa.getDirection() == Direction.RIGHT ?
						Constantes.URL_VEHICLE_RIGHT[(int)Util.getRandomPosition(0, Constantes.URL_VEHICLE_LEFT.length)]:
							Constantes.URL_VEHICLE_LEFT[(int)Util.getRandomPosition(0, Constantes.URL_VEHICLE_LEFT.length)]); 

				vehicle = new Vehicle(picture, faixa, assetManager);
				vehicle.setPosition(positionX, positionY);
				vehicleList.add(vehicle);
				positionX += vehicle.getWidth() + faixa.getCarsBetweenDistance()*Util.getRandomPosition(1, 4)*Util.getRandomPosition(1);
			}
			while(positionX < faixa.getInitialPoint().x+faixa.getWidth() - DESLOC_INIT_X_ROAD);
		}
		return vehicleList;
	}


	public boolean testColissionVehicles(Player player) {

		for(int i=0;i<vehiclesList.size();i++){
			if(Intersector.overlaps(vehiclesList.get(i).getCollisionBounds(), player.getCollisionBounds())){
				return true;
			}
		}
		return false;
	}
	

	public List<Vehicle> getVehiclesList() {
		return vehiclesList;
	}



}
