package br.com.chickenroad.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import br.com.chickenroad.screens.util.Constantes;

/**
 * 
 * Animação do player
 *
 */
public class PlayerAnimation {

	private Animation walkAnimation;         

	private Texture walkDirSheet; 
	private Texture walkEsqSheet;
	private Texture jumpDirSheet;
	private Texture jumpEsqSheet;
	private Texture stopDirSheet;
	private Texture walkCimSheet;
	private Texture walkBaiSheet;
	

	private TextureRegion[] walkFrames;            
	private TextureRegion currentFrame;                                                
	private TextureRegion[][] tmp;

	private TextureRegion[][] spriteSheetTmp1;
	private TextureRegion[][] spriteSheetTmp2;
	private TextureRegion[][] spriteSheetTmp3;
	private TextureRegion[][] spriteSheetTmp4;
	private TextureRegion[][] spriteSheetTmp5;
	private TextureRegion[][] spriteSheetTmp6;
	private TextureRegion[][] spriteSheetTmp7;

	private float stateTime;  
	private int index;
	private float velAnimation=0;
	private float posX=0, posY=0;

	public PlayerAnimation(String sprite, AssetManager assetManager) {
		super();
		//spritesheet animation
		walkDirSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_RIGHT);
		walkEsqSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_LEFT);
		jumpDirSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_JUMP_RIGHT);
		jumpEsqSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_JUMP_LEFT);
		stopDirSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_STOP_RIGHT);
		walkCimSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_UP);
		walkBaiSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_DOWN);

		tmp = TextureRegion.split(stopDirSheet,  stopDirSheet.getWidth()/5, stopDirSheet.getHeight());   
		spriteSheetTmp1 = TextureRegion.split(walkDirSheet, walkDirSheet.getWidth()/5, walkDirSheet.getHeight());   
		spriteSheetTmp2 = TextureRegion.split(walkEsqSheet,  walkEsqSheet.getWidth()/5, walkEsqSheet.getHeight());   
		spriteSheetTmp3 = TextureRegion.split(jumpDirSheet, jumpDirSheet.getWidth()/5, jumpDirSheet.getHeight());   
		spriteSheetTmp4 = TextureRegion.split(jumpEsqSheet, jumpEsqSheet.getWidth()/5, jumpEsqSheet.getHeight());   
		spriteSheetTmp5 = TextureRegion.split(stopDirSheet, stopDirSheet.getWidth()/5, stopDirSheet.getHeight());
		spriteSheetTmp6 = TextureRegion.split(walkCimSheet,  walkCimSheet.getWidth()/5, walkCimSheet.getHeight());   
		spriteSheetTmp7 = TextureRegion.split(walkBaiSheet,  walkBaiSheet.getWidth()/5, walkBaiSheet.getHeight());   

		walkFrames = new TextureRegion[5]; 
		
		this.velAnimation = 1f/20f;
	}


	public void inicializar (float x, float y) {
		this.posX = x;
		this.posY = y;
		index = 0;

		for (int i = 0; i < 1; i++){
			for (int j = 0; j < tmp[0].length; j++){
				walkFrames[index] = tmp[i][j];
				index++;
			}
		}
		walkAnimation = new Animation(velAnimation, walkFrames);  
		stateTime = 0f;   
	}

	private void initSpriteSheet() {
		index = 0;

		for (int i = 0; i < 1; i++){
			for (int j = 0; j < tmp[0].length; j++){
				walkFrames[index] = tmp[i][j];
				index++;
			}
		}
		walkAnimation = new Animation(velAnimation, walkFrames);  
		stateTime = 0f;   

	}

	//seleciona o avatar para cada acao do player
	public void changeSpriteSheet(int ss) {

		switch(ss){
		case 1:
			tmp = spriteSheetTmp1;
			break;
		case 2:
			tmp = spriteSheetTmp2;
			break;
		case 3:
			tmp = spriteSheetTmp3;
			break;
		case 4:
			tmp = spriteSheetTmp4;
			break;
		case 5:
			tmp = spriteSheetTmp5;
			break;
		case 6:
			tmp = spriteSheetTmp6;
			break;
		case 7:
			tmp = spriteSheetTmp7;
			break;
		default:
			break;
		}

		initSpriteSheet();
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
	
	public void draw(Batch batch, float delta){
		stateTime += Gdx.graphics.getDeltaTime();          
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		
		batch.draw(currentFrame, posX, posY);
	}
}
