package br.com.chickenroad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.screens.Play;

//executada a cada frame
public class Player extends Sprite implements InputProcessor {
	private Vector2 velocity = new Vector2();
	float speed = 60*2;
	float gravity = 60*0.8f;
	private int pontoX = 0; //ponto de click/toque na tela
	private int pontoY = 0;
	public boolean movendoX1 = false;//x positivo - diz ppara que lado o avatar esta movendo
	public boolean movendoX2 = false;//x negativo 
	public boolean movendoY1 = false;//y positivo 
	public boolean movendoY2 = false;//y negativo
	
	private ChickenRoadGame chickenRoadGame;
	
	public Player(String sprite, ChickenRoadGame aChickenRoadGame) {
		super(new Texture(sprite));
		
		this.chickenRoadGame = aChickenRoadGame;
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
		if(Gdx.input.getPitch() > 30) {
			velocity.x = -speed/2;
			movendoX1 = false;
			movendoX2 = false;
			movendoY1 = false;
			movendoY2 = false;
		}
		
		//direita
		else if(Gdx.input.getPitch() < -30) {
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
		if(Gdx.input.getRoll() > 30) {
			velocity.y = -speed/2;
			movendoX1 = false;
			movendoX2 = false;
			movendoY1 = false;
			movendoY2 = false;
		}
		
		//baixo
		else if(Gdx.input.getRoll() < -30) {
			velocity.y = speed/2;
			movendoX1 = false;
			movendoX2 = false;
			movendoY1 = false;
			movendoY2 = false;
		}
		
		else if(!movendoX1 && !movendoX2 &&
				!movendoY1 && !movendoY2)
			velocity.y = 0;
			
		//if(Gdx.input.getAzimuth()) { }
		
		//
		this.setX(this.getX()+velocity.x*delta);
		this.setY(this.getY()+velocity.y*delta);
		
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
		Vector3 ponto = Play.orthographicCamera.unproject(new Vector3(screenX, screenY, 0)); //usa o screenZ=0 (2D)
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
