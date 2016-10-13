package model;

public enum DrawPile {

	STACK,

	OPEN_1,

	OPEN_2,

	OPEN_3;
	
	public int getIndex() {
		switch(this) {
		case STACK:
			return 0;
		case OPEN_1:
			return 1;
		case OPEN_2:
			return 2;
		case OPEN_3:
			return 3;
		default:
			throw new RuntimeException("Invalid DrawPile");
		}
	}
	
	public static DrawPile getPile(int index) {
		switch(index) {
		case 0:
			return STACK;
		case 1:
			return OPEN_1;
		case 2:
			return OPEN_2;
		case 3:
			return OPEN_3;
		default:
			throw new RuntimeException("Invalid DrawPile");
		}
	}

}
