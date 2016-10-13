package viewcontroller;

import controller.GameController;

public abstract class ViewController {
	protected MasterViewController masterViewController;
	protected GameController gameController;
	
	public void setController(MasterViewController masterViewController,
			GameController gameController) {
		this.masterViewController = masterViewController;
		this.gameController = gameController;
	}
	
	public void onLoadResources() {
		
	}
}
