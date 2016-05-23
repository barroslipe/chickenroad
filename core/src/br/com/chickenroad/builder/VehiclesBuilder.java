package br.com.chickenroad.builder;

import java.util.ArrayList;
import java.util.List;

import br.com.chickenroad.entities.Direction;
import br.com.chickenroad.entities.Road;
import br.com.chickenroad.entities.RoadFaixa;
import br.com.chickenroad.entities.Vehicle;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.assets.AssetManager;

public class VehiclesBuilder {

	//Deslocando a origem da pista para esquerda, essa variavel vai 
	//evitar que os ve�culos vindos da esquerda para direita, surjam "do nada" na tela
	private static int DESLOC_INIT_X_ROAD = 200;

	public static ArrayList<Vehicle> builder(List<Road> roadList, AssetManager assetManager) {

		//TODO verificar os objetos que estarão nas estradas
		//String[] pictures = {"veicules/veiculo1D.png", "veicules/veiculo1E.png"};

		ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();

		Vehicle vehicle;
		float positionY;

		//quantidade de estradas
		for(int i=0;i<roadList.size();i++){

			Road road = roadList.get(i);

			for(int j=road.getRoadFaixaList().size()-1;j>=0;j--){
				RoadFaixa faixa = road.getRoadFaixaList().get(j);

				positionY = faixa.getInitialPoint().y;
				float positionX =  faixa.getInitialPoint().x;
				do {
					vehicle = new Vehicle("veicules/"+faixa.getVeiculo()+(faixa.getDirection() == Direction.RIGHT ? "D":"E")+".png", faixa, assetManager);
					vehicle.init(positionX, positionY);
					vehicleList.add(vehicle);
					positionX += vehicle.getWidth() + faixa.getCarsDistance()*Util.getRandomPosition(1, 4)*Util.getRandomPosition(1);
				}
				while(positionX < faixa.getInitialPoint().x+road.getWidth() - DESLOC_INIT_X_ROAD);
			}
		}
		return vehicleList;
	}

}
