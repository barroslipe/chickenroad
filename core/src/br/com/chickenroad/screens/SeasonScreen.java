package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.Constantes;
import br.com.chickenroad.entities.MyPlayMusic;
import br.com.chickenroad.screens.screenparts.SeasonMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

/**
 * Implementa a tela de temporadas da aplicação 
 * 
 */
public class SeasonScreen extends ScreenBase {

	//temporadas da aplicação
	private SeasonMenu seasonMenu;

	private Texture textureBACK;
	private Sprite spriteArrowBACK;

	//background
	private Texture textureBackground;
	private Sprite spriteBackground;

	private Music soundPrincipal, soundClick;

	/**
	 * Inicialização dos atributos da classe
	 * @param aChickenRoadGame referência a classe principal do jogo
	 */
	public SeasonScreen(ChickenRoadGame aChickenRoadGame) {
		super(aChickenRoadGame);

		this.seasonMenu = new SeasonMenu(getAssetManager());

		textureBACK = getAssetManager().get(Constantes.URL_BACK_BUTTON);
		spriteArrowBACK = new Sprite(textureBACK);

		textureBackground = getAssetManager().get(Constantes.URL_BACKGROUND_ALL_SEASONS);
		spriteBackground = new Sprite(textureBackground);

		soundPrincipal = getAssetManager().get(Constantes.URL_SOUND_PRINCIPAL);
		soundClick = getAssetManager().get(Constantes.URL_SOUND_CLICK);

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
		spriteBackground.draw(chickenRoadGame.getSpriteBatch());
		seasonMenu.draw(chickenRoadGame.getSpriteBatch());		
		spriteArrowBACK.draw(chickenRoadGame.getSpriteBatch());
		chickenRoadGame.getSpriteBatch().end();

		MyPlayMusic.playSound(soundPrincipal);

	}
	/**
	 * Tratar a entrada de dados do mouse ou touchScreen
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		Vector3 touchPoint = new Vector3(screenX, screenY, 0);
		chickenRoadGame.getOrthographicCamera().unproject(touchPoint);

		//se tocar na seta de volta, transita para menu screen
		if(spriteArrowBACK.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
			MyPlayMusic.playSound(soundClick);
			chickenRoadGame.setScreenWithTransitionFade(new MainMenuScreen(chickenRoadGame));
		}else{
			openSeason(seasonMenu.getClickedSeason(touchPoint.x, touchPoint.y));
		}		
		return false;
	}

	/**
	 * Abrir a tela de fases para a temporada escolhida
	 * @param seasonId identificador da temporada
	 */
	private void openSeason(int seasonId) {

		if(seasonId==-1) return;
		else{
			MyPlayMusic.playSound(soundClick);
			chickenRoadGame.setScreenWithTransitionFade(new FasesScreen(chickenRoadGame, seasonId));
		}
	}
	/**
	 * Liberar recursos ao sair da tela
	 */
	@Override
	public void dispose() {
		this.seasonMenu = null;
	}
}
