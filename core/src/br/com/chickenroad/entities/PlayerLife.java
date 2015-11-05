package br.com.chickenroad.entities;

import br.com.chickenroad.screens.Play;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class PlayerLife{

	private float life;
	private ProgressBar lifeProgressBar;

	private Sprite spriteLifeLevelFull, spriteLifeLevelHalf, spriteLifeLevelEmpty;

	public PlayerLife(AssetManager assetManager){

		this.life = 100;
		this.lifeProgressBar = new ProgressBar(0, life, 1, false, getProgresBarStyle(1));
		this.lifeProgressBar.setWidth(80);
		this.lifeProgressBar.setValue(life);

		Texture lifeLevelFull = assetManager.get(Constantes.URL_PLAYER_LIFE_LEVEL_FULL);
		Texture lifeLevelHalf = assetManager.get(Constantes.URL_PLAYER_LIFE_LEVEL_HALF);
		Texture lifeLevelEmpty = assetManager.get(Constantes.URL_PLAYER_LIFE_LEVEL_EMPTY);

		this.spriteLifeLevelFull = new Sprite(lifeLevelFull);	
		this.spriteLifeLevelHalf = new Sprite(lifeLevelHalf);
		this.spriteLifeLevelEmpty = new Sprite(lifeLevelEmpty);	

	}


	private ProgressBarStyle getProgresBarStyle(int option) {

		Skin skin = new Skin();
		
		Pixmap pixmap = new Pixmap(18, 18, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		
		skin.add("white", new Texture(pixmap));

		TextureRegionDrawable textureBar = null;
		if( option == 1)
			textureBar =  new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("barBlue_horizontalBlue.png"))));
		else if(option == 2)
			textureBar =  new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("barGreen_horizontalMid.png"))));
		else
			textureBar =  new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("barRed_horizontalMid.png"))));

		ProgressBarStyle barStyle = new ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), textureBar);
		barStyle.knobBefore = barStyle.knob;
		barStyle.knob=null;
//		barStyle.knobAfter=null;

		return barStyle;
	}


	public float getLife() {
		return life;
	}

	public void demageVehicle(){
		life -= 20;
		
		if(life < 0 ) life = 0; 
		
		lifeProgressBar.setValue(life);
	}

	public void draw(Batch batch){

		if(life <= 100 && life >= 75){
			lifeProgressBar.setStyle(getProgresBarStyle(1));
			spriteLifeLevelFull.setPosition(10+Play.deltaXPositionButtons,  10+Play.deltaYPositionButtons);
			spriteLifeLevelFull.draw(batch);
		}else if(life <  75 && life > 35) {
			lifeProgressBar.setStyle(getProgresBarStyle(2));
			spriteLifeLevelHalf.setPosition(10+Play.deltaXPositionButtons,  10+Play.deltaYPositionButtons);
			spriteLifeLevelHalf.draw(batch);
		}else{
			lifeProgressBar.setStyle(getProgresBarStyle(3));
			spriteLifeLevelEmpty.setPosition(10+Play.deltaXPositionButtons,  10+Play.deltaYPositionButtons);
			spriteLifeLevelEmpty.draw(batch);
		}

		this.lifeProgressBar.setPosition(46+Play.deltaXPositionButtons,  15+Play.deltaYPositionButtons);
		this.lifeProgressBar.draw(batch, 50);


	}


	public void init() {
		life = 100;
		lifeProgressBar.setValue(life);
	}

}
