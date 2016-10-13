package model;

import java.io.Serializable;

public class DefendHistory implements History, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5855394991012202900L;
	private boolean defend;
	
	public DefendHistory(boolean def) {
		this.defend = def;
	}

	public boolean isDefend() {
		return defend;
	}

}
