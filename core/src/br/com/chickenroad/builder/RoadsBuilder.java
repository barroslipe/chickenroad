package br.com.chickenroad.builder;

import java.util.ArrayList;

import br.com.chickenroad.entities.RoadFaixa;
import br.com.chickenroad.entities.enums.Direction;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.MyProperties;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.math.Vector2;

public class RoadsBuilder {

	//Deslocando a origem da pista para esquerda, essa variavel vai 
	//evitar que os ve�culos vindos da esquerda para direita, surjam "do nada" na tela
	private static int DESLOC_INIT_X_ROAD = 200; 

	/**
	 * Capturar e salvar todas as estradas do mapa. Somente estradas HORIZONTAIS!!! Necessita de refatoração
	 */
	public static ArrayList<RoadFaixa> builder(MyProperties myProperties) {

		ArrayList<String> stringList = myProperties.getRoads();

		float initialPointX, initialPointY, width, height;
		int carsDistance;

		//largura dos carros - padrão sendo a largura do tile  
		int vehicleHeight = 64;

		String[] values;
		ArrayList<RoadFaixa> roadFaixaList = new ArrayList<RoadFaixa>();

		for (int i = 0; i < stringList.size(); i++) {

			values = stringList.get(i).split(",");

			//ponto inicial X da estrada
			initialPointX = Float.parseFloat(values[0])*Constantes.WIDTH_TILE - DESLOC_INIT_X_ROAD;
			//ponto inicial Y da estrada
			initialPointY = Float.parseFloat(values[1])*Constantes.HEIGHT_TILE;
			//comprimento da estrada - compensada com DESLOC_INIT_X_ROAD, devido a origem em X ser deslocada
			width = Float.parseFloat(values[2])*Constantes.WIDTH_TILE + DESLOC_INIT_X_ROAD;
			//largura da estrada
			height = (Float.parseFloat(values[3]))*Constantes.HEIGHT_TILE;

			//calcular o número de faixas para os carros
			int numeroFaixas = (int)Math.floor(height/vehicleHeight);

			for(int j=0;j<numeroFaixas;j++){

				//calcular a velocidade de cada faixa
				float speed = Util.getRandomPosition(5, 30)/10;

				//calcular distancia entre os carros
				//carsDistance = (int)Util.getRandomPosition(width/16, width/14);
				carsDistance = Constantes.DISTANCE_MIN_BETWEEN_VEHICLES;
				//cadastrar dados de cada faixa
				roadFaixaList.add(new RoadFaixa(speed, new Vector2( initialPointX , initialPointY + vehicleHeight*j), width ,carsDistance, (j%2 == 0 ? Direction.RIGHT : Direction.LEFT)));
			}
		}
		return roadFaixaList;
	}

}
