package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.screens.screenparts.MainMenuButtons;
import br.com.chickenroad.screens.screenparts.PopupExit;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen extends ScreenAdapter {

	private ChickenRoadGame chickenRoadGame;
	private AssetManager assetManager;

	//background
	private Texture textureBackground;
	private TextureRegion textureRegionBackground;
	private Sprite spriteBackground;

	private PopupExit popupExit;
	private MainMenuButtons menuButtons;

	private OrthographicCamera orthographicCamera;

	private Music soundMenuBackground, soundClick;

	private boolean showPopupExitFlag;
	private Vector3 touchPoint;

	public MainMenuScreen(ChickenRoadGame aChickenRoadGame) {

		this.chickenRoadGame = aChickenRoadGame;
		this.assetManager = chickenRoadGame.getResourceManager().getAssetManager();

		this.popupExit = new PopupExit(assetManager);
		this.menuButtons = new MainMenuButtons(assetManager);

		this.orthographicCamera = new OrthographicCamera();
		this.orthographicCamera.setToOrtho(false, Constantes.WIDTH_BACKGROUND,Constantes.HEIGHT_BACKGROUND);

		this.textureBackground = assetManager.get(Constantes.URL_BACKGROUND);
		this.textureRegionBackground = new TextureRegion(textureBackground, 0,0,Constantes.WIDTH_BACKGROUND,Constantes.HEIGHT_BACKGROUND);
		this.spriteBackground = new Sprite(textureRegionBackground);

		this.soundMenuBackground = assetManager.get(Constantes.URL_SOUND_MENU_BACKGROUND);
		this.soundClick = assetManager.get(Constantes.URL_SOUND_CLICK);

		this.showPopupExitFlag = false;
		this.touchPoint = new Vector3();

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		orthographicCamera.update();

		chickenRoadGame.getSpriteBatch().setProjectionMatrix(orthographicCamera.combined);

		chickenRoadGame.getSpriteBatch().begin();
		drawBackground();
		drawMenuButtons();
		drawPopupExit();
		chickenRoadGame.getSpriteBatch().end();

		if(!soundMenuBackground.isPlaying() && Constantes.SOUND_ON_FLAG) soundMenuBackground.play(); 

		input();

	}

	private void input() {

		if(Gdx.input.justTouched()){

			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			orthographicCamera.unproject(touchPoint);

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
					soundMenuBackground.stop();
					chickenRoadGame.setScreen(new SeasonScreen(chickenRoadGame));
				}else if(menuButtons.checkClickSoundOnButton(touchPoint.x, touchPoint.y)){
					//TODO tocar o som
					soundClick.play();
					Constantes.SOUND_ON_FLAG = !Constantes.SOUND_ON_FLAG;
					if(!Constantes.SOUND_ON_FLAG) soundMenuBackground.stop();
				}

			}
		}

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
		
	}

}
