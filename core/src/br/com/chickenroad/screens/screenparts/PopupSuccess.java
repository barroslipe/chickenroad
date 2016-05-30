package br.com.chickenroad.screens.screenparts;

import br.com.chickenroad.Constantes;
import br.com.chickenroad.screens.PlayScreen;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class PopupSuccess {

	private Sprite spriteBackground;

	private Sprite spriteFaseList;
	private Sprite spriteNextFase;
	private Sprite spriteRestart;

	private Sprite spriteStar[];

	private BitmapFont scoreFont;
	private GlyphLayout glyphLayout;

	public PopupSuccess(AssetManager assetManager){

		this.glyphLayout = new GlyphLayout();

		Texture texture = assetManager.get(Constantes.URL_SUCCESS_POPUP);
		this.spriteBackground = new Sprite(texture);

		Texture listFase = assetManager.get("listFaseButton.png");
		this.spriteFaseList = new Sprite(listFase);
		this.spriteFaseList.setScale(0.8f);

		Texture nextFase = assetManager.get("nextFaseButton.png");
		this.spriteNextFase = new Sprite(nextFase);
		this.spriteNextFase.setScale(0.8f);

		Texture restartFase = assetManager.get("restartFaseButton.png");
		this.spriteRestart = new Sprite(restartFase);
		this.spriteRestart.setScale(0.8f);

		this.spriteStar = new Sprite[3];
		this.spriteStar[0] = new Sprite((Texture)assetManager.get(Constantes.URL_STAR1_POPUP));
		this.spriteStar[1] = new Sprite((Texture)assetManager.get(Constantes.URL_STAR2_POPUP));
		this.spriteStar[2] = new Sprite((Texture)assetManager.get(Constantes.URL_STAR3_POPUP));

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constantes.URL_FONT_KRAASH_BLACK));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 2;
		parameter.size = 20;
		scoreFont = generator.generateFont(parameter);
		generator.dispose();
		scoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);	


	}

	public boolean checkClickRestartButton(float x, float y) {
		if(spriteRestart.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickNextButton(float x, float y) {

		if(spriteNextFase.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}
	public boolean checkClickFaseListButton(float x, float y) {

		if(spriteFaseList.getBoundingRectangle().contains(x, y))
			return true;

		return false;
	}

	public void draw(SpriteBatch spriteBatch, int scoreGame, Integer maxScoreGame){

		spriteBackground.setPosition(Constantes.WORLD_WIDTH/2 - spriteBackground.getWidth()/2 + PlayScreen.deltaXPositionButtons,
				Constantes.WORLD_HEIGHT/2 - spriteBackground.getHeight()/2+PlayScreen.deltaYPositionButtons);		

		spriteFaseList.setPosition(Constantes.WORLD_WIDTH/2 - 200 +PlayScreen.deltaXPositionButtons,  90+PlayScreen.deltaYPositionButtons);
		spriteRestart.setPosition(Constantes.WORLD_WIDTH/2  -50+ PlayScreen.deltaXPositionButtons,  90+PlayScreen.deltaYPositionButtons);
		spriteNextFase.setPosition(Constantes.WORLD_WIDTH/2 +100 +PlayScreen.deltaXPositionButtons,  90+PlayScreen.deltaYPositionButtons);

		spriteBackground.draw(spriteBatch);

		spriteFaseList.draw(spriteBatch);
		spriteNextFase.draw(spriteBatch);
		spriteRestart.draw(spriteBatch);

		spriteStar[Util.getNumberStarPerSeason(scoreGame, maxScoreGame)-1].setPosition(Constantes.WORLD_WIDTH/2 - 157 +PlayScreen.deltaXPositionButtons, 207+PlayScreen.deltaYPositionButtons);
		spriteStar[Util.getNumberStarPerSeason(scoreGame, maxScoreGame)-1].draw(spriteBatch);

		this.glyphLayout.setText(scoreFont, ""+scoreGame);
		scoreFont.draw(spriteBatch, ""+scoreGame, spriteRestart.getX() + spriteRestart.getWidth()/2 - glyphLayout.width/2, spriteRestart.getY()+135);

	}
}
