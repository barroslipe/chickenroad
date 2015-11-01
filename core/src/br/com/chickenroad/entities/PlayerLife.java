package br.com.chickenroad.entities;

import br.com.chickenroad.screens.Play;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.ResourceManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class PlayerLife{

	private ResourceManager resourceManager;

	private float life;

	private Sprite spriteLifeLevelFull, spriteLifeLevelHalf, spriteLifeLevelEmpty;

	public PlayerLife(ResourceManager resourceManager){
		life = 100;
		this.resourceManager = resourceManager;

		Texture lifeLevelFull = resourceManager.getAssetManager().get(Constantes.URL_PLAYER_LIFE_LEVEL_FULL);
		Texture lifeLevelHalf = resourceManager.getAssetManager().get(Constantes.URL_PLAYER_LIFE_LEVEL_HALF);
		Texture lifeLevelEmpty = resourceManager.getAssetManager().get(Constantes.URL_PLAYER_LIFE_LEVEL_EMPTY);

		this.spriteLifeLevelFull = new Sprite(new TextureRegion(lifeLevelFull));	
		this.spriteLifeLevelHalf = new Sprite(new TextureRegion(lifeLevelHalf));
		this.spriteLifeLevelEmpty = new Sprite(new TextureRegion(lifeLevelEmpty));	

	}


	public float getLife() {
		return life;
	}


	public void setLife(float life) {
		this.life = life;
	}

	public void demageVehicle(){
		life -= 50;
	}

	public void draw(Batch batch){

		if(life == 100){
			spriteLifeLevelFull.setPosition(10+Play.deltaXPositionButtons,  10+Play.deltaYPositionButtons);
			spriteLifeLevelFull.draw(batch);
		}else if(life == 50) {
			spriteLifeLevelHalf.setPosition(10+Play.deltaXPositionButtons,  10+Play.deltaYPositionButtons);
			spriteLifeLevelHalf.draw(batch);
		}else{
			spriteLifeLevelEmpty.setPosition(10+Play.deltaXPositionButtons,  10+Play.deltaYPositionButtons);
			spriteLifeLevelEmpty.draw(batch);
		}
	}


	public void init() {
		life = 100;		
	}

}
