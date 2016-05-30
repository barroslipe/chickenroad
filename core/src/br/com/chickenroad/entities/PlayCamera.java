package br.com.chickenroad.entities;

import br.com.chickenroad.screens.PlayScreen;
import br.com.chickenroad.screens.util.Constantes;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayCamera {

	private OrthographicCamera orthographicCamera;

	public OrthographicCamera getOrthographicCamera() {
		return orthographicCamera;
	}

	public  PlayCamera() {
		this.orthographicCamera = new OrthographicCamera();
		this.orthographicCamera.setToOrtho(false, Constantes.WORLD_WIDTH, Constantes.WORLD_HEIGHT);
	}

	public void setPosition(float x, float y, int w, int h) {

		orthographicCamera.position.set(x, y, 0);

		if(orthographicCamera.position.x < Constantes.WORLD_WIDTH/2) {
			orthographicCamera.position.x = Constantes.WORLD_WIDTH/2;
		}

		if(orthographicCamera.position.y < Constantes.WORLD_HEIGHT/2) {
			orthographicCamera.position.y = Constantes.WORLD_HEIGHT/2;
		}

		if(orthographicCamera.position.x > w-Constantes.WORLD_WIDTH/2) {
			orthographicCamera.position.x = w-Constantes.WORLD_WIDTH/2;

		}

		if(orthographicCamera.position.y > h-Constantes.WORLD_HEIGHT/2) {
			orthographicCamera.position.y = h-Constantes.WORLD_HEIGHT/2;
		}

		PlayScreen.deltaYPositionButtons = (int)(orthographicCamera.position.y - Constantes.WORLD_HEIGHT/2 > 0 ? orthographicCamera.position.y - Constantes.WORLD_HEIGHT/2 : 0);
		PlayScreen.deltaXPositionButtons = (int)(orthographicCamera.position.x - Constantes.WORLD_WIDTH/2 > 0 ? orthographicCamera.position.x - Constantes.WORLD_WIDTH/2 : 0);


		orthographicCamera.update();

	}
}
