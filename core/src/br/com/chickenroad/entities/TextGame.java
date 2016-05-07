package br.com.chickenroad.entities;

import br.com.chickenroad.animations.TextGameAnimation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TextGame extends Sprite{
	private TextGameAnimation textAnimation;

	public TextGame(String sprite, AssetManager assetManager, TextGameTypes text) {
		super(new Texture(sprite));

		this.textAnimation = new TextGameAnimation(sprite, assetManager, text);
	}


	public void init() {
		textAnimation.inicializar();
	}

	public void setPosition(float x, float y){
		textAnimation.setPosX(x);
		textAnimation.setPosY(y);
	}
	public void dispose() {
		getTexture().dispose();
	}




	@Override
	public void draw(Batch batch, float delta){
		super.draw(batch, delta);
		this.textAnimation.draw(batch, delta);	
	}

}
