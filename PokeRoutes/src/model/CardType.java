package model;

public enum CardType {

	CRAB,

	SEAGULL,

	BUCKET;

	public boolean beats(CardType toBeat) {
		switch(this) {
		case CRAB:
			return toBeat == BUCKET;
		case SEAGULL:
			return toBeat == CRAB;
		case BUCKET:
			return toBeat == SEAGULL;
		default:
			throw new RuntimeException("Invalid CardType");
		}
	}

}
