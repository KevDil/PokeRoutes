package model;

import java.io.Serializable;

public class PickBonusCardHistory  implements Serializable, History {
	/**
	 * 
	 */
	private static final long serialVersionUID = -228870723175234951L;

	private int bonusCardID;

	private int castleID;
	
	public PickBonusCardHistory(int bonusCardID, int castleID) {
		this.bonusCardID = bonusCardID;
		this.castleID = castleID;
	}
	
	/**
	 * @return the bonusCardID
	 */
	public int getBonusCardID() {
		return bonusCardID;
	}

	/**
	 * @return the castleID
	 */
	public int getCastleID() {
		return castleID;
	}

}
