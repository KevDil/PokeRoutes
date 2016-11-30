package viewcontroller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.BonusCard;
import model.Card;
import model.CastleCard;
import model.CombatCard;
import model.CountBonusCard;
import model.FormBonusCard;
import viewcontroller.CardActionEvent.ActionCode;

public class CardViewController extends ViewController {

	private Card card;
	private BonusCard bonusCard;
	private boolean showAlways;
	private boolean hasCtxMenu;
	private ContextMenu contextMenu;
	private MenuItem menuItemTrade;
	private MenuItem menuItemBuild;
	private EventHandler<CardActionEvent> onCardAction;

	@FXML private ImageView imgViewBackground;
	@FXML private ImageView imgViewAnimation;
	
	@FXML
	void onMouseClick(MouseEvent event) {
		if(event.getButton().equals(MouseButton.PRIMARY)) {
			if (!showAlways)
				showCard();
		}
	}
	
	@FXML 
	void OnMouseExit(MouseEvent event) {
		if(!showAlways)
			showCardBackground();
	}
	
	@FXML
    void onContextMenuRequest(ContextMenuEvent event) {
		if(this.hasCtxMenu)
			contextMenu.show((Node) event.getSource(), event.getScreenX(), event.getScreenY());
    }

	@FXML
	void onMouseEnter(MouseEvent event) {
		if (!showAlways)
			showCard();
	}
	
	private void fireActionEvent(ActionCode actionCode) {
		if(onCardAction != null) {
			CardActionEvent cardActionEvent =
					new CardActionEvent(this.card, actionCode);
			onCardAction.handle(cardActionEvent);		
		}
	}
	
	public void loadCard(Card card, boolean showAlways, boolean hasCtxMenu) {
		this.card = card;
		this.showAlways = showAlways;
		this.hasCtxMenu = hasCtxMenu;
		
		if(showAlways)
			this.showCard();
		else
			this.showCardBackground();
		
		if(hasCtxMenu && !card.isBigWave()) {
			contextMenu = new ContextMenu();
			
			menuItemTrade = new MenuItem("Tauschen");
			menuItemTrade.setOnAction(x -> fireActionEvent(ActionCode.TRADE));
			contextMenu.getItems().add(menuItemTrade);
			
			if(card.isCastleCard()) {
				menuItemBuild = new MenuItem("Bauen");
				menuItemBuild.setOnAction(x -> fireActionEvent(ActionCode.BUILD));
				contextMenu.getItems().add(menuItemBuild);
			}
		}
	}
	
	public void loadBonusCard(BonusCard bonusCard, boolean showAlways) {
		this.bonusCard = bonusCard;
		this.showAlways = showAlways;
		
		if(showAlways)
			this.showCard();
		else
			this.showCardBackground();
	}

	private void showCard() {
		ResourceController resourceController = masterViewController.getResourceController();

		Image background = null, anim = null;
		Integer degrees = 0;
		
		if (card != null) {
			if (card.isBigWave()) {
				background = resourceController.getBigWave();
			} else if (card.isCombatCard()) {
				CombatCard combatCard = (CombatCard) card;
				background = resourceController.getCombatCard(combatCard.getType());
			} else { // Castle Card
				CastleCard castleCard = (CastleCard) card;
				degrees = castleCard.getRotation().toDegrees();
				background = resourceController.getWayCard(castleCard.getCardForm());

				FrameSet frameSet = resourceController.getCardTypeFrame(castleCard.getType());
				anim = frameSet.getImages()[0];
			}
		} else { // Bonus card
			if (bonusCard.isCountBonusCard()) {
				CountBonusCard countBonusCard = (CountBonusCard) bonusCard;
				background = resourceController.getCountBonusCard(countBonusCard.getType(),
						countBonusCard.getRequired());
			} else if (bonusCard.isFormBonusCard()) {
				FormBonusCard formBonusCard = (FormBonusCard) bonusCard;
				background = resourceController.getFormBonusCard(formBonusCard.getForm());
			} else if (bonusCard.isHeightBonusCard()) {
				background = resourceController.getBonusTowerHeight();
			} else { // Size bonus card
				background = resourceController.getBonusCastleSize();
			}
		}

		imgViewAnimation.setImage(anim);
		imgViewBackground.setImage(background);
		imgViewBackground.setRotate(degrees);
	}

	private void showCardBackground() {
		imgViewAnimation.setImage(null);
		imgViewBackground.setImage(masterViewController.getResourceController().getBack());
		imgViewBackground.setRotate(0);
	}

	/**
	 * @return the onCardAction
	 */
	public EventHandler<CardActionEvent> getOnCardAction() {
		return onCardAction;
	}

	/**
	 * @param onCardAction the onCardAction to set
	 */
	public void setOnCardAction(EventHandler<CardActionEvent> onCardAction) {
		this.onCardAction = onCardAction;
	}
	
	public void rotateBackgroundRight(Integer degrees) {
		imgViewBackground.setRotate(degrees);
	}
	
	public void update() {
		showCard();
	}

}
