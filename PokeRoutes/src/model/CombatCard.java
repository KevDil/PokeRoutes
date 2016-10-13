package model;

public class CombatCard extends Card {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7439067878976806293L;
	
	private CardType type;

	/**
	 *  
	 */
	public CombatCard(int id, CardType type) {
		super(id);
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public CardType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(CardType type) {
		this.type = type;
	}

}
