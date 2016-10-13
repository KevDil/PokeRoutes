package model;

import java.io.Serializable;

public abstract class BonusCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5928689414692837423L;

	private int value;

	private int id;

	private Player player;
	
	
	public BonusCard(int id) {
		this.id = id;
	}
	
	public boolean isHeightBonusCard() {
		return (this instanceof HeightBonusCard);
	}
	public boolean isSizeBonusCard() {
		return (this instanceof SizeBonusCard);
	}
	public boolean isCountBonusCard() {
		return (this instanceof CountBonusCard);
	}
	public boolean isFormBonusCard() {
		return (this instanceof FormBonusCard);
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the bonusCardID
	 */
	public int getID() {
		return id;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BonusCard [value=" + value + ", id=" + id + ", player=" + player + "]";
	}

}
