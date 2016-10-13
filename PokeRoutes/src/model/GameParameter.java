package model;

import java.io.Serializable;
import java.util.ArrayList;

public class GameParameter  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1091390406968002988L;

	private boolean shortGame;

	private long seed;

	private ArrayList<PlayerData> playerList;
	
	public GameParameter() {
		this.playerList = new ArrayList<PlayerData>();
	}
	
	public GameParameter(boolean shortGame, long seed, ArrayList<PlayerData> playerList){
		this.shortGame = shortGame;
		this.seed = seed;
		this.playerList = playerList;
	}
	
	/**
	 * @return the shortGame
	 */
	public boolean isShortGame() {
		return shortGame;
	}

	/**
	 * @param shortGame the shortGame to set
	 */
	public void setShortGame(boolean shortGame) {
		this.shortGame = shortGame;
	}

	/**
	 * @return the seed
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * @param seed the seed to set
	 */
	public void setSeed(long seed) {
		this.seed = seed;
	}

	/**
	 * @return the playerList
	 */
	public ArrayList<PlayerData> getPlayerList() {
		return playerList;
	}

}
