package br.com.chickenroad.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;

import br.com.chickenroad.animations.TargetPlayerAnimation;
import br.com.chickenroad.screens.util.Constantes;

public class TargetPlayer extends Sprite{
	private TargetPlayerAnimation targetPlayerAnimation;

	public TargetPlayer(String sprite, AssetManager assetManager) {
		super(new Texture(sprite));

		this.targetPlayerAnimation = new TargetPlayerAnimation(sprite, assetManager, TargetPlayerTypes.EGGS);
	}

	public boolean checkColision(Player player){

		//fixa o tamanho do boundbox do ovo para ser referente a um ovo 
		setBounds(getX(), getY(), Constantes.WIDTH_EGGS, Constantes.HEIGHT_EGGS);
		if(Intersector.overlaps(getBoundingRectangle(), player.getBoundingRectangle()))
			return true;
		return false;
	}

	public void inicializar(float x, float y) {
		setPosition(x, y);
		targetPlayerAnimation.inicializar(x, y);
	}

	public void dispose() {
		getTexture().dispose();
	}

	@Override
	public void draw(Batch batch, float delta){
		super.draw(batch, delta);
		this.targetPlayerAnimation.draw(batch, delta);	
	}
	
}
