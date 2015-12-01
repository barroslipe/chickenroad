package br.com.chickenroad.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import br.com.chickenroad.animations.TargetPlayerAnimation;
import br.com.chickenroad.animations.TextGameAnimation;

public class TextGame extends Sprite{
	private TextGameAnimation textAnimation;

	public TextGame(String sprite, AssetManager assetManager, TextGameTypes text) {
		super(new Texture(sprite));

		this.textAnimation = new TextGameAnimation(sprite, assetManager, text);
	}


	public void inicializar() {
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
