package br.com.chickenroad.animations;

import br.com.chickenroad.entities.enums.SupportingTypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SupportingAnimation {
	private Animation walkAnimation;         

	private Texture supportingSheet;
	private TextureRegion[] supportingFrames;            
	private TextureRegion currentFrame;                                                
	private TextureRegion[][] tmp;
	private float velAnimation=0;

	private TextureRegion[][] spriteSheetTmp1;
	private float stateTime;  
	private int index;
	private float posX=0, posY=0;

	
	public SupportingAnimation(String sprite, AssetManager assetManager, SupportingTypes supp) {
		super();

		switch(supp){
		case PIG_SLEEPING_RIGHT: //anima��o de ovos
			//player spritesheet animation
			supportingSheet = assetManager.get(sprite);
			
			tmp = TextureRegion.split(supportingSheet, supportingSheet.getWidth()/5, supportingSheet.getHeight());   
			spriteSheetTmp1 = TextureRegion.split(supportingSheet, supportingSheet.getWidth()/5, supportingSheet.getHeight());   
			
			supportingFrames = new TextureRegion[5]; 
		
			velAnimation = 1f/2f;
			break;
		case PIG_SLEEPING_LEFT: //anima��o de ovos
			//player spritesheet animation
			supportingSheet = assetManager.get(sprite);
			
			tmp = TextureRegion.split(supportingSheet, supportingSheet.getWidth()/5, supportingSheet.getHeight());   
			spriteSheetTmp1 = TextureRegion.split(supportingSheet, supportingSheet.getWidth()/5, supportingSheet.getHeight());   
			
			supportingFrames = new TextureRegion[5]; 
		
			velAnimation = 1f/1.5f;
			break;
		case PIG_AWAKE_RIGHT: //anima��o de moedas
			//player spritesheet animation
			supportingSheet = assetManager.get(sprite);
			
			tmp = TextureRegion.split(supportingSheet, supportingSheet.getWidth()/5, supportingSheet.getHeight());   
			spriteSheetTmp1 = TextureRegion.split(supportingSheet, supportingSheet.getWidth()/5, supportingSheet.getHeight());   
			
			supportingFrames = new TextureRegion[5]; 
			velAnimation = 1f/1f;
			break;
		
		case PIG_AWAKE_LEFT: //anima��o de moedas
			//player spritesheet animation
			supportingSheet = assetManager.get(sprite);
			
			tmp = TextureRegion.split(supportingSheet, supportingSheet.getWidth()/5, supportingSheet.getHeight());   
			spriteSheetTmp1 = TextureRegion.split(supportingSheet, supportingSheet.getWidth()/5, supportingSheet.getHeight());   
			
			supportingFrames = new TextureRegion[5]; 
			velAnimation = 1f/1f;
			break;
		}
	}
	
	public void inicializar (float x, float y) {
		this.posX = x;
		this.posY = y;
		
		index = 0;
		
		for (int i = 0; i < 1; i++){
			for (int j = 0; j < tmp[0].length; j++){
				supportingFrames[index] = tmp[i][j];
				index++;
			}
		}
		walkAnimation = new Animation(velAnimation, supportingFrames);  
		stateTime = 0f;   
	}

	private void initSpriteSheet() {
		index = 0;

		for (int i = 0; i < 1; i++){
			for (int j = 0; j < tmp[0].length; j++){
				supportingFrames[index] = tmp[i][j];
				index++;
			}
		}
		walkAnimation = new Animation(velAnimation, supportingFrames);  
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

	public void draw(Batch batch, float delta){
		stateTime += Gdx.graphics.getDeltaTime();          
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		
		batch.draw(currentFrame, posX, posY);
	}
}
