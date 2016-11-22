package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MenuController extends ViewController {

    @FXML
    void onActionAbout(ActionEvent event) {
    	masterViewController.loadAbout();
    }

    @FXML
    void onActionEndGame(ActionEvent event) {
    	masterViewController.closeStage();
    }

    @FXML
    void onActionHighscore(ActionEvent event) {
    	masterViewController.loadHighscore();
    }

    @FXML
    void onActionInstructions(ActionEvent event) {
    	String pdf = "https://sopra.cs.tu-dortmund.de/wiki/_media/sopra/16b/gruppe07/public/produktbeschreibungpokeroutes.pdf";
    	
    	masterViewController.openLink(pdf);
  
    }

    @FXML
    void onActionNewGame(ActionEvent event) {
    	masterViewController.loadManagePlayer();
    }
    
    @FXML
    void onActionLoadGame(ActionEvent event) {
    	try {
			masterViewController.load();
			masterViewController.loadMainscreen();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("Das Spiel konnte nicht geladen werden!");
		}
    }

}
