package br.com.chickenroad.screens.screenparts;

import java.util.ArrayList;

import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.PreferencesUser;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class SeasonMenu {

	//com varias fases, trocar figuras
	private String faseList[] = {"fase1.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png",
			"faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png",
			"faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png",
			"faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png"};

	private ArrayList<Texture> textureFaseList;
	private ArrayList<TextureRegion> textureRegionFaseList;
	private ArrayList<Sprite> spriteFaseList;

	private int fase_atual = 0;

	public SeasonMenu(){

		//testando
		PreferencesUser.setFase(1);

		fase_atual = PreferencesUser.getFase();

		//TODO trocar figura e string
		textureFaseList = new ArrayList<Texture>();
		textureRegionFaseList = new ArrayList<TextureRegion>();
		spriteFaseList = new ArrayList<Sprite>();

		int cont = 0;
		int spriteFaseListHeight = Constantes.HEIGHT_BACKGROUND/2+50;

		String picture;
		for(int i=0;i<faseList.length;i++){
			if(i <= fase_atual)
				picture = faseList[0];    //Aqui ser� apontada a fase correta. Por enquanto, ser� a figura de fase 1 para todas as fases ABERTAS.
			else
				picture = "faseBloqueada.png";

			Texture texture = new Texture(picture);
			TextureRegion textureRegion = new TextureRegion(texture, 0,0,96,94);
			Sprite sprite = new Sprite(textureRegion);

			textureFaseList.add(texture);
			textureRegionFaseList.add(textureRegion);
			spriteFaseList.add(sprite);

			if((i+1)%6 == 0) {
				cont = 0;
				spriteFaseListHeight -= 110; 
				spriteFaseList.get(i).setPosition(Constantes.WIDTH_BACKGROUND/2-340+1350*cont, 
						spriteFaseListHeight);
			}
			else {
				spriteFaseList.get(i).setPosition(Constantes.WIDTH_BACKGROUND/2-340+150*cont, 
						spriteFaseListHeight);
				cont+=1;
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
				if(i<=fase_atual)
					return i;
			}
		}
		return -1;
	}
}
