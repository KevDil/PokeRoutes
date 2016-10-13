package viewcontroller;

import java.util.List;
import java.util.Optional;
import controller.AUIMain;

import controller.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import model.Player;

public class MainscreenController extends ViewController implements AUIMain {

	@FXML
	private DeckController includeDeckController;
	@FXML
	private CurrentPlayerController includeCurrentPlayerController;
	@FXML
	private InactivePlayersController includeInactivePlayer1Controller;
	@FXML
	private InactivePlayersController includeInactivePlayer2Controller;
	@FXML
	private InactivePlayersController includeInactivePlayer3Controller;
	@FXML
	private InactivePlayersController includeInactivePlayer4Controller;
	@FXML
	private InactivePlayersController includeInactivePlayer5Controller;
	@FXML
	private InactivePlayersController includeInactivePlayer6Controller;
	@FXML
	private InactivePlayersController includeInactivePlayer7Controller;

	@FXML
	private Parent includeDeck;
	@FXML
	private Parent includeCurrent;
	@FXML
	private Parent includeInactivePlayer1;
	@FXML
	private Parent includeInactivePlayer2;
	@FXML
	private Parent includeInactivePlayer3;
	@FXML
	private Parent includeInactivePlayer4;
	@FXML
	private Parent includeInactivePlayer5;
	@FXML
	private Parent includeInactivePlayer6;
	@FXML
	private Parent includeInactivePlayer7;

	@FXML
	private MenuBar menuBar;
	@FXML
	private Menu menuGame;
	@FXML
	private MenuItem gameLoad;
	@FXML
	private MenuItem gameSave;
	@FXML
	private MenuItem gameMenu;
	@FXML
	private MenuItem gameEnd;
	@FXML
	private Menu menuExtras;
	@FXML
	private MenuItem extraScreenshot;
	@FXML
	private Menu menuHelp;
	@FXML
	private Accordion accInactivePlayers;
	@FXML
	private MenuItem helpBack;
	@FXML
	private MenuItem helpShowTip;
	@FXML
	private MenuItem helpShowInstructions;
	@FXML
	private TitledPane titledPanePlayer1;
	@FXML
	private TitledPane titledPanePlayer2;
	@FXML
	private TitledPane titledPanePlayer3;
	@FXML
	private TitledPane titledPanePlayer4;
	@FXML
	private TitledPane titledPanePlayer5;
	@FXML
	private TitledPane titledPanePlayer6;
	@FXML
	private TitledPane titledPanePlayer7;
	@FXML
	private BorderPane main;

	
	@FXML
	void loadOnAction(ActionEvent event) {
		try {
			masterViewController.load();
			masterViewController.loadMainscreen();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("Das Spiel konnte nicht geladen werden!");
		}
	}

	@FXML
	void saveOnAction(ActionEvent event) {
		try {
			masterViewController.save();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("Das Spiel konnte nicht gespeichert werden!");
		}
	}
	
	@FXML
	void menuOnAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setResizable(true);
		alert.getDialogPane().setPrefSize(480, 170);
		alert.setTitle("WARNUNG!");
		alert.setHeaderText("Wollen Sie wirklich in das Hauptmenü zurückkehren?");
		alert.setContentText("WARNUNG! Alle ungespeicherten Fortschritte können verloren gehen!");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			masterViewController.loadMenu();
		} else {
		    //Window is closed automatically
		}
	}
	
	@FXML
	void endOnAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setResizable(true);
		alert.getDialogPane().setPrefSize(480, 170);
		alert.setTitle("WARNUNG!");
		alert.setHeaderText("Wollen Sie wirklich das Spiel beenden?");
		alert.setContentText("WARNUNG! Alle ungespeicherten Fortschritte können verloren gehen!");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			masterViewController.closeStage();
		} else {
		    //Window is closed automatically
		}
	}
	
	@FXML
	void screenshotOnAction(ActionEvent event) {
		masterViewController.print(main);
	}
	
	
	
	@FXML
	void backOnAction(ActionEvent event) {
		Alert back = new Alert(AlertType.CONFIRMATION);
		back.setResizable(true);
		back.getDialogPane().setPrefSize(480, 170);
		back.setTitle("Schritt zurück gehen");
		back.setHeaderText("Wollen Sie wirklich einen Schritt zurück gehen?");
		back.setContentText("ACHTUNG! Wenn ja, werden Sie nicht mehr in die Highscore-Liste eingetragen!");

		Optional<ButtonType> result = back.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				masterViewController.undoTurn();
				masterViewController.loadMainscreen();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				alert.setContentText("Zug konnte nicht rückgängig gemacht werden!");
			}
		} else {
			//Window is closed automatically
		}
		
	}
	
	@FXML
	void tipOnAction(ActionEvent event) {
		Alert qTipp = new Alert(AlertType.CONFIRMATION);
		qTipp.setResizable(true);
		qTipp.getDialogPane().setPrefSize(480, 170);
		qTipp.setTitle("Tipp anzeigen");
		qTipp.setHeaderText("Wollen Sie sich wirklich einen Tipp anzeigen lassen?");
		qTipp.setContentText("ACHTUNG! Wenn ja, werden Sie nicht mehr in die Highscore-Liste eingetragen!");

		Optional<ButtonType> result = qTipp.showAndWait();
		if (result.get() == ButtonType.OK){
			Alert sTipp = new Alert(AlertType.INFORMATION);
			sTipp.setTitle("Tipp anzeigen");
			sTipp.setHeaderText("Der Tipp für den aktuellen Zug lautet:");
			sTipp.setContentText("Nicht aufgeben!");

			sTipp.showAndWait();
		} else {
			//Window is closed automatically
		}
	}
	
	@FXML
	void instructionsOnAction(ActionEvent event) {
		String url = "https://sopra.cs.tu-dortmund.de/wiki/sopra/16b/projekt2/start";
		masterViewController.openLink(url);
	}
	
	

	@FXML
	void initialize() {
		includeDeckController.setOnClickPile(x -> clickPileOnAction(x));
		accInactivePlayers.setExpandedPane(titledPanePlayer1);
		
		includeDeckController.setOnClickPile(x -> includeCurrentPlayerController.handleClickPile(x));
	}

	void clickPileOnAction(ClickPileEvent clickPileEvent) {

	}

	public void update() {
		includeDeckController.update();
		includeCurrentPlayerController.update();
		
		

		List<Player> players = gameController.getTable().getPlayers();
		for(int i = 0; i < 7; i++) {
			if(i < players.size()) {
				Player player = players.get(i);
				
				String title = "";
				if(player == gameController.getTable().getCurrentPlayer()) {
					title = String.format("Spieler %d <<AM ZUG>>", i+1);
				} else {
					title = String.format("Spieler %d", i+1);
				}
				
				updatePlayerPane(title, i+1, player);
			} else {
				updatePlayerPane("", i+1, null);
			}
		}
	}
	
	private void updatePlayerPane(String title, int n, Player player) {
		TitledPane pane = null;
		InactivePlayersController inactivePlayerController;
		
		switch(n) {
		case 1:
			pane = titledPanePlayer1;
			inactivePlayerController = includeInactivePlayer1Controller;
			break;
		case 2:
			pane = titledPanePlayer2;
			inactivePlayerController = includeInactivePlayer2Controller;
			break;
		case 3:
			pane = titledPanePlayer3;
			inactivePlayerController = includeInactivePlayer3Controller;
			break;
		case 4:
			pane = titledPanePlayer4;
			inactivePlayerController = includeInactivePlayer4Controller;
			break;
		case 5:
			pane = titledPanePlayer5;
			inactivePlayerController = includeInactivePlayer5Controller;
			break;
		case 6:
			pane = titledPanePlayer6;
			inactivePlayerController = includeInactivePlayer6Controller;
			break;
		case 7:
			pane = titledPanePlayer7;
			inactivePlayerController = includeInactivePlayer7Controller;
			break;
		default:
			throw new RuntimeException("Invalid player number");
		}
		

		pane.setVisible(player != null);
		pane.setText(title);
		if(player != null)
			inactivePlayerController.update(player);
	}

	@Override
	public void setController(MasterViewController masterViewController, GameController gameController) {
		super.setController(masterViewController, gameController);

		includeDeckController.setController(masterViewController, gameController);
		includeInactivePlayer1Controller.setController(masterViewController, gameController);
		includeInactivePlayer2Controller.setController(masterViewController, gameController);
		includeInactivePlayer3Controller.setController(masterViewController, gameController);
		includeInactivePlayer4Controller.setController(masterViewController, gameController);
		includeInactivePlayer5Controller.setController(masterViewController, gameController);
		includeInactivePlayer6Controller.setController(masterViewController, gameController);
		includeInactivePlayer7Controller.setController(masterViewController, gameController);
		includeCurrentPlayerController.setController(masterViewController, gameController);
	}
}
