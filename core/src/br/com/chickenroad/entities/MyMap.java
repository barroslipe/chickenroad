package br.com.chickenroad.entities;

import java.util.ArrayList;
import java.util.List;

import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Guardar informações relativas ao mapa
 * 
 *
 */
public class MyMap {

	private TiledMap tiledMap; //guarda o mapa
	private int widthTiledMap, heightTiledMap;

	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer; //guarda o renderizador do mapa

	private List<Rectangle> tiles;

	public MyMap(String aUrlMap) {

		tiledMap = new TmxMapLoader().load(aUrlMap);
		orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		widthTiledMap = tiledMap.getProperties().get("width", Integer.class)*tiledMap.getProperties().get("tilewidth", Integer.class);
		heightTiledMap = tiledMap.getProperties().get("height", Integer.class)*tiledMap.getProperties().get("tileheight", Integer.class);

		saveColisionTiles();
	}


	public int getWidthTiledMap() {
		return widthTiledMap;
	}

	public int getHeightTiledMap() {
		return heightTiledMap;
	}

	private void saveColisionTiles() {

		//segunda camada que contém os tiles para colisão
		TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer)tiledMap.getLayers().get(1);

		tiles = new ArrayList<Rectangle>();

		for(int x = 0;x < widthTiledMap;x++){
			for(int y = 0; y< heightTiledMap;y++){
				Cell cell = tiledMapTileLayer.getCell(x, y);
				if(cell != null){
					tiles.add(new Rectangle(x*Constantes.WIDTH_TILE, y*Constantes.HEIGHT_TILE, Constantes.WIDTH_TILE, Constantes.HEIGHT_TILE));
				}
			}
		}
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

}
