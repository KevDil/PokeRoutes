package model;

import java.io.Serializable;

public class AttackHistory implements History, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4084701796601753168L;
	
	private int cardID;

	public AttackHistory(int cardId) {
		this.cardID = cardId;
	}
	
	/**
	 * @return the cardID
	 */
	public int getCardID() {
		return cardID;
	}

}
