package br.com.chickenroad.entities;

import java.util.ArrayList;
import java.util.List;

import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends Sprite{
	
	private Vector2 velocity = new Vector2();
	float speed = 60*2;
	private int pontoX = 0; //ponto de click/toque na tela
	private int pontoY = 0;
	private boolean movendoX1 = false;//x positivo - diz ppara que lado o avatar esta movendo
	private boolean movendoX2 = false;//x negativo 
	private boolean movendoY1 = false;//y positivo 
	private boolean movendoY2 = false;//y negativo
	
	private float demageTimerPerSecond, t;
	private boolean demage;

	private int widthTiledMap, heightTileMap;

	private PlayerLife playerLife;
	
	private PlayerAnimation playerAnimation;

	public Player(String sprite, int aWidthTiledMap, int aHeightTiledMap, AssetManager assetManager) {
		super(new Texture(sprite));

		this.playerAnimation = new PlayerAnimation(assetManager);
		
		this.widthTiledMap = aWidthTiledMap;
		this.heightTileMap = aHeightTiledMap;

		this.demageTimerPerSecond = 3;
		this.t=0;
		this.demage = false;
		
		playerLife = new PlayerLife(assetManager);
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
			demage(1);
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

	private void demage(int i) {
		
		if(demage) return;
		
		if(i == 1)
			playerLife.demageVehicle();
		
		demage = true;
		
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

		//recebe a posi��o atual do player
		Rectangle playerPosition = new Rectangle(getX(), getY(), Constantes.WIDTH_PLAYER, Constantes.HEIGHT_PLAYER);
		
		//checa colis�o com veculo com cada posi��o atual do player
		for(int i=0;i<vehiclesList.size();i++){
			if(Intersector.overlaps(vehiclesList.get(i).getBoundingRectangle(), playerPosition)){
				return true;
			}
		}

		return false;
	}

	public void movimentar(int screenX, int screenY, OrthographicCamera orthographicCamera) {

		Vector3 ponto = orthographicCamera.unproject(new Vector3(screenX, screenY, 0));
		pontoX = (int)ponto.x;
		pontoY = (int)ponto.y;

		//pontoX,Y = ponto que o jogador clicou
		if(pontoX > getX()){
			velocity.x = speed;
			movendoX1 = true;
			playerAnimation.changeSpriteSheet(1);

		}
		if(pontoX < getX()){
			velocity.x = -speed;
			movendoX2 = true;
			playerAnimation.changeSpriteSheet(2);

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
		playerAnimation.inicializar(vector2);
		t=0;
		demage=false;
	}

	public void dispose() {
		getTexture().dispose();
	}

	public PlayerLife getPlayerLife() {
		// TODO Auto-generated method stub
		return playerLife;
	}
	
	@Override
	public void draw(Batch batch, float delta){
		
		this.setRegion(playerAnimation.getCurrentFrame());
		
		super.draw(batch);
				
		if(demage){
			//geralmente o delta � 0.2, ou 0.3 ou 0.4
			t += delta;
			//seta um alfa de 0.3 e 0.8
			setAlpha(Util.getRandomPosition(3, 8)/10);
			
			// incrementa 'delta' at� que chegue a aprox. 3 segundos ("demageTimerPerSecond")
			if(t > demageTimerPerSecond){
				t=0;
				demage = false;
				setAlpha(1);
			}
		}
		
		playerLife.draw(batch);
		
		
		
	}

}
