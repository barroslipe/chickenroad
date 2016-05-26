package br.com.chickenroad.builder;

import java.util.ArrayList;
import java.util.Random;

import br.com.chickenroad.Constantes;
import br.com.chickenroad.entities.ChickenNest;
import br.com.chickenroad.entities.MyMap;
import br.com.chickenroad.entities.Player;
import br.com.chickenroad.entities.TargetPlayer;
import br.com.chickenroad.entities.TargetPlayerTypes;
import br.com.chickenroad.entities.TextGame;
import br.com.chickenroad.entities.TextGameTypes;
import br.com.chickenroad.screens.util.MyProperties;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


/**
 * refatorar e adequar a outro pacote
 * @author MacOs
 *
 */
public class TargetPlayerBuilder {

	private int contPlus15, contPlus100;
	private int numCornCatchedIndex; //recebe o valor da posi��o do vetor de milhos encontrados
	private boolean flagPlus15;
	private boolean flagPlus100;

	private TextGame textGame[];
	private ArrayList<TargetPlayer> targetPlayerEggsList, targetPlayerCornsList;

	//presente
	private TargetPlayer targetPlayerGiftSheep;


	public TargetPlayerBuilder(){
		numCornCatchedIndex=0;
		contPlus15 = 0;
		contPlus100 = 0;
		flagPlus15 = false;
		flagPlus100 = false;

		this.textGame = new TextGame[2];

	}

	public void init(MyMap myMap, MyProperties myProperties, AssetManager assetManager, ChickenNest chickenNest){


		this.textGame[0] = new TextGame(Constantes.URL_TEXT_PLUS15, assetManager, TextGameTypes.PLUS15);
		this.textGame[1] = new TextGame(Constantes.URL_TEXT_PLUS100, assetManager, TextGameTypes.PLUS100);

		this.textGame[0].init();
		this.textGame[1].init();

		Vector2 point;

		point = new Vector2(Util.getValidRandomPosition(myMap.getWidthTiledMap(), myMap.getHeightTiledMap(), myMap.getTiles(), chickenNest.getBoundingRectangle()));

		this.targetPlayerGiftSheep = new TargetPlayer(Constantes.URL_GIFT_SHEEP, assetManager, TargetPlayerTypes.SHEEP, 1f/4f);
		this.targetPlayerGiftSheep.init(point.x,  point.y);
		this.targetPlayerGiftSheep.setVisible(false);

		Random generator = new Random();
		//inicializa lista de ovos
		this.targetPlayerEggsList = EggsBuilder.builder(myProperties, myMap, chickenNest, assetManager, generator);
		//inicializa lista de milhos
		this.targetPlayerCornsList = CornsBuilder.builder(myProperties, myMap, chickenNest, assetManager);
	}


	public void drawEggs(SpriteBatch spriteBatch, float delta) {
		for(int i=0;i<targetPlayerEggsList.size();i++) {
			targetPlayerEggsList.get(i).draw(spriteBatch, delta);		
		}
	}

	public boolean testColissionEgg(Player player){
		for(int i=0;i<targetPlayerEggsList.size();i++){
			//so pode pegar ovos se ele nao tiver sido pego antes
			if(targetPlayerEggsList.get(i).checkColision(player)){ // && (playerScore.getCurrentNoCatchedEggs() > 0)){ 
				targetPlayerEggsList.get(i).setVisible(false);//apaga o ovo da tela
				flagPlus15 = true;
				return true;
			}			
		}
		return false;
	}

	public boolean testColissionCorn(Player player){
		for(int i=0;i<targetPlayerCornsList.size();i++){
			if(targetPlayerCornsList.get(i).checkColision(player)){
				targetPlayerCornsList.get(i).setVisible(false); //apaga milho da tela
				targetPlayerCornsList.get(i).setLocker(true);

				flagPlus100 = true;
				numCornCatchedIndex = i;//recebe a posi��o do milho pego
				return true;
			}			
		}
		return false;
	}


	public void leaveEggInNest(ChickenNest chickenNest) {
		Random gerador = new Random();
		for(int i=0;i<targetPlayerEggsList.size();i++){
			if(!targetPlayerEggsList.get(i).isVisible()){
				targetPlayerEggsList.get(i).init(24+chickenNest.getX()+gerador.nextInt(30), 30+chickenNest.getY()+gerador.nextInt(10));
				targetPlayerEggsList.get(i).setVisible(true);
				targetPlayerEggsList.get(i).setLocker(true);

			}
		}
	}


	public void drawCatchEggs(SpriteBatch spriteBatch, Player player, float delta) {


		if(flagPlus15) {
			if(contPlus15++ < 45) {
				textGame[0].setPosition(player.getX()-20, player.getY()+30);
				textGame[0].draw(spriteBatch, delta);
			}else{
				contPlus15 = 0;
				flagPlus15 = false;
			}
		}

	}


	public void drawCatchCorn(SpriteBatch spriteBatch, Player player, float delta) {
		if(flagPlus100) {
			if(contPlus100++ < 48) {
				//exibe apenas o milho encontrado
				targetPlayerCornsList.get(numCornCatchedIndex).setVisible(true);
				targetPlayerCornsList.get(numCornCatchedIndex).draw(spriteBatch, delta);		
				textGame[1].setPosition(player.getX()-20, player.getY()+30);
				textGame[1].draw(spriteBatch, delta);

			}else{
				contPlus100 = 0;
				flagPlus100 = false;
				targetPlayerCornsList.get(numCornCatchedIndex).setVisible(false);
			}
		}
	}

	public void drawGift(SpriteBatch spriteBatch, float delta){
		targetPlayerGiftSheep.setVisible(true);
		targetPlayerGiftSheep.draw(spriteBatch, delta);
	}


	public boolean testColissionGift(Player player) {

		//colisao com o presente ganho
		if(targetPlayerGiftSheep.checkColision(player)) {
			targetPlayerGiftSheep.setVisible(false);
			targetPlayerGiftSheep.setLocker(true);
			return true;

		}
		return false;

	}

	public TargetPlayer getTargetPlayerGiftSheep() {
		return targetPlayerGiftSheep;
	}

	public void dispose(){

		for(int i=0;i<textGame.length;i++)
			this.textGame[i].dispose();

		for(int i=0;i<targetPlayerEggsList.size();i++)
			this.targetPlayerEggsList.get(i).dispose();

		for(int i=0;i<targetPlayerCornsList.size();i++)
			this.targetPlayerCornsList.get(i).dispose();

		this.targetPlayerGiftSheep.dispose();
	}
}
