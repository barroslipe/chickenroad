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
	private String faseList[] = {"fase1.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png",
			"faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png",
			"faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png",
			"faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png"};

	private ArrayList<Sprite> spriteFaseList;

	private int fase_atual = 0;

	//construtor
	public FasesMenu(){
		soundRooster = Gdx.audio.newSound(Gdx.files.internal(Constantes.URL_SOUND_ROOSTER));

				//testando
		fase_atual = PreferencesUser.getFase();

		//TODO trocar figura e string
		spriteFaseList = new ArrayList<Sprite>();

		int cont = 0;
		int spriteFaseListHeight = Constantes.WORLD_HEIGHT/2+90;

		String picture;
		for(int i=0;i<faseList.length;i++){
			if(i <= fase_atual)
				picture = faseList[0];    //Aqui ser� apontada a fase correta. Por enquanto, ser� a figura de fase 1 para todas as fases ABERTAS.
			else
				picture = "faseBloqueada.png";

			Sprite sprite = new Sprite(new Texture(picture));
			spriteFaseList.add(sprite);

			if(i%5 == 0) {
				
				if(cont != 0) spriteFaseListHeight -= 110;
				
				cont = 0;
				spriteFaseList.get(i).setPosition(Constantes.WORLD_WIDTH/2 - 240, spriteFaseListHeight);
			}else {
				++cont;
				spriteFaseList.get(i).setPosition(Constantes.WORLD_WIDTH/2- 240 +105*cont, spriteFaseListHeight);
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
