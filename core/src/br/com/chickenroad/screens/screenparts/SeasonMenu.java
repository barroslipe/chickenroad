package br.com.chickenroad.screens.screenparts;

import java.util.ArrayList;

import br.com.chickenroad.dao.Season;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.PreferencesUser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Botões de temporadas da tela de temporadas da aplicação
 *
 */
public class SeasonMenu {

	//TODO ver método @getClickedSeason
	private Music soundRooster;

	//TODO retirar os cadeados das figura
	private String seasonPicturesList[];

	//lista de sprites das temporadas
	private ArrayList<Sprite> seasonSpriteList;

	//lista de temporadas abertas ao jogador - base de dados
	private ArrayList<Season> openSeasonList;

	private BitmapFont defaultFont;

	/**
	 * Inicialização dos atributos da classe
	 * @param assetManager referência a classe que possui os recursos alocados
	 */
	public SeasonMenu(AssetManager assetManager){

		this.soundRooster = assetManager.get(Constantes.URL_SOUND_ROOSTER);
		this.seasonPicturesList = Constantes.URL_SEASON_PICTURE_LIST;

		//TODO trocar figura e string
		this.seasonSpriteList = new ArrayList<Sprite>();
		this.openSeasonList = PreferencesUser.getSeasons();

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constantes.URL_FONT_KRAASH_BLACK));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 8;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 2;
		defaultFont = generator.generateFont(parameter);
		generator.dispose(); 
		defaultFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);	

		int cont = 0;
		int spriteFaseListHeight = 100;

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

	/**
	 * Desenhar os botões das temporadas
	 * @param spriteBatch área de desenho da aplicação
	 */
	public void draw(SpriteBatch spriteBatch) {

		for(int i=0;i<seasonSpriteList.size();i++){
			seasonSpriteList.get(i).draw(spriteBatch);	
			if(i< openSeasonList.size()){
				defaultFont.draw(spriteBatch, Integer.toString(openSeasonList.get(i).getSeasonTotalScore()), seasonSpriteList.get(i).getX()+30 , seasonSpriteList.get(i).getY()+80);
			}
		}
	}
	/**
	 * Verificar se houve o clique em alguma temporada
	 * @param x posição x
	 * @param y posição y
	 * @return i quando houver clique no botão
	 * 		   -1 quando não houver clique no botão
	 */
	public int getClickedSeason(float x, float y) {

		for(int i=0;i<seasonPicturesList.length;i++){
			if(seasonSpriteList.get(i).getBoundingRectangle().contains(x, y)){
				if(i<openSeasonList.size()){
					//TODO deve tocar aqui?
					//TODO  na classe SeasonScreen, há também uma musica quando eu clico, assim tenho 2 musicas para o mesmo clique. Qual deve ficar?
					//soundRooster.play();
					return i;
				}
			}
		}
		return -1;
	}
}
