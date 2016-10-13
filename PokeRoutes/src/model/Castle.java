package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Castle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 33026559561623218L;

	private boolean completed;

	private int id;

	private Player player;

	private HashMap<Position ,PlayedCardHolder> playedCards;

	private ArrayList<BonusCard> bonusCards;

	/**
	 *  
	 */
	public Castle(int id, CastleCard initialCard) {
		this.id = id;
		
		playedCards = new HashMap<Position, PlayedCardHolder>();
		bonusCards = new ArrayList<BonusCard>();
		addCard(Position.Zero, initialCard);
	}

	public int getPoints() {
		return getSize();
	}

	/**
	 * Searches for the PlayedCardHolder at a certain position
	 * @param pos Position of the card to find
	 * @return the PlayedCardHolder at the specified Position
	 */
	public PlayedCardHolder getCardByPosition(Position pos) {
		return playedCards.get(pos);
	}

	/**
	 * Returns the amount of top cards in a castle
	 * @return the castles size
	 */
	public int getSize() {
		return playedCards.size();
	}

	/**
	 * Computes the highest tower in the castle
	 * @return the height of the highest tower in the castle
	 */
	public int getHeight() {
		int max = 0;
		for (PlayedCardHolder playedCard : playedCards.values()) {
			int size = playedCard.getHeight();
			if (size > max) {
				max = size;
			}
		}
		return max;
	}

	/**
	 * Adds a card at the specified position to a castle
	 * If there is no playedCardHolder in place a new one will be created
	 * @param pos the Position where the card should be added
	 * @param card the card to add
	 */
	public void addCard(Position pos, CastleCard card) {
		PlayedCardHolder playedCardHolder = getCardByPosition(pos);
		if (playedCardHolder == null) {
			PlayedCardHolder playedCard = new PlayedCardHolder(card);
			playedCard.setPosition(pos);
			playedCard.setCastle(this);
			playedCards.put(pos, playedCard);
		} else {
			playedCardHolder.getCastleCards().push(card);
		}
	}
	
	public void addPlayedCardHolder(Position position, PlayedCardHolder pch) {
		playedCards.put(position, pch);
		pch.setCastle(this);
	}
	
	public void removePlayedCardHolder(Position position) {
		playedCards.remove(position);
	}

	/**
	 * Removes the top card of the specified PlayedCardholder from the castle
	 * If there is only one card, the PlayedCardHolder will also be removed
	 * @param cardHolder the PlayedCardholder to remove a card from
	 * @throws IllegalArgumentException if the card to be removed is not part of the castle
	 */
	public void removeCard(PlayedCardHolder cardHolder) throws IllegalArgumentException {
		if (cardHolder.getCastle() != this) {
			throw new IllegalArgumentException("Card is not a part of this castle");
		}
		Stack<CastleCard> cards = cardHolder.getCastleCards();
		cards.pop();
		if (cards.isEmpty()) {
			playedCards.remove(cardHolder.getPosition());
			cardHolder.setCastle(null);
			cardHolder.setPosition(null);
		}
		if (playedCards.isEmpty()) {
			this.getPlayer().getCastles().remove(this);
			this.setPlayer(null);
		}
	}
	
	/**
	 * Removes a card at the specified position
	 * @see Castle#removeCard(PlayedCardHolder cardHolder)
	 * @param position the position where a card should be removed
	 * @throws IllegalArgumentException if the card to be removed is not part of the castle
	 */
	public void removeCard(Position position) throws IllegalArgumentException {
		removeCard(getCardByPosition(position));
	}

	/**
	 * @return the castleID
	 */
	public int getID() {
		return id;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the playedCards
	 */
	public HashMap<Position, PlayedCardHolder> getPlayedCards() {
		return playedCards;
	}

	/**
	 * @return the bonusCards
	 */
	public ArrayList<BonusCard> getBonusCards() {
		return bonusCards;
	}

	/**
	 * @return the completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}
