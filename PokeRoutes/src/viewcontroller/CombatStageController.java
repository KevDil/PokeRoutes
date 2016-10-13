package viewcontroller;

import java.util.List;

import controller.AUIBattleArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.CombatCard;
import model.CombatContext;
import model.Player;

public class CombatStageController extends ViewController implements AUIBattleArea {

	@FXML
	private ImageView imgViewAttacker;

	@FXML
	private Pane paneAttackedCard;

	@FXML
	private ImageView imgViewDefender;

	@FXML
	private Label labelAttacker;

	@FXML
	private Label labelDefender;

	@FXML
	private Pane paneCombatCard1;

	@FXML
	private Pane paneCombatCard2;

	@FXML
	private Pane paneCombatCard3;

	@FXML
	private Pane paneCombatCard4;

	@FXML
	private Pane paneCombatCard5;

	@FXML
	private Pane paneCombatCard6;

	@FXML
	void onActionCounter(ActionEvent event) {
		try {
			if (!gameController.getPlayerController().reattackOrDefend()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Keine Karte zum Kontern");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText(e.toString());
			alert.showAndWait();
		}
	}

	@FXML
	void onActionSurrender(ActionEvent event) {
		gameController.getPlayerController().forfeit();

		masterViewController.loadMainscreen();
	}

	public void update() {
		CombatContext combatContext = gameController.getTable().getCurrentCombatContext();
		Player attacker = combatContext.getAttacker();
		Player defender = combatContext.getDefender();
		ResourceController rc = masterViewController.getResourceController();

		labelAttacker.setText(attacker.getPlayerData().getName());
		labelAttacker.setUnderline(attacker == combatContext.getCurrentPlayer());

		labelDefender.setText(defender.getPlayerData().getName());
		labelDefender.setUnderline(defender == combatContext.getCurrentPlayer());

		imgViewAttacker.setImage(rc.getAvatar(attacker.getPlayerData().getAvatar()));
		imgViewDefender.setImage(rc.getAvatar(defender.getPlayerData().getAvatar()));

		ViewContext<CardViewController> cardView = masterViewController
				.createCardView(combatContext.getPlayedCardHolder().getTopCard(), true, false);
		paneAttackedCard.getChildren().clear();
		paneAttackedCard.getChildren().add(cardView.getPane());

		List<CombatCard> combatCards = combatContext.getCombatCards();
		for (int i = 0; i < combatCards.size(); i++)
			setCombatCardPane(i + 1, combatCards.get(i));

		for (int i = combatCards.size(); i < 6; i++)
			setCombatCardPane(i+1, null);
	}

	private void setCombatCardPane(int n, CombatCard combatCard) {
		Pane pane = null;
		switch (n) {
		case 1:
			pane = paneCombatCard1;
			break;
		case 2:
			pane = paneCombatCard2;
			break;
		case 3:
			pane = paneCombatCard3;
			break;
		case 4:
			pane = paneCombatCard4;
			break;
		case 5:
			pane = paneCombatCard5;
			break;
		case 6:
			pane = paneCombatCard6;
			break;
		}

		pane.getChildren().clear();
		if (combatCard != null) {
			ViewContext<CardViewController> cardView = masterViewController.createCardView(combatCard, true, false);
			pane.getChildren().add(cardView.getPane());
		}
	}

}
