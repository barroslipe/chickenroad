package br.com.chickenroad.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.MyProperties;

/**
 * Guardar informaÃ§Ãµes relativas ao mapa
 * 
 *
 */
public class MyMap {

	private TiledMap tiledMap; //guarda o mapa
	private int widthTiledMap, heightTiledMap;

	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer; //guarda o renderizador do mapa

	private List<Rectangle> tiles;

	private Vector2 playerOrigin;
	
	private List<Road> roadList;
	
	private MyProperties myProperties;

	public MyMap(String aUrlMap) {

		tiledMap = new TmxMapLoader().load(aUrlMap + ".tmx");
		orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		widthTiledMap = tiledMap.getProperties().get("width", Integer.class)*tiledMap.getProperties().get("tilewidth", Integer.class);
		heightTiledMap = tiledMap.getProperties().get("height", Integer.class)*tiledMap.getProperties().get("tileheight", Integer.class);
		
		playerOrigin = new Vector2(0,0);

		myProperties = new MyProperties();

		myProperties.loadProperties(aUrlMap + ".properties");
		
		calcPlayerOrigin();
		saveColisionTiles();
		saveRoads();
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

	public void dispose() {

		orthogonalTiledMapRenderer.dispose();
		tiledMap.dispose();
	}


	public Vector2 getPlayerOrigin() {
		return playerOrigin;
	}

	private void saveColisionTiles() {

		//ultima camada será a que contém os objetos que colidem
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

		//processamento de interseção na horizontal
		for(int p1=0;p1<tilesPre.size();p1++){

			//verificar se o ponto a ser analisado não foi colocado dentro de outro ponto

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


			//verificar interseção com todos os outros pontos do plano
			for(int p2=0;p2<tilesPre.size();p2++){

				//não pode verificar com ele mesmo
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

		System.out.println("Origem do player: x-> "+playerOrigin.x+", y-> "+playerOrigin.y);
		System.out.println("Número de tiles para colisão ANTES do processamento: "+tilesPre.size());
		System.out.println("Número de tiles para colisão APÓS o processamento: "+ tiles.size());

	}
	
	/**
	 * Capturar a posição inicial do player.
	 */
	private void calcPlayerOrigin() {
		String[] points = myProperties.getOriginPlayer().split(",");
		playerOrigin.set(Float.parseFloat(points[0])*Constantes.WIDTH_TILE, heightTiledMap - Float.parseFloat(points[1])*Constantes.HEIGHT_TILE);
	}

	/**
	 * Capturar e salvar todas as estradas do mapa
	 */
	private void saveRoads() {
		
		ArrayList<String> stringList = myProperties.getRoads();
		roadList = new ArrayList<Road>();
		
		float x, y, width, height;
		String[] values = new String[2];
		
		for (int i = 0; i < stringList.size(); i++) {
			
			values = stringList.get(i).split(",");
			x = Float.parseFloat(values[0])*Constantes.WIDTH_TILE;
			y = heightTiledMap - Float.parseFloat(values[1])*Constantes.HEIGHT_TILE;
			width = Float.parseFloat(values[0])*Constantes.WIDTH_TILE;
			height = Float.parseFloat(values[1])*Constantes.HEIGHT_TILE;
			
			roadList.add(new Road(x, y, width, height));
		}
	}
}
