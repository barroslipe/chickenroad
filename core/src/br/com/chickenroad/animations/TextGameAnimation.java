
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

import br.com.chickenroad.entities.TextGameTypes;

public class TextGameAnimation{

	private Animation walkAnimation;         

	private Texture textSheet;
	private TextureRegion[] textFrames;            
	private TextureRegion currentFrame;                                                
	private TextureRegion[][] tmp;


	private TextureRegion[][] spriteSheetTmp1;
	private float stateTime;  
	private int index;
	private float posX=0, posY=0;
	private float velAnimation=0;

	

	public TextGameAnimation(String sprite, AssetManager assetManager, TextGameTypes text) {
		super();

		switch (text) {
			case AMAZING:
				//player spritesheet animation
				textSheet = assetManager.get(sprite);
				
				tmp = TextureRegion.split(textSheet, textSheet.getWidth()/4, textSheet.getHeight());   
				spriteSheetTmp1 = TextureRegion.split(textSheet, textSheet.getWidth()/4, textSheet.getHeight());   
				
				textFrames = new TextureRegion[4]; 
				velAnimation = 1f/4f;
			break;
			
			case POW:
				//player spritesheet animation
				textSheet = assetManager.get(sprite);
				
				tmp = TextureRegion.split(textSheet, textSheet.getWidth()/5, textSheet.getHeight());   
				spriteSheetTmp1 = TextureRegion.split(textSheet, textSheet.getWidth()/5, textSheet.getHeight());   
				
				textFrames = new TextureRegion[5]; 
				velAnimation = 1f/5f;
			break;
			
			case PLUS15:
				//player spritesheet animation
				textSheet = assetManager.get(sprite);
				
				tmp = TextureRegion.split(textSheet, textSheet.getWidth()/5, textSheet.getHeight());   
				spriteSheetTmp1 = TextureRegion.split(textSheet, textSheet.getWidth()/5, textSheet.getHeight());   
				
				textFrames = new TextureRegion[5]; 
				velAnimation = 1f/6f;
			break;
			
			case PLUS100:
				//player spritesheet animation
				textSheet = assetManager.get(sprite);
				
				tmp = TextureRegion.split(textSheet, textSheet.getWidth()/5, textSheet.getHeight());   
				spriteSheetTmp1 = TextureRegion.split(textSheet, textSheet.getWidth()/5, textSheet.getHeight());   
				
				textFrames = new TextureRegion[5]; 
				velAnimation = 1f/6f;
			break;
		}
	}
	
	public void inicializar () {
	
		index = 0;
	
		for (int i = 0; i < 1; i++){
			for (int j = 0; j < tmp[0].length; j++){
				textFrames[index] = tmp[i][j];
				index++;
			}
		}
		walkAnimation = new Animation(velAnimation, textFrames);  
		stateTime = 0f;   
	}

	private void initSpriteSheet() {
		index = 0;

		for (int i = 0; i < 1; i++){
			for (int j = 0; j < tmp[0].length; j++){
				textFrames[index] = tmp[i][j];
				index++;
			}
		}
		walkAnimation = new Animation(velAnimation, textFrames);  
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

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
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
		
		batch.draw(currentFrame, this.posX, this.posY);
	}
}
