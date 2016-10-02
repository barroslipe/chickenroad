package br.com.chickenroad.builder;

import java.util.ArrayList;
import java.util.List;

import br.com.chickenroad.entities.Enemy;
import br.com.chickenroad.entities.MyMap;
import br.com.chickenroad.entities.Player;
import br.com.chickenroad.entities.enums.EnemyTypes;
import br.com.chickenroad.screens.util.MyProperties;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;

public class EnemiesBuilder {

	private List<Enemy> enemiesList;

	public void init(MyMap myMap, MyProperties myProperties,AssetManager assetManager) {

		this.enemiesList = new ArrayList<Enemy>();
		//TODO parametrizar do fase e qual figura
		if(myProperties.getEnemy() != null){
			float points[] = Util.getPoints(myProperties.getEnemy()); 

			Enemy fox = new Enemy(assetManager, "enemy/fox_sleep.png", EnemyTypes.FOX_SLEEP, 0.7f);
			fox.init(points[0], points[1]);
			this.enemiesList.add(fox);
		}

	}

	public void drawEnemies(SpriteBatch spriteBatch, float delta, Player player) {

		
		for(int i=0;i<enemiesList.size();i++){
			if(player.isNear(enemiesList.get(i), 200))
				enemiesList.get(i).draw(spriteBatch, delta, true);
			else
				enemiesList.get(i).draw(spriteBatch);
		}

	}

	public boolean testColissionEnemy(Player player) {
		for(int i=0;i<enemiesList.size();i++){
			if(Intersector.overlaps(enemiesList.get(i).getCollisionBounds(), player.getCollisionBounds())){
				return true;
			}			
		}
		return false;
	}

}
