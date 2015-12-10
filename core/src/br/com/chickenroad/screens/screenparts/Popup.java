package br.com.chickenroad.screens.screenparts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.ResourceManager;

public class Popup{

	private Sprite congratulationSprite;
	private Sprite next, restart, backToMenu;

	private Texture backgroundTexture;
	private TextureRegion backgroundTextureRegion;

	private Texture popupTexture;
	private TextureRegion popupTextureRegion;
	
	public void setPopupInitPositionX(int popupInitPositionX) {
		this.popupInitPositionX = popupInitPositionX;
	}

	public void setPopupInitPositionY(int popupInitPositionY) {
		this.popupInitPositionY = popupInitPositionY;
	}
	private int popupInitPositionX, popupInitPositionY, popupWidthSize, popupHeightSize;


	public Popup(ResourceManager resourceManager, PopupTypes popupTypes) {
		popupWidthSize = 50;
		popupHeightSize = 50;
		
		popupInitPositionX = (Constantes.WORLD_WIDTH - popupWidthSize)/2;
		popupInitPositionY = (Constantes.WORLD_HEIGHT - popupHeightSize)/2;
		
		Pixmap backgroundPixmap = new Pixmap(50, 50, Format.RGBA8888);
		backgroundPixmap.setColor(0,0,0,0.5f);
		backgroundPixmap.fill();

		this.backgroundTexture = new Texture(backgroundPixmap);
		this.backgroundTextureRegion = new TextureRegion(backgroundTexture, 0, 0, Constantes.WORLD_WIDTH, Constantes.WORLD_HEIGHT);

		Pixmap popupPixmap = new Pixmap(50, 50, Format.RGBA8888);
		popupPixmap.setColor(Color.BLACK);
		popupPixmap.fill();

		this.popupTexture = new Texture(popupPixmap);
		this.popupTextureRegion  = new TextureRegion(popupTexture, 0, 0, popupWidthSize, popupHeightSize);

		Texture texture = resourceManager.getAssetManager().get(Constantes.URL_GAMEOVER_POPUP);
		this.congratulationSprite = new Sprite(texture);
		this.congratulationSprite.setPosition((Constantes.WORLD_WIDTH/2) , (Constantes.WORLD_HEIGHT/2)+80);

		
		Texture texture3 = resourceManager.getAssetManager().get("restartFaseButton.png");
		this.restart = new Sprite(texture3);
		this.restart.setPosition(Constantes.WORLD_WIDTH/2 - restart.getWidth()/2+100, popupInitPositionY+50);
		
		Texture texture2 = resourceManager.getAssetManager().get("listFaseButton.png");
		this.backToMenu = new Sprite(texture2);
		this.backToMenu.setPosition(Constantes.WORLD_WIDTH/2 - restart.getWidth()/2 +100, popupInitPositionY+50);
		
		Texture texture4 = resourceManager.getAssetManager().get(Constantes.URL_NEXT_FASE_BUTTON);
		this.next = new Sprite(texture4);
		this.next.setPosition(Constantes.WORLD_WIDTH/2 + 200, popupInitPositionY+50);

		switch(popupTypes) {
		case GAME_TUTORIAL:
			

			break;	

		case GIFT_TUTORIAL:
			
			
			break;
			
		case END_FASE:
		
				break;
					
		}
		
		
		
		
	}

	public void draw(Batch batch, float delta) {


		batch.draw(backgroundTextureRegion, popupInitPositionX, popupInitPositionY);
		batch.draw(popupTextureRegion, popupInitPositionX, popupInitPositionY);

		congratulationSprite.draw(batch);
		backToMenu.draw(batch);
		restart.draw(batch);
		next.draw(batch);


	}
	
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
	}
	
	
	
}
