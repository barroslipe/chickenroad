package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.screens.PlayScreen;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PopupTutorial{

	private Sprite tutorialSprite;
	private Sprite okTutorial;
	
	private boolean visible;

	public PopupTutorial(AssetManager assetManager) {

//		Pixmap backgroundPixmap = new Pixmap(300, Constantes.WORLD_HEIGHT, Format.RGBA8888);
//		backgroundPixmap.setColor(Color.BLACK);
//		backgroundPixmap.fill();

		Texture texture = assetManager.get(Constantes.URL_TURORIAL_POPUP);
		this.tutorialSprite = new Sprite(texture);

		Texture texture1 = assetManager.get(Constantes.URL_OK_BUTTON);
		this.okTutorial = new Sprite(texture1);
		
		this.visible = false;
	}


	public void draw(Batch batch) {
		
		if(!visible) return;

		this.tutorialSprite.setPosition((Constantes.WORLD_WIDTH - tutorialSprite.getWidth())/2 + PlayScreen.deltaXPositionButtons,
				(Constantes.WORLD_HEIGHT - tutorialSprite.getHeight())/2+PlayScreen.deltaYPositionButtons);
		this.okTutorial.setPosition(Constantes.WORLD_WIDTH - okTutorial.getWidth()/2 -(Constantes.WORLD_WIDTH - tutorialSprite.getWidth())/2+PlayScreen.deltaXPositionButtons,
				(Constantes.WORLD_HEIGHT - tutorialSprite.getHeight())/2 - okTutorial.getHeight()/2+PlayScreen.deltaYPositionButtons);

		tutorialSprite.draw(batch);
		okTutorial.draw(batch);


	}

	public boolean checkClickOkTutorialButton(float x, float y) {

		if(okTutorial.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}

	public Sprite getTutorialSprite() {
		return tutorialSprite;
	}

	public void setTutorialSprite(Sprite tutorialSprite) {
		this.tutorialSprite = tutorialSprite;
	}

	public Sprite getOkTutorial() {
		return okTutorial;
	}

	public void setOkTutorial(Sprite okTutorial) {
		this.okTutorial = okTutorial;
	}


	public void setVisible(boolean aVisible) {
		this.visible = aVisible;
		
	}


	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}
}
