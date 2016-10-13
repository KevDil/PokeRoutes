package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

public class AboutController extends ViewController {

    @FXML
    private Hyperlink hyperlink;

    @FXML
    void hyperlinkOnAction(ActionEvent event) {
    	String url = "https://sopra.cs.tu-dortmund.de/wiki/sopra/16b/projekt2/start";
 
    	masterViewController.openLink(url);
    }
    
    @FXML
    void onActionBackToMenu(ActionEvent event) {
    	masterViewController.loadMenu();
    }

}
