package br.com.chickenroad.animations;

import br.com.chickenroad.entities.VehicleTypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 
 * Animação do player
 *
 */
public class VehicleAnimation {

	private Animation walkAnimation;         

	private Texture spriteSheet; 
	

	private TextureRegion[] walkFrames;            
	public TextureRegion[] getWalkFrames() {
		return walkFrames;
	}

	private TextureRegion currentFrame;                                                
	private TextureRegion[][] spriteSheetTmp;

	private AssetManager assetManager;

	private float stateTime;  
	private int index;
	private float velAnimation=0;
	private float posX=0, posY=0;

	public VehicleAnimation(AssetManager assetManager) {
		super();
		this.assetManager = assetManager;
	}

	public void inicializar (float x, float y) {
		this.posX = x;
		this.posY = y;
	}
	
	//muda o sprite sheet
	public void setSpriteSheet(String sprite, VehicleTypes vehicleTypes){
		
		switch(vehicleTypes){
			case  YELLOW_CAR_RIGHT:
				spriteSheet = assetManager.get(sprite);
				spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/2, spriteSheet.getHeight());   
				
				walkFrames = new TextureRegion[2]; 

				this.velAnimation = 1f/3f;

			break;
			case  YELLOW_CAR_LEFT:
				spriteSheet = assetManager.get(sprite);
				spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/2, spriteSheet.getHeight());   
				
				walkFrames = new TextureRegion[2]; 

				this.velAnimation = 1f/5f;

			break;
		default:
			break;
		}

		initSpriteSheet();
	}
	
	private void initSpriteSheet() {
		System.err.println("iniciou spritesheet vehicle");
		index = 0;

		for (int i = 0; i < 1; i++){
			for (int j = 0; j < spriteSheetTmp[0].length; j++){
				walkFrames[index] = spriteSheetTmp[i][j];
				index++;
			}
		}
		
		walkAnimation = new Animation(velAnimation, walkFrames);  
		stateTime = 0f;   
	}
	
	/**
	 * @return the currentFrame
	 */
	public TextureRegion getCurrentFrame() {
		stateTime += Gdx.graphics.getDeltaTime();
		System.err.println(stateTime);
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
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
}
