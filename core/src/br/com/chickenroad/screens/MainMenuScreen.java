package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.screens.screenparts.MainMenuButtons;
import br.com.chickenroad.screens.screenparts.PopupExit;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;


/**
 * Tela que implementa a tela principal da aplicação 
 *
 */
public class MainMenuScreen extends ScreenBase {

	private Texture textureBackground;
	private Sprite spriteBackground;

	private PopupExit popupExit;
	private MainMenuButtons menuButtons;

	private Music soundPrincipal, soundClick;

	private boolean showPopupExitFlag;
	private Vector3 touchPoint;

	/**
	 * Inicialização dos atributos da classe
	 * @param aChickenRoadGame referência a classe principal do jogo
	 */
	public MainMenuScreen(ChickenRoadGame aChickenRoadGame) {
		super(aChickenRoadGame);

		this.popupExit = new PopupExit(getAssetManager());
		this.menuButtons = new MainMenuButtons(getAssetManager());

		this.textureBackground = getAssetManager().get(Constantes.URL_BACKGROUND);
		this.spriteBackground = new Sprite(textureBackground);

		this.soundPrincipal = getAssetManager().get(Constantes.URL_SOUND_PRINCIPAL);
		this.soundClick = getAssetManager().get(Constantes.URL_SOUND_CLICK);

		this.showPopupExitFlag = false;
		this.touchPoint = new Vector3();

	}

	/**
	 * Renderizador principal da classe
	 */
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		chickenRoadGame.getOrthographicCamera().update();
		chickenRoadGame.getSpriteBatch().setProjectionMatrix(chickenRoadGame.getOrthographicCamera().combined);

		chickenRoadGame.getSpriteBatch().begin();
		drawBackground();
		drawMenuButtons();
		drawPopupExit();
		chickenRoadGame.getSpriteBatch().end();

		if(!soundPrincipal.isPlaying() && Constantes.SOUND_ON_FLAG) soundPrincipal.play(); 

	}
	/**
	 * Tratar a entrada de dados do mouse ou touchScreen
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		touchPoint.set(screenX, screenY, 0);
		chickenRoadGame.getOrthographicCamera().unproject(touchPoint);

		/**
		 * Botões do menu de saída da aplicação
		 */
		if(showPopupExitFlag){

			if(popupExit.checkClickYesButton(touchPoint.x, touchPoint.y)){
				playSoundClick();
				//TODO verificar uma possibilidade mais elegante de encerrar uma aplicação
				Gdx.app.exit();
			}else if(popupExit.checkClickNoButton(touchPoint.x, touchPoint.y)){
				playSoundClick();
				showPopupExitFlag=false;
			}

		}else{

			/**
			 * Botões da tela principal
			 */

			if(menuButtons.checkClickExitButton(touchPoint.x, touchPoint.y)){
				playSoundClick();
				showPopupExitFlag = true;
			}else if(menuButtons.checkClickPlayButton(touchPoint.x, touchPoint.y)){
				playSoundClick();
				chickenRoadGame.setScreenWithTransitionFade(new SeasonScreen(chickenRoadGame));
			}else if(menuButtons.checkClickSoundOnButton(touchPoint.x, touchPoint.y)){
				Constantes.SOUND_ON_FLAG = !Constantes.SOUND_ON_FLAG;
				if(!Constantes.SOUND_ON_FLAG) soundPrincipal.stop();
			}
		}
		return true;
	}

	private void playSoundClick() {
		if(Constantes.SOUND_ON_FLAG)
			soundClick.play();
		
	}

	/**
	 * Desenhar o popup de saída
	 */
	private void drawPopupExit(){

		if(showPopupExitFlag == true)
			popupExit.draw(chickenRoadGame.getSpriteBatch());
	}

	/**
	 * Desenhar os botões da tela inicial
	 */
	private void drawMenuButtons() {
		menuButtons.draw(chickenRoadGame.getSpriteBatch());

	}

	/**
	 * Desenhar a imagem de fundo da tela inicial
	 */
	private void drawBackground() {
		spriteBackground.draw(chickenRoadGame.getSpriteBatch());
	}

	/**
	 * Liberar recursos ao encerrar a aplicação
	 */
	@Override
	public void dispose() {
		this.touchPoint = null;
	}
}
