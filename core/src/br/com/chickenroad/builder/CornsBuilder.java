package br.com.chickenroad.builder;

import java.util.ArrayList;

import br.com.chickenroad.entities.ChickenNest;
import br.com.chickenroad.entities.MyMap;
import br.com.chickenroad.entities.TargetPlayer;
import br.com.chickenroad.entities.TargetPlayerTypes;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.MyProperties;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;

public class CornsBuilder {

	public static  ArrayList<TargetPlayer> builder(MyProperties myProperties, MyMap myMap, ChickenNest chickenNest, AssetManager assetManager){ 

		ArrayList<TargetPlayer> targetPlayerCornsList = new ArrayList<TargetPlayer>();

		Vector2 point;

		//gera milhos em posições aleatorios
		for(int i=0;i<Integer.parseInt(myProperties.getNumberCorns());i++){
			targetPlayerCornsList.add(new TargetPlayer(Constantes.URL_YELLOW_CORN, assetManager,TargetPlayerTypes.YELLOW_CORN,1f/7f ));
			point = new Vector2(Util.getValidRandomPosition(myMap.getWidthTiledMap(), myMap.getHeightTiledMap(), myMap.getTiles(), chickenNest.getBoundingRectangle()));
			targetPlayerCornsList.get(i).init(point.x, point.y);
			targetPlayerCornsList.get(i).setVisible(true);
		}

		return targetPlayerCornsList;
	}

}
