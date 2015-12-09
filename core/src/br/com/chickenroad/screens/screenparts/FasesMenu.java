package br.com.chickenroad.screens.screenparts;

import java.util.ArrayList;

import br.com.chickenroad.dao.Fase;
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


public class FasesMenu {
	private Sound soundRooster;
	//com varias fases, trocar figuras
	private String fasePicturesList[] = {"fases/fase_101.png", "fases/fase_102.png", "fases/fase_103.png", "fases/fase_104.png",
			"fases/fase_105.png", "fases/fase_106.png", "fases/fase_107.png", "fases/fase_108.png",
			"fases/fase_109.png", "fases/fase_110.png", "fases/fase_111.png", "fases/fase_112.png",
			"fases/fase_113.png", "fases/fase_114.png", "fases/fase_115.png"};

	private ArrayList<Sprite> spriteFaseList;
	
	private ArrayList<Fase> openFaseList;
	
	private BitmapFont defaultFont;

	/**
	 * 
	 * @param seasonId identificador da temporada
	 */
	public FasesMenu(int seasonId){
		
		this.soundRooster = Gdx.audio.newSound(Gdx.files.internal(Constantes.URL_SOUND_ROOSTER));
		this.openFaseList = PreferencesUser.getFases(seasonId);
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constantes.URL_FONT_KRAASH_BLACK));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 8;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 2;
		defaultFont = generator.generateFont(parameter);
		generator.dispose(); // don't forget to dispose to 
		defaultFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);	

		//TODO trocar figura e string
		spriteFaseList = new ArrayList<Sprite>();

		int cont = 0;
		int spriteFaseListHeight = Constantes.WORLD_HEIGHT/2+110;

		String picture;
		for(int i=0;i<fasePicturesList.length;i++){
			if(i < openFaseList.size())
				picture = fasePicturesList[0];    //Aqui ser� apontada a fase correta. Por enquanto, ser� a figura de fase 1 para todas as fases ABERTAS.
			else
				picture = "fases/fase_bloqueada.png";

			Sprite sprite = new Sprite(new Texture(picture));
			spriteFaseList.add(sprite);

			if(i%5 == 0) {
				
				if(cont != 0) spriteFaseListHeight -= 110;
				
				cont = 0;
				spriteFaseList.get(i).setPosition(Constantes.WORLD_WIDTH/2 - 245, spriteFaseListHeight);
			}else {
				++cont;
				spriteFaseList.get(i).setPosition(Constantes.WORLD_WIDTH/2- 245 +105*cont, spriteFaseListHeight);
			}
		}
	}

	public void draw(SpriteBatch spriteBatch) {

		for(int i=0;i<spriteFaseList.size();i++){
			spriteFaseList.get(i).draw(spriteBatch);
			if(i< openFaseList.size()){
				defaultFont.draw(spriteBatch, Integer.toString(openFaseList.get(i).getScore()), spriteFaseList.get(i).getX()+20 , spriteFaseList.get(i).getY() - 5);
			}
		}
	}

	public int getClickedFase(float x, float y) {

		for(int i=0;i<fasePicturesList.length;i++){
			if(spriteFaseList.get(i).getBoundingRectangle().contains(x, y)){
				soundRooster.play();
				if(i< openFaseList.size())
					return i;
			}
		}
		return -1;
	}
}
