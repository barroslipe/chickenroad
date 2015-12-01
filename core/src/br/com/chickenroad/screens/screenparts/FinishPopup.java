package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.ResourceManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FinishPopup{

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


	public FinishPopup(ResourceManager resourceManager) {

		popupWidthSize = 340;
		popupHeightSize = 400;
		
		popupInitPositionX = (Constantes.WORLD_WIDTH - popupWidthSize)/2;
		popupInitPositionY = (Constantes.WORLD_HEIGHT - popupHeightSize)/2;
		
		Pixmap backgroundPixmap = new Pixmap(50, 50, Format.RGBA8888);
		backgroundPixmap.setColor(0,0,0,0.5f);
		backgroundPixmap.fill();

		this.backgroundTexture = new Texture(backgroundPixmap);
		this.backgroundTextureRegion = new TextureRegion(backgroundTexture, 0, 0, Constantes.WORLD_WIDTH, Constantes.WORLD_HEIGHT);

		Pixmap popupPixmap = new Pixmap(100, 100, Format.RGBA8888);
		popupPixmap.setColor(Color.BLACK);
		popupPixmap.fill();

		this.popupTexture = new Texture(popupPixmap);
		this.popupTextureRegion  = new TextureRegion(popupTexture, 0, 0, popupWidthSize, popupHeightSize);

		Texture texture = resourceManager.getAssetManager().get("congratulation.png");
		this.congratulationSprite = new Sprite(texture);
		this.congratulationSprite.setPosition(Constantes.WORLD_WIDTH/2 - congratulationSprite.getWidth()/2, Constantes.WORLD_HEIGHT/2 + Constantes.WORLD_HEIGHT/4);

		
		Texture texture3 = resourceManager.getAssetManager().get("restartFaseButton.png");
		this.restart = new Sprite(texture3);
		this.restart.setPosition(Constantes.WORLD_WIDTH/2 - restart.getWidth()/2, popupInitPositionY+50);
		
		Texture texture2 = resourceManager.getAssetManager().get("listFaseButton.png");
		this.backToMenu = new Sprite(texture2);
		this.backToMenu.setPosition(Constantes.WORLD_WIDTH/2 - restart.getWidth()/2 - 100, popupInitPositionY+50);
		
		Texture texture4 = resourceManager.getAssetManager().get("popupYesButton.png");
		this.next = new Sprite(texture4);
		this.next.setPosition(Constantes.WORLD_WIDTH/2 + 100, popupInitPositionY+50);


	}

	public void draw(Batch batch, float delta) {

		//spriteBatch.begin();

		batch.draw(backgroundTextureRegion, popupInitPositionX, popupInitPositionY);
		batch.draw(popupTextureRegion, popupInitPositionX, popupInitPositionY);

		congratulationSprite.draw(batch);
		backToMenu.draw(batch);
		restart.draw(batch);
		next.draw(batch);

		//batch.end();

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
