package controller;

public class NetworkController extends PlayerController {

	private GameController gameController;

	/**
	 *  
	 */
	public NetworkController(GameController gameController) {
		super(gameController);
	}

	public void update(long deltaMs) {

	}

	public GameController getGameController() {
		return gameController;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

}
