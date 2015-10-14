package br.com.chickenroad.entities;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.screens.Play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends Sprite implements InputProcessor {

	private Vector2 velocity = new Vector2();
	float speed = 60*2;
	private int pontoX = 0; //ponto de click/toque na tela
	private int pontoY = 0;
	private boolean movendoX1 = false;//x positivo - diz ppara que lado o avatar esta movendo
	private boolean movendoX2 = false;//x negativo 
	private boolean movendoY1 = false;//y positivo 
	private boolean movendoY2 = false;//y negativo

	private int widthTiledMap, heightTileMap;

	private ChickenRoadGame chickenRoadGame;

	public Player(String sprite, ChickenRoadGame aChickenRoadGame, int aWidthTiledMap, int aHeightTiledMap) {
		super(new Texture(sprite));

		this.chickenRoadGame = aChickenRoadGame;
		this.widthTiledMap = aWidthTiledMap;
		this.heightTileMap = aHeightTiledMap;

	}

	public void update(float delta) {


		if(velocity.x > speed)
			velocity.x = speed;
		if(velocity.x < -speed)
			velocity.x = -speed;
		if(velocity.y > speed)
			velocity.y = speed;
		if(velocity.y < -speed)
			velocity.y = -speed;

		//usando ao acelerometro 

		//esquerda
		if(Gdx.input.getPitch() > 15) {
			velocity.x = -speed/2;
			movendoX1 = false;
			movendoX2 = false;
			movendoY1 = false;
			movendoY2 = false;
		}

		//direita
		else if(Gdx.input.getPitch() < -15) {
			velocity.x = speed/2;
			movendoX1 = false;
			movendoX2 = false;
			movendoY1 = false;
			movendoY2 = false;
		}

		else if(!movendoX1 && !movendoX2 &&
				!movendoY1 && !movendoY2) {
			velocity.x = 0;
		}

		//cima
		if(Gdx.input.getRoll() > 15) {
			velocity.y = -speed/2;
			movendoX1 = false;
			movendoX2 = false;
			movendoY1 = false;
			movendoY2 = false;
		}

		//baixo
		else if(Gdx.input.getRoll() < -15) {
			velocity.y = speed/2;
			movendoX1 = false;
			movendoX2 = false;
			movendoY1 = false;
			movendoY2 = false;
		}

		else if(!movendoX1 && !movendoX2 &&
				!movendoY1 && !movendoY2)
			velocity.y = 0;

		float newPositionX = this.getX()+velocity.x*delta;
		float newPositionY = this.getY()+velocity.y*delta;

		if(newPositionX > widthTiledMap-40)
			newPositionX = widthTiledMap-40;
		if(newPositionX < 0)
			newPositionX = 0;
		if(newPositionY > heightTileMap-64)
			newPositionY = heightTileMap-64;
		if(newPositionY <0)
			newPositionY = 0;

		this.setX(newPositionX);
		this.setY(newPositionY);


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

	public void draw(SpriteBatch spritebatch) {
		//desenha o personagem
		update(Gdx.graphics.getDeltaTime());
		super.draw(chickenRoadGame.getSpriteBatch());

	}
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	//evento para teclado
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	//evento para teclado
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	//evento para mouse ou dedo
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	//evento para liberação de toque na tela - quando solta a tela
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		Vector3 ponto = Play.orthographicCamera.unproject(new Vector3(screenX, screenY, 0));
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
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
