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
 */
public class FasesScreen extends ScreenBase {

	private FasesMenu fasesMenu;

	private Texture textureBACK;
	private Sprite spriteArrowBACK;

	//background
	private Texture textureBackground;
	private Sprite spriteBackground;

	private Music soundPrincipal, soundClick;
	
	private int seasonId;
	
	/**
	 * Inicialização dos atributos da classe
	 * @param aChickenRoadGame referência a classe principal do jogo
	 * @param seasonId identificador da temporada selecionada
	 */
	public FasesScreen(ChickenRoadGame aChickenRoadGame, int seasonId) {
		super(aChickenRoadGame);

		this.seasonId = seasonId;
		this.fasesMenu = new FasesMenu(getAssetManager(), seasonId);

		textureBACK = getAssetManager().get(Constantes.URL_BACK_BUTTON);
		spriteArrowBACK = new Sprite(textureBACK);

		textureBackground = getAssetManager().get(Constantes.URL_BACKGROUND_SEASON);
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
		fasesMenu.draw(chickenRoadGame.getSpriteBatch());		
		spriteArrowBACK.draw(chickenRoadGame.getSpriteBatch());
		chickenRoadGame.getSpriteBatch().end();

		if(!soundPrincipal.isPlaying() && Constantes.SOUND_ON_FLAG) soundPrincipal.play(); 

	}
	/**
	 * Tratar a entrada de dados do mouse ou touchScreen
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		Vector3 touchPoint = new Vector3(screenX, screenY, 0);
		chickenRoadGame.getOrthographicCamera().unproject(touchPoint);

		//se tocar na seta de volta, transita para season screen
		if(spriteArrowBACK.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
			soundClick.play();
			chickenRoadGame.setScreenWithTransitionFade(new SeasonScreen(chickenRoadGame));
		}else{
			openFase(fasesMenu.getClickedFase(touchPoint.x, touchPoint.y));
		}		
		return false;
	}

	/**
	 * Abrir a fase
	 * @param faseId identificador da fase
	 */
	private void openFase(int faseId) {

		if(faseId==-1) return;
		else{
			soundClick.play();
			soundPrincipal.stop();
			if(faseId==0){
				chickenRoadGame.setScreen(new Play(Constantes.URL_MAP_FASE_1_0_1, chickenRoadGame, seasonId, faseId));
			}else if(faseId==1){
				chickenRoadGame.setScreen(new Play(Constantes.URL_MAP_FASE_1_0_2, chickenRoadGame, seasonId, faseId));
			}
		}
	}

	@Override
	public void dispose() {
		this.fasesMenu = null;
	}
}
