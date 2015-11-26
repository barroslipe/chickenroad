
/*
 * TargetPlayerAnimation.java
 * 
 * Um target � qualquer sprite que pode ser interessante para o player juntar pontos ou concluir tarefas: 
 * Ex. Ovos, moedas, alimento, ..etc
 * 
 * Natal, 23/11/2015
 * */
package br.com.chickenroad.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import br.com.chickenroad.entities.TargetPlayerTypes;
import br.com.chickenroad.screens.util.Constantes;

public class TargetPlayerAnimation{

	private Animation walkAnimation;         

	private Texture eggsSheet;
	private TextureRegion[] eggsFrames;            
	private TextureRegion currentFrame;                                                
	private TextureRegion[][] tmp;


	private TextureRegion[][] spriteSheetTmp1;
	private float stateTime;  
	private int index;
	private float posX, posY;

	public TargetPlayerAnimation(String sprite, AssetManager assetManager, TargetPlayerTypes eggs) {
		super();

		switch(eggs){
		case EGGS: //anima��o de ovos
			//player spritesheet animation
			eggsSheet = assetManager.get(sprite);
			
			tmp = TextureRegion.split(eggsSheet, Constantes.WIDTH_EGGS, Constantes.HEIGHT_EGGS);   
			spriteSheetTmp1 = TextureRegion.split(eggsSheet, Constantes.WIDTH_EGGS, Constantes.HEIGHT_EGGS);   
			
			eggsFrames = new TextureRegion[5]; 
		break;
		case COINS: //anima��o de moedas
		
		break;
		}
	}
	
	public void inicializar (float x, float y) {
		this.posX = x;
		this.posY = y;
		
		index = 0;
		
		for (int i = 0; i < 1; i++){
			for (int j = 0; j < tmp[0].length; j++){
				eggsFrames[index] = tmp[i][j];
				index++;
			}
		}
		walkAnimation = new Animation(1f/4f, eggsFrames);  
		stateTime = 0f;   
	}

	private void initSpriteSheet() {
		index = 0;

		for (int i = 0; i < 1; i++){
			for (int j = 0; j < tmp[0].length; j++){
				eggsFrames[index] = tmp[i][j];
				index++;
			}
		}
		walkAnimation = new Animation(1f/4f, eggsFrames);  
		stateTime = 0f;   
	}

	//seleciona o avatar para cada acao do player
	public void changeSpriteSheet(int ss) {

		switch(ss){
		case 1:
			tmp = spriteSheetTmp1;
			break;
		/*
		 * case 2: // para a anima��o de pegar o objeto
			tmp = spriteSheetTmp1;
			break;
		 * */
			
		default:
			break;
		}

		initSpriteSheet();
	}

	/**
	 * @return the currentFrame
	 */
	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}
	
	public void draw(Batch batch, float delta){
		stateTime += Gdx.graphics.getDeltaTime();          
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		
		batch.draw(currentFrame, posX, posY);
	}
}
