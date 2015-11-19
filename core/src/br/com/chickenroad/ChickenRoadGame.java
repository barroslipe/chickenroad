package br.com.chickenroad;

import br.com.chickenroad.screens.ScreenBase;
import br.com.chickenroad.screens.SplashScreen;
import br.com.chickenroad.screens.transitions.ScreenTransition;
import br.com.chickenroad.screens.transitions.ScreenTransitionFade;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.ResourceManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

/**
 * 
 * Classe principal que inicia a lógica do jogo
 *
 */
public class ChickenRoadGame extends Game {

	private SpriteBatch spriteBatch;
	private ResourceManager resourceManager;
	private OrthographicCamera orthographicCamera;
	
	private boolean init;
	private ScreenBase currentScreen;
	private ScreenBase nextScreen;
	private FrameBuffer currentFrameBuffer;
	private FrameBuffer nextFrameBuffer;
	private float t; //tempo
	private ScreenTransition screenTransition; //interface criada para fazer transi��o

	@Override
	public void create () {

		this.resourceManager = new ResourceManager();
		this.orthographicCamera = new OrthographicCamera();
		//cria o "mundo"
		this.orthographicCamera.setToOrtho(false, Constantes.WORLD_WIDTH, Constantes.WORLD_HEIGHT);

		Gdx.input.setCatchBackKey(true);

		//carrega todos os arquivos
		this.resourceManager.load();

		//direciona para o Splash Screen e leva referencia da chickenroadgame.java
		setScreenWithTransitionFade(new SplashScreen(this));

	}
	public OrthographicCamera getOrthographicCamera() {
		return orthographicCamera;
	}
	/**
	 * Tempo da transição default de 0.5 ms
	 * @param screen
	 */
	public void setScreenWithTransitionFade (ScreenBase screen) {
		ScreenTransitionFade transition = new ScreenTransitionFade(1.2f);
		setScreenWithTransition(screen, transition);
	}
	/**
	 * Trocar de tela sem transição
	 * @param screen
	 */
	public void setScreen(ScreenBase screen){
		setScreenWithTransition(screen, null);
	}

	public void setScreenWithTransition (ScreenBase screen, ScreenTransition screenTransition) {

		int w = Constantes.WORLD_WIDTH;
		int h = Constantes.WORLD_HEIGHT;

		if (!init) {
			currentFrameBuffer = new FrameBuffer(Format.RGB888, w, h, false);
			nextFrameBuffer = new FrameBuffer(Format.RGB888, w, h, false);
			spriteBatch = new SpriteBatch();
			init = true;
		}

		nextScreen = screen;
		nextScreen.show();

		if (currentScreen != null)
			currentScreen.pause();

		nextScreen.disableInputProcessor();

		this.screenTransition = screenTransition;

		t = 0;
	}


	@Override
	public void render () {

		// get delta time and ensure an upper limit of one 60th second
		float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 60.0f);

		if (nextScreen == null) {

			if (currentScreen != null)
				currentScreen.render(deltaTime);

		} else {

			float duration = 0;

			if (screenTransition != null)
				duration = screenTransition.getDuration();

			t = Math.min(t + deltaTime, duration);


			if (screenTransition == null || t >= duration) {

				if (currentScreen != null)
					currentScreen.hide();

				nextScreen.resume();

				// enable input for next screen
				nextScreen.enableInputProcessor();
				// switch screens

				currentScreen = nextScreen;
				nextScreen = null;
				screenTransition = null;

			} else {

				currentFrameBuffer.begin();

				if (currentScreen != null)
					currentScreen.render(deltaTime);

				currentFrameBuffer.end();

				nextFrameBuffer.begin();

				nextScreen.render(deltaTime);

				nextFrameBuffer.end();

				float alpha = t/duration;
				screenTransition.render(spriteBatch, currentFrameBuffer.getColorBufferTexture(), nextFrameBuffer.getColorBufferTexture(), alpha);
			}
		}
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	@Override
	public void resize (int width, int height) {
		if (currentScreen != null) currentScreen.resize(width, height);
		if (nextScreen != null) nextScreen.resize(width, height);
	}

	@Override
	public void pause () {
		if (currentScreen != null) currentScreen.pause();
	}

	@Override
	public void resume () {
		if (currentScreen != null) currentScreen.resume();
	}


	@Override
	public void dispose () {
		if (currentScreen != null)
			currentScreen.hide();
		
		if (nextScreen != null)
			nextScreen.hide();
		
		if (init) {
			resourceManager.dispose();
			resourceManager = null;
			currentFrameBuffer.dispose();
			currentScreen = null;
			nextFrameBuffer.dispose();
			nextScreen = null;
			spriteBatch.dispose();
			init = false;
		}
	}
}
