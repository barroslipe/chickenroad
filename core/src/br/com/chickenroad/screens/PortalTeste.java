package br.com.chickenroad.screens;

import br.com.chickenroad.entities.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;

public class PortalTeste extends Sprite{

	public PortalTeste(String url) {
		super(new Texture(url));
		setPosition(300, 100);

	}

	
	
	public boolean checkColision(Player player){
		if(Intersector.overlaps(getBoundingRectangle(), player.getBoundingRectangle()))
			return true;
		
		return false;
	}
}
