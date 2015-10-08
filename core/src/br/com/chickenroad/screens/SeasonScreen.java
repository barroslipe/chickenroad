package br.com.chickenroad.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import br.com.chickenroad.ChickenRoadGame;

//TODO avaliar melhor o background. Além disso, avaliar se será uma classe como a principal.
//Não consegui adicionar outro background.
public class SeasonScreen implements Screen {

	//TODO buscar uma maneira de trabalhar essas constantes
	private final int WIDTH = 893;
	private final int HEIGHT = 540;

	private final String URL_ARROW_LEFT_BUTTON = "arrowLeft.png";
	//TODO trocar o background
	private final String URL_BACKGROUND = "backgroundMenu.jpg";

	private ChickenRoadGame chickenRoadGame;

	private Texture textureArrowLeft;
	private TextureRegion textureRegionArrowLeft;
	private Sprite spriteArrowLeft;
	
	//com varias fases, uma lista será criada
	private String faseList[] = {"fase1.png", "fase1.png", "fase1.png", "fase1.png", "fase1.png"};
	
	private ArrayList<Texture> textureFaseList;
	private ArrayList<TextureRegion> textureRegionFaseList;
	private ArrayList<Sprite> spriteFaseList;
	
	
	//background
	private Texture textureBackground;
	private TextureRegion textureRegionBackground;
	private Sprite spriteBackground;

	private OrthographicCamera orthographicCamera;


	public SeasonScreen(ChickenRoadGame chickenRoadGame) {

		this.chickenRoadGame = chickenRoadGame;

		
		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false, WIDTH,HEIGHT);
		
		textureArrowLeft = new Texture(URL_ARROW_LEFT_BUTTON);
		textureRegionArrowLeft = new TextureRegion(textureArrowLeft,0,0,96,96);
		spriteArrowLeft = new Sprite(textureRegionArrowLeft);
		
		//TODO trocar figura e string
		textureFaseList = new ArrayList<Texture>();
		textureRegionFaseList = new ArrayList<TextureRegion>();
		spriteFaseList = new ArrayList<Sprite>();
		
		for(int i=0;i<faseList.length;i++){
			Texture texture = new Texture(faseList[i]);
			TextureRegion textureRegion = new TextureRegion(texture, 0,0,68,95);
			Sprite sprite = new Sprite(textureRegion);
			
			textureFaseList.add(texture);
			textureRegionFaseList.add(textureRegion);
			spriteFaseList.add(sprite);
			
		}
		
		textureBackground = new Texture(URL_BACKGROUND);
		textureRegionBackground = new TextureRegion(textureBackground, 0,0,WIDTH,HEIGHT);
		spriteBackground = new Sprite(textureRegionBackground);
	
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
		
		for(int i=0;i<faseList.length;i++){
			spriteFaseList.get(i).setPosition(WIDTH/2-200+90*i, HEIGHT/2-70);
			spriteFaseList.get(i).draw(chickenRoadGame.getSpriteBatch());
			
		}
		
		spriteArrowLeft.draw(chickenRoadGame.getSpriteBatch());

		chickenRoadGame.getSpriteBatch().end();

		if(Gdx.input.justTouched()){

			Vector3 touchPoint = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			orthographicCamera.unproject(touchPoint);

			System.out.println("-> x = "+ touchPoint.x + " , y = "+touchPoint.y);

			if(spriteArrowLeft.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				//TODO liberar tudo
				dispose();
				chickenRoadGame.setScreen(new MainMenuScreen(chickenRoadGame));
			}else if(spriteFaseList.get(0).getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				//TODO abrir fase 1
				System.out.println("Abrir fase 1");
			}else{
				System.out.println("Clicou em nada ou a fase está bloqueado");
			}
		}

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
		// TODO Auto-generated method stub

	}

}
