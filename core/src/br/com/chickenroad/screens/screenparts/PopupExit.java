package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.Constantes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Tela do popup de saída da aplicação
 *
 */

public class PopupExit {

	//no button
	private Texture texturePopupNo;
	private Sprite spritePopupNo;

	//yes button
	private Texture texturePopupYes;
	private Sprite spritePopupYes;

	//popup background
	private Texture texturePopupBackground;
	private Sprite spritePopupBackground;

	/**
	 * Inicialização dos atributos da classe
	 * @param assetManager referência a classe que possui os recursos alocados
	 */
	public PopupExit(AssetManager assetManager){

		this.texturePopupYes = assetManager.get(Constantes.URL_POPUP_YES_BUTTON);
		this.spritePopupYes = new Sprite(texturePopupYes);

		this.texturePopupNo = assetManager.get(Constantes.URL_POPUP_NO_BUTTON);
		this.spritePopupNo = new Sprite(texturePopupNo);

		this.texturePopupBackground = assetManager.get(Constantes.URL_POPUP_EXIT_BACKGROUND);
		this.spritePopupBackground = new Sprite(texturePopupBackground);

	}

	/**
	 * Desenhar a tela de popup
	 * @param spriteBatch área de desenho da aplicação
	 */
	public void draw(SpriteBatch spriteBatch){

		spritePopupNo.setPosition(Constantes.WORLD_WIDTH/2 - 120, Constantes.WORLD_HEIGHT/2 - 180);
		spritePopupYes.setPosition(Constantes.WORLD_WIDTH/2 + 60, Constantes.WORLD_HEIGHT/2 - 180);
		spritePopupBackground.setPosition(Constantes.WORLD_WIDTH/2- spritePopupBackground.getWidth()/2, Constantes.WORLD_HEIGHT/2 - spritePopupBackground.getHeight()/2);

		spritePopupBackground.draw(spriteBatch);
		spritePopupNo.draw(spriteBatch);
		spritePopupYes.draw(spriteBatch);	
	}

	/**
	 * Verificar se houve o clique no botão Yes
	 * @param x posição x
	 * @param y posição y
	 * @return true quando houver clique no botão
	 * 		   false quando não houver clique no botão
	 */
	public boolean checkClickYesButton(float x, float y){
		if(spritePopupYes.getBoundingRectangle().contains(x, y)){
			return true;
		}
		return false;
	}
	/**
	 * Verificar se houve o clique no botão No
	 * @param x posição x
	 * @param y posição y
	 * @return true quando houver clique no botão
	 * 		   false quando não houver clique no botão
	 */
	public boolean checkClickNoButton(float x, float y){
		if(spritePopupNo.getBoundingRectangle().contains(x, y))
			return true;
		return false;
	}
}
