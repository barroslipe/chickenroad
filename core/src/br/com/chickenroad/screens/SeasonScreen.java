package br.com.chickenroad.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.PreferencesUser;

//TODO avaliar melhor o background. Al√©m disso, avaliar se ser√° uma classe como a principal.
//N√£o consegui adicionar outro background.
public class SeasonScreen implements Screen {

	private ChickenRoadGame chickenRoadGame;
	private AssetManager assetManager;

	private Texture textureBACK;
	private TextureRegion textureRegionBACK;
	private Sprite spriteArrowBACK;
	
	//com varias fases, trocar figuras
	private String faseList[] = {"fase1.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png",
			"faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png",
			"faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png",
			"faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png",
			"faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png"};

	private ArrayList<Texture> textureFaseList;
	private ArrayList<TextureRegion> textureRegionFaseList;
	private ArrayList<Sprite> spriteFaseList;

	//background
	private Texture textureBackground;
	private TextureRegion textureRegionBackground;
	private Sprite spriteBackground;

	private OrthographicCamera orthographicCamera;

	private Music soundMenuBackground, soundClick;

	private int fase_atual = 0;
	
	public SeasonScreen(ChickenRoadGame chickenRoadGame) {

		this.chickenRoadGame = chickenRoadGame;
		assetManager = chickenRoadGame.getResourceManager().getAssetManager();
		
		//PreferencesUser.setFase(0);
		fase_atual = PreferencesUser.getFase();

		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false, Constantes.WIDTH_BACKGROUND,Constantes.HEIGHT_BACKGROUND);
		
		textureBACK = assetManager.get(Constantes.URL_BACK_BUTTON);
		textureRegionBACK = new TextureRegion(textureBACK,0,0,Constantes.WIDTH_BACK_BUTTON,Constantes.HEIGHT_BACK_BUTTON);
		spriteArrowBACK = new Sprite(textureRegionBACK);
				
		//TODO trocar figura e string
		textureFaseList = new ArrayList<Texture>();
		textureRegionFaseList = new ArrayList<TextureRegion>();
		spriteFaseList = new ArrayList<Sprite>();
		
		
		String picture;
		for(int i=0;i<faseList.length;i++){
			if(i <= fase_atual)
				picture = faseList[0];    //Aqui ser· apontada a fase correta. Por enquanto, ser· a figura de fase 1 para todas as fases ABERTAS.
			else
				picture = "faseBloqueada.png";
			
			Texture texture = new Texture(picture);
			TextureRegion textureRegion = new TextureRegion(texture, 0,0,96,94);
			Sprite sprite = new Sprite(textureRegion);
			
			textureFaseList.add(texture);
			textureRegionFaseList.add(textureRegion);
			spriteFaseList.add(sprite);
		}
		
		textureBackground = assetManager.get(Constantes.URL_BACKGROUND);
		textureRegionBackground = new TextureRegion(textureBackground, 0,0,Constantes.WIDTH_BACKGROUND,Constantes.HEIGHT_BACKGROUND);
		spriteBackground = new Sprite(textureRegionBackground);
	
		soundMenuBackground = assetManager.get(Constantes.URL_SOUND_MENU_BACKGROUND);
		soundClick = assetManager.get(Constantes.URL_SOUND_CLICK);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		orthographicCamera.update();
		chickenRoadGame.getSpriteBatch().setProjectionMatrix(orthographicCamera.combined);
		
		chickenRoadGame.getSpriteBatch().begin();

		spriteBackground.draw(chickenRoadGame.getSpriteBatch());
		
		// 12.10.15 - exibe as fases em forma de matriz de 3x6
		float spriteFaseListHeight = Constantes.HEIGHT_BACKGROUND/2+50;
		int cont = 0;
		
		for(int i=0;i<faseList.length;i++){
			if((i+1)%7 == 0) {
				cont = 0;
				spriteFaseListHeight -= 110; 
				spriteFaseList.get(i).setPosition(Constantes.WIDTH_BACKGROUND/2-320+110*cont, 
						spriteFaseListHeight);
			}
			else {
				spriteFaseList.get(i).setPosition(Constantes.WIDTH_BACKGROUND/2-320+110*cont, 
						spriteFaseListHeight);
				cont+=1;
			}
			spriteFaseList.get(i).draw(chickenRoadGame.getSpriteBatch());	
		}
		
		spriteArrowBACK.draw(chickenRoadGame.getSpriteBatch());

		chickenRoadGame.getSpriteBatch().end();

		if(!soundMenuBackground.isPlaying() && Constantes.SOUND_ON_FLAG) soundMenuBackground.play(); 

		if(Gdx.input.justTouched()){

			Vector3 touchPoint = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			orthographicCamera.unproject(touchPoint);

			if(spriteArrowBACK.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				//TODO liberar tudo
				soundClick.play();
				dispose();
				chickenRoadGame.setScreen(new MainMenuScreen(chickenRoadGame));
			}else{
				for(int i=0;i<faseList.length;i++){
					if(spriteFaseList.get(i).getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
						soundClick.play();
						if(i<=fase_atual){
							openFase(i);
						}else
							System.out.println("Fase bloqueada");
					}
				}
			}
		}

	}

	private void openFase(int i) {
		
		String map = "maps/";
		
		//TODO
		//fase 1
		if(i==0){
			map += "fase2_estrada.tmx";
			chickenRoadGame.setScreen(new Play(map, chickenRoadGame));
		}
		//dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {

		//soundClick.dispose();
		//soundMenuBackground.dispose();
		soundMenuBackground.stop();
	}
}
