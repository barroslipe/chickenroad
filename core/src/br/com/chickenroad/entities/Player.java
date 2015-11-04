package br.com.chickenroad.entities;

import java.util.ArrayList;
import java.util.List;

import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.ResourceManager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends Sprite{
	public int contCollision=0;
	private Vector2 velocity = new Vector2();
	float speed = 60*2;
	private int pontoX = 0; //ponto de click/toque na tela
	private int pontoY = 0;
	private boolean movendoX1 = false;//x positivo - diz ppara que lado o avatar esta movendo
	private boolean movendoX2 = false;//x negativo 
	private boolean movendoY1 = false;//y positivo 
	private boolean movendoY2 = false;//y negativo

	private int widthTiledMap, heightTileMap;

	private PlayerLife playerLife;

	public Player(String sprite, int aWidthTiledMap, int aHeightTiledMap, ResourceManager resourceManager) {
		super(new Texture(sprite));

		this.widthTiledMap = aWidthTiledMap;
		this.heightTileMap = aHeightTiledMap;

		playerLife = new PlayerLife(resourceManager);
	}

	public void updatePlayerPosition(float delta, List<Rectangle> tiles, ArrayList<Vehicle> vehiclesList) {

		if(velocity.x > speed)
			velocity.x = speed;
		if(velocity.x < -speed)
			velocity.x = -speed;
		if(velocity.y > speed)
			velocity.y = speed;
		if(velocity.y < -speed)
			velocity.y = -speed;

		float newPositionX = this.getX()+velocity.x*delta;
		float newPositionY = this.getY()+velocity.y*delta;

		if(newPositionX > widthTiledMap-32)
			newPositionX = widthTiledMap-32;
		if(newPositionX < 0)
			newPositionX = 0;
		if(newPositionY > heightTileMap-20)
			newPositionY = heightTileMap-20;
		if(newPositionY <0)
			newPositionY = 0;


		if(checkVehiclesColision(vehiclesList)){
			playerLife.demageVehicle();
		}
		
		if(!checkTilesColision(newPositionX, newPositionY, tiles)){
			this.setX(newPositionX);
			this.setY(newPositionY);
		}


		//regras de parada de movimento
		if(movendoX1 && getX()+this.getWidth()/2 >= pontoX) {
			movendoX1 = false;
			velocity.x = 0;
		}

		if(movendoY1 && getY()+this.getHeight()/2 >= pontoY) {
			movendoY1 = false;
			velocity.y = 0;
		}

		if(movendoX2 && getX()+this.getWidth()/2 <= pontoX) {
			movendoX2 = false;
			velocity.x = 0;
		}

		if(movendoY2 && getY()+this.getHeight()/2 <= pontoY) {
			movendoY2 = false;
			velocity.y = 0;
		}
	}

	private boolean checkTilesColision(float newPositionX, float newPositionY, List<Rectangle> tiles) {

		Rectangle playerPosition = new Rectangle(newPositionX, newPositionY, Constantes.WIDTH_PLAYER, Constantes.HEIGHT_PLAYER);

		for(Rectangle object : tiles){
			if(Intersector.overlaps(object, playerPosition)){
				return true;
			}
		}

		return false;
	}

	private boolean checkVehiclesColision(ArrayList<Vehicle> vehiclesList){

		Rectangle playerPosition = new Rectangle(pontoX, pontoY, Constantes.WIDTH_PLAYER, Constantes.HEIGHT_PLAYER);

		for(int i=0;i<vehiclesList.size();i++){
			if(Intersector.overlaps(vehiclesList.get(i).getBoundingRectangle(), playerPosition)){


				System.out.println("player: "+playerPosition.toString());
				//System.out.println("carro: "+vehiclesList.get(i).x+ " "+ vehiclesList.);

				return true;
				//				// o player pode colidir apenas uma vez. Na segunda ele morre
				//				if(contCollision > 1)
				//					contCollision = 0;
				//				else 
				//					contCollision++;
				//				
				//				return contCollision;
			}
		}
		return false;
		//		return contCollision;
	}

	public void movimentar(int screenX, int screenY, OrthographicCamera orthographicCamera) {

		Vector3 ponto = orthographicCamera.unproject(new Vector3(screenX, screenY, 0));
		pontoX = (int)ponto.x;
		pontoY = (int)ponto.y;

		if(pontoX > getX()){
			velocity.x = speed;
			movendoX1 = true;
		}
		if(pontoX < getX()){
			velocity.x = -speed;
			movendoX2 = true;
		}
		if(pontoY > getY()){
			velocity.y = speed;
			movendoY1 = true;
		}
		if(pontoY < getY()){
			velocity.y = -speed;
			movendoY2 = true;
		}

	}

	public void inicializar(Vector2 vector2) {
		setPosition(vector2.x, vector2.y);
		velocity.x = 0;
		velocity.y = 0;
		movendoX1 = false;
		movendoX2 = false;
		movendoY1 = false;
		movendoY2 = false;
		playerLife.init();
	}


	public void dispose() {

		getTexture().dispose();
	}

	public PlayerLife getPlayerLife() {
		// TODO Auto-generated method stub
		return playerLife;
	}
	
	@Override
	public void draw(Batch batch){
		super.draw(batch);
		
		playerLife.draw(batch);
		
		
	}

}
