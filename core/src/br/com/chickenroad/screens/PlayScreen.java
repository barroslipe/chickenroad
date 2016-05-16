package br.com.chickenroad.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.chickenroad.ChickenRoadGame;
import br.com.chickenroad.configuration.ApplicationConfig;
import br.com.chickenroad.entities.ChickenNest;
import br.com.chickenroad.entities.Direction;
import br.com.chickenroad.entities.MyMap;
import br.com.chickenroad.entities.MyPlayMusic;
import br.com.chickenroad.entities.Player;
import br.com.chickenroad.entities.PlayerScore;
import br.com.chickenroad.entities.PlayerTypes;
import br.com.chickenroad.entities.Road;
import br.com.chickenroad.entities.RoadFaixa;
import br.com.chickenroad.entities.StateGame;
import br.com.chickenroad.entities.TargetPlayer;
import br.com.chickenroad.entities.TargetPlayerTypes;
import br.com.chickenroad.entities.TextGame;
import br.com.chickenroad.entities.TextGameTypes;
import br.com.chickenroad.entities.Vehicle;
import br.com.chickenroad.screens.screenparts.PlayMenuButtons;
import br.com.chickenroad.screens.screenparts.Popup;
import br.com.chickenroad.screens.screenparts.PopupTypes;
import br.com.chickenroad.screens.util.Constantes;
import br.com.chickenroad.screens.util.MyProperties;
import br.com.chickenroad.screens.util.PlayCamera;
import br.com.chickenroad.screens.util.PreferencesUser;
import br.com.chickenroad.screens.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Responsável pelo controle da fase
 * 
 *
 */
public class PlayScreen extends ScreenBase {

	//músicas da fase
	private MyPlayMusic myPlayMusic;
	//botões do jogo[menu de contexto]
	private PlayMenuButtons playMenuButtons;
	//ovos e espigas
	private ArrayList<TargetPlayer> targetPlayerEggsList, targetPlayerCornsList;
	//presente
	private TargetPlayer targetPlayerGiftSheep;
	//textos
	private TextGame textGame[];
	//porcos
	//private Supporting supporting[];
	//jogador
	private Player player;
	//score do jogador
	private PlayerScore playerScore;
	//ninho
	private ChickenNest chickenNest;
	//mapa
	private MyMap myMap;
	//estado do jogo
	private StateGame stateGame;
	//popup de término
	private Popup popupTutorial;
	//camera do jogo
	private PlayCamera playCamera;
	//lista de estradas
	private List<Road> roadList;
	//veiculos
	private ArrayList<Vehicle> vehicleList;
	//propriedades da fase
	private MyProperties myProperties;

	//variáveis de controle
	private int contAmazing, contPow, contPlus15, contPlus100;
	private int numCornCatchedIndex; //recebe o valor da posi��o do vetor de milhos encontrados
	private boolean flagPlus15;
	private boolean flagPlus100;

	//Deslocando a origem da pista para esquerda, essa variavel vai 
	//evitar que os ve�culos vindos da esquerda para direita, surjam "do nada" na tela
	private int DESLOC_INIT_X_ROAD = 200; 

	public static int deltaXPositionButtons=0, deltaYPositionButtons=0;

	//parametros
	private int seasonId, faseId;
	//flag para visualizar popup
	private boolean flagPopupTutorial;

	/**
	 * Inicialização dos atributos da classe
	 * @param urlMap url do mapa
	 * @param aChickenRoadGame referência a classe principal do jogo
	 * @param seasonId identificador da temporada
	 * @param faseId identificador da fase
	 */
	public PlayScreen(String urlMap, ChickenRoadGame aChickenRoadGame, int seasonId, int faseId) {
		super(aChickenRoadGame);

		this.seasonId = seasonId;
		this.faseId = faseId;

		this.myPlayMusic = new MyPlayMusic(getAssetManager());
		this.chickenNest = new ChickenNest(getAssetManager());
		this.myMap = new MyMap(urlMap);
		this.playMenuButtons = new PlayMenuButtons(getAssetManager());
		this.playCamera = new PlayCamera();
		this.player = new Player(getAssetManager(),  myMap.getWidthTiledMap(), myMap.getHeightTiledMap());
		this.playerScore = new PlayerScore();
		this.targetPlayerGiftSheep = new TargetPlayer(Constantes.URL_GIFT_SHEEP, getAssetManager(), TargetPlayerTypes.SHEEP, 1f/4f);
		this.myProperties = new MyProperties();
		this.myProperties.loadProperties(urlMap + ".properties");

		//inicia popup de tutorial
		this.popupTutorial = new Popup(getAssetManager(), PopupTypes.GAME_TUTORIAL);

		//iniciar fase
		init();
	}

	/**
	 * Inicializa váriaveis de contagem, flags de estado entre outras coisas
	 */
	private void init() {


		String[] points = myProperties.getOriginPlayer().split(",");
		player.init(Float.parseFloat(points[0])*Constantes.WIDTH_TILE,Float.parseFloat(points[1])*Constantes.HEIGHT_TILE);

		myPlayMusic.init();

		playerScore.init(Integer.parseInt(myProperties.getNumberEggs()), Integer.parseInt(myProperties.getNumberCorns()));

		points = myProperties.getOriginChickenNest().split(",");
		chickenNest.init(Float.parseFloat(points[0])*Constantes.WIDTH_TILE,Float.parseFloat(points[1])*Constantes.HEIGHT_TILE);

		Vector2 point;

		point = new Vector2(Util.getValidRandomPosition(myMap.getWidthTiledMap(), myMap.getHeightTiledMap(), myMap.getTiles(), chickenNest.getBoundingRectangle()));
		this.targetPlayerGiftSheep.init(point.x,  point.y);
		//não está visível
		this.targetPlayerGiftSheep.setVisible(false);

		//this.supporting = new Supporting[ApplicationConfig.numSupporting];
		this.textGame = new TextGame[ApplicationConfig.numTexts];	

		this. textGame[0] = new TextGame(Constantes.URL_TEXT_AMAZING, getAssetManager(), TextGameTypes.AMAZING);
		this. textGame[1] = new TextGame(Constantes.URL_TEXT_POW, getAssetManager(), TextGameTypes.POW);
		this. textGame[2] = new TextGame(Constantes.URL_TEXT_PLUS15, getAssetManager(), TextGameTypes.PLUS15);
		this. textGame[3] = new TextGame(Constantes.URL_TEXT_PLUS100, getAssetManager(), TextGameTypes.PLUS100);

		//inicializa todos os textos
		for(int i=0;i<textGame.length;i++)
			textGame[i].init(); //exibe texto na posi��o do player

		//inicializa vetor de coadjuvantes - personagens secundarios
		/*
		 * TODO retirado os porcos, pois ainda não fechamos a real posição deles
		this.supporting[0] = new Supporting(Constantes.URL_PIG_STOP_RIGHT, getAssetManager(), SupportingTypes.PIG_AWAKE_RIGHT);
		this.supporting[1] = new Supporting(Constantes.URL_PIG_SLEEPING_RIGHT, getAssetManager(), SupportingTypes.PIG_SLEEPING_RIGHT);
		this.supporting[2] = new Supporting(Constantes.URL_PIG_SLEEPING_LEFT, getAssetManager(), SupportingTypes.PIG_SLEEPING_LEFT);
		 */
		//inicializa vetor de ovos com velocidades aleatorias
		Random generator = new Random();

		//inicia vetor de coadjuvantes
		//		for(int i=0;i<supporting.length;i++){
		//			supporting[i].init(generator.nextInt(130)+10, generator.nextInt(150)+160);
		//		}

		//inicializa lista de ovos
		this.targetPlayerEggsList = new ArrayList<TargetPlayer>();
		//gera ovos em posi��es aleatorios
		for(int i=0;i<Integer.parseInt(myProperties.getNumberEggs());i++){
			this.targetPlayerEggsList.add( new TargetPlayer(Constantes.URL_EGGS, getAssetManager(),TargetPlayerTypes.EGGS, 1f/(float)generator.nextInt(5)));
			point = new Vector2(Util.getValidRandomPosition(myMap.getWidthTiledMap(), myMap.getHeightTiledMap(), myMap.getTiles(), chickenNest.getBoundingRectangle()));
			targetPlayerEggsList.get(i).init(point.x, point.y);
			targetPlayerEggsList.get(i).setVisible(true);
		}

		//inicializa lista de milhos
		this.targetPlayerCornsList = new ArrayList<TargetPlayer>();
		//gera milhos em posições aleatorios
		for(int i=0;i<Integer.parseInt(myProperties.getNumberCorns());i++){
			this.targetPlayerCornsList.add(new TargetPlayer(Constantes.URL_YELLOW_CORN, getAssetManager(),TargetPlayerTypes.YELLOW_CORN,1f/7f ));
			point = new Vector2(Util.getValidRandomPosition(myMap.getWidthTiledMap(), myMap.getHeightTiledMap(), myMap.getTiles(), chickenNest.getBoundingRectangle()));
			targetPlayerCornsList.get(i).init(point.x, point.y);
			targetPlayerCornsList.get(i).setVisible(true);
		}


		numCornCatchedIndex=0;
		contAmazing = 0;
		contPow = 0;
		contPlus15 = 0;
		contPlus100 = 0;

		flagPlus15 = false;
		flagPlus100 = false;

		saveRoads();
		createVehicles();

		//muda o estado do jogo para 'PAUSE', pois um popup irá aparecer e não posso ficar rodando a aplicação
		stateGame = StateGame.PAUSE;

		//visualizar popup - flag para auxiliar o jogador[tutorial]
		//TODO pausar as animações quando a aplicação estiver em pause[pelo usuário ou por mostrar o popup tutorial]
		this.flagPopupTutorial = true;
		this.playMenuButtons.disable();

	}

	private void playSound(Music sound) {
		if(Constantes.SOUND_ON_FLAG && !sound.isPlaying())
			sound.play();
	}

	/**
	 * Renderizador principal da classe. Nele há a lógica de estados do jogo.
	 */
	@Override
	public void render(float delta) {

		switch (stateGame) {
		case PLAYING:
			player.updatePlayerPosition(Gdx.graphics.getDeltaTime(), myMap.getTiles(), vehicleList);
			break;

		case RESTART:
			init();
			break;

		case GAME_OVER:
			break;
		default:
			break;
		}
		//desenhar na tela
		draw(delta);
	}
	/**
	 * Método principal de desenho
	 * @param delta
	 */
	private void draw(float delta) {	

		Gdx.gl.glClearColor(0, 0, 0, 1); //cor preta
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		

		//posição da camera deve acompanhar o jogador
		playCamera.setPosition(player.getX(), player.getY(), myMap.getWidthTiledMap(), myMap.getHeightTiledMap());

		//PROJETA NA MATRIX DO SPRITEBATCH DO MAPA
		chickenRoadGame.getSpriteBatch().setProjectionMatrix(playCamera.getOrthographicCamera().combined);		

		myMap.draw(playCamera.getOrthographicCamera());

		//musicas de fundo em looping
		if(stateGame != StateGame.PAUSE){
			playSound(myPlayMusic.getSoundBackgroundFase());
			playSound(myPlayMusic.getSoundBackgroundChicken());
		}

		chickenRoadGame.getSpriteBatch().begin();

		//desenhar o ninho
		chickenNest.draw(chickenRoadGame.getSpriteBatch());

		//desenhar o jogador
		player.draw(chickenRoadGame.getSpriteBatch(), delta);


		//desenha os personagens coadjuvantes
		//		for(int i=0;i<supporting.length;i++) {
		//			supporting[i].draw(chickenRoadGame.getSpriteBatch(), delta);
		//		}

		//desenhar todos os ovos
		for(int i=0;i<targetPlayerEggsList.size();i++) {
			targetPlayerEggsList.get(i).draw(chickenRoadGame.getSpriteBatch(), delta);		
		}

		//exibe presente - se pegar Y ovos
		if(playerScore.getCurrentNoCatchedEggs() == 0 && !targetPlayerGiftSheep.isLocker())
			targetPlayerGiftSheep.setVisible(true);
		else
			targetPlayerGiftSheep.setVisible(false);


		if(targetPlayerGiftSheep.isVisible()){	
			playSound(myPlayMusic.getSoundSheep());
			targetPlayerGiftSheep.draw(chickenRoadGame.getSpriteBatch(), delta);
		}

		//se pegou todos os ovos, exibe texto animado de fim de fase
		if(playerScore.getCurrentNoCatchedEggs() == 0 && chickenNest.checkColision(player)) {

			stopBackgroundMusic();

			//TODO aqui será o sucesso da aplicação certo? então, aqui iremos persistir o score e o presente[não há variável do presente por enquanto]
			if(stateGame != StateGame.FINISH)
				PreferencesUser.setSucesso(seasonId, faseId, playerScore.getScoreGame());


			if(contAmazing++ < 140) {//este if evitar que a anima��o fique infinita

				playSound(myPlayMusic.getSoundEndFase());

				//mostra no meio da tela aproximadamente
				textGame[0].setPosition((Constantes.WORLD_WIDTH - 350)/2 + deltaXPositionButtons, 
						(Constantes.WORLD_HEIGHT - 100)/2 + deltaYPositionButtons); //exibe texto na posi��o do playe
				textGame[0].draw(chickenRoadGame.getSpriteBatch(), delta);
			}else{
				myPlayMusic.getSoundEndFase().stop();

				//TODO isso não deve existir quando não for protótipo
				if((faseId+1) != Constantes.MAX_FASES){

					chickenRoadGame.setScreen(new PlayScreen(Constantes.URL_MAPS[seasonId][faseId+1], chickenRoadGame, seasonId, faseId+1));
				}else{
					chickenRoadGame.setScreen(new FasesScreen(chickenRoadGame, seasonId));
				}
			}

			myPlayMusic.getSoundBackgroundFase().pause();
			myPlayMusic.getSoundBackgroundChicken().pause();

			stateGame = StateGame.FINISH;

		}
		else { //else GAMBIARRA TEMPOR�RIA - :(
			contAmazing = 0;
		}

		//testa colis�o do alvo 
		for(int i=0;i<targetPlayerEggsList.size();i++){
			//so pode pegar ovos se ele nao tiver sido pego antes
			if(targetPlayerEggsList.get(i).checkColision(player) && (playerScore.getCurrentNoCatchedEggs() > 0)){ 
				targetPlayerEggsList.get(i).setVisible(false);//apaga o ovo da tela

				playSound(myPlayMusic.getSoundEggs());
				playerScore.addScoreGame(PlayerScore.EGGS_SCORE);
				playerScore.minusCurrentNoCatchedEggs();
				flagPlus15 = true;
			}			
		}
		//desenhar os veículos
		for(int i=0;i<vehicleList.size();i++){
			if(stateGame == StateGame.PLAYING)
				vehicleList.get(i).walkX();
			vehicleList.get(i).draw(chickenRoadGame.getSpriteBatch(), Gdx.graphics.getDeltaTime());
		}

		//testa colis�o do alvo - MILHOS ESCONDIDOS
		for(int i=0;i<targetPlayerCornsList.size();i++){
			//so pode pegar ovos se ele nao tiver sido pego antes
			if(targetPlayerCornsList.get(i).checkColision(player)){
				targetPlayerCornsList.get(i).setVisible(false); //apaga milho da tela
				targetPlayerCornsList.get(i).setLocker(true);
				playSound(myPlayMusic.getSoundCorns());
				playerScore.addScoreGame(PlayerScore.CORN_SCORE);
				playerScore.minusCurrentNoCatchedCorn();//decrementa o valor do milho
				flagPlus100 = true;
				numCornCatchedIndex = i;//recebe a posi��o do milho pego
			}			
		}

		chickenRoadGame.getSpriteBatch().end();
		//desenhar camada do mapa depois do player
		myMap.renderLayerColisaoObjetosImoveis();

		chickenRoadGame.getSpriteBatch().begin();


		/**
		 * Apresentar o +15 quando captura um ovo
		 */
		if(flagPlus15) {
			if(contPlus15++ < 45) {
				textGame[2].setPosition(player.getX()-20, player.getY()+30);
				textGame[2].draw(chickenRoadGame.getSpriteBatch(), delta);
			}else{
				contPlus15 = 0;
				flagPlus15 = false;
			}
		}

		/**
		 * Apresentar o +100 quando captura um milho
		 */
		if(flagPlus100) {
			if(contPlus100++ < 48) {
				//exibe apenas o milho encontrado
				targetPlayerCornsList.get(numCornCatchedIndex).setVisible(true);
				targetPlayerCornsList.get(numCornCatchedIndex).draw(chickenRoadGame.getSpriteBatch(), delta);		
				textGame[3].setPosition(player.getX()-20, player.getY()+30);
				textGame[3].draw(chickenRoadGame.getSpriteBatch(), delta);

			}else{
				contPlus100 = 0;
				flagPlus100 = false;
				targetPlayerCornsList.get(numCornCatchedIndex).setVisible(false);
			}
		}

		//renderizar os botões de play, restart, sair
		playMenuButtons.draw(chickenRoadGame.getSpriteBatch(), stateGame, deltaXPositionButtons, deltaYPositionButtons);

		//renderizar o score do player
		playerScore.draw(chickenRoadGame.getSpriteBatch(), deltaXPositionButtons, deltaYPositionButtons);
		
		player.getPlayerLife().draw(chickenRoadGame.getSpriteBatch());

		//exibe anima��o de colis�o se houve colis�o
		if(player.isColisionVehiclesStatus()) {
			playSound(myPlayMusic.getSoundChickenDemage());

			if(contPow++ < 55) {
				textGame[1].setPosition(player.getX()-30, player.getY()-30); //exibe texto na posi��o do playe
				textGame[1].draw(chickenRoadGame.getSpriteBatch(), delta);
			}
		} else {//else GAMBIARRA TEMPOR�RIA - :(
			contPow = 0;
		}


		//SE O LIFE FOR MENOR QUE ZERO - gameover
		if(player.getPlayerLife().getLife() <= 0) {
			player.getPlayerAnimation().setSpriteSheet(Constantes.URL_PLAYER_AVATAR_DEAD, PlayerTypes.AVATAR_DEAD); //muda para anima��o de avatar morto
			player.draw(chickenRoadGame.getSpriteBatch(), delta);

			stateGame = StateGame.GAME_OVER;

			stopBackgroundMusic();

		}

		//popup tutorial
		//TODO implementar botão para apresentar o tutorial, caso o jogador tenha duvida
		if(flagPopupTutorial)
			popupTutorial.draw(chickenRoadGame.getSpriteBatch(), delta);

		chickenRoadGame.getSpriteBatch().end();

		//se aproximar do ninho, deixa um ovo
		if(chickenNest.checkColision(player)) {
			Random gerador = new Random();
			for(int i=0;i<targetPlayerEggsList.size();i++){
				if(!targetPlayerEggsList.get(i).isVisible()){
					targetPlayerEggsList.get(i).init(24+chickenNest.getX()+gerador.nextInt(30), 30+chickenNest.getY()+gerador.nextInt(10));
					targetPlayerEggsList.get(i).setVisible(true);
					targetPlayerEggsList.get(i).setLocker(true);

				}
			}
		}

		//colisao com o presente ganho
		if(targetPlayerGiftSheep.checkColision(player)) {
			targetPlayerGiftSheep.setVisible(false);
			targetPlayerGiftSheep.setLocker(true);
			playSound(myPlayMusic.getSoundCoinEndFase());
			myPlayMusic.getSoundSheep().stop();
		}


	}//end draw()

	private void stopBackgroundMusic() {
		myPlayMusic.getSoundBackgroundChicken().stop();
		myPlayMusic.getSoundBackgroundFase().stop();
		myPlayMusic.getSoundCoinEndFase().stop();
		myPlayMusic.getSoundSheep().stop();
		myPlayMusic.getSoundChickenDemage().stop();

	}

	@Override
	public boolean keyDown(int keycode) {

		if((keycode == Keys.BACK)|| (keycode == Keys.ESCAPE)){
			if(stateGame == StateGame.PLAYING)
				stateGame = StateGame.PAUSE;
			else
				stateGame = StateGame.PLAYING;

			return true;
		}
		return false;
	}

	@Override
	//evento para liberação de toque na tela - quando solta a tela
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		Vector3 touchPoint = new Vector3(screenX, screenY, 0);
		playCamera.getOrthographicCamera().unproject(touchPoint);

		//clicou em pause
		if((stateGame != StateGame.PAUSE) && playMenuButtons.checkClickPauseButton(touchPoint.x, touchPoint.y)){
			stateGame = StateGame.PAUSE;
			return true;
		}
		//clicou no play
		if(playMenuButtons.checkClickPlayButton(touchPoint.x, touchPoint.y)){
			stateGame = StateGame.PLAYING;
			return true;
		}
		//clicou no restart
		if(playMenuButtons.checkClickRestartButton(touchPoint.x, touchPoint.y)){
			stateGame = StateGame.RESTART;
			return true;
		}
		//clicou para sair da fase e ir a tela de fases
		if(playMenuButtons.checkClickFaseListButton(touchPoint.x, touchPoint.y)){
			stopBackgroundMusic();
			myPlayMusic.getSoundEndFase().stop();
			chickenRoadGame.setScreenWithTransitionFade(new FasesScreen(chickenRoadGame, seasonId));
			return true;
		}

		if(popupTutorial != null && popupTutorial.checkClickOkTutorialButton(touchPoint.x, touchPoint.y)){
			flagPopupTutorial = false;
			playMenuButtons.enable();
			stateGame = StateGame.PLAYING;
			return true;
		}


		//se nao for clicando em nada acima, devo me movimentar
		if(stateGame == StateGame.PLAYING) player.movimentar(touchPoint);

		return false;
	}



	/**
	 * Capturar e salvar todas as estradas do mapa. Somente estradas HORIZONTAIS!!! Necessita de refatoração
	 */
	private void saveRoads() {

		ArrayList<String> stringList = myProperties.getRoads();

		roadList = new ArrayList<Road>();

		float initialPointX, initialPointY, width, height;
		int carsDistance;

		//largura dos carros - padrão sendo a largura do tile  
		int vehicleHeight = 64;

		String[] values = new String[2];
		ArrayList<RoadFaixa> roadFaixaList;
		for (int i = 0; i < stringList.size(); i++) {

			values = stringList.get(i).split(",");

			//ponto inicial X da estrada
			initialPointX = Float.parseFloat(values[0])*Constantes.WIDTH_TILE - DESLOC_INIT_X_ROAD;
			//ponto inicial Y da estrada
			initialPointY = Float.parseFloat(values[1])*Constantes.HEIGHT_TILE;
			//comprimento da estrada - compensada com DESLOC_INIT_X_ROAD, devido a origem em X ser deslocada
			width = Float.parseFloat(values[2])*Constantes.WIDTH_TILE + DESLOC_INIT_X_ROAD;
			//largura da estrada
			height = (Float.parseFloat(values[3]))*Constantes.HEIGHT_TILE;

			//a lista de faixas de cada estrada
			roadFaixaList = new ArrayList<RoadFaixa>();

			//calcular o número de faixas para os carros
			int numeroFaixas = (int)Math.floor(height/vehicleHeight);

			for(int j=0;j<numeroFaixas;j++){

				//calcular a velocidade de cada faixa
				float speed = Util.getRandomPosition(5, 30)/10;

				//calcular distancia entre os carros
				//carsDistance = (int)Util.getRandomPosition(width/16, width/14);
				carsDistance = Constantes.DISTANCE_MIN_BETWEEN_VEHICLES;
				//cadastrar dados de cada faixa
				roadFaixaList.add(new RoadFaixa(speed, new Vector2( initialPointX , initialPointY + vehicleHeight*j), width ,carsDistance, (j%2 == 0 ? Direction.RIGHT : Direction.LEFT)));
			}

			//estrada no jogo
			roadList.add(new Road(initialPointX, initialPointY, width, height, roadFaixaList));
		}
	}


	/**
	 * Criar os veículos. Necessita de refatoração
	 */
	private void createVehicles() {

		//TODO verificar os objetos que estarão nas estradas
		String[] pictures = {"veicules/veiculo1D.png", "veicules/veiculo1E.png"};

		vehicleList = new ArrayList<Vehicle>();

		Vehicle vehicle;
		float positionY;

		//quantidade de estradas
		for(int i=0;i<roadList.size();i++){

			Road road = roadList.get(i);

			for(int j=road.getRoadFaixaList().size()-1;j>=0;j--){
				RoadFaixa faixa = road.getRoadFaixaList().get(j);

				positionY = faixa.getInitialPoint().y;
				float positionX =  faixa.getInitialPoint().x;
				do {
					vehicle = new Vehicle(pictures[j%2], faixa, getAssetManager());
					vehicle.init(positionX, positionY);
					vehicleList.add(vehicle);
					positionX += vehicle.getWidth() + faixa.getCarsDistance()*Util.getRandomPosition(1, 4)*Util.getRandomPosition(1);
				}
				while(positionX < faixa.getInitialPoint().x+road.getWidth() - DESLOC_INIT_X_ROAD);
			}
		}
	}

	@Override
	public void pause() {
		stateGame = StateGame.PAUSE;
	}

	@Override
	public void dispose() {
		this.chickenRoadGame = null;
		this.playMenuButtons.dispose();
		this.stateGame = null;
		this.myMap.dispose();
		this.playerScore = null;
		this.vehicleList = null;

		for(int i=0;i<textGame.length;i++)
			this.textGame[i].dispose();

		//		for(int i=0;i<supporting.length;i++)
		//			this.supporting[i].dispose();

		for(int i=0;i<targetPlayerEggsList.size();i++)
			this.targetPlayerEggsList.get(i).dispose();

		for(int i=0;i<targetPlayerCornsList.size();i++)
			this.targetPlayerCornsList.get(i).dispose();

		this.targetPlayerGiftSheep.dispose();
	}
}
