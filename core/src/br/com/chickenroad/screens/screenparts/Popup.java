package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.screens.PlayScreen;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Popup{


	private Sprite tutorialSprite;
	private Sprite next, restart, backToMenu, okTutorial;

	private Texture backgroundTexture;
	private TextureRegion backgroundTextureRegion;

	private Texture popupTexture;
	//private TextureRegion popupTextureRegion;
	private int popupInitPositionX, popupInitPositionY, popupWidthSize, popupHeightSize;

	private PopupTypes popupTypes;

	public Popup(AssetManager assetManager, PopupTypes popupTypes) {

		this.popupTypes = popupTypes;
		popupWidthSize = 100;
		popupHeightSize = 100;

		popupInitPositionX = (Constantes.WORLD_WIDTH - popupWidthSize)/2;
		popupInitPositionY = (Constantes.WORLD_HEIGHT - popupHeightSize)/2;

		Pixmap backgroundPixmap = new Pixmap(300, Constantes.WORLD_HEIGHT, Format.RGBA8888);
		backgroundPixmap.setColor(Color.BLACK);
		backgroundPixmap.fill();

		this.backgroundTexture = new Texture(backgroundPixmap);
		this.backgroundTextureRegion = new TextureRegion(backgroundTexture, 0, 0, 300, Constantes.WORLD_HEIGHT);

		Texture texture = assetManager.get(Constantes.URL_TURORIAL_POPUP);
		this.tutorialSprite = new Sprite(texture);
		//this.tutorialSprite.setPosition((Constantes.WORLD_WIDTH/2)+130 , (Constantes.WORLD_HEIGHT/3)-65);

		Texture texture1 = assetManager.get(Constantes.URL_OK_BUTTON);
		this.okTutorial = new Sprite(texture1);
		//this.okTutorial.setPosition((Constantes.WORLD_WIDTH/2)+160+420, (Constantes.WORLD_HEIGHT/3)-90);

		/*		
		popupWidthSize = 300;
		popupHeightSize = 200;

		popupInitPositionX = (Constantes.WORLD_WIDTH - popupWidthSize)/2;
		popupInitPositionY = (Constantes.WORLD_HEIGHT - popupHeightSize)/2;

		Pixmap backgroundPixmap = new Pixmap(300, Constantes.WORLD_HEIGHT, Format.RGBA8888);
		backgroundPixmap.setColor(Color.BLACK);
		backgroundPixmap.fill();

		this.backgroundTexture = new Texture(backgroundPixmap);
		this.backgroundTextureRegion = new TextureRegion(backgroundTexture, 0, 0, 300, Constantes.WORLD_HEIGHT);

		Texture texture = resourceManager.getAssetManager().get(Constantes.URL_GAMEOVER_POPUP);
		this.congratulationSprite = new Sprite(texture);
		this.congratulationSprite.setPosition((Constantes.WORLD_WIDTH/2) , (Constantes.WORLD_HEIGHT/2)+90);

		Texture texture1 = resourceManager.getAssetManager().get(Constantes.URL_TURORIAL_POPUP);
		this.restart = new Sprite(texture1);
		this.restart.setPosition(Constantes.WORLD_WIDTH/2 - restart.getWidth()/2+30, popupInitPositionY+50);

		Texture texture2 = resourceManager.getAssetManager().get("restartFaseButton.png");
		this.backToMenu = new Sprite(texture2);
		this.backToMenu.setPosition(Constantes.WORLD_WIDTH/2 - restart.getWidth()/2 +100, popupInitPositionY+50);

		Texture texture3 = resourceManager.getAssetManager().get(Constantes.URL_NEXT_FASE_BUTTON);
		this.next = new Sprite(texture3);
		this.next.setPosition(Constantes.WORLD_WIDTH/2- restart.getWidth()/2 +170, popupInitPositionY+50);

		 */		
		switch(popupTypes) {
		case GAME_TUTORIAL:


			break;	

		case GIFT_TUTORIAL:


			break;

		case END_FASE:

			break;

		}




	}


	public void setPopupInitPositionX(int popupInitPositionX) {
		this.popupInitPositionX = popupInitPositionX;
	}

	public void setPopupInitPositionY(int popupInitPositionY) {
		this.popupInitPositionY = popupInitPositionY;
	}

	public void draw(Batch batch, float delta) {

		if(popupTypes == PopupTypes.GAME_TUTORIAL) {

			this.tutorialSprite.setPosition((Constantes.WORLD_WIDTH - tutorialSprite.getWidth())/2 + PlayScreen.deltaXPositionButtons,
					(Constantes.WORLD_HEIGHT - tutorialSprite.getHeight())/2+PlayScreen.deltaYPositionButtons);
			this.okTutorial.setPosition(Constantes.WORLD_WIDTH - okTutorial.getWidth()/2 -(Constantes.WORLD_WIDTH - tutorialSprite.getWidth())/2+PlayScreen.deltaXPositionButtons,
					(Constantes.WORLD_HEIGHT - tutorialSprite.getHeight())/2 - okTutorial.getHeight()/2+PlayScreen.deltaYPositionButtons);

			tutorialSprite.draw(batch);
			okTutorial.draw(batch);
		}

		//		batch.draw(backgroundTextureRegion, popupInitPositionX, popupInitPositionY);

		//	batch.draw(popupTextureRegion, popupInitPositionX, popupInitPositionY);


		//backToMenu.draw(batch);
		//restart.draw(batch);
		//next.draw(batch);


	}



	public boolean checkClickOkTutorialButton(float x, float y) {

		if(okTutorial.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	/*
	public boolean checkClickRestartButton(float x, float y) {

		if(restart.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickBackMenuButton(float x, float y) {

		if(backToMenu.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClicNextButton(float x, float y) {

		if(next.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}*/


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
}
