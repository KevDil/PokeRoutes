package viewcontroller;

import javafx.event.Event;
import javafx.event.EventType;
import model.Card;

public class CardActionEvent extends Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2860360310142139932L;

	static final EventType<CardActionEvent> CARD_ACTION = new EventType<>("CARD_ACTION");
	
	private final Card card;
	private final ActionCode actionCode;
	
	public CardActionEvent(Card card, ActionCode actionCode)
	{
		super(CARD_ACTION);
		
		this.card = card;
		this.actionCode = actionCode;
	}
	
	enum ActionCode {
		ATTACK,
		BUILD,
		TRADE
	}

	/**
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * @return the actionCode
	 */
	public ActionCode getActionCode() {
		return actionCode;
	}
}
