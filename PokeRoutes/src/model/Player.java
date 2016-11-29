package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1160530600985188894L;

	private PlayerData playerData;

	private ArrayList<Castle> castles;

	private Card[] cards;

	private ArrayList<BonusCard> bonusCards;
	
	private int points;

	/**
	 *  
	 */
	public Player(PlayerData playerData) {
		this.playerData = playerData;
		this.points = 0;
		this.castles = new ArrayList<Castle>();
		this.cards = new Card[3];
		this.bonusCards = new ArrayList<BonusCard>();
	}
	
	public void addPoints(int points) {
		this.points += points;
	}
	
	public List<Castle> getIncompleteCastle() {
		return castles.stream()
				.filter(c -> !c.isCompleted())
				.collect(Collectors.toList());
	}
	
	public boolean hasFullHandCards() {
		for(Card card : cards) {
			if(card == null)
				return false;
		}
		
		return true;
	}
	
	public void removeBonusCard(BonusCard bonusCard) {
		for(int i = 0; i < this.bonusCards.size(); i++) {
			if(this.bonusCards.get(i) == bonusCard) {
				this.bonusCards.remove(i);
				bonusCard.setPlayer(null);
			}
		}
	}
	
	/**
	 * @return the playerData
	 */
	public PlayerData getPlayerData() {
		return playerData;
	}

	/**
	 * @return the castles
	 */
	public ArrayList<Castle> getCastles() {
		return castles;
	}

	/**
	 * @return the left card
	 */
	public Card getLeftCard() {
		return cards[0];
	}
	
	/**
	 * @return the middle cards
	 */
	public Card getMiddleCard() {
		return cards[1];
	}
	
	/**
	 * @return the cards
	 */
	public Card getRightCard() {
		return cards[2];
	}
	
	/**
	 * @return the cards
	 */
	public Card[] getCards() {
		return cards;
	}

	/**
	 * @return the bonusCards
	 */
	public ArrayList<BonusCard> getBonusCards() {
		return bonusCards;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.playerData.setScore(points);
		this.points = points;
	}
	
	public boolean isAI() {
		return this.playerData.isAI();
	}

	public void setHandCard(int i, Card card){
		cards[i] = card;
	}
	
	public void removeHandCard(int i) {
		cards[i] = null;
	}
}
