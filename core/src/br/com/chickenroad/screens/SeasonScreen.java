package br.com.chickenroad.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
	private final int WIDTH_BACKGROUND = 893;
	private final int HEIGHT_BACKGROUND = 540;
	private final int HEIGHT_BACK_BUTTON = 96;
	private final int WIDTH_BACK_BUTTON = 96;

	private final String URL_BACK_BUTTON = "backButton.png";
	//TODO trocar o background
	private final String URL_BACKGROUND = "backgroundMenu.jpg";

	private ChickenRoadGame chickenRoadGame;

	private Texture textureBACK;
	private TextureRegion textureRegionBACK;
	private Sprite spriteArrowBACK;
	
	//com varias fases, trocar figuras
	private String faseList[] = {"fase1.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png", "faseBloqueada.png"};

	private ArrayList<Texture> textureFaseList;
	private ArrayList<TextureRegion> textureRegionFaseList;
	private ArrayList<Sprite> spriteFaseList;
	
	//background
	private Texture textureBackground;
	private TextureRegion textureRegionBackground;
	private Sprite spriteBackground;

	private OrthographicCamera orthographicCamera;

	private Music soundMenuBackground, soundClick;


	public SeasonScreen(ChickenRoadGame chickenRoadGame) {

		this.chickenRoadGame = chickenRoadGame;

		
		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false, WIDTH_BACKGROUND,HEIGHT_BACKGROUND);
		
		textureBACK = new Texture(URL_BACK_BUTTON);
		textureRegionBACK = new TextureRegion(textureBACK,0,0,WIDTH_BACK_BUTTON,HEIGHT_BACK_BUTTON);
		spriteArrowBACK = new Sprite(textureRegionBACK);
		
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
		textureRegionBackground = new TextureRegion(textureBackground, 0,0,WIDTH_BACKGROUND,HEIGHT_BACKGROUND);
		spriteBackground = new Sprite(textureRegionBackground);
	

		soundMenuBackground = Gdx.audio.newMusic(Gdx.files.internal("sounds/soundMenuBackground.mp3"));
		soundClick = Gdx.audio.newMusic(Gdx.files.internal("sounds/soundClick.mp3"));

		
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
			spriteFaseList.get(i).setPosition(WIDTH_BACKGROUND/2-200+90*i, HEIGHT_BACKGROUND/2-70);
			spriteFaseList.get(i).draw(chickenRoadGame.getSpriteBatch());
			
		}
		
		spriteArrowBACK.draw(chickenRoadGame.getSpriteBatch());

		chickenRoadGame.getSpriteBatch().end();

		if(!soundMenuBackground.isPlaying() && MainMenuScreen.soundOnFlag) soundMenuBackground.play(); 

		if(Gdx.input.justTouched()){

			Vector3 touchPoint = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			orthographicCamera.unproject(touchPoint);

			if(spriteArrowBACK.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				//TODO liberar tudo
				soundClick.play();
				dispose();
				chickenRoadGame.setScreen(new MainMenuScreen(chickenRoadGame));
			}else if(spriteFaseList.get(0).getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
				//TODO abrir fase 1
				soundClick.play();
				dispose();
			}else{
				for(int i=1;i<faseList.length;i++){
					if(spriteFaseList.get(i).getBoundingRectangle().contains(touchPoint.x, touchPoint.y)){
						soundClick.play();
						System.out.println("Fase bloqueada");
					}
				}
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

		//soundClick.dispose();
		//soundMenuBackground.dispose();
		soundMenuBackground.stop();
	}

}