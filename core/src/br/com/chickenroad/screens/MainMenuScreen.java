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
 * Tela que implementa o menu da aplicação
 *
 */
public class MainMenuScreen extends ScreenBase {

	//background
	private Texture textureBackground;
	private Sprite spriteBackground;

	private PopupExit popupExit;
	private MainMenuButtons menuButtons;

	private Music soundMenuBackground, soundClick;

	private boolean showPopupExitFlag;
	private Vector3 touchPoint;

	public MainMenuScreen(ChickenRoadGame aChickenRoadGame) {
		super(aChickenRoadGame);

		this.popupExit = new PopupExit(getAssetManager());
		this.menuButtons = new MainMenuButtons(getAssetManager());

		this.textureBackground = getAssetManager().get(Constantes.URL_BACKGROUND);
		this.spriteBackground = new Sprite(textureBackground);

		this.soundMenuBackground = getAssetManager().get(Constantes.URL_SOUND_MENU_BACKGROUND);
		this.soundClick = getAssetManager().get(Constantes.URL_SOUND_CLICK);

		this.showPopupExitFlag = false;
		this.touchPoint = new Vector3();

	}

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

		if(!soundMenuBackground.isPlaying() && Constantes.SOUND_ON_FLAG) soundMenuBackground.play(); 

	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		touchPoint.set(screenX, screenY, 0);
		chickenRoadGame.getOrthographicCamera().unproject(touchPoint);

		if(showPopupExitFlag){

			if(popupExit.checkClickYesButton(touchPoint.x, touchPoint.y)){
				soundClick.play();
				dispose();
				Gdx.app.exit();
			}else if(popupExit.checkClickNoButton(touchPoint.x, touchPoint.y)){
				soundClick.play();
				showPopupExitFlag=false;
			}

		}else{

			if(menuButtons.checkClickExitButton(touchPoint.x, touchPoint.y)){
				soundClick.play();
				showPopupExitFlag = true;

			}else if(menuButtons.checkClickPlayButton(touchPoint.x, touchPoint.y)){
				//se pressionar PLAY
				soundClick.play();
				//soundMenuBackground.stop();
				chickenRoadGame.setScreenWithTransitionFade(new SeasonScreen(chickenRoadGame));
			}else if(menuButtons.checkClickSoundOnButton(touchPoint.x, touchPoint.y)){
				//TODO tocar o som
				soundClick.play();
				Constantes.SOUND_ON_FLAG = !Constantes.SOUND_ON_FLAG;
				if(!Constantes.SOUND_ON_FLAG) soundMenuBackground.stop();
			}
		}
		return true;
	}

	private void drawPopupExit(){

		if(showPopupExitFlag == true)
			popupExit.draw(chickenRoadGame.getSpriteBatch());
	}

	private void drawMenuButtons() {
		menuButtons.draw(chickenRoadGame.getSpriteBatch());

	}

	private void drawBackground() {
		spriteBackground.draw(chickenRoadGame.getSpriteBatch());
	}

	@Override
	public void dispose() {
		this.touchPoint = null;
		this.popupExit.dispose();
		this.menuButtons.dispose();	
	}
}
