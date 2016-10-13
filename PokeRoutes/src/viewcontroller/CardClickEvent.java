package viewcontroller;

import javafx.event.Event;
import javafx.event.EventType;
import model.PlayedCardHolder;

public class CardClickEvent extends Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2860360310142139932L;

	static final EventType<CardClickEvent> CARD_CLICK = new EventType<>("CARD_CLICK");
	
	private final PlayedCardHolder playedCardHolder;
	
	public CardClickEvent(PlayedCardHolder playedCardHolder)
	{
		super(CARD_CLICK);
		
		this.playedCardHolder = playedCardHolder;
	}

	public PlayedCardHolder getPlayedCardHolder() {
		return playedCardHolder;
	}
}
