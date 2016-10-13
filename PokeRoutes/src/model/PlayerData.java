package model;

import java.io.Serializable;

public class PlayerData  implements Serializable, Comparable<PlayerData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7606141386464491054L;

	private String name;

	private int score;

	private int avatar;

	private boolean networkPlayer;
	
	public PlayerData() {
		
	}
	
	public PlayerData(String name, int score, int avatar, boolean networkPlayer) {
		this.name = name;
		this.score = score;
		this.avatar = avatar;
		this.networkPlayer = networkPlayer;
	}

	/**
	 *  checks if the player is an AI player
	 */
	public boolean isAI() {
		return (this instanceof AIPlayerData);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the avatar
	 */
	public int getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	/**
	 * @return the networkPlayer
	 */
	public boolean isNetworkPlayer() {
		return networkPlayer;
	}

	/**
	 * @param networkPlayer the networkPlayer to set
	 */
	public void setNetworkPlayer(boolean networkPlayer) {
		this.networkPlayer = networkPlayer;
	}

	

	
	public int compareTo(PlayerData o) {
		if(score == o.getScore())return 0;
		if(score > o.getScore())return -1;
		return 1;
	}

	
	public int compare(PlayerData o1, PlayerData o2) {
		return o1.getScore()-o2.getScore();
	}
	
}
