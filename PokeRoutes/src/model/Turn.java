package model;

import java.io.Serializable;

public class Turn   implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7177973232705289821L;
	private TurnStage turnStage;
	private final Player player;
	
	public Turn(Player player, TurnStage initTurnStage) {
		this.player = player;
		this.turnStage = initTurnStage;
	}

	/**
	 * @return the turnStage
	 */
	public TurnStage getTurnStage() {
		return turnStage;
	}

	/**
	 * @param turnStage the turnStage to set
	 */
	public void setTurnStage(TurnStage turnStage) {
		this.turnStage = turnStage;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
}
