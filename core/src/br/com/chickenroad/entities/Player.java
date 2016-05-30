package br.com.chickenroad.entities;

import java.util.ArrayList;
import java.util.List;

import br.com.chickenroad.animations.PlayerAnimation;
import br.com.chickenroad.entities.enums.DemageTypes;
import br.com.chickenroad.entities.enums.Direction;
import br.com.chickenroad.entities.enums.PlayerTypes;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends Sprite{

	private Vector2 velocity = new Vector2();
	private Direction playerDirectionX, playerDirectionY;
	private int pontoX = 0; //ponto de click/toque na tela
	private int pontoY = 0;

	private float timerDemage;
	private boolean demage;
	private boolean collisionVehiclesStatus; // retorna o estado atual da colisao com veiculos

	private PlayerLife playerLife;
	private PlayerAnimation playerAnimation;

	private final int DEMAGE_TIMER_PER_SECOND=3;
	private final int PLAYER_SPEED = 120;

	public Player(AssetManager assetManager) {
		super((Texture) assetManager.get(Constantes.URL_PLAYER_AVATAR));
		this.playerAnimation = new PlayerAnimation(assetManager);
		this.playerLife = new PlayerLife(assetManager);

	}

	public void init(String[] points) {

		float posX = Float.parseFloat(points[0])*Constantes.WIDTH_TILE;
		float posY = Float.parseFloat(points[1])*Constantes.HEIGHT_TILE;

		setPosition(posX, posY);

		//TODO deve ser figura original
		setOrigin(0, 0);
		setScale(1.3f);

		velocity.x = 0;
		velocity.y = 0;
		playerDirectionX = Direction.NONE;
		playerDirectionY = Direction.NONE;

		playerLife.init();
		timerDemage=0;
		demage=false;
		setAlpha(1);

	}

	public void move(Vector3 touchPoint) {

		pontoX = (int)touchPoint.x;
		pontoY = (int)touchPoint.y;

		final float NO_ZERO = 0.999999f; //usada para evitar ideterminacao em 'diffPontoX' caso nao haja variacao em X

		//calculos de declividade de reta
		float diffPontoX = (float)(pontoX - getX()*NO_ZERO);
		float diffPontoY = (float)(pontoY - getY());

		//relacionado ao angulo entre retas de toque na tela
		float declividadeReta = (diffPontoY)/(diffPontoX);

		/*
		 * m = 0, angulo 0, retas paralelas no eixo X (toque paralelo)
		 * m = 0.5,  angulo 45 graus
		 * m = -0.5, angulo de -45 graus
		 * m < 0, angulos maiores que 90 graus
		 * m > 0, angulos menores que 90 graus
		 * */

		final float ANGLE_45 = 0.5f;

		if(pontoX > getX()) {
			if(declividadeReta <= ANGLE_45 && declividadeReta >= -ANGLE_45) {
				velocity.x = PLAYER_SPEED;
				playerDirectionX = Direction.RIGHT;
				playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_RIGHT, PlayerTypes.AVATAR_RIGHT);
			}
			if(declividadeReta > ANGLE_45) {
				velocity.y = PLAYER_SPEED;
				playerDirectionY = Direction.UP;
				playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_UP, PlayerTypes.AVATAR_UP);
			}
			if(declividadeReta < -ANGLE_45) {
				velocity.y = -PLAYER_SPEED;
				playerDirectionY = Direction.DOWN;
				playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_DOWN, PlayerTypes.AVATAR_DOWN);
			}
		}

		if(pontoX < getX()) {
			if(declividadeReta >= -ANGLE_45 && declividadeReta <= ANGLE_45) {
				velocity.x = -PLAYER_SPEED;
				playerDirectionX = Direction.LEFT;
				playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_LEFT, PlayerTypes.AVATAR_LEFT);

			}
			if(declividadeReta < -ANGLE_45) {
				velocity.y = PLAYER_SPEED;
				playerDirectionY = Direction.UP;
				playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_UP, PlayerTypes.AVATAR_UP);
			}
			if(declividadeReta > ANGLE_45) {
				velocity.y = -PLAYER_SPEED;
				playerDirectionY = Direction.DOWN;
				playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_DOWN, PlayerTypes.AVATAR_DOWN);
			}
		}
	}
	
	public void updatePlayerPosition(float delta, List<Rectangle> tiles, ArrayList<Vehicle> vehiclesList, int mapWidth, int mapHeight) {

		float newPositionX = this.getX()+this.velocity.x*delta;
		float newPositionY = this.getY()+this.velocity.y*delta;

		newPositionX = checkForOutOfBoundX(mapWidth, newPositionX);
		newPositionY = checkForOutOfBoundsY(mapHeight, newPositionY);

		checkVehiclesCollision(vehiclesList);
		checkTilesMapCollision(newPositionX, newPositionY, tiles);

		checkStopPlayer();
	}

	private float checkForOutOfBoundX(int mapWidth, float newPositionX) {
		if(newPositionX > mapWidth - this.getWidth())
			newPositionX = mapWidth - this.getWidth();
		if(newPositionX < 0)
			newPositionX = 0;
		return newPositionX;
	}

	private float checkForOutOfBoundsY(int mapHeight, float newPositionY) {
		if(newPositionY > mapHeight - this.getHeight())
			newPositionY = mapHeight - this.getHeight();
		if(newPositionY < 0)
			newPositionY = 0;
		return newPositionY;
	}

	/**
	 * Verificar se houve colisao com algum veículo do cenário
	 * @param vehiclesList os veículos do cenário
	 */
	private void checkVehiclesCollision(ArrayList<Vehicle> vehiclesList) {

		if(demage)
			return;
		else
			collisionVehiclesStatus=false;

		float diffX = 4;
		float diffY = 2;

		//diminuição do limite de colisão do jogador
		Rectangle playerCollisionBounds = new Rectangle(this.getX() + diffX, this.getY()+diffY,
				this.getWidth()-diffX, this.getHeight()/4);

		for(int i=0;i<vehiclesList.size();i++){
			if(Intersector.overlaps(vehiclesList.get(i).getCollisionBounds(), playerCollisionBounds)){
				demage(DemageTypes.VEHICLE_DEMAGE);
				collisionVehiclesStatus=true;
				return;
			}
		}
	}

	/**
	 * Verificar se houve alguma colisão com os tiles de colisão do mapa
	 * @param tilesMap
	 * @param newPositionX
	 * @param newPositionY
	 */
	private void checkTilesMapCollision(float newPositionX, float newPositionY, List<Rectangle> tilesMap) {

		float diffX = 6;
		float diffY = 6; 

		//diminuição do limite de colisão do jogador
		Rectangle playerCollisionBounds = new Rectangle(newPositionX + diffX, newPositionY +diffY,
				this.getWidth(), this.getHeight()/4);

		for(Rectangle object : tilesMap){
			if(Intersector.overlaps(object, playerCollisionBounds)){
				return;
			}
		}

		this.setX(newPositionX);
		this.setY(newPositionY);	

	}

	private void checkStopPlayer() {

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

		if(velocity.x == 0 && velocity.y == 0)
			playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_STOP_RIGHT, PlayerTypes.AVATAR_STOP_RIGHT);
	}

	private void demage(DemageTypes demageType) {
		if(demage) return;
		playerLife.demage(demageType);
		demage = true;
	}

	@Override
	public void draw(Batch batch, float delta){

		this.setRegion(playerAnimation.getCurrentFrame(delta));

		super.draw(batch);

		if(demage){
			//geralmente o delta � 0.2, ou 0.3 ou 0.4
			timerDemage += delta;
			//seta um alfa de 0.3 e 0.8
			setAlpha(Util.getRandomPosition(3, 8)/10);

			// incrementa 'delta' at� que chegue a aprox. 3 segundos ("demageTimerPerSecond")
			if(timerDemage > DEMAGE_TIMER_PER_SECOND){
				timerDemage=0;
				demage = false;
				setAlpha(1);
			}
		}
	}

	public boolean isDead() {
		if(playerLife.getLife() <= 0)
			return true;
		else
			return false;
	}

	public void changeDead() {
		playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_DEAD, PlayerTypes.AVATAR_DEAD);
	}

	public boolean isColisionVehiclesStatus() {
		return collisionVehiclesStatus;
	}

	public void drawLife(SpriteBatch spriteBatch) {
		playerLife.draw(spriteBatch);
	}

	public PlayerLife getPlayerLife() {
		return playerLife;
	}

	public void dispose() {
		getTexture().dispose();
		playerLife.dispose();
		playerAnimation.dispose();
	}
}