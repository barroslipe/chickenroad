package br.com.chickenroad.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;

import br.com.chickenroad.Constantes;
import br.com.chickenroad.animations.SupportingAnimation;

/*Refere-se aos personagens de segundo plano do cenario do game.
 *  Pessoas, animais, etc..
 * */
public class Supporting extends Sprite{
	private SupportingAnimation supportingAnimation;

	public Supporting(String sprite, AssetManager assetManager, SupportingTypes supp) {
		super(new Texture(sprite));

		this.supportingAnimation = new SupportingAnimation(sprite, assetManager, supp);
	}

	public boolean checkColision(Player player){

		//fixa o tamanho do boundbox do ovo para ser referente a um ovo 
		setBounds(getX(), getY(), Constantes.WIDTH_EGGS, Constantes.HEIGHT_EGGS);
		if(Intersector.overlaps(getBoundingRectangle(), player.getBoundingRectangle()))
			return true;
		return false;
	}

	public void init(float x, float y) {
		setPosition(x, y);
		supportingAnimation.inicializar(x, y);
	}

	public void dispose() {
		getTexture().dispose();
	}

	@Override
	public void draw(Batch batch, float delta){
		super.draw(batch, delta);
		this.supportingAnimation.draw(batch, delta);	
	}
	
}
