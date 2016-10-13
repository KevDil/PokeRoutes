package viewcontroller;

import java.io.IOException;
import java.net.URL;

import controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ViewContext<TController extends ViewController> {
	private TController controller;
	private Parent pane;
	private Scene scene;
	private boolean hasController;
	
	@SuppressWarnings("unchecked")
	public ViewContext(URL fxmlPath, boolean hasController, String css) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(fxmlPath);
	
		this.pane = fxmlLoader.load();
		
		//Only load controller if there is one
		if(hasController)
			this.controller = (TController)fxmlLoader.getController();
		
		this.scene = new Scene(this.pane);
		this.hasController = hasController;
		
		this.scene.getStylesheets().add(css);
	}
	
	public void setController(MasterViewController masterViewController,
			GameController gameController) {
		if(hasController)
			controller.setController(masterViewController, gameController);
	}
	

	public TController getController() {
		return controller;
	}

	public Scene getScene() {
		return scene;
	}

	public Parent getPane() {
		return pane;
	}
}
