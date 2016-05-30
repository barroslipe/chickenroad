package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.screens.PlayScreen;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PopupGameOver {

	private Sprite spriteBackground;

	private Sprite spriteFaseList;
	private Sprite spriteRestart;


	public PopupGameOver(AssetManager assetManager) {

		Texture texture = assetManager.get(Constantes.URL_GAMEOVER_POPUP);
		this.spriteBackground = new Sprite(texture);

		Texture restartFase = assetManager.get("restartFaseButton.png");
		this.spriteRestart = new Sprite(restartFase);
		this.spriteRestart.setScale(0.8f);

		Texture listFase = assetManager.get("listFaseButton.png");
		this.spriteFaseList = new Sprite(listFase);
		this.spriteFaseList.setScale(0.8f);
	}
	public boolean checkClickRestartButton(float x, float y) {
		if(spriteRestart.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickFaseListButton(float x, float y) {

		if(spriteFaseList.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}

	public void draw(SpriteBatch spriteBatch){

		spriteBackground.setPosition(Constantes.WORLD_WIDTH/2 - spriteBackground.getWidth()/2 + PlayScreen.deltaXPositionButtons,
				Constantes.WORLD_HEIGHT/2 - spriteBackground.getHeight()/2+PlayScreen.deltaYPositionButtons);		

		spriteRestart.setPosition(Constantes.WORLD_WIDTH/2 - 3*spriteRestart.getWidth()/2 + 30 +PlayScreen.deltaXPositionButtons,  10+PlayScreen.deltaYPositionButtons);
		spriteFaseList.setPosition(Constantes.WORLD_WIDTH/2 + spriteFaseList.getWidth()/2 - 30 +PlayScreen.deltaXPositionButtons,  10+PlayScreen.deltaYPositionButtons);

		spriteBackground.draw(spriteBatch);
		spriteRestart.draw(spriteBatch);
		spriteFaseList.draw(spriteBatch);
	}
}
