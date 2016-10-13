package model;

import java.io.Serializable;
import java.util.Stack;

public class PlayedCardHolder  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8700414804194894972L;

	private Position position;

	private Castle castle;
	private Stack<CastleCard> castleCards;

	/**
	 *  
	 */
	public PlayedCardHolder(CastleCard castleCard) {
		this.castleCards = new Stack<CastleCard>();
		this.castleCards.push(castleCard);
	}

	/**
	 *  
	 */
	public int getHeight() {
		return this.castleCards.size();
	}

	/**
	 *  
	 */
	public PlayedCardHolder getNorthCard() {
		return this.castle.getCardByPosition(
				position.add(0,-1));
	}

	/**
	 *  
	 */
	public PlayedCardHolder getEastCard() {
		return this.castle.getCardByPosition(
				position.add(1,0));
	}

	/**
	 *  
	 */
	public PlayedCardHolder getSouthCard() {
		return this.castle.getCardByPosition(
				position.add(0,1));
	}

	/**
	 *  
	 */
	public PlayedCardHolder getWestCard() {
		return this.castle.getCardByPosition(
				position.add(-1,0));
	}

	/**
	 *  
	 */
	public CastleCard getTopCard() {
		return this.castleCards.peek();
	}
	
	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return the castle
	 */
	public Castle getCastle() {
		return castle;
	}

	/**
	 * @param castle the castle to set
	 */
	public void setCastle(Castle castle) {
		this.castle = castle;
	}

	/**
	 * @return the castleCards
	 */
	public Stack<CastleCard> getCastleCards() {
		return castleCards;
	}

}
