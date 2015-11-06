package br.com.chickenroad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import br.com.chickenroad.screens.util.Constantes;

public class PlayerAnimation extends Sprite{
	private Animation walkAnimation;         
    private Texture walkDirSheet; 
    private Texture walkEsqSheet;
    private Texture jumpDirSheet;
    private Texture jumpEsqSheet;
    private Texture stopDirSheet;

    private TextureRegion[] walkFrames;            
    private SpriteBatch spriteBatch;          
    private TextureRegion currentFrame;                                                
    private TextureRegion[][] tmp;
    private TextureRegion[][] spriteSheetTmp1;
    private TextureRegion[][] spriteSheetTmp2;
    private TextureRegion[][] spriteSheetTmp3;
    private TextureRegion[][] spriteSheetTmp4;
    private TextureRegion[][] spriteSheetTmp5;
    private float stateTime;   

    
	
	public PlayerAnimation(AssetManager assetManager) {
		super();
	    //spritesheet animation
		walkDirSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_RIGHT);
		walkEsqSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_LEFT);
		jumpDirSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_JUMP_RIGHT);
		jumpEsqSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_JUMP_LEFT);
		stopDirSheet = assetManager.get(Constantes.URL_PLAYER_AVATAR_STOP_RIGHT);

		tmp = TextureRegion.split(stopDirSheet,  64, 72);   
		spriteSheetTmp1 = TextureRegion.split(walkDirSheet,  64, 72);   
		spriteSheetTmp2 = TextureRegion.split(walkEsqSheet,  64, 72);   
		spriteSheetTmp3 = TextureRegion.split(jumpDirSheet, 64, 119);   
		spriteSheetTmp4 = TextureRegion.split(jumpEsqSheet, 64, 119);   
		spriteSheetTmp5 = TextureRegion.split(stopDirSheet, 64, 72);   
        
        walkFrames = new TextureRegion[5]; 
        spriteBatch = new SpriteBatch();   
	}

	public void inicializar () {
		
			int index = 0;
			
			 for (int i = 0; i < 1; i++){
		    	  for (int j = 0; j < tmp[0].length; j++){
		            walkFrames[index] = tmp[i][j];
		            index++;
		    	  }
		    }
		    walkAnimation = new Animation(1f/6f,walkFrames);  
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
		   default:
				break;
		   }
		   
		   inicializar();
	   }

	   @Override
	   public void draw (Batch batch) {
		   stateTime += Gdx.graphics.getDeltaTime();          
	       currentFrame = walkAnimation.getKeyFrame(stateTime, true); 
	       spriteBatch.begin();
	       spriteBatch.draw(currentFrame, 250, 50);            
	       spriteBatch.end();
	      
	   }

}
