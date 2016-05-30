package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.Constantes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Botões da tela principal de menu da aplicação
 *
 */

public class MainMenuButtons {

	//play button
	private Sprite spritePlay;

	//quit button
	private Sprite spriteExit;

	//sound on button
	private Sprite spriteSoundOn;

	//sound off button
	private Sprite spriteSoundOff;


	/**
	 * Inicialização dos atributos da classe
	 * @param assetManager referência a classe que possui os recursos alocados
	 */

	public MainMenuButtons(AssetManager assetManager) {

		Texture texturePlay = assetManager.get(Constantes.URL_PLAY_BUTTON);
		this.spritePlay = new Sprite(texturePlay);
		
		Texture textureExit = assetManager.get(Constantes.URL_EXIT_BUTTON);
		this.spriteExit = new Sprite(textureExit);
		this.spriteExit.setScale(0.7f);

		Texture textureSoundOn = assetManager.get(Constantes.URL_SOUND_ON_BUTTON);
		this.spriteSoundOn = new Sprite(textureSoundOn);
		Texture textureSoundOff = assetManager.get(Constantes.URL_SOUND_OFF_BUTTON);
		this.spriteSoundOff = new Sprite(textureSoundOff);

		this.spritePlay.setPosition(Constantes.WORLD_WIDTH/2-spritePlay.getWidth()/2+2, Constantes.WORLD_HEIGHT/2 - 54);
		
		final int POSITION_SOUND = 20;
		this.spriteExit.setPosition(Constantes.WORLD_WIDTH-spriteExit.getWidth(), POSITION_SOUND+5);

		this.spriteSoundOn.setPosition(POSITION_SOUND, POSITION_SOUND);
		this.spriteSoundOff.setPosition(POSITION_SOUND, POSITION_SOUND);

	}
	/**
	 * Desenhar os botões do menu principal
	 * @param spriteBatch área de desenho da aplicação
	 */
	public void draw(SpriteBatch spriteBatch){

		spritePlay.draw(spriteBatch);
		spriteExit.draw(spriteBatch);

		if(Constantes.SOUND_ON_FLAG){
			spriteSoundOn.draw(spriteBatch);
		}else{
			spriteSoundOff.draw(spriteBatch);
		}
	}
	/**
	 * Verificar se houve o clique no botão de EXIT
	 * @param x posição x
	 * @param y posição y
	 * @return true quando houver clique no botão
	 * 		   false quando não houver clique no botão
	 */
	public boolean checkClickExitButton(float x, float y) {

		if(spriteExit.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	/**
	 * Verificar se houve o clique no botão de PLAY
	 * @param x posição x
	 * @param y posição y
	 * @return true quando houver clique no botão
	 * 		   false quando não houver clique no botão
	 */
	public boolean checkClickPlayButton(float x, float y) {

		if(spritePlay.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	/**
	 * Verificar se houve o clique no botão de desabilitar som
	 * @param x posição x
	 * @param y posição y
	 * @return true quando houver clique no botão
	 * 		   false quando não houver clique no botão
	 */
	public boolean checkClickSoundButton(float x, float y) {

		if(spriteSoundOn.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
}
