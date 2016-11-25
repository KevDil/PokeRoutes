package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class AboutController extends ViewController {


    @FXML
    void qrcodeOnAction(MouseEvent event) {
    	String url = "https://sopra.cs.tu-dortmund.de/wiki/sopra/16b/projekt2/start";
    	masterViewController.openLink(url);
    }
    
    @FXML
    void javaOnAction(MouseEvent event) {
    	String url = "https://www.java.com/de/";
    	masterViewController.openLink(url);
    }
    
    @FXML
    void onActionBackToMenu(ActionEvent event) {
    	masterViewController.loadMenu();
    }

}
