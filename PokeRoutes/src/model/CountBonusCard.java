package model;

public class CountBonusCard extends BonusCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5100280797362120245L;

	private CardType type;

	private int required;
	

	public CountBonusCard(int id, CardType type, int required, int value) {
		super(id);
		this.type = type;
		this.required = required;
		this.setValue(value);
	}


	/**
	 * @return the type
	 */
	public CardType getType() {
		return type;
	}


	/**
	 * @return the required
	 */
	public int getRequired() {
		return required;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	
	

}
