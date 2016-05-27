package br.com.chickenroad.entities;

import br.com.chickenroad.Constantes;
import br.com.chickenroad.screens.PlayScreen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PlayerLife{

	private final int DEMAGE_VEHICLE = 20;
	private final int TOTAL_LIFE = 60;
	private final int lifeHalf = 10;
	private final int lifeFull = 20;
	public static int MAX_SCORE_LIFE = 1500;

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

		Color color = spriteBatch.getColor();
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
					spriteBatch.setColor(color.r, color.g, color.b, .3f);
					spriteBatch.draw(spriteEmpty,60+PlayScreen.deltaXPositionButtons + i*30, 445+PlayScreen.deltaYPositionButtons);
				}
			}

		}
		spriteBatch.setColor(color.r, color.g, color.b, 1f);
	}

	private int getTotalNumberHearts(){
		return TOTAL_LIFE/lifeFull;
	}


	public int calculateScoreLife(){

		int score;

		if(life == TOTAL_LIFE) score = MAX_SCORE_LIFE;
		else if(life >= 0.75*TOTAL_LIFE) score = MAX_SCORE_LIFE/2;
		else score = MAX_SCORE_LIFE/5;

		return score;
	}

}
