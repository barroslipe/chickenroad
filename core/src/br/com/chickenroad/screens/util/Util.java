package br.com.chickenroad.screens.util;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Util {
	
	private static Random random = new Random();
	
	public static float getRandomPosition(float min, float max){
		return random.nextInt((int)max - (int)min) + min;
	}
	public static float getRandomPosition(float min){
		return random.nextFloat() + min;
	}
	public static Vector2 getValidRandomPosition(int width, int height, List<Rectangle> tiles) {
		Vector2 point = new Vector2();

		boolean flag=true;
		//20 é o tamanho do ovo como padrao
		do{
			flag=true;
			int x = random.nextInt(width - 20);
			int y = random.nextInt(height - 20);
			point.x = x;
			point.y = y;
			Rectangle rect = new Rectangle(x, y, 20, 20);
			for(int i=0;i<tiles.size();i++){
				if(Intersector.overlaps(tiles.get(i), rect)){
					flag = false;
				}
			}
			
		}while(!flag);
		
		
		return point;
	}
}
