package viewcontroller;

import javafx.event.Event;
import javafx.event.EventType;
import model.Direction;
import model.PlayedCardHolder;

public class AddCardEvent extends Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2860360310142139932L;

	static final EventType<AddCardEvent> ADD_CARD = new EventType<>("ADD_CARD");
	
	private final PlayedCardHolder playedCardHolder;
	private final Direction direction;
	
	public AddCardEvent(PlayedCardHolder playedCardHolder, Direction dir)
	{
		super(ADD_CARD);
		
		this.playedCardHolder = playedCardHolder;
		this.direction = dir;
	}

	public PlayedCardHolder getPlayedCardHolder() {
		return playedCardHolder;
	}

	public Direction getDirection() {
		return direction;
	}
}
