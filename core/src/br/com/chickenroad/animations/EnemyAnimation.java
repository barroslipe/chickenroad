package br.com.chickenroad.animations;

import br.com.chickenroad.entities.enums.EnemyTypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyAnimation {

	private Animation walkAnimation;         

	private Texture textureSheet;
	private TextureRegion[] textureRegionFrames;            
	private TextureRegion[][] tmp;
	private TextureRegion currentFrame;

	private TextureRegion[][] spriteSheetTmp1;
	private float velAnimation;
	private float stateTime;  
	private int index;
	private float posX, posY;

	public EnemyAnimation(String sprite, AssetManager assetManager, EnemyTypes enemy, float velAnim){

		switch (enemy) {
		case FOX_SLEEP:

			int quantidadeFrames=2;
			textureSheet = assetManager.get(sprite);

			tmp = TextureRegion.split(textureSheet, textureSheet.getWidth()/quantidadeFrames, textureSheet.getHeight());   
			spriteSheetTmp1 = TextureRegion.split(textureSheet, textureSheet.getWidth()/quantidadeFrames, textureSheet.getHeight());   

			textureRegionFrames = new TextureRegion[quantidadeFrames]; 

			velAnimation  = velAnim;
			break;

		default:
			break;
		}
	}
	public void inicializar (float x, float y) {
		this.posX = x;
		this.posY = y;

		index = 0;

		for (int i = 0; i < 1; i++){
			for (int j = 0; j < tmp[0].length; j++){
				textureRegionFrames[index] = tmp[i][j];
				index++;
			}
		}
		walkAnimation = new Animation(velAnimation, textureRegionFrames);  
		stateTime = 0f;   
	}

	private void initSpriteSheet() {
		index = 0;

		for (int i = 0; i < 1; i++){
			for (int j = 0; j < tmp[0].length; j++){
				textureRegionFrames[index] = tmp[i][j];
				index++;
			}
		}
		walkAnimation = new Animation(velAnimation, textureRegionFrames);  
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

	public void draw(Batch batch, float delta){
		stateTime += Gdx.graphics.getDeltaTime();          
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);

		batch.draw(currentFrame, posX, posY);
	}

}
