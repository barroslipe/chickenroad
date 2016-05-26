package br.com.chickenroad.builder;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;

import br.com.chickenroad.Constantes;
import br.com.chickenroad.entities.ChickenNest;
import br.com.chickenroad.entities.MyMap;
import br.com.chickenroad.entities.TargetPlayer;
import br.com.chickenroad.entities.TargetPlayerTypes;
import br.com.chickenroad.screens.util.MyProperties;
import br.com.chickenroad.screens.util.Util;

public class EggsBuilder {

	public static ArrayList<TargetPlayer> builder(MyProperties myProperties, MyMap myMap, ChickenNest chickenNest, AssetManager assetManager, Random generator) {

		ArrayList<TargetPlayer> targetPlayerEggsList = new ArrayList<TargetPlayer>();
		
		Vector2 point;
		
		//gera ovos em posi��es aleatorios
		for(int i=0;i<Integer.parseInt(myProperties.getNumberEggs());i++){
			targetPlayerEggsList.add( new TargetPlayer(Constantes.URL_EGGS, assetManager,TargetPlayerTypes.EGGS, 1f/(float)generator.nextInt(5)));
			point = new Vector2(Util.getValidRandomPosition(myMap.getWidthTiledMap(), myMap.getHeightTiledMap(), myMap.getTiles(), chickenNest.getBoundingRectangle()));
			targetPlayerEggsList.get(i).init(point.x, point.y);
			targetPlayerEggsList.get(i).setVisible(true);
		}
		return targetPlayerEggsList;
	}

}
