package br.com.chickenroad.screens.screenparts;

import java.util.ArrayList;

import br.com.chickenroad.dao.PreferencesUser;
import br.com.chickenroad.dao.Season;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.MyProperties;
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

/**
 * Botões de temporadas da tela de temporadas da aplicação
 *
 */
public class SeasonMenu {

	//lista de sprites das temporadas
	private ArrayList<Sprite> seasonSpriteList;

	//lista de temporadas abertas ao jogador - base de dados
	private ArrayList<Season> openSeasonList;
	private ArrayList<Integer> maxScoreFase;

	private BitmapFont defaultFont;

	/*
	 * Auxiliar a impressão do score em cada temporada
	 */
	private GlyphLayout glyphLayout;
	private String textScore, textStar;



	/**
	 * Inicialização dos atributos da classe
	 * @param assetManager referência a classe que possui os recursos alocados
	 */
	public SeasonMenu(AssetManager assetManager){

		this.openSeasonList = PreferencesUser.getSeasons();
		this.maxScoreFase = new ArrayList<Integer>();


		this.glyphLayout = new GlyphLayout();
		this.seasonSpriteList = new ArrayList<Sprite>();

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constantes.URL_FONT_KRAASH_BLACK));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 8;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 2;

		this.defaultFont = generator.generateFont(parameter);
		this.defaultFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);

		generator.dispose(); 

		initSeasons(assetManager);
	}

	private void initSeasons(AssetManager assetManager) {

		MyProperties myProperties = new MyProperties();

		int cont = 0;
		int positionY = 100;

		for(int i=0;i<Constantes.URL_SEASON_PICTURE_LIST.length;i++){
			if(i< openSeasonList.size()){
				for(int j=0;j<openSeasonList.get(i).getFaseList().size();j++){
					myProperties.loadProperties(Constantes.URL_MAPS[i][j] + ".properties");
					maxScoreFase.add(Util.getMaxScoreFase(myProperties.getNumberEggs(), myProperties.getNumberCorns()));
				}
			}

			Sprite sprite = new Sprite((Texture)assetManager.get(Constantes.URL_SEASON_PICTURE_LIST[i]));
			seasonSpriteList.add(sprite);

			if(i%5 == 0) {

				if(cont != 0) positionY -= 110;

				cont = 0;
				seasonSpriteList.get(i).setPosition(Constantes.WORLD_WIDTH/2 - 236, positionY);
			}else {
				++cont;
				seasonSpriteList.get(i).setPosition(Constantes.WORLD_WIDTH/2- 236 +125*cont, positionY);
			}
		}
	}

	/**
	 * Desenhar os botões das temporadas
	 * @param spriteBatch área de desenho da aplicação
	 */
	public void draw(SpriteBatch spriteBatch) {

		for(int i=0;i<seasonSpriteList.size();i++){
			seasonSpriteList.get(i).draw(spriteBatch);	
			if(i< openSeasonList.size()){
				textScore = Integer.toString(openSeasonList.get(i).getSeasonTotalScore());
				textStar = Integer.toString(getStarTotal(openSeasonList.get(i)));
			}else{
				textScore = "0";
				textStar = "0";
			}

			glyphLayout.setText(defaultFont, textScore);
			defaultFont.draw(spriteBatch, textScore, 
					seasonSpriteList.get(i).getX() + (seasonSpriteList.get(i).getWidth() - glyphLayout.width)/2,
					seasonSpriteList.get(i).getY()+87);
			glyphLayout.setText(defaultFont, textStar);
			defaultFont.draw(spriteBatch, textStar, 
					seasonSpriteList.get(i).getX() - 18 + (seasonSpriteList.get(i).getWidth() - glyphLayout.width)/2,
					seasonSpriteList.get(i).getY()+33);


		}
	}
	private int getStarTotal(Season season) {

		int total = 0;
		for(int i=0;i<season.getFaseList().size();i++){
			if(season.getFaseList().get(i).getScore() != 0)
				total += Util.getNumberStarPerSeason(season.getFaseList().get(i).getScore(), maxScoreFase.get(i));
		}

		return total;
	}

	/**
	 * Verificar se houve o clique em alguma temporada
	 * @param x posição x
	 * @param y posição y
	 * @return i quando houver clique no botão
	 * 		   -1 quando não houver clique no botão
	 */
	public int getClickedSeason(float x, float y) {

		for(int i=0;i<Constantes.URL_SEASON_PICTURE_LIST.length;i++){
			if(seasonSpriteList.get(i).getBoundingRectangle().contains(x, y)){
				if(i<openSeasonList.size()){
					return i;
				}
			}
		}
		return -1;
	}
}
