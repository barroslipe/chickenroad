
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
	private float velAnimation = 0;

	public TargetPlayerAnimation(String sprite, AssetManager assetManager, TargetPlayerTypes eggs, float velAnim) {
		super();

		switch(eggs){
		case EGGS: //anima��o de ovos
			//player spritesheet animation
			eggsSheet = assetManager.get(sprite);
			
			tmp = TextureRegion.split(eggsSheet, eggsSheet.getWidth()/5, eggsSheet.getHeight());   
			spriteSheetTmp1 = TextureRegion.split(eggsSheet, eggsSheet.getWidth()/5, eggsSheet.getHeight());   
			
			eggsFrames = new TextureRegion[5]; 
		
		velAnimation  = velAnim;
			break;
		case COINS: //anima��o de moedas
			velAnimation  = velAnim;
		break;

		case YELLOW_CORN: //anima��o de nilhos
			//player spritesheet animation
			eggsSheet = assetManager.get(sprite);
			
			tmp = TextureRegion.split(eggsSheet, eggsSheet.getWidth()/9, eggsSheet.getHeight());   
			spriteSheetTmp1 = TextureRegion.split(eggsSheet, eggsSheet.getWidth()/9, eggsSheet.getHeight());   
			
			eggsFrames = new TextureRegion[9]; 
			velAnimation  = velAnim;
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
		walkAnimation = new Animation(velAnimation, eggsFrames);  
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
		walkAnimation = new Animation(velAnimation, eggsFrames);  
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
