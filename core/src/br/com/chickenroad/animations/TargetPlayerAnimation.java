
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
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import br.com.chickenroad.screens.util.Constantes;

public class TargetPlayerAnimation {

	private Animation walkAnimation;         

	private Texture eggsSheet;
	private TextureRegion[] eggsFrames;            
	private TextureRegion currentFrame;                                                
	private TextureRegion[][] tmp;


	private TextureRegion[][] spriteSheetTmp1;
	private float stateTime;  
	private int index;

	public TargetPlayerAnimation(AssetManager assetManager) {
		super();

		//player spritesheet animation
		eggsSheet = assetManager.get(Constantes.URL_EGGS);
		
		tmp = TextureRegion.split(eggsSheet,  38, 51);   
		spriteSheetTmp1 = TextureRegion.split(eggsSheet, 38, 51);   
		
		eggsFrames = new TextureRegion[5]; 
	
		//secundaries spritesheet animation
		
	
	}
	
	public void inicializar () {
		index = 0;

		for (int i = 0; i < 1; i++){
			for (int j = 0; j < tmp[0].length; j++){
				eggsFrames[index] = tmp[i][j];
				index++;
			}
		}
		walkAnimation = new Animation(1f/7f, eggsFrames);  
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
		walkAnimation = new Animation(1f/7f, eggsFrames);  
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

	/**
	 * @return the currentFrame
	 */
	public TextureRegion getCurrentFrame() {
		stateTime += Gdx.graphics.getDeltaTime();          
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		return currentFrame;
	}

}
