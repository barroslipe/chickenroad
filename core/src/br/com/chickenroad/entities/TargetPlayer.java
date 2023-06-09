package br.com.chickenroad.entities;

import br.com.chickenroad.animations.TargetPlayerAnimation;
import br.com.chickenroad.entities.enums.TargetPlayerTypes;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;

public class TargetPlayer extends Sprite{

	/**
	 * Para apontar se o objeto deve estar visível no jogo
	 */
	private boolean visible;
	/**
	 * Apontar se o objeto pode ser manuseado
	 */
	private boolean locker;
	
	private TargetPlayerAnimation targetPlayerAnimation;

	public TargetPlayer(String sprite, AssetManager assetManager, TargetPlayerTypes targ, float velAnim) {
		super(new Texture(sprite));

		this.targetPlayerAnimation = new TargetPlayerAnimation(sprite, assetManager, targ, velAnim);
	}

	public boolean checkColision(Player player){
		
		if(locker || !visible) return false;

		//fixa o tamanho do boundbox do ovo para ser referente a um ovo 
		setBounds(getX(), getY(), Constantes.WIDTH_EGGS, Constantes.HEIGHT_EGGS);
		if(Intersector.overlaps(getBoundingRectangle(), player.getBoundingRectangle()))
			return true;
		return false;
	}

	public void init(float x, float y) {
		setPosition(x, y);
		targetPlayerAnimation.inicializar(x,y);
		visible=true;
		locker = false;
	}

	public void dispose() {
		getTexture().dispose();
	}

	@Override
	public void draw(Batch batch, float delta){

		if(visible){
			super.draw(batch, delta);
			this.targetPlayerAnimation.draw(batch, delta);	
		}
	}

	public void setVisible(boolean b) {
		this.visible = b;

	}

	public boolean isVisible() {
		return visible;
	}
	public boolean isLocker() {
		return locker;
	}

	public void setLocker(boolean b) {
		this.locker = b;
	}

}
