package br.com.chickenroad.screens.screenparts;

import java.util.ArrayList;

import br.com.chickenroad.Constantes;
import br.com.chickenroad.dao.Fase;
import br.com.chickenroad.dao.PreferencesUser;

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

	private BitmapFont defaultFont;

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

		//lista de fases abertas para a temporada escolhida
		this.openFaseList = PreferencesUser.getOpenFases(seasonId);

		this.glyphLayout = new GlyphLayout();
		spriteFaseList = new ArrayList<Sprite>();

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
			if(i < openFaseList.size())
				picture = Constantes.URL_FASE_PICTURE_LIST[i];
			else
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
