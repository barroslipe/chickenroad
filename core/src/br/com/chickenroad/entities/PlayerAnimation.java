package br.com.chickenroad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import br.com.chickenroad.screens.util.Constantes;

public class PlayerAnimation {
	private Animation walkAnimation;         
    private Texture walkDirSheet; 
    private Texture walkEsqSheet;
    private Texture jumpDirSheet;
    private Texture jumpEsqSheet;
    private Texture stopDirSheet;
    private Texture walkCimSheet;
    
    private TextureRegion[] walkFrames;            
    private SpriteBatch spriteBatch;          
    private TextureRegion currentFrame;                                                
    private TextureRegion[][] tmp;
    /**
	 * @return the tmp
	 */
	public TextureRegion[][] getTmp() {
		return tmp;
	}

	/**
	 * @param tmp the tmp to set
	 */
	public void setTmp(TextureRegion[][] tmp) {
		this.tmp = tmp;
	}

	/**
	 * @return the currentFrame
	 */
	public TextureRegion getCurrentFrame() {
		stateTime += Gdx.graphics.getDeltaTime();          
	    currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		return currentFrame;
	}

	private TextureRegion[][] spriteSheetTmp1;
    private TextureRegion[][] spriteSheetTmp2;
    private TextureRegion[][] spriteSheetTmp3;
    private TextureRegion[][] spriteSheetTmp4;
    private TextureRegion[][] spriteSheetTmp5;
    private TextureRegion[][] spriteSheetTmp6;
    private float stateTime;  
    private int index;
    
	
	public PlayerAnimation(AssetManager assetManager) {
		super();
		
	    //spritesheet animation
		walkDirSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_RIGHT);
		walkEsqSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_LEFT);
		jumpDirSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_JUMP_RIGHT);
		jumpEsqSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_JUMP_LEFT);
		stopDirSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_STOP_RIGHT);
		walkCimSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_UP);


		tmp = TextureRegion.split(stopDirSheet,  96, 112);   
		spriteSheetTmp1 = TextureRegion.split(walkDirSheet, 96, 113);   
		spriteSheetTmp2 = TextureRegion.split(walkEsqSheet,  96, 113);   
		spriteSheetTmp3 = TextureRegion.split(jumpDirSheet, 96, 181);   
		spriteSheetTmp4 = TextureRegion.split(jumpEsqSheet, 96, 181);   
		spriteSheetTmp5 = TextureRegion.split(stopDirSheet,  96, 112);
		spriteSheetTmp6 = TextureRegion.split(walkCimSheet,  84, 132);   

        
        walkFrames = new TextureRegion[5]; 
        spriteBatch = new SpriteBatch();   
	}

	public void inicializar (Vector2 vector2) {
		
			index = 0;
			
			 for (int i = 0; i < 1; i++){
		    	  for (int j = 0; j < tmp[0].length; j++){
		            walkFrames[index] = tmp[i][j];
		            index++;
		    	  }
		    }
		    walkAnimation = new Animation(1f/7f, walkFrames);  
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
	    walkAnimation = new Animation(1f/7f, walkFrames);  
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
		   default:
				break;
		   }
		   
		   initSpriteSheet();
	   }


	   //@Override
	   /*public void draw (Batch batch) {
		   stateTime += Gdx.graphics.getDeltaTime();          
	       currentFrame = walkAnimation.getKeyFrame(stateTime, true); 
	       spriteBatch.begin();
	       spriteBatch.draw(currentFrame, 96, 113);            
	       spriteBatch.end();
	      
	   }*/

}
