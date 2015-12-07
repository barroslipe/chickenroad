package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.screens.screenparts.FasesMenu;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

/**
 * Tela que apresenta as fases da aplicação
 * 
 *
 */
public class FasesScreen extends ScreenBase {

	private FasesMenu fasesMenu;

	private Texture textureBACK;
	private Sprite spriteArrowBACK;

	//background
	private Texture textureBackground;
	private Sprite spriteBackground;

	private Music soundMenuBackground, soundClick;


	public FasesScreen(ChickenRoadGame aChickenRoadGame) {
		super(aChickenRoadGame);

		this.fasesMenu = new FasesMenu();

		textureBACK = getAssetManager().get(Constantes.URL_BACK_BUTTON);
		spriteArrowBACK = new Sprite(textureBACK);

		textureBackground = getAssetManager().get(Constantes.URL_BACKGROUND_SEASON);
		spriteBackground = new Sprite(textureBackground);

		soundMenuBackground = getAssetManager().get(Constantes.URL_SOUND_MENU_BACKGROUND);
		soundClick = getAssetManager().get(Constantes.URL_SOUND_CLICK);

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		chickenRoadGame.getOrthographicCamera().update();
		chickenRoadGame.getSpriteBatch().setProjectionMatrix(chickenRoadGame.getOrthographicCamera().combined);

		chickenRoadGame.getSpriteBatch().begin();
		spriteBackground.draw(chickenRoadGame.getSpriteBatch());
		fasesMenu.draw(chickenRoadGame.getSpriteBatch());		
		spriteArrowBACK.draw(chickenRoadGame.getSpriteBatch());
		chickenRoadGame.getSpriteBatch().end();

		if(!soundMenuBackground.isPlaying() && Constantes.SOUND_ON_FLAG) soundMenuBackground.play(); 

	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {


		Vector3 touchPoint = new Vector3(screenX, screenY, 0);
		chickenRoadGame.getOrthographicCamera().unproject(touchPoint);

		//se tocar na seta de volta, transita para menu screen
		if(spriteArrowBACK.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
			//TODO liberar tudo
			soundClick.play();
			//soundMenuBackground.stop();
			chickenRoadGame.setScreenWithTransitionFade(new SeasonScreen(chickenRoadGame));
		}else{
			openFase(fasesMenu.getClickedFase(touchPoint.x, touchPoint.y));
		}		
		return false;
	}

	private void openFase(int i) {

		if(i==-1) return;
		else{
			soundClick.play();

			if(i==0){
				chickenRoadGame.setScreen(new Play(Constantes.URL_MAP_FASE_1_0_1, chickenRoadGame));
			}else if(i==1){
				chickenRoadGame.setScreen(new Play(Constantes.URL_MAP_FASE_1_0_2, chickenRoadGame));
			}
		}
	}

	@Override
	public void dispose() {
		
	}
}
