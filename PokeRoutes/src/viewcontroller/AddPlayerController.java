package viewcontroller;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.AIPlayerData;
import model.PlayerData;

public class AddPlayerController extends ViewController {
	
	@FXML private Pane paneAddPlayer;
	@FXML private GridPane grPane;
    @FXML private ImageView imgAvatar;
    @FXML private Label newPlayerNo;
    @FXML private TextField txtName;
    @FXML private RadioButton choiceHuman;
    @FXML private ToggleGroup choiceType;
    @FXML private RadioButton choiceAI;
    @FXML private ComboBox<String> comboDifficult;
    
    private int avatarId = 1;
    
    HashMap<Integer, Image> images = new HashMap<Integer, Image>();
    
    @FXML
    private void initialize() {
    	comboDifficult.getItems().addAll("Leicht", "Mittel", "Schwer");
    	comboDifficult.getSelectionModel().select(0);
    }

    @FXML
    void aiOnAction(ActionEvent event) {
    	comboDifficult.setDisable(isHuman());
    }

    @FXML
    void humanOnAction(ActionEvent event) {
    	comboDifficult.setDisable(isHuman());
    }
    
    @FXML
    void onActionNextAvatar(ActionEvent event) {
    	
    }

    @FXML
    void onActionPreviousAvatar(ActionEvent event) {

    }
    
    @FXML
    void test(ActionEvent event) {
    	if(grPane.isVisible()) {
    		setVisible(false);
    	} else {
    		setVisible(true);
    	}
    }
    
    private boolean isHuman() {
    	return choiceType.getSelectedToggle().equals(choiceHuman);
    }
    
    public PlayerData toPlayerData() {
    	PlayerData playerData;
    	if(isHuman()) {
    		playerData = new PlayerData();
    	} else {
    		playerData = new AIPlayerData();
    		((AIPlayerData)playerData).setLevel(
    				comboDifficult.getSelectionModel().getSelectedIndex() + 1);
    	}
    	
    	playerData.setName(txtName.getText());
    	playerData.setAvatar(avatarId);
    	
    	return playerData;
    }
    
    public String getPlayerNo() {
    	return newPlayerNo.getText();
    }
    public void setPlayerNo(String no) {
    	newPlayerNo.setText(no);
    }
    
    public String getPlayerName() {
    	return txtName.getText();
    }
    
    public void disableAI() {
    	choiceAI.setDisable(true);
    }
    
    public void reset() {
    	txtName.clear();
    	choiceType.selectToggle(choiceHuman);
    }
    
    public void setVisible(boolean visible) {
    	grPane.setVisible(visible);
    }
    
}
