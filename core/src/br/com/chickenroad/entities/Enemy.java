package br.com.chickenroad.entities;

import br.com.chickenroad.animations.EnemyAnimation;
import br.com.chickenroad.entities.enums.EnemyTypes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends Sprite{

	private EnemyAnimation enemyAnimation;

	public Enemy(AssetManager assetManager, String sprite, EnemyTypes enemyType, float velAnim ){
		super((Texture)assetManager.get("enemy/raposa1.png"));

		this.enemyAnimation = new EnemyAnimation(sprite, assetManager, enemyType, velAnim);
	}

	public Rectangle getCollisionBounds(){

		float diffX = 4;
		float diffY = 2;

		//diminuição do limite de colisão do inimigo
		Rectangle enemyCollisionBounds = new Rectangle(this.getX() + diffX, this.getY()+diffY,
				this.getWidth()-diffX, this.getHeight()/4);

		return enemyCollisionBounds;
	}


	public void draw(Batch batch, float delta, boolean animated){

		super.draw(batch, delta);
		if(animated){
			this.enemyAnimation.draw(batch, delta);
		}
	}
	public void draw(Batch batch){


		super.draw(batch);

	}

	public void init(float x, float y) {
		setPosition(x, y);
		this.enemyAnimation.inicializar(x, y);
	}

	public void dispose() {
		getTexture().dispose();
	}

}
