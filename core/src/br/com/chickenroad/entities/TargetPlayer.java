package br.com.chickenroad.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import br.com.chickenroad.animations.TargetPlayerAnimation;

public class TargetPlayer extends Sprite{
	private TargetPlayerAnimation targetPlayerAnimation;

	public TargetPlayer(String sprite, AssetManager assetManager) {
		super(new Texture(sprite));

		this.targetPlayerAnimation = new TargetPlayerAnimation(assetManager);
	}


	/*private boolean checkTilesColision(float newPositionX, float newPositionY, List<Rectangle> tiles) {

		Rectangle playerPosition = new Rectangle(newPositionX, newPositionY, Constantes.WIDTH_PLAYER, Constantes.HEIGHT_PLAYER);

		for(Rectangle object : tiles){
			if(Intersector.overlaps(object, playerPosition)){
				return true;
			}
		}

		return false;
	}*/

	public void inicializar() {
		setPosition(100, 220);
		targetPlayerAnimation.inicializar();
	}
	public void dispose() {
		getTexture().dispose();
	}


	@Override
	public void draw(Batch batch, float delta){
		//muda o texture region do sprite 
		this.setRegion(targetPlayerAnimation.getCurrentFrame());
		super.draw(batch);

	}

}
