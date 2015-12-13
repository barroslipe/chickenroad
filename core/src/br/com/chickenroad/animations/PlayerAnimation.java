package br.com.chickenroad.animations;

import br.com.chickenroad.entities.PlayerTypes;

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
public class PlayerAnimation {

	private Animation walkAnimation;         

	private Texture spriteSheet; 
	/*private Texture walkEsqSheet;
	private Texture jumpDirSheet;
	private Texture jumpEsqSheet;
	private Texture stopDirSheet;
	private Texture walkCimSheet;
	private Texture walkBaiSheet;
	private Texture deadSheet;
*/
	private TextureRegion[] walkFrames;            
	private TextureRegion currentFrame;                                                
	private TextureRegion[][] spriteSheetTmp;

	private AssetManager assetManager;
	/*private TextureRegion[][] spriteSheetTmp2;
	private TextureRegion[][] spriteSheetTmp3;
	private TextureRegion[][] spriteSheetTmp4;
	private TextureRegion[][] spriteSheetTmp5;
	private TextureRegion[][] spriteSheetTmp6;
	private TextureRegion[][] spriteSheetTmp7;
	private TextureRegion[][] spriteSheetTmp8;
*/
	private float stateTime;  
	private int index;
	private float velAnimation=0;
	private float posX=0, posY=0;


	public PlayerAnimation(AssetManager assetManager) {
		super();
		
		this.assetManager = assetManager;
				
		//spritesheet animation
		/*walkEsqSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_LEFT);
		jumpDirSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_JUMP_RIGHT);
		jumpEsqSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_JUMP_LEFT);
		stopDirSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_STOP_RIGHT);
		walkCimSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_UP);
		walkBaiSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_DOWN);
		deadSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_DEAD);
		
		tmp = TextureRegion.split(stopDirSheet,  stopDirSheet.getWidth()/5, stopDirSheet.getHeight());   
		spriteSheetTmp2 = TextureRegion.split(walkEsqSheet,  walkEsqSheet.getWidth()/5, walkEsqSheet.getHeight());   
		spriteSheetTmp3 = TextureRegion.split(jumpDirSheet, jumpDirSheet.getWidth()/5, jumpDirSheet.getHeight());   
		spriteSheetTmp4 = TextureRegion.split(jumpEsqSheet, jumpEsqSheet.getWidth()/5, jumpEsqSheet.getHeight());   
		spriteSheetTmp5 = TextureRegion.split(stopDirSheet, stopDirSheet.getWidth()/5, stopDirSheet.getHeight());
		spriteSheetTmp6 = TextureRegion.split(walkCimSheet,  walkCimSheet.getWidth()/5, walkCimSheet.getHeight());   
		spriteSheetTmp7 = TextureRegion.split(walkBaiSheet,  walkBaiSheet.getWidth()/5, walkBaiSheet.getHeight());   
		spriteSheetTmp8 = TextureRegion.split(deadSheet,  walkBaiSheet.getWidth()/3, walkBaiSheet.getHeight());   
		
		walkFrames = new TextureRegion[5]; 
		
		this.velAnimation = 1f/20f;
	*/}

	public void inicializar (float x, float y) {
		this.posX = x;
		this.posY = y;
	}
	
	//muda o sprite sheet
	public void setSpriteSheet(String sprite, PlayerTypes ptypes){
		
		switch(ptypes){
			case  AVATAR_RIGHT:
				spriteSheet = assetManager.get(sprite);
				spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
				
				walkFrames = new TextureRegion[5]; 

				this.velAnimation = 1f/20f;

			break;
	
			case AVATAR_LEFT:
				spriteSheet = assetManager.get(sprite);
				spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
				
				walkFrames = new TextureRegion[5]; 

				this.velAnimation = 1f/20f;

			break;

			case AVATAR_UP:
				spriteSheet = assetManager.get(sprite);
				spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
				
				walkFrames = new TextureRegion[5]; 

				this.velAnimation = 1f/20f;

			break;

			case AVATAR_DOWN:
				spriteSheet = assetManager.get(sprite);
				spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
				
				walkFrames = new TextureRegion[5]; 

				this.velAnimation = 1f/20f;

			break;
			
			case AVATAR_JUMP_RIGHT:
				spriteSheet = assetManager.get(sprite);
				spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
				
				walkFrames = new TextureRegion[5]; 

				this.velAnimation = 1f/20f;

			break;

			case AVATAR_JUMP_LEFT:
				spriteSheet = assetManager.get(sprite);
				spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
				
				walkFrames = new TextureRegion[5]; 

				this.velAnimation = 1f/20f;

			break;
			
			case AVATAR_STOP_RIGHT:
				spriteSheet = assetManager.get(sprite);
				spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
				
				walkFrames = new TextureRegion[5]; 

				this.velAnimation = 1f/20f;

			break;
			
			case AVATAR_STOP_LEFT:
				spriteSheet = assetManager.get(sprite);
				spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/5, spriteSheet.getHeight());   
				
				walkFrames = new TextureRegion[5]; 

				this.velAnimation = 1f/20f;

			break;
			case AVATAR_DEAD:
				spriteSheet = assetManager.get(sprite);
				spriteSheetTmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/1, spriteSheet.getHeight());   
				
				walkFrames = new TextureRegion[1]; 

				this.velAnimation = 1f/18f;

			break;
		}

		initSpriteSheet();
	}
	
	private void initSpriteSheet() {
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
