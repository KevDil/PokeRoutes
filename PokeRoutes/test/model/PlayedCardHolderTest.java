package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PlayedCardHolderTest {

	private CastleCard card1;
	private CastleCard card2;
	private CastleCard card3;
	private CastleCard card4;
	private CastleCard card5;
	private CastleCard card6;
	
	private Position pos1;
	private Position pos2;
	private Position pos3;
	private Position pos4;
	
	
	private Castle castle;
	
	@Before
	public void startup() {
		card1 = new CastleCard(1, CardType.BUCKET, CardForm.CROSS);
		card2 = new CastleCard(1, CardType.SEAGULL, CardForm.STRAIGHT);
		card3 = new CastleCard(1, CardType.BUCKET, CardForm.END);
		card4 = new CastleCard(1, CardType.CRAB, CardForm.CURVE);
		card5 = new CastleCard(1, CardType.SEAGULL, CardForm.T_CROSS);
		card6 = new CastleCard(1, CardType.SEAGULL, CardForm.T_CROSS);
		
		//West
		pos1 = new Position(-1, 0);
		//East
		pos2 = new Position(1, 0);
		//South
		pos3 = new Position(0, 1);
		//North
		pos4 = new Position(0, -1);
		
		castle = new Castle(1, card1);
		
		castle.addCard(pos1, card2);
		castle.addCard(pos2, card3);
		castle.addCard(pos3, card4);
		castle.addCard(pos4, card5);
		castle.addCard(pos4, card6);
		castle.addCard(pos4, card5);
		castle.addCard(pos4, card6);
	}
	
	/**
	 * Tests if the northern card of a specified card is correctly recognized
	 */
	@Test
	public void testGetNorthCard() {
		assertEquals(card6, castle.getCardByPosition(Position.Zero).getNorthCard().getTopCard());
	}
	
	/**
	 * Tests if the southern card of a specified card is correctly recognized
	 */
	@Test
	public void testGetSouthCard() {
		assertEquals(card4, castle.getCardByPosition(Position.Zero).getSouthCard().getTopCard());
	}
	
	/**
	 * Tests if the eastern card of a specified card is correctly recognized
	 */
	@Test
	public void testGetEastCard() {
		assertEquals(card3, castle.getCardByPosition(Position.Zero).getEastCard().getTopCard());
	}
	
	/**
	 * Tests if the western card of a specified card is correctly recognized
	 */
	@Test
	public void testGetWestCard() {
		assertEquals(card2, castle.getCardByPosition(Position.Zero).getWestCard().getTopCard());
	}

}
