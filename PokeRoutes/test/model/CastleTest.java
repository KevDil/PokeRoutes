package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CastleTest {
	
	private CastleCard card1;
	private CastleCard card2;
	private CastleCard card3;
	private CastleCard card4;
	private CastleCard card5;
	
	private Position pos1;
	private Position pos2;
	private Position pos3;
	
	
	private Castle castle;
	
	@Before
	public void startup() {
		card1 = new CastleCard(1, CardType.BUCKET, CardForm.CROSS);
		card2 = new CastleCard(1, CardType.SEAGULL, CardForm.STRAIGHT);
		card3 = new CastleCard(1, CardType.BUCKET, CardForm.END);
		card4 = new CastleCard(1, CardType.CRAB, CardForm.CURVE);
		card5 = new CastleCard(1, CardType.SEAGULL, CardForm.T_CROSS);
		
		pos1 = new Position(1, 0);
		pos2 = new Position(1, 1);
		pos3 = new Position(0, 1);
		
		castle = new Castle(1, card1);
	}

	/**
	 * Tests the successful addition of cards to a castle
	 */
	@Test
	public void testAddCard() {
		castle.addCard(pos1, card2);
		PlayedCardHolder playedCardHolder = castle.getCardByPosition(pos1);
		assertEquals(playedCardHolder, castle.getPlayedCards().get(pos1));
		assertEquals(card2, playedCardHolder.getCastleCards().peek());
		assertEquals(castle, playedCardHolder.getCastle());
		
		castle.addCard(pos2, card3);
		playedCardHolder = castle.getCardByPosition(pos2);
		assertEquals(playedCardHolder, castle.getPlayedCards().get(pos2));
		assertEquals(card3, playedCardHolder.getCastleCards().peek());
		assertEquals(castle, playedCardHolder.getCastle());
		
		castle.addCard(pos3, card4);
		playedCardHolder = castle.getCardByPosition(pos3);
		assertEquals(playedCardHolder, castle.getPlayedCards().get(pos3));
		assertEquals(card4, playedCardHolder.getCastleCards().peek());
		assertEquals(castle, playedCardHolder.getCastle());
		
		castle.addCard(pos3, card5);
		playedCardHolder = castle.getCardByPosition(pos3);
		assertEquals(playedCardHolder, castle.getPlayedCards().get(pos3));
		assertEquals(card5, playedCardHolder.getCastleCards().peek());
		assertEquals(castle, playedCardHolder.getCastle());
		playedCardHolder.getCastleCards().pop();
		assertEquals(card4, playedCardHolder.getCastleCards().peek());
		assertEquals(castle, playedCardHolder.getCastle());
	}
	
	/**
	 * Tests the removal of a card that does not belong to the specified castle
	 * 
	 * @throws IllegalArgumentException
	 * 						{@link Castle#removeCard(PlayedCardHolder)}
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testRemoveWrongCard() {
		castle.addCard(pos1, card2);
		
		PlayedCardHolder playedCardHolder1 = castle.getCardByPosition(pos1);
		
		playedCardHolder1.setCastle(null);
		castle.removeCard(playedCardHolder1);
	}
	
	/**
	 * Tests the successful removal cards from a castle
	 */
	@Test
	public void testRemoveCard() {
		castle.addCard(pos1, card2);
		castle.addCard(pos2, card3);
		castle.addCard(pos3, card4);
		castle.addCard(pos3, card5);
		
		PlayedCardHolder playedCardHolder1 = castle.getCardByPosition(pos1);
		PlayedCardHolder playedCardHolder2 = castle.getCardByPosition(pos2);
		PlayedCardHolder playedCardHolder3 = castle.getCardByPosition(pos3);
		PlayedCardHolder playedCardHolder4 = castle.getCardByPosition(Position.Zero);

		castle.removeCard(playedCardHolder1);
		assertEquals(null, castle.getCardByPosition(pos1));
		assertEquals(null, playedCardHolder1.getCastle());
		
		castle.removeCard(playedCardHolder2);
		assertEquals(null, castle.getCardByPosition(pos2));
		assertEquals(null, playedCardHolder1.getCastle());
		
		castle.removeCard(playedCardHolder3);
		assertEquals(playedCardHolder3, castle.getCardByPosition(pos3));
		assertEquals(castle, playedCardHolder3.getCastle());
		assertEquals(card4, playedCardHolder3.getCastleCards().peek());
		
		castle.removeCard(playedCardHolder4);
		assertEquals(null, castle.getCardByPosition(Position.Zero));
		assertEquals(null, playedCardHolder4.getCastle());
	}
	
	/**
	 * Tests the computation of a castles height
	 */
	@Test
	public void testGetHeight() {
		castle.addCard(pos1, card2);
		castle.addCard(pos2, card3);
		castle.addCard(pos3, card4);
		assertEquals(1, castle.getHeight());

		castle.addCard(pos3, card5);
		castle.addCard(pos3, card4);
		assertEquals(3, castle.getHeight());

		castle.addCard(pos2, card3);
		assertEquals(3, castle.getHeight());
		
		castle.addCard(pos2, card3);
		castle.addCard(pos2, card3);
		castle.addCard(pos2, card3);
		castle.addCard(pos2, card3);
		assertEquals(6, castle.getHeight());
	}
	
}
