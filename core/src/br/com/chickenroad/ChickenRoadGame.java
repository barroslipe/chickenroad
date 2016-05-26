package br.com.chickenroad;

import br.com.chickenroad.screens.ScreenBase;
import br.com.chickenroad.screens.SplashScreen;
import br.com.chickenroad.screens.transitions.ScreenTransition;
import br.com.chickenroad.screens.transitions.ScreenTransitionFade;

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

	private ScreenBase currentScreen;
	private ScreenBase nextScreen;
	private FrameBuffer currentFrameBuffer;
	private FrameBuffer nextFrameBuffer;
	private float timer;
	private ScreenTransition screenTransition;

	@Override
	public void create () {

		this.spriteBatch = new SpriteBatch();
		this.orthographicCamera = new OrthographicCamera();
		this.orthographicCamera.setToOrtho(false, Constantes.WORLD_WIDTH, Constantes.WORLD_HEIGHT);
		
		this.currentFrameBuffer = new FrameBuffer(Format.RGB888, Constantes.WORLD_WIDTH, Constantes.WORLD_HEIGHT, false);
		this.nextFrameBuffer = new FrameBuffer(Format.RGB888,  Constantes.WORLD_WIDTH, Constantes.WORLD_HEIGHT, false);
		this.resourceManager = new ResourceManager();
		
		Gdx.input.setCatchBackKey(true);
		setScreenWithTransitionFade(new SplashScreen(this));

	}
	/**
	 * Tempo da transição
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

	private void setScreenWithTransition (ScreenBase screenBase, ScreenTransition aScreenTransition) {

		nextScreen = screenBase;
		nextScreen.show();

		if (currentScreen != null)
			currentScreen.pause();

		nextScreen.disableInputProcessor();

		this.screenTransition = aScreenTransition;

		this.timer = 0;
	}


	@Override
	public void render () {

		float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 60.0f);

		if (nextScreen == null) {

			if (currentScreen != null)
				currentScreen.render(deltaTime);

		} else {

			float duration = 0;

			if (screenTransition != null)
				duration = screenTransition.getDuration();

			timer = Math.min(timer + deltaTime, duration);


			if (screenTransition == null || timer >= duration) {

				if (currentScreen != null)
					currentScreen.dispose();

				nextScreen.resume();

				nextScreen.enableInputProcessor();

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

				float alpha = timer/duration;

				screenTransition.render(spriteBatch, currentFrameBuffer.getColorBufferTexture(), nextFrameBuffer.getColorBufferTexture(), alpha);
			}
		}
	}

	public OrthographicCamera getOrthographicCamera() {
		return orthographicCamera;
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
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
			currentScreen.dispose();

		if (nextScreen != null)
			nextScreen.dispose();

		resourceManager.dispose();
		resourceManager = null;
		currentFrameBuffer.dispose();
		currentScreen = null;
		nextFrameBuffer.dispose();
		nextScreen = null;
		spriteBatch.dispose();
	}
}
