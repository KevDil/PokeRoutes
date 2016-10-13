package model;

import java.io.Serializable;

public class AddNewCardHistory implements History, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 342683105298164824L;

	//private int targetCardId;
	
	private Position position;

	private Direction direction;

	private int newCardID;

	private Rotation rotation;
	
	public AddNewCardHistory(Position position, CastleCard card, Direction direction, Rotation rot) {
		this.position = position;
		this.newCardID = card.getID();
		this.direction = direction;
		this.rotation = rot;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @return the newCardID
	 */
	public int getNewCardID() {
		return newCardID;
	}

	/**
	 * @return the rotation
	 */
	public Rotation getRotation() {
		return rotation;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

}
