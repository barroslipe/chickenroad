package br.com.chickenroad.screens.screenparts;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.PreferencesUser;


public class SeasonMenu {
	private AssetManager assetManager;
	private Sound soundRooster;
	//com varias fases, trocar figuras
	private String seasonList[] = {"seasons/seasonRoosterSong.png", "seasons/seasonFireInFarm.png",  
					"seasons/seasonInvasion1.png", "seasons/seasonHorrorNight.png"};

	private ArrayList<Sprite> spriteSeasonList;

	private int sessaoAtual = 0;

	//construtor
	public SeasonMenu(){
		soundRooster = Gdx.audio.newSound(Gdx.files.internal(Constantes.URL_SOUND_ROOSTER));

		//testando
		sessaoAtual = 0;//PreferencesUser.getFase();

		//TODO trocar figura e string
		spriteSeasonList = new ArrayList<Sprite>();

		int cont = 0;
		int spriteFaseListHeight = 100;// Constantes.WORLD_HEIGHT/2+90;

		String picture;
		for(int i=0;i<seasonList.length;i++){
			if(i <= sessaoAtual)
				picture = seasonList[0];    //Aqui ser� apontada a fase correta. Por enquanto, ser� a figura de fase 1 para todas as fases ABERTAS.
			else
				picture = seasonList[i];

			Sprite sprite = new Sprite(new Texture(picture));
			spriteSeasonList.add(sprite);

			if(i%5 == 0) {
				
				if(cont != 0) spriteFaseListHeight -= 110;
				
				cont = 0;
				spriteSeasonList.get(i).setPosition(Constantes.WORLD_WIDTH/2 - 236, spriteFaseListHeight);
			}else {
				++cont;
				spriteSeasonList.get(i).setPosition(Constantes.WORLD_WIDTH/2- 236 +125*cont, spriteFaseListHeight);
			}
		}
	}

	public void draw(SpriteBatch spriteBatch) {

		for(int i=0;i<spriteSeasonList.size();i++){
			spriteSeasonList.get(i).draw(spriteBatch);	
		}
	}

	public int getClickedFase(float x, float y) {

		for(int i=0;i<seasonList.length;i++){
			if(spriteSeasonList.get(i).getBoundingRectangle().contains(x, y)){
				soundRooster.play();
				if(i<=sessaoAtual)
					return i;
			}
		}
		return -1;
	}
}
