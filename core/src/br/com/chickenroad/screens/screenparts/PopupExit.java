package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Tela do popup de saída da aplicação
 * 
 *
 */

public class PopupExit {

	private Texture texturePopupNo;
	private TextureRegion textureRegionPopupNo;
	private Sprite spritePopupNo;

	private Texture texturePopupYes;
	private TextureRegion textureRegionPopupYes;
	private Sprite spritePopupYes;

	private Texture texturePopupBackground;
	private TextureRegion textureRegionPopupBackground;
	private Sprite spritePopupBackground;


	public PopupExit(AssetManager assetManager){

		this.texturePopupYes = assetManager.get(Constantes.URL_POPUP_YES_BUTTON);
		this.textureRegionPopupYes = new TextureRegion(texturePopupYes,0,0,56,55);
		this.spritePopupYes = new Sprite(textureRegionPopupYes);

		this.texturePopupNo = assetManager.get(Constantes.URL_POPUP_NO_BUTTON);
		this.textureRegionPopupNo = new TextureRegion(texturePopupNo,0,0,56,53);
		this.spritePopupNo = new Sprite(textureRegionPopupNo);

		this.texturePopupBackground = assetManager.get(Constantes.URL_POPUP_EXIT_BACKGROUND);
		this.textureRegionPopupBackground = new TextureRegion(texturePopupBackground,0,0,500,131);
		this.spritePopupBackground = new Sprite(textureRegionPopupBackground);
	}

	public void draw(SpriteBatch spriteBatch){

		spritePopupNo.setPosition(Constantes.WORLD_WIDTH/2 - 200, Constantes.WORLD_HEIGHT/2 - 50);
		spritePopupYes.setPosition(Constantes.WORLD_WIDTH/2 + 200, Constantes.WORLD_HEIGHT/2 - 50);
		spritePopupBackground.setPosition(Constantes.WORLD_WIDTH/2- 225, Constantes.WORLD_HEIGHT/2 - 80);

		spritePopupBackground.draw(spriteBatch);
		spritePopupNo.draw(spriteBatch);
		spritePopupYes.draw(spriteBatch);	
	}

	public boolean checkClickYesButton(float x, float y){
		if(spritePopupYes.getBoundingRectangle().contains(x, y)){
			dispose();
			return true;
		}
		return false;
	}

	public boolean checkClickNoButton(float x, float y){
		if(spritePopupNo.getBoundingRectangle().contains(x, y))
			return true;
		return false;
	}

	public void dispose(){
		
	}
}
