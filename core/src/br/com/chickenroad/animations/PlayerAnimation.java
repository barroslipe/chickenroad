package br.com.chickenroad.animations;

import br.com.chickenroad.entities.enums.PlayerTypes;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 
 * Animação do player
 *
 */
public class PlayerAnimation {

	private Animation walkAnimation;         

	private Texture spriteSheet; 
	private TextureRegion[] walkFrames;            
	private TextureRegion currentFrame;                                                
	private TextureRegion[][] spriteSheetTmp;

	private AssetManager assetManager;

	private float stateTime;  
	private float animationSpeed;


	public PlayerAnimation(AssetManager assetManager) {
		this.assetManager = assetManager;
		setSpriteSheet(Constantes.URL_PLAYER_AVATAR_STOP_RIGHT, PlayerTypes.AVATAR_STOP_RIGHT);
	}

	//muda o sprite sheet
	public void setSpriteSheet(String sprite, PlayerTypes ptypes){

		spriteSheet = assetManager.get(sprite);

		switch(ptypes){
		case  AVATAR_RIGHT:
			spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
			walkFrames = new TextureRegion[5]; 
			this.animationSpeed = 1f/20f;

			break;

		case AVATAR_LEFT:
			spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
			walkFrames = new TextureRegion[5]; 
			this.animationSpeed = 1f/20f;

			break;

		case AVATAR_UP:
			spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
			walkFrames = new TextureRegion[5]; 
			this.animationSpeed = 1f/20f;

			break;

		case AVATAR_DOWN:
			spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
			walkFrames = new TextureRegion[5]; 
			this.animationSpeed = 1f/20f;

			break;

		case AVATAR_JUMP_RIGHT:
			spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
			walkFrames = new TextureRegion[5]; 
			this.animationSpeed = 1f/20f;

			break;

		case AVATAR_JUMP_LEFT:
			spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
			walkFrames = new TextureRegion[5]; 
			this.animationSpeed = 1f/20f;

			break;

		case AVATAR_STOP_RIGHT:
			spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
			walkFrames = new TextureRegion[5]; 
			this.animationSpeed = 1f/20f;

			break;

		case AVATAR_STOP_LEFT:
			spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
			walkFrames = new TextureRegion[5]; 
			this.animationSpeed = 1f/20f;

			break;
		case AVATAR_DEAD:
			spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/1, spriteSheet.getHeight());   
			walkFrames = new TextureRegion[1]; 
			this.animationSpeed = 1f/18f;
			break;
		}

		initSpriteSheet();
	}

	private void initSpriteSheet() {
		int index = 0;

		for (int i = 0; i < 1; i++){
			for (int j = 0; j < spriteSheetTmp[0].length; j++){
				walkFrames[index] = spriteSheetTmp[i][j];
				index++;
			}
		}

		walkAnimation = new Animation(animationSpeed, walkFrames);  
		stateTime = 0f;   
	}

	/**
	 * @return the currentFrame
	 */
	public TextureRegion getCurrentFrame(float delta) {
		stateTime += delta;          
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		return currentFrame;
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
