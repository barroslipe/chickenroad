package br.com.chickenroad.entities;

import br.com.chickenroad.screens.PlayScreen;
import br.com.chickenroad.screens.util.Constantes;

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
	private Skin skin;

	private Sprite spriteNormalLife, spriteDeadLife;
	private TextureRegionDrawable textureRegionDrawableBlue, textureRegionDrawableGreen, textureRegionDrawableRed;
	private ProgressBarStyle barStyle;

	private final int TOTAL_LIFE = 100;

	public PlayerLife(AssetManager assetManager){

		this.skin = new Skin();
		Pixmap pixmap = new Pixmap(18, 18, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		this.skin.add("white", new Texture(pixmap));

		this.barStyle = new ProgressBarStyle();
		barStyle.background = skin.newDrawable("white", Color.DARK_GRAY);		

		this.textureRegionDrawableBlue = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get(Constantes.URL_LIFE_BARS[0])));
		this.textureRegionDrawableGreen = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get(Constantes.URL_LIFE_BARS[1])));
		this.textureRegionDrawableRed = new TextureRegionDrawable(new TextureRegion((Texture)assetManager.get(Constantes.URL_LIFE_BARS[2])));

		this.life = 100;
		this.lifeProgressBar = new ProgressBar(0, life, 1, false, getProgresBarStyle(1));
		this.lifeProgressBar.setWidth(80);
		this.lifeProgressBar.setValue(life);

		Texture normalLife = assetManager.get(Constantes.URL_PLAYER_NORMAL_LIFE);
		Texture deadLife = assetManager.get(Constantes.URL_PLAYER_DEAD_LIFE);

		this.spriteNormalLife = new Sprite(normalLife);	
		this.spriteDeadLife = new Sprite(deadLife);

	}


	private ProgressBarStyle getProgresBarStyle(int option) {

		TextureRegionDrawable textureBar = null;
		if( option == 1)
			textureBar =  textureRegionDrawableBlue;
		else if(option == 2)
			textureBar =  textureRegionDrawableGreen;
		else if(option == 3)
			textureBar =  textureRegionDrawableRed;
		else
			textureBar = null;

		barStyle.knobBefore = textureBar;
		barStyle.knob=null;

		return barStyle;
	}


	public float getLife() {
		return life;
	}

	public void demageVehicle() {
		life -= 20;

		if(life < 0 ) life = 0; 

		lifeProgressBar.setValue(life);
	}

	public void draw(Batch batch){

		if(life <= TOTAL_LIFE && life >= 75){
			lifeProgressBar.setStyle(getProgresBarStyle(1));
			//spriteNormalLife.setPosition(10+PlayScreen.deltaXPositionButtons,  10+PlayScreen.deltaYPositionButtons);
			//spriteNormalLife.draw(batch);
		}else if(life > 35) {
			lifeProgressBar.setStyle(getProgresBarStyle(2));
			//spriteNormalLife.setPosition(10+PlayScreen.deltaXPositionButtons,  10+PlayScreen.deltaYPositionButtons);
			//spriteNormalLife.draw(batch);
		}else if(life > 0){
			lifeProgressBar.setStyle(getProgresBarStyle(3));
			//spriteDeadLife.setPosition(10+PlayScreen.deltaXPositionButtons,  10+PlayScreen.deltaYPositionButtons);
			//spriteDeadLife.draw(batch);
		}else
			lifeProgressBar.setStyle(getProgresBarStyle(4));

		
		this.lifeProgressBar.setPosition(430+PlayScreen.deltaXPositionButtons,  440+PlayScreen.deltaYPositionButtons);
		this.lifeProgressBar.draw(batch, 50);

	}


	public void init() {
		life = TOTAL_LIFE;
		lifeProgressBar.setValue(life);
	}

	public int calculateScoreLife(){

		int score;

		if(life == TOTAL_LIFE) score = 2000;
		else if(life >= 0.75*TOTAL_LIFE) score = 1000;
		else if( life >= 0.5*TOTAL_LIFE) score = 500;
		else score = 200;

		return score;
	}

}
