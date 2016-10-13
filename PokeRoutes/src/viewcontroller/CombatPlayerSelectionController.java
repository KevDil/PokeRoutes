package viewcontroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import model.Castle;
import model.Player;
import model.Table;

public class CombatPlayerSelectionController extends ViewController {

	@FXML
	private TabPane tabCastles;

	@FXML
	private ChoiceBox<PlayerModel> choiceBoxPlayerSelection;

	private ObservableList<PlayerModel> playerModels;

	@FXML
	void onActionStopCombat(ActionEvent event) {
		masterViewController.loadMainscreen();
	}

	public void load() {
		Table table = gameController.getTable();

		playerModels.clear();
		for (Player player : table.getPlayers()) {
			if (player == table.getCurrentPlayer())
				continue;
			playerModels.add(new PlayerModel(player));
		}
	}

	@FXML
	void onActionAttackPlayer(ActionEvent event) {
		PlayerModel playerModel = choiceBoxPlayerSelection.getSelectionModel().getSelectedItem();
		if (playerModel != null) {
			loadCastles(playerModel.getPlayer());
		}
	}

	@FXML
	void initialize() {
		playerModels = FXCollections.observableArrayList();
		choiceBoxPlayerSelection.setItems(playerModels);
	}

	private void loadCastles(Player player) {
		tabCastles.getTabs().clear();

		int i = 1;
		for (Castle castle : player.getCastles()) {
			Tab tab = new Tab();
			tab.setText("" + i++);

			ViewContext<CastleViewController> castleView = masterViewController.createCastleView(castle, false, false);
			castleView.getController().setOnClickCard(x -> handleClickCard(castle, x));
			tab.setContent(castleView.getPane());
			tabCastles.getTabs().add(tab);
		}
	}

	private void handleClickCard(Castle castle, CardClickEvent x) {
		if(!gameController.getCastleController().canRemove(x.getPlayedCardHolder())) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Karte nicht angreifbar");
			alert.showAndWait();
		}
			
		
		if (gameController.getPlayerController().attack(x.getPlayedCardHolder())) {
			masterViewController.loadCombatStage();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Keine Karte zum angreifen");
			alert.showAndWait();
		}

	}

	class PlayerModel {
		private final Player player;

		public PlayerModel(Player player) {
			this.player = player;
		}

		@Override
		public String toString() {
			return player.getPlayerData().getName();
		}

		public Player getPlayer() {
			return player;
		}
	}
}
