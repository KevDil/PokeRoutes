package model;

import java.io.Serializable;

public class TradeHistory implements Serializable, History {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7254056623773517927L;

	private DrawPile pile;

	private int cardIndex;
	
	public TradeHistory(DrawPile pile, int cardIndex) {
		this.pile = pile;
		this.cardIndex = cardIndex;
	}
	
	/**
	 * @return the pile
	 */
	public DrawPile getPile() {
		return pile;
	}

	/**
	 * @return the cardIndex
	 */
	public int getCardIndex() {
		return cardIndex;
	}
}
