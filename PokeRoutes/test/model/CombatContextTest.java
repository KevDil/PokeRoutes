package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class CombatContextTest {
	
	
	private CastleCard castleCard = new CastleCard(1, CardType.BUCKET, CardForm.CROSS);
	
	private CombatCard card1 = new CombatCard(2, CardType.SEAGULL);
	
	private CombatCard card2 = new CombatCard(3, CardType.BUCKET);
	
	private CombatCard card3 = new CombatCard(4, CardType.CRAB);
	
	private PlayerData playerData = new PlayerData();
	
	private Player attacker = new Player(playerData);
	
	private Player defender = new Player(playerData);
	
	private Castle castle = new Castle(1, castleCard);
	
	private PlayedCardHolder cardHolder = castle.getCardByPosition(Position.Zero);
	
	private CombatContext comCon;
	
	private ArrayList<CombatCard> combatCards;
	
	
	@Before
	public void startup() {
		castle.setPlayer(defender);
		comCon = new CombatContext(cardHolder, attacker);
		combatCards = comCon.getCombatCards();
	}

	/**
	 * Tests if the method correctly recognizes the winner
	 */
	@Test
	public void testGetWinner() {
		comCon.addCombatCard(card1);
		assertEquals(attacker, comCon.getWinner());
		comCon.addCombatCard(card2);
		assertEquals(defender, comCon.getWinner());
		comCon.addCombatCard(card3);
		assertEquals(attacker, comCon.getWinner());
	}
	
	/**
	 * Tests the addition of a card to the prize cards
	 */
	@Test
	public void testAddCard() {
		assertTrue(comCon.getCombatCards().isEmpty());
		comCon.addCombatCard(card1);
		assertEquals(card1, combatCards.get(combatCards.size()-1));
		comCon.addCombatCard(card2);
		assertEquals(card1, combatCards.get(combatCards.size()-2));
		assertEquals(card2, combatCards.get(combatCards.size()-1));
		comCon.addCombatCard(card3);
		assertEquals(card1, combatCards.get(combatCards.size()-3));
		assertEquals(card2, combatCards.get(combatCards.size()-2));
		assertEquals(card3, combatCards.get(combatCards.size()-1));
	}

}
