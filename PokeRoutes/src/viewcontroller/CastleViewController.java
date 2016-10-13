package viewcontroller;

import java.util.Collection;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Castle;
import model.PlayedCardHolder;
import model.Position;

public class CastleViewController extends ViewController {
	private Castle castle;
	private boolean allowAdd;
	private boolean small;
	private EventHandler<AddCardEvent> onAddCard;
	private EventHandler<CardClickEvent> onClickCard;


	@FXML
	private GridPane gridPaneCards;

	public void loadCastle(Castle castle, boolean allowAdd, boolean small) {
		this.castle = castle;
		this.allowAdd = allowAdd;
		this.small = small;

		fill();

		gridPaneCards.autosize();
	}

	private void fillGrid(Position pos, PlayedCardHolder pch) {
		if (!small) {
			ViewContext<CastleCardViewController> cardView = masterViewController.createCastleCardView(pch,
					this.allowAdd);
			gridPaneCards.add(cardView.getPane(), pos.getX(), pos.getY());

			cardView.getController().setOnAddCard((event) -> {
				if (onAddCard != null)
					onAddCard.handle(event);
			});
			
			cardView.getController().setOnClickCard((event) -> {
				if(onClickCard != null)
					onClickCard.handle(event);
			});
		} else {
			ImageView imgViewCard = new ImageView();
			imgViewCard.setFitHeight(32);
			imgViewCard.setFitWidth(32);
			imgViewCard.setImage(masterViewController.getResourceController().getWayCard(pch.getTopCard().getCardForm()));
			
			gridPaneCards.add(imgViewCard, pos.getX(), pos.getY());
		}
	}

	private void fill() {
		Collection<PlayedCardHolder> cards = castle.getPlayedCards().values();
		int maxX = 0, minX = 0, maxY = 0, minY = 0;
		for (PlayedCardHolder current : cards) {
			Position pos = current.getPosition();
			maxX = Math.max(pos.getX(), maxX);
			minX = Math.min(pos.getX(), minX);
			maxY = Math.max(pos.getY(), maxY);
			minY = Math.min(pos.getY(), minY);
		}
		for (PlayedCardHolder current : cards) {
			fillGrid(current.getPosition().add(-minX, -minY), current);
		}
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
