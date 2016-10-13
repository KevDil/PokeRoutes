package model;

import java.io.Serializable;

public class Position  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4499736404069657950L;
	
	public final static Position Zero = new Position(0, 0);

	private int x;

	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position add(int offsetX, int offsetY) {
		return new Position(x + offsetX, y + offsetY);
	}
	
	public Position add(Position offset) {
		return add(offset.getX(), offset.getY());
	}
	
	public Direction toDirection() {
		if (this.equals(new Position(0, -1))) return Direction.TOP;
		else if (this.equals(new Position(0, 1))) return Direction.BOTTOM;
		else if (this.equals(new Position(1, 0))) return Direction.RIGHT;
		else if (this.equals(new Position(-1, 0))) return Direction.LEFT;
		else return null;
	}
	

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
