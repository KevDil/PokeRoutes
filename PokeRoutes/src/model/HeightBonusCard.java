package model;

import java.io.Serializable;

public class HeightBonusCard extends BonusCard  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1419369167412090559L;
	private int currentHighest;
	
	public HeightBonusCard(int id) {
		super(id);
	}

	public int getCurrentHighest() {
		return currentHighest;
	}

	public void setCurrentHighest(int currentHighest) {
		this.currentHighest = currentHighest;
	}

}
