package br.com.chickenroad.entities;

import java.util.ArrayList;
import java.util.List;

import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * Guardar informações relativas ao mapa
 * 
 *
 */
public class MyMap {

	private TiledMap tiledMap;
	private int widthTiledMap, heightTiledMap;
	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

	//tiles para colisão
	private List<Rectangle> tiles;

	/**
	 * Inicialização das variáveis
	 * @param aUrlMap mapa utilizado
	 */
	public MyMap(String aUrlMap) {

		this.tiledMap = new TmxMapLoader().load(aUrlMap + ".tmx");
		this.orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		this.widthTiledMap = tiledMap.getProperties().get("width", Integer.class)*tiledMap.getProperties().get("tilewidth", Integer.class);
		this.heightTiledMap = tiledMap.getProperties().get("height", Integer.class)*tiledMap.getProperties().get("tileheight", Integer.class);

		saveColisionTiles();
	}

	public int getWidthTiledMap() {
		return widthTiledMap;
	}

	public int getHeightTiledMap() {
		return heightTiledMap;
	}

	public List<Rectangle> getTiles() {
		return tiles;
	}

	public OrthogonalTiledMapRenderer getOrthogonalTiledMapRenderer() {
		return orthogonalTiledMapRenderer;
	}



	/**
	 * Salvar os bounds relativos a ultima camada do mapa.
	 */
	private void saveColisionTiles() {

		//ultima camada ser� a que cont�m os objetos que colidem
		int ultimaCamada = tiledMap.getLayers().getCount() - 1;

		TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer)tiledMap.getLayers().get(ultimaCamada);

		List<Rectangle> tilesPre = new ArrayList<Rectangle>();

		for(int x = 0;x < widthTiledMap;x++){
			for(int y = 0; y< heightTiledMap;y++){
				Cell cell = tiledMapTileLayer.getCell(x, y);
				if(cell != null){
					tilesPre.add(new Rectangle(x*Constantes.WIDTH_TILE, y*Constantes.HEIGHT_TILE, Constantes.WIDTH_TILE, Constantes.HEIGHT_TILE));

				}
			}
		}

		tiles = new ArrayList<Rectangle>();

		float width, height;
		boolean pontoInvalido;

		//processamento de interse��o na horizontal
		for(int p1=0;p1<tilesPre.size();p1++){

			//verificar se o ponto a ser analisado n�o foi colocado dentro de outro ponto

			pontoInvalido = false;
			//para cada ponto do plano
			for(int j=0;j<tiles.size();j++){
				if(Intersector.overlaps(new Rectangle(tilesPre.get(p1).getX(), tilesPre.get(p1).getY(), Constantes.WIDTH_TILE-1, Constantes.HEIGHT_TILE-1), tiles.get(j))){
					pontoInvalido = true;
					break;
				}
			}

			if(pontoInvalido) continue;

			width=Constantes.WIDTH_TILE;
			height=Constantes.HEIGHT_TILE;


			//verificar interse��o com todos os outros pontos do plano
			for(int p2=0;p2<tilesPre.size();p2++){

				//n�o pode verificar com ele mesmo
				if(p2 == p1) continue;

				if((tilesPre.get(p1).getX() + width  == tilesPre.get(p2).getX()) &&  (tilesPre.get(p1).getY() == tilesPre.get(p2).getY())  ){

					width += Constantes.WIDTH_TILE;

				}
				if((tilesPre.get(p1).getY() + height == tilesPre.get(p2).getY()) && (tilesPre.get(p1).getX() == tilesPre.get(p2).getX()) ){

					height += Constantes.HEIGHT_TILE;
				}
			}
			//maior retangulo
			if(width >= height)
				height = Constantes.HEIGHT_TILE;
			else
				width = Constantes.WIDTH_TILE;

			tiles.add(new Rectangle(tilesPre.get(p1).getX(), tilesPre.get(p1).getY(), width, height));
		}
	}

	/**
	 * Desenhar mapa
	 * @param orthographicCamera camera do jogo
	 */
	public void draw(OrthographicCamera orthographicCamera) {
		getOrthogonalTiledMapRenderer().setView(orthographicCamera);
		getOrthogonalTiledMapRenderer().render();		
	}

	/**
	 * Liberar recursos
	 */
	public void dispose() {

		orthogonalTiledMapRenderer.dispose();
		tiledMap.dispose();
	}

	public void renderLayerColisaoObjetosImoveis() {
		if(tiledMap.getLayers().get("intermediaria") == null) return;

		getOrthogonalTiledMapRenderer().getBatch().begin();
		getOrthogonalTiledMapRenderer().renderTileLayer((TiledMapTileLayer)tiledMap.getLayers().get("intermediaria"));
		getOrthogonalTiledMapRenderer().getBatch().end();

	}
}
