package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.screens.screenparts.SeasonMenu;
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

/**
 * Apresentar as fases da aplicação
 * 
 *
 */
public class SeasonScreen extends ScreenAdapter {

	private ChickenRoadGame chickenRoadGame;
	private AssetManager assetManager;

	private SeasonMenu faseMenu;

	private Texture textureBACK;
	private TextureRegion textureRegionBACK;
	private Sprite spriteArrowBACK;

	//background
	private Texture textureBackground;
	private TextureRegion textureRegionBackground;
	private Sprite spriteBackground;

	private OrthographicCamera orthographicCamera;

	private Music soundMenuBackground, soundClick;


	public SeasonScreen(ChickenRoadGame chickenRoadGame) {

		this.chickenRoadGame = chickenRoadGame;
		this.assetManager = chickenRoadGame.getResourceManager().getAssetManager();

		this.faseMenu = new SeasonMenu();

		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false, Constantes.WIDTH_BACKGROUND,Constantes.HEIGHT_BACKGROUND);

		textureBACK = assetManager.get(Constantes.URL_BACK_BUTTON);
		textureRegionBACK = new TextureRegion(textureBACK,0,0,Constantes.WIDTH_BACK_BUTTON,Constantes.HEIGHT_BACK_BUTTON);
		spriteArrowBACK = new Sprite(textureRegionBACK);

		textureBackground = assetManager.get(Constantes.URL_BACKGROUND_SEASON);
		textureRegionBackground = new TextureRegion(textureBackground, 0,0,Constantes.WIDTH_BACKGROUND,Constantes.HEIGHT_BACKGROUND);
		spriteBackground = new Sprite(textureRegionBackground);

		soundMenuBackground = assetManager.get(Constantes.URL_SOUND_MENU_BACKGROUND);
		soundClick = assetManager.get(Constantes.URL_SOUND_CLICK);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		orthographicCamera.update();
		chickenRoadGame.getSpriteBatch().setProjectionMatrix(orthographicCamera.combined);

		chickenRoadGame.getSpriteBatch().begin();
		spriteBackground.draw(chickenRoadGame.getSpriteBatch());
		faseMenu.draw(chickenRoadGame.getSpriteBatch());		
		spriteArrowBACK.draw(chickenRoadGame.getSpriteBatch());
		chickenRoadGame.getSpriteBatch().end();

		if(!soundMenuBackground.isPlaying() && Constantes.SOUND_ON_FLAG) soundMenuBackground.play(); 

		input();

	}

	private void input(){

		if(Gdx.input.justTouched()){

			soundClick.play();

			Vector3 touchPoint = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			orthographicCamera.unproject(touchPoint);

			if(spriteArrowBACK.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				//TODO liberar tudo
				soundMenuBackground.stop();
				chickenRoadGame.setScreen(new MainMenuScreen(chickenRoadGame));
			}else{
				openFase(faseMenu.getClickedFase(touchPoint.x, touchPoint.y));
			}
		}
	}

	private void openFase(int i) {

		//fase 1
		if(i==0){
			chickenRoadGame.setScreen(new Play(Constantes.URL_MAP_FASE_1_0_1, chickenRoadGame));
		}else if(i==1){
			chickenRoadGame.setScreen(new Play(Constantes.URL_MAP_FASE_1_0_2, chickenRoadGame));
		}
	}

	@Override
	public void dispose() {

	}
}
