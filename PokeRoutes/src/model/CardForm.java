package model;

public enum CardForm {

	CROSS,

	STRAIGHT,

	T_CROSS,

	CURVE,

	END;

	public boolean hasLeftConnection(Rotation rotation) {
		switch(this) {
		case CROSS:
			return true;
		case END:
			return rotation == Rotation.WEST;
		case STRAIGHT:
			return rotation == Rotation.WEST || rotation == Rotation.EAST;
		case T_CROSS:
			return rotation != Rotation.EAST;
		case CURVE:
			return rotation == Rotation.WEST || rotation == Rotation.SOUTH;
		default:
			throw new RuntimeException("Invalid CardForm");
		}
	}
		

	public boolean hasRightConnection(Rotation rotation) {
		return hasLeftConnection(rotation.rotateLeft().rotateLeft());
	}

	public boolean hasTopConnection(Rotation rotation) {
		return hasLeftConnection(rotation.rotateLeft());
	}

	public boolean hasBottomConnection(Rotation rotation) {
		return hasLeftConnection(rotation.rotateRight());
	}

}
