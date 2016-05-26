package br.com.chickenroad.screens.screenparts;

import java.util.ArrayList;

import br.com.chickenroad.Constantes;
import br.com.chickenroad.dao.Fase;
import br.com.chickenroad.dao.PreferencesUser;
import br.com.chickenroad.screens.util.MyProperties;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Botões de fase da tela de temporadas escolhida
 *
 */
public class FasesMenu {

	private ArrayList<Sprite> spriteFaseList;

	private ArrayList<Fase> openFaseList;
	private ArrayList<Integer> maxScoreFase;

	private BitmapFont defaultFont;

	private Sprite spriteStar[];

	private int seasonId;

	/**
	 * Inicialização dos atributos da classe
	 * @param assetManager referência a classe que possui os recursos alocados
	 * @param seasonId identificador da temporada
	 */
	public FasesMenu(AssetManager assetManager, int aSeasonId){

		this.seasonId = aSeasonId;

		//lista de fases abertas para a temporada escolhida
		this.openFaseList = PreferencesUser.getOpenFases(seasonId);
		this.maxScoreFase = new ArrayList<Integer>();

		this.spriteFaseList = new ArrayList<Sprite>();

		this.spriteStar = new Sprite[3];
		this.spriteStar[0] = new Sprite((Texture)assetManager.get(Constantes.URL_STAR1_SCORE));
		this.spriteStar[1] = new Sprite((Texture)assetManager.get(Constantes.URL_STAR2_SCORE));
		this.spriteStar[2] = new Sprite((Texture)assetManager.get(Constantes.URL_STAR3_SCORE));

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constantes.URL_FONT_KRAASH_BLACK));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 8;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 2;
		defaultFont = generator.generateFont(parameter);
		defaultFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		generator.dispose();

		initFases(assetManager);
	}

	private void initFases(AssetManager assetManager) {	

		MyProperties myProperties = new MyProperties();

		int cont = 0;
		int positionY = Constantes.WORLD_HEIGHT/2+110;
		int positionX = Constantes.WORLD_WIDTH/2 - 245;
		int distanceBetweenFases_X = 105;
		int distanceBetweenFases_Y = 110;

		String picture;

		for(int i=0;i<Constantes.URL_FASE_PICTURE_LIST[seasonId].length;i++){
			if(i < openFaseList.size()){
				picture = Constantes.URL_FASE_PICTURE_LIST[seasonId][i];
				myProperties.loadProperties(Constantes.URL_MAPS[seasonId][i] + ".properties");
				this.maxScoreFase.add(Util.getMaxScoreFase(myProperties.getNumberEggs(), myProperties.getNumberEggs()));
			}else
				picture = Constantes.URL_FASE_BLOQUEADA;

			Sprite sprite = new Sprite((Texture)assetManager.get(picture));

			if(i%5 == 0) {

				if(cont != 0) positionY -= distanceBetweenFases_Y;

				cont = 0;
				sprite.setPosition(positionX, positionY);
			}else {
				++cont;
				sprite.setPosition(positionX + distanceBetweenFases_X*cont, positionY);
			}
			
			spriteFaseList.add(sprite);

		}
	}
	/**
	 * Desenhar os botões das temporadas e o score relacionado
	 * @param spriteBatch área de desenho da aplicação
	 */
	public void draw(SpriteBatch spriteBatch) {

		for(int i=0;i<spriteFaseList.size();i++){
			spriteFaseList.get(i).draw(spriteBatch);
			if(i < openFaseList.size()){
				if(openFaseList.get(i).getScore() == 0)
					continue;

				spriteStar[Util.getNumberStarPerSeason(openFaseList.get(i).getScore(), maxScoreFase.get(i)) - 1].setPosition(spriteFaseList.get(i).getX()+5, spriteFaseList.get(i).getY()+2);
				spriteStar[Util.getNumberStarPerSeason(openFaseList.get(i).getScore(), maxScoreFase.get(i)) - 1].draw(spriteBatch);
	
			}
		}
	}
	/**
	 * Verificar se houve o clique em alguma fase
	 * @param x posição x
	 * @param y posição y
	 * @return i quando houver clique no botão
	 * 		   -1 quando não houver clique no botão
	 */
	public int getClickedFase(float x, float y) {

		for(int i=0;i<Constantes.URL_FASE_PICTURE_LIST[seasonId].length;i++){
			if(spriteFaseList.get(i).getBoundingRectangle().contains(x, y)){
				if(i< openFaseList.size()){
					return i;
				}
			}
		}
		return -1;
	}
}
