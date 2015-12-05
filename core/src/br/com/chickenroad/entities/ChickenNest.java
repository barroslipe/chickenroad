package br.com.chickenroad.entities;

import br.com.chickenroad.screens.PlayConfig;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;

/*
 * Ninho da galinha - local onde os ovos ser�o depositados
 * */
public class ChickenNest extends Sprite{

	public ChickenNest(String url) {
		super(new Texture(url));
	}

	public boolean checkColision(Player player){

		if(Intersector.overlaps(getBoundingRectangle(), player.getBoundingRectangle()))
			return true;

		return false;
	}

	public void inicializar() {
		setPosition(PlayConfig.x, PlayConfig.y);
	}
}
