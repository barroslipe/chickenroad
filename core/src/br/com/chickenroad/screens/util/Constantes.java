package br.com.chickenroad.screens.util;

/**
 * 
 * Guardar todas as constantes da aplicação
 */
public class Constantes {

	public static final int MAX_FASES = 9; //para versao beta
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
	public static final String URL_PLAY_BUTTON = "playButton.png";
	public static final String URL_EXIT_BUTTON = "exitButton.png";
	//public static final String URL_NEXT_FASE_BUTTON = "proximaFaseButton.png";
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

	public static final String URL_PLAYER_NORMAL_LIFE = "player/normalLife.png";
	public static final String URL_PLAYER_DEAD_LIFE = "player/deadLife.png";
	public static final String [] URL_LIFE_BARS = {"barBlue_horizontalBlue.png", "barGreen_horizontalMid.png", "barRed_horizontalMid.png"};


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

	//popup
	public static final String URL_GAMEOVER_POPUP = "gameoverPopup.png";
	public static final String URL_TURORIAL_POPUP = "tutorialPopup.png";

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
	public static final String URL_CAR_RIGHT[] = {"veicules/veiculo1D.png", "veicules/veiculo2D.png", "veicules/veiculo3D.png"};
	public static final String URL_CAR_LEFT[]  = {"veicules/veiculo1E.png", "veicules/veiculo2E.png", "veicules/veiculo3E.png"};
	public static final String URL_MOTORCYCLE_LEFT[]  = {"veicules/moto1E.png", "veicules/moto2E.png"};
	public static final String URL_MOTORCYCLE_RIGHT[] = {"veicules/moto1D.png", "veicules/moto1D.png"};

	//textos
	public static final String URL_TEXT_FONT_KRAASH1 = "fonts/Kraash.ttf";
	public static final String URL_FONT_KRAASH_BLACK = "fonts/Kraash Black.ttf";
	public static final String URL_TEXT_AMAZING = "play/amazing.png";
	public static final String URL_TEXT_POW = "play/pow.png";
	public static final String URL_TEXT_PLUS15 = "play/plus15.png";
	public static final String URL_TEXT_PLUS100 = "play/plus100.png";


	public static final String URL_SEASON_PICTURE_LIST[] = {"seasons/seasonRoosterSong.png", "seasons/seasonFireInFarm.png",  
		"seasons/seasonInvasion1.png", "seasons/seasonHorrorNight.png"};
	public static final String URL_FASE_PICTURE_LIST [] = {"fases/fase_101.png", "fases/fase_102.png", "fases/fase_103.png", "fases/fase_104.png",
		"fases/fase_105.png", "fases/fase_106.png", "fases/fase_107.png", "fases/fase_108.png",
		"fases/fase_109.png", "fases/fase_110.png", "fases/fase_111.png", "fases/fase_112.png",
		"fases/fase_113.png", "fases/fase_114.png", "fases/fase_115.png"};
	public static final String URL_FASE_BLOQUEADA = "fases/fase_bloqueada.png";

	//mapas
	public static final String [][]URL_MAPS = {
		{"maps/map_101/map_101", "maps/map_102/map_102", "maps/map_103/map_103", "maps/map_104/map_104",
			"maps/map_105/map_105","maps/map_106/map_106","maps/map_107/map_107",
			"maps/map_108/map_108", "maps/map_115/map_115", "maps/map_special/map_special"}
	};
}
