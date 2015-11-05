package br.com.chickenroad.screens.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ScreenTransition {
	public float getDuration ();
	public void render (SpriteBatch spriteBatch, Texture currentScreen,Texture nextScreen, float alpha);
}
