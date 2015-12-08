package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.screens.screenparts.SeasonMenu;
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
public class SeasonScreen extends ScreenBase {

	private SeasonMenu seasonMenu;

	private Texture textureBACK;
	private Sprite spriteArrowBACK;

	//background
	private Texture textureBackground;
	private Sprite spriteBackground;

	private Music soundMenuBackground, soundClick;


	public SeasonScreen(ChickenRoadGame aChickenRoadGame) {
		super(aChickenRoadGame);

		this.seasonMenu = new SeasonMenu();

		textureBACK = getAssetManager().get(Constantes.URL_BACK_BUTTON);
		spriteArrowBACK = new Sprite(textureBACK);

		textureBackground = getAssetManager().get(Constantes.URL_BACKGROUND_ALL_SEASONS);
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
		seasonMenu.draw(chickenRoadGame.getSpriteBatch());		
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
			chickenRoadGame.setScreenWithTransitionFade(new MainMenuScreen(chickenRoadGame));
		}else{
			openSeason(seasonMenu.getClickedFase(touchPoint.x, touchPoint.y));
		}		
		return false;
	}

	private void openSeason(int i) {


		if(i==-1) return;
		else{
			soundClick.play();
			//passar a lista de fases da temporada clicada
			chickenRoadGame.setScreenWithTransitionFade(new FasesScreen(chickenRoadGame, i));
		}
	}

	@Override
	public void dispose() {

	}
}
