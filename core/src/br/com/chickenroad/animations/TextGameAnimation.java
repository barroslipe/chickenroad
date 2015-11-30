
/*
 * TargetPlayerAnimation.java
 * 
 * Um target é qualquer sprite que pode ser interessante para o player juntar pontos ou concluir tarefas: 
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

import br.com.chickenroad.screens.TextGameTypes;

public class TextGameAnimation{

	private Animation walkAnimation;         

	private Texture textSheet;
	private TextureRegion[] textFrames;            
	private TextureRegion currentFrame;                                                
	private TextureRegion[][] tmp;


	private TextureRegion[][] spriteSheetTmp1;
	private float stateTime;  
	private int index;
	private float posX, posY;

	

	public TextGameAnimation(String sprite, AssetManager assetManager, TextGameTypes text) {
		super();

			//player spritesheet animation
			textSheet = assetManager.get(sprite);
			
			tmp = TextureRegion.split(textSheet, 179, 44);   
			spriteSheetTmp1 = TextureRegion.split(textSheet, 179, 44);   
			
			textFrames = new TextureRegion[4]; 
	}
	
	public void inicializar () {
	
		index = 0;
	
		for (int i = 0; i < 1; i++){
			for (int j = 0; j < tmp[0].length; j++){
				textFrames[index] = tmp[i][j];
				index++;
			}
		}
		walkAnimation = new Animation(1f/4f, textFrames);  
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
		walkAnimation = new Animation(1f/4f, textFrames);  
		stateTime = 0f;   
	}

	//seleciona o avatar para cada acao do player
	public void changeSpriteSheet(int ss) {

		switch(ss){
		case 1:
			tmp = spriteSheetTmp1;
			break;
		/*
		 * case 2: // para a animação de pegar o objeto
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
