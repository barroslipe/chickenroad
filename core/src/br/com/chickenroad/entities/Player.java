package br.com.chickenroad.entities;

import java.util.ArrayList;
import java.util.List;

import br.com.chickenroad.Constantes;
import br.com.chickenroad.animations.PlayerAnimation;
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
	float speed = 60*2;
	private int pontoX = 0; //ponto de click/toque na tela
	private int pontoY = 0;
	private Direction playerDirectionX, playerDirectionY;

	private float timer;
	private boolean demage;
	private boolean colisionVehiclesStatus; // retorna o estado atual da colis�o com veiculos

	private PlayerLife playerLife;
	private PlayerAnimation playerAnimation;

	private final float DEMAGE_TIMER_PER_SECOND=3;
	private final float WIDTH_PLAYER_COLISION = 10;
	private final float HEIGHT_PLAYER_COLISION = 14;

	public Player(AssetManager assetManager) {
		super((Texture) assetManager.get(Constantes.URL_PLAYER_AVATAR));

		setScale(1.2f);
		this.timer=0;
		this.demage = false;
		this.playerAnimation = new PlayerAnimation(assetManager);
		this.playerLife = new PlayerLife(assetManager);
	}

	public boolean isColisionVehiclesStatus() {
		return colisionVehiclesStatus;
	}
	public void updatePlayerPosition(float delta, List<Rectangle> tiles, ArrayList<Vehicle> vehiclesList, int widthTiledMap, int heightTiledMap) {

		float newPositionX = this.getX()+velocity.x*delta;
		float newPositionY = this.getY()+velocity.y*delta;

		if(newPositionX > widthTiledMap-32)
			newPositionX = widthTiledMap-32;
		if(newPositionX < 0)
			newPositionX = 0;
		if(newPositionY > heightTiledMap-20)
			newPositionY = heightTiledMap-20;
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

		if(velocity.x == 0 && velocity.y == 0)
			playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_STOP_RIGHT, PlayerTypes.AVATAR_STOP_RIGHT);
	}

	private void demage(int i) {

		if(demage) return;

		if(i == 1)
			playerLife.demageVehicle();

		demage = true;

	}

	private boolean checkTilesColision(float newPositionX, float newPositionY, List<Rectangle> tiles) {

		Rectangle playerPosition = new Rectangle(newPositionX, newPositionY, WIDTH_PLAYER_COLISION, HEIGHT_PLAYER_COLISION);

		for(Rectangle object : tiles){
			if(Intersector.overlaps(object, playerPosition)){
				return true;
			}
		}

		return false;
	}


	public boolean checkVehiclesColision(ArrayList<Vehicle> vehiclesList){

		//recebe a posi��o atual do player
		Rectangle playerPosition = new Rectangle(getX(), getY(), WIDTH_PLAYER_COLISION, HEIGHT_PLAYER_COLISION);

		//checa colis�o com veculo com cada posi��o atual do player
		for(int i=0;i<vehiclesList.size();i++){
			if(Intersector.overlaps(vehiclesList.get(i).getBoundingRectangleColision(), playerPosition)){
				colisionVehiclesStatus = true;//colidiu
				return true;
			}
		}

		colisionVehiclesStatus = false;//nao colidiu
		return false;
	}

	public void movimentar(Vector3 touchPoint) {

		pontoX = (int)touchPoint.x;
		pontoY = (int)touchPoint.y;

		float declividadeReta; //relacionado ao angulo entre retas de toque na tela
		float diffPontoX;
		float diffPontoY;
		final float noZero = 0.999999f; //usada para evitar idetermina��o em 'diffPontoX' caso n�o haja varia��o em X
		//final float infinitezimal = 100000000f; //evita que der angulo 90 graus, pois n�o tem tangente para e ele

		//calculos de declividade de reta
		diffPontoX = (float)(pontoX - getX()*noZero);
		diffPontoY = (float)(pontoY - getY());

		declividadeReta = (diffPontoY)/(diffPontoX);

		/*
		 * m = 0, angulo 0, retas paralelas no eixo X (toque paralelo)
		 * m = 1,  angulo 45 graus
		 * m = -1, angulo de -45 graus
		 * m < 0, angulos maiores que 90 graus
		 * m > 0, angulos menores que 90 graus
		 * */

		// [EM RELA��O A POSI��O ATUAL DO PLAYER]
		//pontoX,Y = ponto que o jogador clicou
		if(pontoX > getX()) {
			if(declividadeReta <= 0.5 && declividadeReta >= -0.5) {
				velocity.x = speed;
				playerDirectionX = Direction.RIGHT;
				playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_RIGHT, PlayerTypes.AVATAR_RIGHT);
			}
			if(declividadeReta > 0.5) {
				velocity.y = speed;
				playerDirectionY = Direction.UP;
				playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_UP, PlayerTypes.AVATAR_UP);
			}
			if(declividadeReta < -0.5) {
				velocity.y = -speed;
				playerDirectionY = Direction.DOWN;
				playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_DOWN, PlayerTypes.AVATAR_DOWN);
			}
		}

		if(pontoX < getX()) {
			if(declividadeReta >= -0.5 && declividadeReta <=0.5) {
				velocity.x = -speed;
				playerDirectionX = Direction.LEFT;
				playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_LEFT, PlayerTypes.AVATAR_LEFT);

			}
			if(declividadeReta < -0.5) {
				velocity.y = speed;
				playerDirectionY = Direction.UP;
				playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_UP, PlayerTypes.AVATAR_UP);
			}
			if(declividadeReta > 0.5) {
				velocity.y = -speed;
				playerDirectionY = Direction.DOWN;
				playerAnimation.setSpriteSheet(Constantes.URL_PLAYER_AVATAR_DOWN, PlayerTypes.AVATAR_DOWN);
			}
		}
	}

	public void init(String[] points) {
		float posX = Float.parseFloat(points[0])*Constantes.WIDTH_TILE;
		float posY = Float.parseFloat(points[1])*Constantes.HEIGHT_TILE;
		
		setPosition(posX, posY);
		setSize(40, 40);

		velocity.x = 0;
		velocity.y = 0;
		playerDirectionX = Direction.NONE;
		playerDirectionY = Direction.NONE;
		playerLife.init();
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
	//
	public PlayerAnimation getPlayerAnimation() {
		return playerAnimation;
	}

	@Override
	public void draw(Batch batch, float delta){

		this.setRegion(playerAnimation.getCurrentFrame(delta));

		super.draw(batch);

		if(demage){
			//geralmente o delta � 0.2, ou 0.3 ou 0.4
			timer += delta;
			//seta um alfa de 0.3 e 0.8
			setAlpha(Util.getRandomPosition(3, 8)/10);

			// incrementa 'delta' at� que chegue a aprox. 3 segundos ("demageTimerPerSecond")
			if(timer > DEMAGE_TIMER_PER_SECOND){
				timer=0;
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

	public void drawLife(SpriteBatch spriteBatch) {
		playerLife.draw(spriteBatch);
	}
}
