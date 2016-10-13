package viewcontroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.CardForm;
import model.CastleCard;
import model.Direction;
import model.PlayedCardHolder;
import model.Rotation;

public class CastleCardViewController extends ViewController {
	
	
	
	private PlayedCardHolder playedCardHolder;
	private boolean allowAdd;
	private Animation animation;
	private EventHandler<AddCardEvent> onAddCard;
	private EventHandler<CardClickEvent> onClickCard;

    @FXML
    private ImageView imgViewBackground;
    
    @FXML
    private ImageView imgViewAnimation;

    @FXML
    private Button btnNorth;

    @FXML
    private Button btnEast;

    @FXML
    private Button btnSouth;

    @FXML
    private Button btnWest;

    @FXML
    private Button btnUpperCard;

    @FXML
    private Button btnStackCount;

    @FXML
    void onAddCard(ActionEvent event) {
    	Object source = event.getSource();
    	
    	Direction dir = Direction.TOP;
    	
    	if(source == btnNorth) {
    		dir = Direction.TOP;
    	} else if(source == btnEast) {
    		dir = Direction.RIGHT;
    	} else if(source == btnSouth) {
    		dir = Direction.BOTTOM;
    	} else if(source == btnWest) {
    		dir = Direction.LEFT;
    	} else if(source == btnUpperCard) {
    		dir = Direction.UPPER;
    	}
    	
    	if(onAddCard != null) {
    		onAddCard.handle(new AddCardEvent(this.playedCardHolder, dir));
    	}
    }

    @FXML
    void onShowStackCard(ActionEvent event) {
    	//TODO: fire event
    }
    
    @FXML
    void onAnimationClicked(MouseEvent event) {
    	if(onClickCard != null)
    		onClickCard.handle(new CardClickEvent(this.playedCardHolder));
    }
    
    public void loadPlayedCardHolder(PlayedCardHolder playedCardHolder, boolean allowAdd) {
    	this.allowAdd = allowAdd;
    	this.playedCardHolder = playedCardHolder;
    	
    	ResourceController rc = masterViewController.getResourceController();
    	CastleCard castleCard = playedCardHolder.getTopCard();
    	
    	FrameSet frameSet = rc.getCardTypeFrame(castleCard.getType());
    	Image img = rc.getWayCard(castleCard.getCardForm());
    	
    	this.animation = new Animation(frameSet.getImages(),
    			frameSet.getFramesPerMs() * 250,
    			imgViewAnimation);
    	this.animation.setCycleCount(-1);
    	this.animation.play();
    	
    	this.imgViewAnimation.setFitHeight(128);
    	this.imgViewAnimation.setFitWidth(128);
    	this.imgViewBackground.setImage(img);
    	this.imgViewBackground.setRotate((double)castleCard.getRotation().toDegrees());
    	
    	btnStackCount.setText("#" + playedCardHolder.getHeight());
    	
    	fillEdges();
    }
    
    public void fillEdges() {
    	CastleCard castleCard = this.playedCardHolder.getTopCard();
    	
    	CardForm cf = castleCard.getCardForm();
    	Rotation rot = castleCard.getRotation();
    	PlayedCardHolder pch = this.playedCardHolder;
    	
    	btnNorth.setVisible(cf.hasTopConnection(rot) && pch.getNorthCard() == null && this.allowAdd);
    	btnSouth.setVisible(cf.hasBottomConnection(rot) && pch.getSouthCard() == null && this.allowAdd);
    	btnWest.setVisible(cf.hasLeftConnection(rot) && pch.getWestCard() == null && this.allowAdd);
    	btnEast.setVisible(cf.hasRightConnection(rot) && pch.getEastCard() == null && this.allowAdd);
    	btnUpperCard.setVisible(this.allowAdd);
    }

	public EventHandler<AddCardEvent> getOnAddCard() {
		return onAddCard;
	}

	public void setOnAddCard(EventHandler<AddCardEvent> onAddCard) {
		this.onAddCard = onAddCard;
	}

	/**
	 * @return the onClickCard
	 */
	public EventHandler<CardClickEvent> getOnClickCard() {
		return onClickCard;
	}

	/**
	 * @param onClickCard the onClickCard to set
	 */
	public void setOnClickCard(EventHandler<CardClickEvent> onClickCard) {
		this.onClickCard = onClickCard;
	}

}
