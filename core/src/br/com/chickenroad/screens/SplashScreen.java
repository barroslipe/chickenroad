package br.com.chickenroad.screens;

import br.com.chickenroad.ChickenRoadGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * A tela servirá para carregar os dados que a aplicação necessita. 
 * Ela apresentará o logotipo da organização que desenvolveu, 
 * além de algo relacionado ao jogo como apresentação.
 *
 */
public class SplashScreen implements Screen {

	private ChickenRoadGame chickenRoadGame;
	
	private Texture textureBackground;
	private TextureRegion textureRegionBackground;
	private Sprite spriteBackground;
	
	private OrthographicCamera orthographicCamera;
	
	final long start = System.currentTimeMillis();

	public SplashScreen(ChickenRoadGame chickenRoadGame) {
		this.chickenRoadGame = chickenRoadGame;
		
		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false, 640, 480);
		
		textureBackground = new Texture("splashScreenBackground.jpg");
		textureRegionBackground = new TextureRegion(textureBackground, 0, 0, 640, 480);
		spriteBackground = new Sprite(textureRegionBackground);
		

		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

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
		//desenhar a porcentagem do carregamento dos dados
		new BitmapFont().draw(chickenRoadGame.getSpriteBatch(), "Carregando... "+chickenRoadGame.getResourceManager().getAssetManager().getProgress()*100 + "%", 640/2, 480/2 - 70);

		chickenRoadGame.getSpriteBatch().end();
		
		if(chickenRoadGame.getResourceManager().getAssetManager().getProgress() == 1){
			long now = System.currentTimeMillis() - start;
			if(now > 2000)
				chickenRoadGame.setScreen(new MainMenuScreen(chickenRoadGame));
		}
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {

		textureBackground.dispose();
	}

}
