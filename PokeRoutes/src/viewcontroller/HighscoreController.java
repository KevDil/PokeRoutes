package viewcontroller;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.PlayerData;

public class HighscoreController extends ViewController {
	private ObservableList<HighscoreEntry> highscores;
	
	@FXML
    private TableView<HighscoreEntry> tvHighscore;

    @FXML
    private TableColumn<HighscoreEntry, String> tcPosition;

    @FXML
    private TableColumn<HighscoreEntry, String> tcPlayer;

    @FXML
    private TableColumn<HighscoreEntry, String> tcScore;

    @FXML
    void resetOnAction(ActionEvent event) {
    	gameController.getHighscoreController().resetScore();
    }
    
    @FXML
    void onActionMenu(ActionEvent event) {
    	masterViewController.loadMenu();
    }
    
    public void initialize() {
    	 highscores = FXCollections.observableArrayList();
    	 
    	 tvHighscore.setItems(highscores);
    	 tcPosition.setCellValueFactory(
    			 new PropertyValueFactory<HighscoreEntry, String>("number"));
    	 tcPlayer.setCellValueFactory(
    			 new PropertyValueFactory<HighscoreEntry, String>("playerName"));
    	 tcScore.setCellValueFactory(
    			 new PropertyValueFactory<HighscoreEntry, String>("score"));
    }
    
    public void loadHighscore() {
    	highscores.clear();
    	
    	List<PlayerData> playerData = 
    			gameController.getHighscoreController().showTop(50);
    	
    	for(int i = 0; i < playerData.size(); i++)
    		highscores.add(new HighscoreEntry(i+1, playerData.get(i)));
    }
    
    public static class HighscoreEntry {
		private final String number;
        private final String playerName;
        private final String score;
 
        private HighscoreEntry(int n, PlayerData playerData) {
            this.number = "" + n;
            this.playerName = playerData.getName();
            this.score = "" + playerData.getScore();
        }
        
        /**
		 * @return the number
		 */
		public String getNumber() {
			return number;
		}

		/**
		 * @return the playerName
		 */
		public String getPlayerName() {
			return playerName;
		}

		/**
		 * @return the score
		 */
		public String getScore() {
			return score;
		}
    }

}
