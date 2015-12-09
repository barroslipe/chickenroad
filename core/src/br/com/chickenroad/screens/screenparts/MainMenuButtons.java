package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.screens.util.Constantes;

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
	private Texture texturePlay;
	private Sprite spritePlay;

	//quit button
	private Texture textureExit;
	private Sprite spriteExit;

	//sound on button
	private Texture textureSoundOn;
	private Sprite spriteSoundOn;

	//sound off button
	private Texture textureSoundOff;
	private Sprite spriteSoundOff;

	/**
	 * Inicialização dos atributos da classe
	 * @param assetManager referência a classe que possui os recursos alocados
	 */

	public MainMenuButtons(AssetManager assetManager) {

		this.texturePlay = assetManager.get(Constantes.URL_PLAY_BUTTON);
		this.spritePlay = new Sprite(texturePlay);

		this.textureExit = assetManager.get(Constantes.URL_EXIT_BUTTON);
		this.spriteExit = new Sprite(textureExit);

		this.textureSoundOn = assetManager.get(Constantes.URL_SOUND_ON_BUTTON);
		this.spriteSoundOn = new Sprite(textureSoundOn);

		this.textureSoundOff = assetManager.get(Constantes.URL_SOUND_OFF_BUTTON);
		this.spriteSoundOff = new Sprite(textureSoundOff);
	}
	/**
	 * Desenhar os botões do menu principal
	 * @param spriteBatch área de desenho da aplicação
	 */
	public void draw(SpriteBatch spriteBatch){

		final int positionXMenu = 68;
		final int positionYMenu = 120;

		spritePlay.setPosition(Constantes.WORLD_WIDTH/2-positionXMenu-35, Constantes.WORLD_HEIGHT/2);
		spriteExit.setPosition(Constantes.WORLD_WIDTH/2-positionXMenu-35, Constantes.WORLD_HEIGHT/2-positionYMenu);

		spritePlay.draw(spriteBatch);
		spriteExit.draw(spriteBatch);

		final int positionSound = 20;

		if(Constantes.SOUND_ON_FLAG){
			spriteSoundOn.setPosition(positionSound, positionSound);
			spriteSoundOn.draw(spriteBatch);
		}else{
			spriteSoundOff.setPosition(positionSound, positionSound);
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
	public boolean checkClickSoundOnButton(float x, float y) {

		if(spriteSoundOn.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	/**
	 * Verificar se houve o clique no botão de habilitar som
	 * @param x posição x
	 * @param y posição y
	 * @return true quando houver clique no botão
	 * 		   false quando não houver clique no botão
	 */
	public boolean checkClickSoundOffButton(float x, float y) {

		if(spriteSoundOff.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
}
