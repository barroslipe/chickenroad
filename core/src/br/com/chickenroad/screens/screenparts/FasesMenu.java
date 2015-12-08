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


public class FasesMenu {
	private AssetManager assetManager;
	private Sound soundRooster;
	//com varias fases, trocar figuras
	private String faseList[] = {"fases/fase_101.png", "fases/fase_102.png", "fases/fase_103.png", "fases/fase_104.png",
			"fases/fase_105.png", "fases/fase_106.png", "fases/fase_107.png", "fases/fase_108.png",
			"fases/fase_109.png", "fases/fase_110.png", "fases/fase_111.png", "fases/fase_112.png",
			"fases/fase_113.png", "fases/fase_114.png", "fases/fase_115.png"};

	private ArrayList<Sprite> spriteFaseList;

	private int fase_atual = 0;

	//construtor
	public FasesMenu(){
		soundRooster = Gdx.audio.newSound(Gdx.files.internal(Constantes.URL_SOUND_ROOSTER));

				//testando
		fase_atual = 0;//PreferencesUser.getFase();

		//TODO trocar figura e string
		spriteFaseList = new ArrayList<Sprite>();

		int cont = 0;
		int spriteFaseListHeight = Constantes.WORLD_HEIGHT/2+110;

		String picture;
		for(int i=0;i<faseList.length;i++){
			if(i <= fase_atual)
				picture = faseList[0];    //Aqui ser� apontada a fase correta. Por enquanto, ser� a figura de fase 1 para todas as fases ABERTAS.
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
		}
	}

	public int getClickedFase(float x, float y) {

		for(int i=0;i<faseList.length;i++){
			if(spriteFaseList.get(i).getBoundingRectangle().contains(x, y)){
				soundRooster.play();
				if(i<=fase_atual)
					return i;
			}
		}
		return -1;
	}
}
