package br.com.chickenroad.screens.screenparts;

import java.util.ArrayList;

import br.com.chickenroad.dao.Season;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.PreferencesUser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;


public class SeasonMenu {
	
	private Sound soundRooster;
	
	//TODO retirar os cadeados das figura
	private String seasonPicturesList[] = {"seasons/seasonRoosterSong.png", "seasons/seasonFireInFarm.png",  
					"seasons/seasonInvasion1.png", "seasons/seasonHorrorNight.png"};

	private ArrayList<Sprite> seasonSpriteList;

	//lista de temporadas abertas ao jogador
	private ArrayList<Season> openSeasonList;
	
	private BitmapFont defaultFont;

	
	public SeasonMenu(){
		
		soundRooster = Gdx.audio.newSound(Gdx.files.internal(Constantes.URL_SOUND_ROOSTER));

		//TODO trocar figura e string
		this.seasonSpriteList = new ArrayList<Sprite>();
		this.openSeasonList = PreferencesUser.getSeasons();
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constantes.URL_FONT_KRAASH_BLACK));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 8;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 2;
		defaultFont = generator.generateFont(parameter);
		generator.dispose(); // don't forget to dispose to 
		defaultFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);	

		int cont = 0;
		int spriteFaseListHeight = 100;// Constantes.WORLD_HEIGHT/2+90;

		String picture;
		
		for(int i=0;i<seasonPicturesList.length;i++){
			if(i < openSeasonList.size())
				picture = seasonPicturesList[0];
			else{
				picture = seasonPicturesList[i];
			}
			Sprite sprite = new Sprite(new Texture(picture));
			seasonSpriteList.add(sprite);

			if(i%5 == 0) {
				
				if(cont != 0) spriteFaseListHeight -= 110;
				
				cont = 0;
				seasonSpriteList.get(i).setPosition(Constantes.WORLD_WIDTH/2 - 236, spriteFaseListHeight);
			}else {
				++cont;
				seasonSpriteList.get(i).setPosition(Constantes.WORLD_WIDTH/2- 236 +125*cont, spriteFaseListHeight);
			}
		}
	}

	public void draw(SpriteBatch spriteBatch) {
		
		for(int i=0;i<seasonSpriteList.size();i++){
			seasonSpriteList.get(i).draw(spriteBatch);	
			if(i< openSeasonList.size()){
				defaultFont.draw(spriteBatch, Integer.toString(openSeasonList.get(i).getSeasonTotalScore()), seasonSpriteList.get(i).getX()+30 , seasonSpriteList.get(i).getY()+80);
			}
		}
	}

	public int getClickedFase(float x, float y) {
		
		for(int i=0;i<seasonPicturesList.length;i++){
			if(seasonSpriteList.get(i).getBoundingRectangle().contains(x, y)){
				soundRooster.play();
				if(i<openSeasonList.size())
					return i;
			}
		}
		return -1;
	}
}
