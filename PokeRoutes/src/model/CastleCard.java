package model;

public class CastleCard extends Card {

	/**
	 * 
	 */
	private static final long serialVersionUID = -514244843061990934L;

	private CardType type;
	private CardForm cardForm;
	private Rotation rotation;

	/**
	 *  
	 */
	public CastleCard(int id, CardType type, CardForm cardForm) {
		super(id);
		this.type = type;
		this.cardForm = cardForm;
		rotation = Rotation.NORTH;
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

	/**
	 * @return the cardForm
	 */
	public CardForm getCardForm() {
		return cardForm;
	}

	/**
	 * @param cardForm the cardForm to set
	 */
	public void setCardForm(CardForm cardForm) {
		this.cardForm = cardForm;
	}

	/**
	 * @return the rotation
	 */
	public Rotation getRotation() {
		return rotation;
	}

	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(Rotation rotation) {
		this.rotation = rotation;
	}

}
