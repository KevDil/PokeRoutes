package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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

}
