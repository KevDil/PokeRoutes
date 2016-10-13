package viewcontroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * package viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class AvatarController {

    @FXML
    private Button checkAvatar;

    @FXML
    private ListView<?> lvAvatars;

    @FXML
    void avatarOnAction(ActionEvent event) {

    }

}

 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class AvatarController extends ViewController {

    @FXML
    private Button checkAvatar;

    @FXML
    private HBox hboxav;

    @FXML
    private ListView<Image> lvAvatars;
    
    private ObservableList<Image> images = FXCollections.observableArrayList();

    @FXML
    void avatarOnAction(ActionEvent event) {
    }
    
    public void initialize() {
    }
    
    @Override
    public void onLoadResources() {
    	ResourceController rc = masterViewController.getResourceController();
    	
    	for(int i = 0; i < 9; i++)
    		images.add(rc.getAvatar(i));
    	
    	lvAvatars.setCellFactory(listView -> new ListCell<Image>() {
    	    private ImageView imageView = new ImageView();

    	    public void updateItem(Image item, boolean empty) {
    	        super.updateItem(item, empty);
    	        if (empty) {
    	            setGraphic(null);
    	        } else { 
    	            imageView.setImage(item);
    	            setGraphic(imageView);
    	        }
    	    }
    	});
    	
    	lvAvatars.setItems(images);
    	lvAvatars.getSelectionModel().select(0);
    }
    
    public int getSelectedAvatarId() {
    	return lvAvatars.getSelectionModel().getSelectedIndex();
    }

}