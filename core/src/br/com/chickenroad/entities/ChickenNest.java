package br.com.chickenroad.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;

/*
 * Ninho da galinha - local onde os ovos serão depositados
 * */
public class ChickenNest extends Sprite{
	
	public ChickenNest(String url) {
		// TODO Auto-generated constructor stub
		super(new Texture(url));
	}
	
	public boolean checkColision(Player player){
		if(Intersector.overlaps(getBoundingRectangle(), player.getBoundingRectangle()))
			return true;
		
		return false;
	}
}
