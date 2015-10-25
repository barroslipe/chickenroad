package br.com.chickenroad.screens.util;

import java.util.Random;

public class Util {
	
	private static Random random = new Random();
	
	public static float getRandomPosition(float min, float max){
		return random.nextInt((int)max - (int)min) + min;
	}
	public static float getRandomPosition(float min){
		return random.nextFloat() + min;
	}

}
