package br.com.chickenroad.entities;

import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * Modela o ninho da galinha - onde os ovos serão depositados
 */
public class ChickenNest extends Sprite{

	/**
	 * Inicialização dos atributos da classe
	 * @param assetManager
	 */
	public ChickenNest(AssetManager assetManager) {
		super((Texture) assetManager.get(Constantes.URL_CHICKENNEST));
	}

	/**
	 *  Verificar colisão entre o jogador e o ninho
	 * @param player jogador
	 * @return true caso haja colisão
	 * 		   false caso não haja colisão
	 */
	public boolean checkColision(Rectangle rectangle){

		if(Intersector.overlaps(getBoundingRectangle(), rectangle))
			return true;

		return false;
	}

	public void init(String[] points) {
		float posX = Float.parseFloat(points[0])*Constantes.WIDTH_TILE;
		float posY = Float.parseFloat(points[1])*Constantes.HEIGHT_TILE;
		setPosition(posX, posY);
	}
}
