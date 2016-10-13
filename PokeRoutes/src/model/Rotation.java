package model;

public enum Rotation {

	NORTH,

	EAST,

	SOUTH,

	WEST;

	/**
	 *  
	 */
	public Rotation rotateRight() {
		switch(this) {
		case NORTH:
			return EAST;
		case EAST:
			return SOUTH;
		case SOUTH:
			return WEST;
		case WEST:
			return NORTH;
		default:
			throw new RuntimeException("Invalid rotation");
		}
	}

	public Rotation rotateLeft() {
		switch(this) {
		case NORTH:
			return WEST;
		case EAST:
			return NORTH;
		case SOUTH:
			return EAST;
		case WEST:
			return SOUTH;
		default:
			throw new RuntimeException("Invalid rotation");
		}
	}
	
	public int toDegrees() {
		switch(this) {
		case NORTH:
			return 0;
		case EAST:
			return 90;
		case SOUTH:
			return 180;
		case WEST:
			return 270;
		default:
			throw new RuntimeException("Invalid rotation");
		}
	}

}
