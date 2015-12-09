package br.com.chickenroad.entities;

import java.util.ArrayList;
import java.util.List;

import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.MyProperties;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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

	private Vector2 playerOrigin;

	private List<Road> roadList;

	private MyProperties myProperties;

	private ArrayList<Vehicle> vehicleList;
	
	private AssetManager assetManager;

	//Deslocando a origem da pista para esquerda, essa variavel vai 
	//evitar que os ve�culos vindos da esquerda para direita, surjam "do nada" na tela
	private int DESLOC_INIT_X_ROAD = 200; 
	
	public MyMap(String aUrlMap, AssetManager assetManager) {

		this.tiledMap = new TmxMapLoader().load(aUrlMap + ".tmx");
		this.orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		this.widthTiledMap = tiledMap.getProperties().get("width", Integer.class)*tiledMap.getProperties().get("tilewidth", Integer.class);
		this.heightTiledMap = tiledMap.getProperties().get("height", Integer.class)*tiledMap.getProperties().get("tileheight", Integer.class);

		this.playerOrigin = new Vector2(0,0);

		this.myProperties = new MyProperties();

		this.myProperties.loadProperties(aUrlMap + ".properties");

		this.assetManager = assetManager;
		
		calcPlayerOrigin();
		saveColisionTiles();
		saveRoads();
		createVehicles();
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
		vehicleList = null;
	}


	public Vector2 getPlayerOrigin() {
		return playerOrigin;
	}

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
	 * Capturar a posi��o inicial do player.
	 */
	private void calcPlayerOrigin() {
		String[] points = myProperties.getOriginPlayer().split(",");
		playerOrigin.set(Float.parseFloat(points[0])*Constantes.WIDTH_TILE, Float.parseFloat(points[1])*Constantes.HEIGHT_TILE);
	}

	/**
	 * Capturar e salvar todas as estradas do mapa. Somente estradas HORIZONTAIS!!!
	 */
	private void saveRoads() {

		ArrayList<String> stringList = myProperties.getRoads();

		roadList = new ArrayList<Road>();

		float initialPointX, initialPointY, width, height;
		int carsDistance;

		//largura dos carros - padrão sendo a largura do tile  
		int vehicleHeight = 64;

		String[] values = new String[2];
		ArrayList<RoadFaixa> roadFaixaList;
		for (int i = 0; i < stringList.size(); i++) {

			values = stringList.get(i).split(",");

			//ponto inicial X da estrada
			initialPointX = Float.parseFloat(values[0])*Constantes.WIDTH_TILE - DESLOC_INIT_X_ROAD;
			//ponto inicial Y da estrada
			initialPointY = Float.parseFloat(values[1])*Constantes.HEIGHT_TILE;
			//comprimento da estrada - compensada com DESLOC_INIT_X_ROAD, devido a origem em X ser deslocada
			width = Float.parseFloat(values[2])*Constantes.WIDTH_TILE + DESLOC_INIT_X_ROAD;
			//largura da estrada
			height = (Float.parseFloat(values[3]))*Constantes.HEIGHT_TILE;

			//a lista de faixas de cada estrada
			roadFaixaList = new ArrayList<RoadFaixa>();

			//calcular o número de faixas para os carros
			int numeroFaixas = (int)Math.floor(height/vehicleHeight);

			for(int j=0;j<numeroFaixas;j++){

				//calcular a velocidade de cada faixa
				float speed = Util.getRandomPosition(5, 30)/10;

				//calcular distancia entre os carros
				//carsDistance = (int)Util.getRandomPosition(width/16, width/14);
				carsDistance = 140;
				//cadastrar dados de cada faixa
				roadFaixaList.add(new RoadFaixa(speed, new Vector2( initialPointX , initialPointY + vehicleHeight*j), width ,carsDistance, (j%2 == 0 ? Direction.RIGHT : Direction.LEFT)));
			}

			//estrada no jogo
			roadList.add(new Road(initialPointX, initialPointY, width, height, roadFaixaList));
		}
	}

	public List<Road> getRoadList() {
		return roadList;
	}

	/**
	 * Criar os ve�culos.
	 */
	private void createVehicles() {

		//TODO verificar os objetos que estarão nas estradas
		String[] pictures = {"veicules/veiculo1D.png", "veicules/veiculo1E.png"};

		vehicleList = new ArrayList<Vehicle>();

		Vehicle vehicle;
		float positionY;

		//quantidade de estradas
		for(int i=0;i<roadList.size();i++){

			Road road = roadList.get(i);

			int j=0;
			boolean a = true;
			while(a){
				RoadFaixa faixa = road.getRoadFaixaList().get(j%road.getRoadFaixaList().size());

				positionY = faixa.getInitialPoint().y;

				float positionX = faixa.getInitialPoint().x + faixa.getCarsDistance()*j;

				vehicle = new Vehicle(pictures[(j%road.getRoadFaixaList().size())%2], faixa, assetManager);
				vehicle.init(positionX, positionY);
				
				vehicleList.add(vehicle);
				j++;

				if(positionX >faixa.getInitialPoint().x+road.getWidth()) a=false;
			}
		}
	}

	public void drawVehicles(SpriteBatch spriteBatch, StateGame stateGame) {

		for(int i=0;i<vehicleList.size();i++){
			if(stateGame == StateGame.PLAYING)
				vehicleList.get(i).walkX();
			vehicleList.get(i).draw(spriteBatch, Gdx.graphics.getDeltaTime());
		}
	}

	public ArrayList<Vehicle> getVehicleList() {
		return vehicleList;
	}

	public void draw(OrthographicCamera orthographicCamera) {
		getOrthogonalTiledMapRenderer().setView(orthographicCamera);
		getOrthogonalTiledMapRenderer().render();		
	}

}
