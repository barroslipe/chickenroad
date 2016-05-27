package br.com.chickenroad.entities;

import br.com.chickenroad.Constantes;
import br.com.chickenroad.screens.PlayScreen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PlayerLife{

	private final int DEMAGE_VEHICLE = 20;
	private final int TOTAL_LIFE = 60;
	private final int lifeHalf = 10;
	private final int lifeFull = 20;

	private int life;

	private Sprite spriteFull, spriteHalf, spriteEmpty;

	public PlayerLife(AssetManager assetManager){

		this.spriteFull = new Sprite((Texture)assetManager.get(Constantes.URL_LIFE_FULL));
		this.spriteHalf = new Sprite((Texture)assetManager.get(Constantes.URL_LIFE_HALF));
		this.spriteEmpty = new Sprite((Texture)assetManager.get(Constantes.URL_LIFE_EMPTY));
	}

	public void init() {
		life = TOTAL_LIFE;
	}


	public float getLife() {
		return life;
	}

	public void demageVehicle() {
		life -= DEMAGE_VEHICLE;

		if(life < 0 ) life = 0; 

	}

	public void draw(SpriteBatch spriteBatch){

		int lifeLocal = life;

		for(int i=0;i<getTotalNumberHearts();i++){
			int result = lifeLocal%lifeFull;
			if(result == 0 && (lifeLocal - lifeFull >= 0)){
				lifeLocal -= lifeFull;
				spriteBatch.draw(spriteFull,60+PlayScreen.deltaXPositionButtons + i*30, 445+PlayScreen.deltaYPositionButtons);
			}else{
				result = lifeLocal%lifeHalf;
				if(result == 0 && (lifeLocal - lifeHalf >= 0)){
					lifeLocal -= lifeHalf;
					spriteBatch.draw(spriteHalf,60+PlayScreen.deltaXPositionButtons + i*30, 445+PlayScreen.deltaYPositionButtons);
				}else{
					spriteBatch.draw(spriteEmpty,60+PlayScreen.deltaXPositionButtons + i*30, 445+PlayScreen.deltaYPositionButtons);
				}
			}

		}

	}

	private int getTotalNumberHearts(){
		return TOTAL_LIFE/lifeFull;
	}


	public int calculateScoreLife(){

		int score;

		if(life == TOTAL_LIFE) score = 1000;
		else if(life >= 0.5*TOTAL_LIFE) score = 500;
		else score = 200;

		return score;
	}

}
