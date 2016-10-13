package model;

import java.io.Serializable;
import java.util.ArrayList;

public class CombatContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2373401473849066009L;

	private ArrayList<CombatCard> combatCards;
	private Player attacker;
	private Player defender;
	private PlayedCardHolder playedCardHolder;
	private boolean finished = false;

	public CombatContext(PlayedCardHolder card, Player attacker) {
		this.playedCardHolder = card;
		this.attacker = attacker;
		this.defender = card.getCastle().getPlayer();
		this.combatCards = new ArrayList<CombatCard>();		
	}

	/**
	 * Computes the amount of Cards played in this Combat
	 * @return the points this combat is worth
	 */
	public int getPoints() {
		return combatCards.size();
	}

	public CombatCard getLastCombatCard() {
		return combatCards.get(combatCards.size() - 1);
	}
	
	/**
	 * Computes the player who won this combat
	 * @return the winner of the current combat
	 */
	public Player getWinner() {
		if (combatCards.size()%2 == 0) return defender;
		else return attacker;
	}

	public Player getCurrentPlayer() {
		if (combatCards.size()%2 == 1) return defender;
		else return attacker;
	}
	
	/**
	 * Adds a card to the stack of played combat cards
	 * @param card the card to add to the list
	 */
	public void addCombatCard(CombatCard card) {
		this.combatCards.add(card);
	}

	/**
	 * @return the combatCards
	 */
	public ArrayList<CombatCard> getCombatCards() {
		return combatCards;
	}
	
	/**
	 * Return true, if the defender have a combat card, which can beats the attacker card
	 * @return true or false
	 */
	public boolean canDefend() {
		if(!isFinished()) {
			CombatCard combatCard = null;
			
			for (Card handCard : defender.getCards()) {
				if (handCard.isCombatCard()) {
					combatCard = (CombatCard) handCard;				
					
					if (combatCard.getType().beats(getLastCombatCard().getType())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Return true, if the attacker have a combat card, which can beats the defend card
	 * @return true or false
	 */
	public boolean canAttack() {
		if(!isFinished()) {
			CombatCard combatCard = null;
			
			for (Card handCard : attacker.getCards()) {
				if (handCard.isCombatCard()) {
					combatCard = (CombatCard) handCard;				

					if (combatCard.getType().beats(getLastCombatCard().getType())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * @param combatCards the combatCards to set
	 */
	public void setCombatCards(ArrayList<CombatCard> combatCards) {
		this.combatCards = combatCards;
	}

	/**
	 * @return the attacker
	 */
	public Player getAttacker() {
		return attacker;
	}

	/**
	 * @return the defender
	 */
	public Player getDefender() {
		return defender;
	}

	/**
	 * @return the playedCardHolder
	 */
	public PlayedCardHolder getPlayedCardHolder() {
		return playedCardHolder;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished() {
		finished = true;
	}

}
