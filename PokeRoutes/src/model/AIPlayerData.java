package model;

import java.io.Serializable;

public class AIPlayerData extends PlayerData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5421311372284416218L;
	
	private int level;

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

}
