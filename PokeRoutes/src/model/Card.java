package model;

import java.io.Serializable;

public abstract class Card implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -730124978954447617L;
	
	private int id;

	/**
	 *  
	 */
	public Card(int id) {
		this.id = id;
	}
	
	public boolean isCombatCard() {
		return this instanceof CombatCard;
	}
	
	public boolean isCastleCard() {
		return this instanceof CastleCard;
	}
	
	public boolean isBigWave() {
		return this instanceof BigWave;
	}

	/**
	 * @return the id
	 */
	public int getID() {
		return id;
	}

}
