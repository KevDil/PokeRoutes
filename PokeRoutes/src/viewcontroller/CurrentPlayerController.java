package viewcontroller;

import com.sun.xml.internal.ws.handler.HandlerChainsModel;

import controller.AUIMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.BonusCard;
import model.Card;
import model.CardForm;
import model.Castle;
import model.CastleCard;
import model.Player;
import model.Rotation;
import model.TurnStage;

public class CurrentPlayerController extends ViewController implements AUIMain {
	@FXML private Pane paneHandCard1;
	@FXML private Pane paneHandCard2;
	@FXML private Pane paneHandCard3;
    @FXML private ImageView imgViewBuildCard;
	@FXML private ImageView imgViewAvatar;
	@FXML private Label txtPlayerName;
	@FXML private Label txtPlayerPoints;
	@FXML private TabPane tabPaneCastles;
    @FXML private HBox hboxBonusCards;
    
    CastleCard currentCastleCard;
    Card tradeCard;
    
    int selectedHandIndex;

    private TurnStage getTurnStage() {
    	return gameController
    			.getTable()
    			.getCurrentTurn()
    			.getTurnStage();
    }
    

    @FXML
    void c1Click(MouseEvent event) {
    	if(event.getButton().equals(MouseButton.PRIMARY)) {
    		Player player = gameController.getActivePlayer();
    		Card[] handcards = player.getCards();
    		currentCastleCard = (CastleCard)handcards[0];
    		currentCastleCard.setRotation(currentCastleCard.getRotation().rotateRight());
    		ViewContext<CardViewController> cardView = masterViewController.createCardView(handcards[0], false, true);
			cardView.getController().setOnCardAction(x -> handleHandCardAction(x, 0));
//			paneHandCard1.getChildren().clear();
//			paneHandCard1.getChildren().add(cardView.getPane());
			
			CardForm cardForm = currentCastleCard.getCardForm();
			ImageView rotateImage = new ImageView();
			rotateImage.setImage(masterViewController
					.getResourceController()
					.getWayCard(cardForm));
			
			rotateImage.setRotate(currentCastleCard.getRotation().toDegrees());
			
			paneHandCard1.getChildren().clear();
			paneHandCard1.getChildren().add(rotateImage);
    	}
    }
    
    @FXML
    void onActionCancelBuild(ActionEvent event) {
    	if(getTurnStage() == TurnStage.BUILD_ONLY) {
    		Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Bauen abbrechen");
			alert.setHeaderText("Warnung");
			alert.setContentText("Die Karte muss verbaut!");
			alert.showAndWait();
			return;
    	}
    	
    	resetBuildCard();

    }

    @FXML
    void onActionRotateLeft(ActionEvent event) {
    	if(currentCastleCard == null)
    		return;
    	
    	currentCastleCard.setRotation(currentCastleCard.getRotation().rotateLeft());
    	updateBuildCard();
    }

    @FXML
    void onActionRotateRight(ActionEvent event) {
    	if(currentCastleCard == null)
    		return;
    	
    	currentCastleCard.setRotation(currentCastleCard.getRotation().rotateRight());
    	updateBuildCard();
    }

    @FXML
    void onActionAttack(ActionEvent event) {
    	if(getTurnStage() == TurnStage.ACTION)
    		masterViewController.loadCombatPlayerSelection();
    }

	
	private void resetBuildCard() {
		currentCastleCard = null;
		selectedHandIndex = -1;
		
		updateBuildCard();
	}
	
	private void resetTradeCard() {
		tradeCard = null;
		selectedHandIndex = -1;
	}
	
	
	public void handleClickPile(ClickPileEvent clickPileEvent) {
		TurnStage stage = getTurnStage();
		
		if(stage == TurnStage.REDRAW) {
			gameController.getPlayerController().drawCard(clickPileEvent.getDrawPile());
			gameController.getPlayerController().finishTurnStage();
		} else if(tradeCard != null) {
			gameController.getPlayerController().tradeCard(
					clickPileEvent.getDrawPile(), selectedHandIndex);
			
			gameController.getPlayerController().endTurn();
			resetTradeCard();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Karte tauschen");
			alert.setHeaderText("Warnung");
			alert.setContentText("Keine Karte zum tauschen ausgewählt!");

			alert.showAndWait();
			return;
		}
	}
	
	private void handleCastleAddCard(Castle castle, AddCardEvent addCardEvent) {
		if(currentCastleCard == null) {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Route (an)bauen");
    		alert.setHeaderText("Warnung");
    		alert.setContentText("Keine Routenkarte ausgewÃ¤hlt!");

    		alert.showAndWait();
			return;
		}
		
		CastleCard castleCard = currentCastleCard;
		try {
			gameController.getPlayerController().addToExistingCastle(addCardEvent.getPlayedCardHolder(),
					addCardEvent.getDirection(),
					castleCard,
					castleCard.getRotation());
			gameController.getPlayerController().removeHandCard(castleCard);
			resetBuildCard();
			gameController.getPlayerController().finishTurnStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
    void onActionCreateCaslte(ActionEvent event) {
    	if(currentCastleCard == null) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Route (an)bauen");
    		alert.setHeaderText("Warnung");
    		alert.setContentText("Keine Routenkarte ausgewÃ¤hlt!");

    		alert.showAndWait();
			return;
		}
    	
    	CastleCard castleCard = currentCastleCard;
    	gameController.getPlayerController().createNewCastle(castleCard, castleCard.getRotation());
    	gameController.getPlayerController().removeHandCard(castleCard);
    	resetBuildCard();
    	gameController.getPlayerController().finishTurnStage();

    }
	
	private void handleHandCardAction(CardActionEvent cardActionEvent, int i) {
		TurnStage stage = getTurnStage();
		if(currentCastleCard != null || stage != TurnStage.ACTION) {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Nicht möglich");
    		alert.setHeaderText("Warnung");
    		alert.setContentText("Ausgewählte Aktion nicht möglich!");

    		alert.showAndWait();
			return;
		}
		
		Card card = cardActionEvent.getCard();
		switch(cardActionEvent.getActionCode()) {
		case BUILD:
			resetTradeCard();
			currentCastleCard =  (CastleCard)card;
			currentCastleCard.setRotation(Rotation.NORTH);
			updateBuildCard();
			break;
		case TRADE:
			resetBuildCard();
			tradeCard = card;
			break;
		default:
			break;
		}
		
		selectedHandIndex = i;
	}
	
	@Override
	public void update() {
		Player player = gameController.getActivePlayer();
		
		if(getTurnStage() == TurnStage.BUILD_ONLY) {
			currentCastleCard = gameController
					.getTable()
					.getCurrentCombatContext()
					.getPlayedCardHolder()
					.getTopCard();
		} else {
			currentCastleCard = null;
		}
		updateBuildCard();

		// Open
		txtPlayerName.setText(gameController.getActivePlayer().getPlayerData().getName());
		txtPlayerPoints.setText(new Integer(gameController.getActivePlayer().getPoints()).toString());
		imgViewAvatar.setImage(masterViewController.getResourceController().getAvatar(
				gameController.getActivePlayer().getPlayerData().getAvatar()));
		
		Card[] handCards = player.getCards();
		addHandCard(paneHandCard1, handCards[0], 0);
		addHandCard(paneHandCard2, handCards[1], 1);
		addHandCard(paneHandCard3, handCards[2], 2);
		
		tabPaneCastles.getTabs().clear();
		
		int i = 1;
		for(Castle castle : player.getIncompleteCastle()) {
			Tab tab = new Tab();
			tab.setText("" + i++);
			
			ViewContext<CastleViewController> castleView =
					masterViewController.createCastleView(castle, true, false);
			castleView.getController().setOnAddCard(x -> handleCastleAddCard(castle, x));
			tab.setContent(castleView.getPane());
			tabPaneCastles.getTabs().add(tab);
		}
		
//		hboxBonusCards.getChildren().clear();
//		for(BonusCard bonusCard : player.getBonusCards()) {
//			hboxBonusCards.getChildren().add(
//					masterViewController.createCardView(bonusCard, true).getPane());
//		}

	}
	
	private void updateBuildCard() {
		if(currentCastleCard == null) {
			imgViewBuildCard.setImage(null);
			return;
		}
		
		CastleCard card = currentCastleCard;
		CardForm cardForm = card.getCardForm();
		imgViewBuildCard.setImage(masterViewController
				.getResourceController()
				.getWayCard(cardForm));
		
		imgViewBuildCard.setRotate(card.getRotation().toDegrees());
	}
	
	private void addHandCard(Pane cardPane, Card card, int i) {
		cardPane.getChildren().clear();
		if (card != null) {
			ViewContext<CardViewController> cardView = masterViewController.createCardView(card, false, true);
			cardView.getController().setOnCardAction(x -> handleHandCardAction(x, i));
			cardPane.getChildren().add(cardView.getPane());
			
		}
	}

}
