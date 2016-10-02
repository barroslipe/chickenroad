package br.com.chickenroad.screens.util;

/**
 * 
 * Guardar todas as constantes da aplicação
 */
public class Constantes {

	public static final int MAX_FASES = 10; //para versao beta
	public static final int DISTANCE_MIN_BETWEEN_VEHICLES = 100;

	public static final int WIDTH_TILE = 32;
	public static final int HEIGHT_TILE = 32;
	public static final int WIDTH_EGGS = 18;
	public static final int HEIGHT_EGGS = 26;

	public static final int WORLD_WIDTH = 640;
	public static final int WORLD_HEIGHT = 480;

	public static boolean SOUND_ON_FLAG = true;

	public static final String URL_BACKGROUND = "backgroundMenu.png";
	public static final String URL_BACKGROUND_SEASON_ROOSTER_SONG = "backgroundSeasonRoosterSong.png";
	public static final String URL_BACKGROUND_ALL_SEASONS = "backgroundAllSeasons.png";
	public static final String URL_BACKGROUND_SPLASHSCREEN = "splashScreenBackground.jpg";
	public static final String URL_EXIT_BUTTON = "exitButton.png";
	public static final String URL_NEXT_FASE_BUTTON = "nextFaseButton.png";
	public static final String URL_OK_BUTTON = "okButton.png";
	public static final String URL_BACK_BUTTON = "backButton.png";	
	public static final String URL_SOUND_ON_BUTTON = "soundOnButton.png";
	public static final String URL_SOUND_OFF_BUTTON = "soundOffButton.png";
	public static final String URL_POPUP_NO_BUTTON = "popupNoButton.png";
	public static final String URL_POPUP_YES_BUTTON = "popupYesButton.png";
	public static final String URL_POPUP_EXIT_BACKGROUND = "popupExitBackground.png";

	//sons
	public static final String URL_SOUND_PRINCIPAL = "sounds/soundMenuBackground.mp3";
	public static final String URL_SOUND_CLICK = "sounds/soundClick.mp3";
	public static final String URL_SOUND_EGGS = "sounds/soundEggs.mp3";
	public static final String URL_SOUND_CHICKEN_DEMAGE = "sounds/soundChickenDemage.mp3";
	public static final String URL_SOUND_CORNS = "sounds/soundCorns.mp3";
	//public static final String URL_SOUND_ROOSTER = "sounds/soundRooster.mp3";
	public static final String URL_SOUND_BACKGROUND_FASE1 = "sounds/soundBackgroundFase1.mp3";
	public static final String URL_SOUND_END_FASE = "sounds/soundEndFase.wav";
	public static final String URL_SOUND_END_FASE_COIN = "sounds/soundCoinEndFase.mp3";
	public static final String URL_SOUND_BACKGROUND_CHICKEN = "sounds/soundChickenBackground.mp3";
	public static final String URL_SOUND_SHEEP = "sounds/soundSheep.mp3";
	public static final String URL_SOUND_HORN = "sounds/soundHorn.mp3";

	//player
	public static final String URL_PLAYER_AVATAR = "player/player.png";
	public static final String URL_PLAYER_AVATAR_RIGHT ="player/playerD_caminhando.png"; 
	public static final String URL_PLAYER_AVATAR_LEFT = "player/playerE_caminhando.png";
	public static final String URL_PLAYER_AVATAR_JUMP_RIGHT = "player/playerD_pulando.png";
	public static final String URL_PLAYER_AVATAR_JUMP_LEFT = "player/playerE_pulando.png";
	public static final String URL_PLAYER_AVATAR_STOP_RIGHT = "player/playerD_parado.png";
	public static final String URL_PLAYER_AVATAR_STOP_LEFT = "player/playerE_parado.png";
	public static final String URL_PLAYER_AVATAR_UP = "player/playerU_caminhando.png";
	public static final String URL_PLAYER_AVATAR_DOWN = "player/playerB_caminhando.png";
	public static final String URL_PLAYER_AVATAR_DEAD = "player/player_morto.png";
	
	public static final String URL_LIFE_FULL = "player/full.png";
	public static final String URL_LIFE_HALF = "player/half.png";
	public static final String URL_LIFE_EMPTY = "player/empty.png";
	
	//popup
	public static final String URL_GAMEOVER_POPUP = "gameoverPopup.png";
	public static final String URL_PAUSE_POPUP = "pausePopup.png";
	public static final String URL_TURORIAL_POPUP = "tutorialPopup.png";
	public static final String URL_SUCCESS_POPUP = "successPopup.png";

	//sprites secundarios
	public static final String URL_PIG_STOP_RIGHT = "play/porcoD_parado.png";
	public static final String URL_PIG_STOP_LEFT = "play/porcoE_parado.png";
	public static final String URL_PIG_SLEEPING_RIGHT = "play/porcoD_durmindo.png";
	public static final String URL_PIG_SLEEPING_LEFT = "play/porcoE_durmindo.png";
	public static final String URL_CHICKENNEST = "play/ninho.png";
	//public static final String URL_PORTAL = "play/portal.png";
	public static final String URL_YELLOW_CORN = "play/milhoAmarelo.png";
	public static final String URL_YELLOW_CORN_SCORE = "play/milhoAmareloScore.png";
	public static final String URL_EGGS = "play/ovo.png";
	public static final String URL_EGGS_SCORE = "play/ovoScore.png";
	public static final String URL_GIFT_SHEEP = "play/giftOvelha.png";

	//veiculos
	public static final String URL_VEHICLE_RIGHT[] = {"vehicles/veiculo1D.png","vehicles/moto1D.png", "vehicles/veiculo2D.png", "vehicles/veiculo3D.png", "vehicles/moto2D.png"};
	public static final String URL_VEHICLE_LEFT[]  = {"vehicles/veiculo1E.png", "vehicles/moto2E.png", "vehicles/veiculo2E.png", "vehicles/veiculo3E.png", "vehicles/moto1E.png"};

	//textos
	public static final String URL_TEXT_FONT_KRAASH1 = "fonts/Kraash.ttf";
	public static final String URL_FONT_KRAASH_BLACK = "fonts/Kraash Black.ttf";
	public static final String URL_TEXT_AMAZING = "play/amazing.png";
	public static final String URL_TEXT_POW = "play/pow.png";
	public static final String URL_TEXT_PLUS15 = "play/plus15.png";
	public static final String URL_TEXT_PLUS100 = "play/plus100.png";


	public static final String URL_SEASON_PICTURE_LIST[] = {"seasons/seasonRoosterSong.png", "seasons/seasonFireInFarm.png",  
		"seasons/seasonInvasion1.png", "seasons/seasonHorrorNight.png"};
	public static final String URL_FASE_PICTURE_LIST [][] = {{"fases/fase_101.png", "fases/fase_102.png", "fases/fase_103.png", "fases/fase_104.png",
		"fases/fase_105.png", "fases/fase_106.png", "fases/fase_107.png", "fases/fase_108.png",
		"fases/fase_109.png", "fases/fase_110.png", "fases/fase_111.png", "fases/fase_112.png",
		"fases/fase_113.png", "fases/fase_114.png", "fases/fase_115.png"},
		{"fases/fase_201.png", "fases/fase_202.png", "fases/fase_203.png", "fases/fase_204.png",
			"fases/fase_205.png", "fases/fase_206.png", "fases/fase_207.png", "fases/fase_208.png",
			"fases/fase_209.png", "fases/fase_210.png", "fases/fase_211.png", "fases/fase_212.png",
			"fases/fase_213.png", "fases/fase_214.png", "fases/fase_215.png"},
			{"fases/fase_301.png", "fases/fase_302.png", "fases/fase_303.png", "fases/fase_304.png",
				"fases/fase_305.png", "fases/fase_306.png", "fases/fase_307.png", "fases/fase_308.png",
				"fases/fase_309.png", "fases/fase_310.png", "fases/fase_311.png", "fases/fase_312.png",
				"fases/fase_313.png", "fases/fase_314.png", "fases/fase_315.png"},
				{"fases/fase_401.png", "fases/fase_402.png", "fases/fase_403.png", "fases/fase_404.png",
					"fases/fase_405.png", "fases/fase_406.png", "fases/fase_407.png", "fases/fase_408.png",
					"fases/fase_409.png", "fases/fase_410.png", "fases/fase_411.png", "fases/fase_412.png",
					"fases/fase_413.png", "fases/fase_414.png", "fases/fase_415.png"},};
	public static final String URL_FASE_BLOQUEADA = "fases/fase_bloqueada.png";

	//mapas
	public static final String [][]URL_MAPS = {
		{"maps/map_101/map_101", "maps/map_102/map_102", "maps/map_103/map_103", "maps/map_104/map_104",
			"maps/map_105/map_105","maps/map_106/map_106","maps/map_107/map_107",
			"maps/map_108/map_108", "maps/map_109/map_109","maps/map_115/map_115", "maps/map_special/map_special"}
	};
	public static final String URL_STAR1_SCORE = "star1.png";
	public static final String URL_STAR2_SCORE = "star2.png";
	public static final String URL_STAR3_SCORE = "star3.png";

	public static final String URL_STAR1_POPUP = "star1Popup.png";
	public static final String URL_STAR2_POPUP = "star2Popup.png";
	public static final String URL_STAR3_POPUP = "star3Popup.png";
	public static final String URL_PLAY_BUTTON = "playFaseButton.png";
	public static final String URL_PAUSE_BUTTON = "pauseFaseButton.png";
	public static final String URL_RESTART_BUTTON = "restartFaseButton.png";
	public static final String URL_LIST_FASE_BUTTON = "listFaseButton.png";

}
