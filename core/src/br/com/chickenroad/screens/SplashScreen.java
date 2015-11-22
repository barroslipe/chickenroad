package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

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


	public SplashScreen(ChickenRoadGame aChickenRoadGame) {
		super(aChickenRoadGame);

		this.textureBackground = new Texture(Constantes.URL_BACKGROUND_SPLASHSCREEN);
		this.spriteBackground = new Sprite(textureBackground);

		this.nextScreen = true;

	}

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
		chickenRoadGame.getSpriteBatch().end();

		if(getAssetManager().getProgress() == 1){
			now = System.currentTimeMillis() - start;
			if(now > 2000 && nextScreen){
				nextScreen = false;
				chickenRoadGame.setScreenWithTransitionFade(new MainMenuScreen(chickenRoadGame));
			}
		}
	}

	@Override
	public void dispose(){
		this.textureBackground.dispose();
		this.textureBackground = null;
		this.spriteBackground = null;
	}
}
