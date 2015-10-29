package br.com.chickenroad.screens.screenparts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PopupFinish {
	
	private Sprite next;
	private BitmapFont bitmapFont;

	public PopupFinish() {
		this.next = new Sprite(new Texture("popupYesButton.png"));
		this.next.setPosition(300, 300);
		this.bitmapFont = new BitmapFont();
	}
	
	
	public void draw(SpriteBatch spriteBatch){
		bitmapFont.draw(spriteBatch, "SUCESSO", 300, 300);
		next.draw(spriteBatch);
	}
	
	public boolean checkClickNextButton(float x, float y){
		if(next.getBoundingRectangle().contains(x, y)){
			return true;
		}
		return false;
	}
}
