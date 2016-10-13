package model;

import java.io.Serializable;

public class NewBuildHistory  implements Serializable, History{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2501846288766992626L;

	private int cardID;

	private Rotation rotation;
	
	public NewBuildHistory(int cardID, Rotation rotation) {
		this.cardID = cardID;
		this.rotation = rotation;
	}
	

	public int getCardID() {
		return cardID;
	}

	public Rotation getRotation() {
		return rotation;
	}

}
