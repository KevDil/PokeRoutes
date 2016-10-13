package model;

import java.io.Serializable;

public class SizeBonusCard extends BonusCard  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8999171163431903831L;
	private int currentBiggest;
	
	public SizeBonusCard(int id) {
		super(id);
	}

	/**
	 * @return the currentBiggest
	 */
	public int getCurrentBiggest() {
		return currentBiggest;
	}

	/**
	 * @param currentBiggest the currentBiggest to set
	 */
	public void setCurrentBiggest(int currentBiggest) {
		this.currentBiggest = currentBiggest;
	}

}
