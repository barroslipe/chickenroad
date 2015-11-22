package br.com.chickenroad.entities;

import java.util.ArrayList;
import java.util.List;

import br.com.chickenroad.animations.PlayerAnimation;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.assets.AssetManager;
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
	private Direction playerDirectionX, playerDirectionY;
	private float demageTimerPerSecond, timer;
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
		this.timer=0;
		this.demage = false;

		playerLife = new PlayerLife(assetManager);
	}

	public void updatePlayerPosition(float delta, List<Rectangle> tiles, ArrayList<Vehicle> vehiclesList) {

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
		if(playerDirectionX == Direction.RIGHT && getX()+this.getWidth()/2 >= pontoX) {
			playerDirectionX = Direction.NONE;
			velocity.x = 0;
		}

		if(playerDirectionY == Direction.UP && getY()+this.getHeight()/2 >= pontoY) {
			playerDirectionY = Direction.NONE;
			velocity.y = 0;
		}

		if(playerDirectionX == Direction.LEFT && getX()+this.getWidth()/2 <= pontoX) {
			playerDirectionX = Direction.NONE;
			velocity.x = 0;
		}

		if(playerDirectionY == Direction.DOWN && getY()+this.getHeight()/2 <= pontoY) {
			playerDirectionY = Direction.NONE;
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

	public void movimentar(Vector3 touchPoint) {

		pontoX = (int)touchPoint.x;
		pontoY = (int)touchPoint.y;

		//pontoX,Y = ponto que o jogador clicou
		if(pontoX > getX()){
			velocity.x = speed;
			playerDirectionX = Direction.RIGHT;
			playerAnimation.changeSpriteSheet(1);

		}
		if(pontoX < getX()){
			velocity.x = -speed;
			playerDirectionX = Direction.LEFT;
			playerAnimation.changeSpriteSheet(2);

		}
		if(pontoY > getY()){
			velocity.y = speed;
			playerDirectionY = Direction.UP;
		}
		if(pontoY < getY()){
			velocity.y = -speed;
			playerDirectionY = Direction.DOWN;
		}

	}

	public void inicializar(Vector2 vector2) {
		setPosition(vector2.x, vector2.y);
		velocity.x = 0;
		velocity.y = 0;
		playerDirectionX = Direction.NONE;
		playerDirectionY = Direction.NONE;
		playerLife.init();
		playerAnimation.inicializar(vector2);
		timer=0;
		demage=false;
		setAlpha(1);
	}

	public void dispose() {
		getTexture().dispose();
	}

	public PlayerLife getPlayerLife() {
		return playerLife;
	}

	@Override
	public void draw(Batch batch, float delta){

		this.setRegion(playerAnimation.getCurrentFrame());

		super.draw(batch);

		if(demage){
			//geralmente o delta � 0.2, ou 0.3 ou 0.4
			timer += delta;
			//seta um alfa de 0.3 e 0.8
			setAlpha(Util.getRandomPosition(3, 8)/10);

			// incrementa 'delta' at� que chegue a aprox. 3 segundos ("demageTimerPerSecond")
			if(timer > demageTimerPerSecond){
				timer=0;
				demage = false;
				setAlpha(1);
			}
		}

		playerLife.draw(batch);
	}

}
