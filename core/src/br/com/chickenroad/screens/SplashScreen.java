package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * A tela servirá para carregar os dados que a aplicação necessita. 
 * Ela apresentará algo relacionado ao jogo como apresentação.
 *
 */
public class SplashScreen extends ScreenBase {

	private Texture textureBackground;
	private Sprite spriteBackground;

	private final long start = System.currentTimeMillis();
	private long now;

	private boolean nextScreen;
	
	//características da palavra loading
	private BitmapFont defaultFont;

	/**
	 * Inicialização dos atributos da classe
	 * @param aChickenRoadGame referência a classe principal do jogo
	 */
	public SplashScreen(ChickenRoadGame aChickenRoadGame) {
		super(aChickenRoadGame);

		this.textureBackground = new Texture(Constantes.URL_BACKGROUND_SPLASHSCREEN);
		this.spriteBackground = new Sprite(textureBackground);

		this.nextScreen = true;

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constantes.URL_FONT_KRAASH_BLACK));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 8;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 2;
		defaultFont = generator.generateFont(parameter);
		generator.dispose(); // don't forget to dispose to 
		defaultFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);	
		
	}

	/**
	 * Renderizador principal da classe
	 * É executado no mínimo 2 segundos e pode se estender até o carregamento total dos arquivos que a aplicação necessita.
	 */
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//capturar o progresso do carregamento
		getAssetManager().update();

		chickenRoadGame.getOrthographicCamera().update();

		chickenRoadGame.getSpriteBatch().setProjectionMatrix(chickenRoadGame.getOrthographicCamera().combined);

		chickenRoadGame.getSpriteBatch().begin();
		spriteBackground.draw(chickenRoadGame.getSpriteBatch());
		defaultFont.draw(chickenRoadGame.getSpriteBatch(), "LOADING...", Constantes.WORLD_WIDTH-100, 30);
		chickenRoadGame.getSpriteBatch().end();

		if(getAssetManager().getProgress() == 1){
			now = System.currentTimeMillis() - start;
			if(now > 2000 && nextScreen){
				nextScreen = false;
				chickenRoadGame.setScreenWithTransitionFade(new MainMenuScreen(chickenRoadGame));
			}
		}
	}

	/**
	 * Liberar recursos ao sair da tela
	 */
	@Override
	public void dispose(){
		this.textureBackground.dispose();
		this.textureBackground = null;
		this.spriteBackground = null;
	}
}
