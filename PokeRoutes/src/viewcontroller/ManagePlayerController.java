package viewcontroller;

import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.AIPlayerData;
import model.GameParameter;
import model.PlayerData;

public class ManagePlayerController extends ViewController {

	@FXML
	private Button addPlayer;
	@FXML
	private Button deletePlayer;
	@FXML
	private Button resetPlayers;

	@FXML
	private GridPane gridPanePlayer1;
	@FXML
	private ImageView imgAvatar1;
	@FXML
	private TextField txtName1;
	@FXML
	private RadioButton choiceHuman1;
	@FXML
	private ToggleGroup choiceType1;
	@FXML
	private RadioButton choiceAI1;
	@FXML
	private ComboBox<String> comboDifficult1;

	@FXML
	private GridPane gridPanePlayer2;
	@FXML
	private ImageView imgAvatar2;
	@FXML
	private TextField txtName2;
	@FXML
	private RadioButton choiceHuman2;
	@FXML
	private ToggleGroup choiceType2;
	@FXML
	private RadioButton choiceAI2;
	@FXML
	private ComboBox<String> comboDifficult2;

	@FXML
	private GridPane gridPanePlayer3;
	@FXML
	private ImageView imgAvatar3;
	@FXML
	private TextField txtName3;
	@FXML
	private RadioButton choiceHuman3;
	@FXML
	private ToggleGroup choiceType3;
	@FXML
	private RadioButton choiceAI3;
	@FXML
	private ComboBox<String> comboDifficult3;

	@FXML
	private GridPane gridPanePlayer4;
	@FXML
	private ImageView imgAvatar4;
	@FXML
	private TextField txtName4;
	@FXML
	private RadioButton choiceHuman4;
	@FXML
	private ToggleGroup choiceType4;
	@FXML
	private RadioButton choiceAI4;
	@FXML
	private ComboBox<String> comboDifficult4;

	@FXML
	private GridPane gridPanePlayer5;
	@FXML
	private ImageView imgAvatar5;
	@FXML
	private TextField txtName5;
	@FXML
	private RadioButton choiceHuman5;
	@FXML
	private ToggleGroup choiceType5;
	@FXML
	private RadioButton choiceAI5;
	@FXML
	private ComboBox<String> comboDifficult5;

	@FXML
	private GridPane gridPanePlayer6;
	@FXML
	private ImageView imgAvatar6;
	@FXML
	private TextField txtName6;
	@FXML
	private RadioButton choiceHuman6;
	@FXML
	private ToggleGroup choiceType6;
	@FXML
	private RadioButton choiceAI6;
	@FXML
	private ComboBox<String> comboDifficult6;

	@FXML
	private GridPane gridPanePlayer7;
	@FXML
	private ImageView imgAvatar7;
	@FXML
	private TextField txtName7;
	@FXML
	private RadioButton choiceHuman7;
	@FXML
	private ToggleGroup choiceType7;
	@FXML
	private RadioButton choiceAI7;
	@FXML
	private ComboBox<String> comboDifficult7;

	private int countPlayer = 2;
	private int[] imageIndex = {0, 1, 2, 3, 4, 5, 6};

	@FXML
	private void initialize() {
		comboDifficult1.getItems().addAll("Leicht", "Mittel", "Schwer");
		comboDifficult1.getSelectionModel().select(0);
		comboDifficult2.getItems().addAll("Leicht", "Mittel", "Schwer");
		comboDifficult2.getSelectionModel().select(0);
		comboDifficult3.getItems().addAll("Leicht", "Mittel", "Schwer");
		comboDifficult3.getSelectionModel().select(0);
		comboDifficult4.getItems().addAll("Leicht", "Mittel", "Schwer");
		comboDifficult4.getSelectionModel().select(0);
		comboDifficult5.getItems().addAll("Leicht", "Mittel", "Schwer");
		comboDifficult5.getSelectionModel().select(0);
		comboDifficult6.getItems().addAll("Leicht", "Mittel", "Schwer");
		comboDifficult6.getSelectionModel().select(0);
		comboDifficult7.getItems().addAll("Leicht", "Mittel", "Schwer");
		comboDifficult7.getSelectionModel().select(0);

		gridPanePlayer3.setVisible(false);
		gridPanePlayer4.setVisible(false);
		gridPanePlayer5.setVisible(false);
		gridPanePlayer6.setVisible(false);
		gridPanePlayer7.setVisible(false);
	}

	@FXML
	void addPlayerOnAction(ActionEvent event) {
		switch (countPlayer) {
		case 2:
			gridPanePlayer3.setVisible(true);
			countPlayer++;
			break;
		case 3:
			gridPanePlayer4.setVisible(true);
			countPlayer++;
			break;
		case 4:
			gridPanePlayer5.setVisible(true);
			countPlayer++;
			break;
		case 5:
			gridPanePlayer6.setVisible(true);
			countPlayer++;
			break;
		case 6:
			gridPanePlayer7.setVisible(true);
			countPlayer++;
			break;
		default:
			break;
		}
	}

	@FXML
	void deletePlayerOnAction(ActionEvent event) {
		switch (countPlayer) {
		case 3:
			resetPlayer(countPlayer);
			gridPanePlayer3.setVisible(false);

			countPlayer--;
			break;
		case 4:
			gridPanePlayer4.setVisible(false);
			resetPlayer(countPlayer);
			countPlayer--;
			break;
		case 5:
			gridPanePlayer5.setVisible(false);
			resetPlayer(countPlayer);
			countPlayer--;
			break;
		case 6:
			gridPanePlayer6.setVisible(false);
			resetPlayer(countPlayer);
			countPlayer--;
			break;
		case 7:
			gridPanePlayer7.setVisible(false);
			resetPlayer(countPlayer);
			countPlayer--;
			break;
		default:
			break;
		}
	}

	@FXML
	void startGameOnAction(ActionEvent event) {
		ArrayList<PlayerData> newPlayerDataList = new ArrayList<>();
		Random rand = new Random();
		long seed = rand.nextLong();
		
		for (int i = 1; i <= countPlayer; i++) {
			newPlayerDataList.add(toPlayerData(i));
		}

		GameParameter newGameParameter = new GameParameter(false, seed, newPlayerDataList);
		gameController.initialize(newGameParameter);
		masterViewController.loadMainscreen();
	}

	@FXML
	void resetPlayersOnAction(ActionEvent event) {
		gridPanePlayer3.setVisible(false);
		gridPanePlayer4.setVisible(false);
		gridPanePlayer5.setVisible(false);
		gridPanePlayer6.setVisible(false);
		gridPanePlayer7.setVisible(false);

		for (int i = 1; i <= 7; i++) {
			resetPlayer(i);
		}
		countPlayer = 2;
	}

	@FXML
	void onActionMenu(ActionEvent event) {
		masterViewController.loadMenu();
		resetPlayersOnAction(null);
	}

	@FXML
	void aiOnAction1(ActionEvent event) {
		comboDifficult1.setDisable(false);
	}

	@FXML
	void aiOnAction2(ActionEvent event) {
		comboDifficult2.setDisable(false);
	}

	@FXML
	void aiOnAction3(ActionEvent event) {
		comboDifficult3.setDisable(false);
	}

	@FXML
	void aiOnAction4(ActionEvent event) {
		comboDifficult4.setDisable(false);
	}

	@FXML
	void aiOnAction5(ActionEvent event) {
		comboDifficult5.setDisable(false);
	}

	@FXML
	void aiOnAction6(ActionEvent event) {
		comboDifficult6.setDisable(false);
	}

	@FXML
	void aiOnAction7(ActionEvent event) {
		comboDifficult7.setDisable(false);
	}

	@FXML
	void humanOnAction1(ActionEvent event) {
		comboDifficult1.setDisable(true);
	}

	@FXML
	void humanOnAction2(ActionEvent event) {
		comboDifficult2.setDisable(true);
	}

	@FXML
	void humanOnAction3(ActionEvent event) {
		comboDifficult3.setDisable(true);
	}

	@FXML
	void humanOnAction4(ActionEvent event) {
		comboDifficult4.setDisable(true);
	}

	@FXML
	void humanOnAction5(ActionEvent event) {
		comboDifficult5.setDisable(true);
	}

	@FXML
	void humanOnAction6(ActionEvent event) {
		comboDifficult6.setDisable(true);
	}

	@FXML
	void humanOnAction7(ActionEvent event) {
		comboDifficult7.setDisable(true);
	}

	@FXML
	void onActionNextAvatar1(ActionEvent event) {
		incrementImageIndex(0);
		imgAvatar1.setImage(masterViewController.getResourceController().getAvatar(imageIndex[0]));
	}

	@FXML
	void onActionNextAvatar2(ActionEvent event) {
		incrementImageIndex(1);
		imgAvatar2.setImage(masterViewController.getResourceController().getAvatar(imageIndex[1]));
	}

	@FXML
	void onActionNextAvatar3(ActionEvent event) {
		incrementImageIndex(2);
		imgAvatar3.setImage(masterViewController.getResourceController().getAvatar(imageIndex[2]));
	}

	@FXML
	void onActionNextAvatar4(ActionEvent event) {
		incrementImageIndex(3);
		imgAvatar4.setImage(masterViewController.getResourceController().getAvatar(imageIndex[3]));
	}

	@FXML
	void onActionNextAvatar5(ActionEvent event) {
		incrementImageIndex(4);
		imgAvatar5.setImage(masterViewController.getResourceController().getAvatar(imageIndex[4]));
	}

	@FXML
	void onActionNextAvatar6(ActionEvent event) {
		incrementImageIndex(5);
		imgAvatar6.setImage(masterViewController.getResourceController().getAvatar(imageIndex[5]));
	}

	@FXML
	void onActionNextAvatar7(ActionEvent event) {
		incrementImageIndex(6);
		imgAvatar7.setImage(masterViewController.getResourceController().getAvatar(imageIndex[6]));
	}

	@FXML
	void onActionPreviousAvatar1(ActionEvent event) {
		decrementImageIndex(0);
		imgAvatar1.setImage(masterViewController.getResourceController().getAvatar(imageIndex[0]));
	}

	@FXML
	void onActionPreviousAvatar2(ActionEvent event) {
		decrementImageIndex(1);
		imgAvatar2.setImage(masterViewController.getResourceController().getAvatar(imageIndex[1]));
	}

	@FXML
	void onActionPreviousAvatar3(ActionEvent event) {
		decrementImageIndex(2);
		imgAvatar3.setImage(masterViewController.getResourceController().getAvatar(imageIndex[2]));
	}

	@FXML
	void onActionPreviousAvatar4(ActionEvent event) {
		decrementImageIndex(3);
		imgAvatar4.setImage(masterViewController.getResourceController().getAvatar(imageIndex[3]));
	}

	@FXML
	void onActionPreviousAvatar5(ActionEvent event) {
		decrementImageIndex(4);
		imgAvatar5.setImage(masterViewController.getResourceController().getAvatar(imageIndex[4]));
	}

	@FXML
	void onActionPreviousAvatar6(ActionEvent event) {
		decrementImageIndex(5);
		imgAvatar6.setImage(masterViewController.getResourceController().getAvatar(imageIndex[5]));
	}

	@FXML
	void onActionPreviousAvatar7(ActionEvent event) {
		decrementImageIndex(6);
		imgAvatar7.setImage(masterViewController.getResourceController().getAvatar(imageIndex[6]));
	}

	public PlayerData toPlayerData(int playerIndex) {
		PlayerData playerData = null;

		switch (playerIndex) {
		case 1:
			if (choiceHuman1.isSelected()) {
				playerData = new PlayerData();
			} else {
				playerData = new AIPlayerData();
				((AIPlayerData) playerData).setLevel(comboDifficult1.getSelectionModel().getSelectedIndex() + 1);
			}
			playerData.setName(txtName1.getText());
			playerData.setAvatar(imageIndex[0]);
			break;
		case 2:
			if (choiceHuman2.isSelected()) {
				playerData = new PlayerData();
			} else {
				playerData = new AIPlayerData();
				((AIPlayerData) playerData).setLevel(comboDifficult2.getSelectionModel().getSelectedIndex() + 1);
			}
			playerData.setName(txtName2.getText());
			playerData.setAvatar(imageIndex[1]);
			break;
		case 3:
			if (choiceHuman3.isSelected()) {
				playerData = new PlayerData();
			} else {
				playerData = new AIPlayerData();
				((AIPlayerData) playerData).setLevel(comboDifficult3.getSelectionModel().getSelectedIndex() + 1);
			}
			playerData.setName(txtName3.getText());
			playerData.setAvatar(imageIndex[2]);
			break;
		case 4:
			if (choiceHuman4.isSelected()) {
				playerData = new PlayerData();
			} else {
				playerData = new AIPlayerData();
				((AIPlayerData) playerData).setLevel(comboDifficult4.getSelectionModel().getSelectedIndex() + 1);
			}
			playerData.setName(txtName4.getText());
			playerData.setAvatar(imageIndex[3]);
			break;
		case 5:
			if (choiceHuman5.isSelected()) {
				playerData = new PlayerData();
			} else {
				playerData = new AIPlayerData();
				((AIPlayerData) playerData).setLevel(comboDifficult5.getSelectionModel().getSelectedIndex() + 1);
			}
			playerData.setName(txtName5.getText());
			playerData.setAvatar(imageIndex[4]);
			break;
		case 6:
			if (choiceHuman6.isSelected()) {
				playerData = new PlayerData();
			} else {
				playerData = new AIPlayerData();
				((AIPlayerData) playerData).setLevel(comboDifficult6.getSelectionModel().getSelectedIndex() + 1);
			}
			playerData.setName(txtName6.getText());
			playerData.setAvatar(imageIndex[5]);
			break;
		case 7:
			if (choiceHuman7.isSelected()) {
				playerData = new PlayerData();
			} else {
				playerData = new AIPlayerData();
				((AIPlayerData) playerData).setLevel(comboDifficult7.getSelectionModel().getSelectedIndex() + 1);
			}
			playerData.setName(txtName7.getText());
			playerData.setAvatar(imageIndex[6]);
			break;
		default:
			break;
		}
		return playerData;
	}

	private void incrementImageIndex(int playerIndex) {
		imageIndex[playerIndex]++;
		if (imageIndex[playerIndex] >= 10)
			imageIndex[playerIndex] = 0;
	}

	private void decrementImageIndex(int playerIndex) {
		imageIndex[playerIndex]--;
		if (imageIndex[playerIndex] < 0)
			imageIndex[playerIndex] = 9;
	}

	private void resetPlayer(int number) {
		switch (number) {
		case 1:
			imgAvatar1.setImage(masterViewController.getResourceController().getAvatar(0));
			txtName1.clear();
			choiceHuman1.setSelected(true);
			comboDifficult1.setDisable(true);
			break;
		case 2:
			imgAvatar2.setImage(masterViewController.getResourceController().getAvatar(1));
			txtName2.clear();
			choiceHuman2.setSelected(true);
			comboDifficult2.setDisable(true);
			break;
		case 3:
			imgAvatar3.setImage(masterViewController.getResourceController().getAvatar(2));
			txtName3.clear();
			choiceHuman3.setSelected(true);
			comboDifficult3.setDisable(true);
			break;
		case 4:
			imgAvatar4.setImage(masterViewController.getResourceController().getAvatar(3));
			txtName4.clear();
			choiceHuman4.setSelected(true);
			comboDifficult4.setDisable(true);
			break;
		case 5:
			imgAvatar5.setImage(masterViewController.getResourceController().getAvatar(4));
			txtName5.clear();
			choiceHuman5.setSelected(true);
			comboDifficult5.setDisable(true);
			break;
		case 6:
			imgAvatar6.setImage(masterViewController.getResourceController().getAvatar(5));
			txtName6.clear();
			choiceHuman6.setSelected(true);
			comboDifficult6.setDisable(true);
			break;
		case 7:
			imgAvatar7.setImage(masterViewController.getResourceController().getAvatar(6));
			txtName7.clear();
			choiceHuman7.setSelected(true);
			comboDifficult7.setDisable(true);
			break;
		default:
			break;
		}
	}

}