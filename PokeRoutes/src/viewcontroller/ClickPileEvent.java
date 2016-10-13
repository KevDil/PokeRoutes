package viewcontroller;

import javafx.event.Event;
import javafx.event.EventType;
import model.DrawPile;

public class ClickPileEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final EventType<ClickPileEvent> CLICK_PILE = new EventType<>("CLICK_PILE");
	
	private final DrawPile drawPile;
	
	public ClickPileEvent(DrawPile drawPile)
	{
		super(CLICK_PILE);
		
		this.drawPile = drawPile;
	}

	public DrawPile getDrawPile() {
		return drawPile;
	}
}
