package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A tela servirá para carregar os dados que a aplicação necessita. 
 * Ela apresentará o logotipo da organização que desenvolveu, 
 * além de algo relacionado ao jogo como apresentação.
 *
 */
public class SplashScreen extends ScreenAdapter {

	private ChickenRoadGame chickenRoadGame;

	private Texture textureBackground;
	private TextureRegion textureRegionBackground;
	private Sprite spriteBackground;

	private OrthographicCamera orthographicCamera;

	private final long start = System.currentTimeMillis();
	private long now;

	public SplashScreen(ChickenRoadGame chickenRoadGame) {

		this.chickenRoadGame = chickenRoadGame;

		this.orthographicCamera = new OrthographicCamera();
		this.orthographicCamera.setToOrtho(false, 640, 480);

		this.textureBackground = new Texture("splashScreenBackground.jpg");
		this.textureRegionBackground = new TextureRegion(textureBackground, 0, 0, 640, 480);
		this.spriteBackground = new Sprite(textureRegionBackground);

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//capturar o progresso do carregamento
		chickenRoadGame.getResourceManager().getAssetManager().update();

		orthographicCamera.update();

		chickenRoadGame.getSpriteBatch().setProjectionMatrix(orthographicCamera.combined);

		chickenRoadGame.getSpriteBatch().begin();
		spriteBackground.draw(chickenRoadGame.getSpriteBatch());
		chickenRoadGame.getSpriteBatch().end();

		if(chickenRoadGame.getResourceManager().getAssetManager().getProgress() == 1){
			now = System.currentTimeMillis() - start;
			if(now > 2000){
				chickenRoadGame.setScreen(new MainMenuScreen(chickenRoadGame));
				dispose();
			}
		}
	}

	@Override
	public void dispose() {
		this.textureBackground.dispose();
		this.textureBackground = null;
		this.textureRegionBackground = null;
		this.spriteBackground = null;
		this.orthographicCamera = null;
		this.chickenRoadGame = null;
	}
}
