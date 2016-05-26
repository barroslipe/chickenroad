package br.com.chickenroad.screens.screenparts;

import java.util.ArrayList;

import br.com.chickenroad.Constantes;
import br.com.chickenroad.dao.Fase;
import br.com.chickenroad.dao.PreferencesUser;
import br.com.chickenroad.screens.util.MyProperties;

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
 * Botões de fase da tela de temporadas escolhida
 *
 */
public class FasesMenu {

	private ArrayList<Sprite> spriteFaseList;

	private ArrayList<Fase> openFaseList;
	private ArrayList<Integer> scoreMaxfase;

	private BitmapFont defaultFont;
	
	private Sprite spriteStar[];
	
	private MyProperties myProperties;
	
	private int seasonId;
	

	/*
	 * Auxiliar a impressão do score em cada fase
	 */
	private GlyphLayout glyphLayout;
	private String textScore;

	/**
	 * Inicialização dos atributos da classe
	 * @param assetManager referência a classe que possui os recursos alocados
	 * @param seasonId identificador da temporada
	 */
	public FasesMenu(AssetManager assetManager, int seasonId){

		this.seasonId = seasonId;
		
		//lista de fases abertas para a temporada escolhida
		this.openFaseList = PreferencesUser.getOpenFases(seasonId);
		this.scoreMaxfase = new ArrayList<Integer>();
		
		this.myProperties = new MyProperties();

		this.glyphLayout = new GlyphLayout();
		this.spriteFaseList = new ArrayList<Sprite>();
		this.spriteStar = new Sprite[3];
		this.spriteStar[0] = new Sprite((Texture)assetManager.get(Constantes.URL_STAR1));
		this.spriteStar[1] = new Sprite((Texture)assetManager.get(Constantes.URL_STAR2));
		this.spriteStar[2] = new Sprite((Texture)assetManager.get(Constantes.URL_STAR3));

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
		int cont = 0;
		int spriteFaseListHeight = Constantes.WORLD_HEIGHT/2+110;

		String picture;

		for(int i=0;i<Constantes.URL_FASE_PICTURE_LIST.length;i++){
			if(i < openFaseList.size()){
				picture = Constantes.URL_FASE_PICTURE_LIST[i];
				this.myProperties.loadProperties(Constantes.URL_MAPS[seasonId][i] + ".properties");
				this.scoreMaxfase.add(myProperties.getMaxScore());
			}else
				picture = Constantes.URL_FASE_BLOQUEADA;

			Sprite sprite = new Sprite((Texture)assetManager.get(picture));
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
	/**
	 * Desenhar os botões das temporadas e o score relacionado
	 * @param spriteBatch área de desenho da aplicação
	 */
	public void draw(SpriteBatch spriteBatch) {

		for(int i=0;i<spriteFaseList.size();i++){
			spriteFaseList.get(i).draw(spriteBatch);
			if(i< openFaseList.size()){
				if(openFaseList.get(i).getScore() == 0)
					continue;
				
				textScore = Integer.toString(openFaseList.get(i).getScore());
				glyphLayout.setText(defaultFont, textScore);
				defaultFont.draw(spriteBatch, textScore, spriteFaseList.get(i).getX() + (spriteFaseList.get(i).getWidth() - glyphLayout.width)/2 , spriteFaseList.get(i).getY() - 2);
				
				if(openFaseList.get(i).getScore() <= 0.45*scoreMaxfase.get(i)){
					spriteStar[0].setPosition(spriteFaseList.get(i).getX()+5, spriteFaseList.get(i).getY()+2);
					spriteStar[0].draw(spriteBatch);
				}else if(openFaseList.get(i).getScore() <= 0.85*scoreMaxfase.get(i)){
					spriteStar[1].setPosition(spriteFaseList.get(i).getX()+5, spriteFaseList.get(i).getY()+2);
					spriteStar[1].draw(spriteBatch);
					
				}else{
					spriteStar[2].setPosition(spriteFaseList.get(i).getX()+5, spriteFaseList.get(i).getY()+2);
					spriteStar[2].draw(spriteBatch);
				}
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

		for(int i=0;i<Constantes.URL_FASE_PICTURE_LIST.length;i++){
			if(spriteFaseList.get(i).getBoundingRectangle().contains(x, y)){
				if(i< openFaseList.size()){
					return i;
				}
			}
		}
		return -1;
	}
}
