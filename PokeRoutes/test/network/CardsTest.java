package network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.InvalidParameterException;

import org.junit.Test;

import model.BigWave;
import model.Card;
import model.CardType;
import model.CastleCard;
import model.CombatCard;

public class CardsTest {

	@Test
	public void testDecodeCombatCard() {
		CombatCard c = Cards.decodeCombatCard("b", 1);
		assertEquals(CardType.BUCKET, c.getType());
	}
	
	@Test (expected = InvalidParameterException.class)
	public void testDecodeCombatCardInvalid() {
		CombatCard c = Cards.decodeCombatCard("a", 1);
		assertEquals(CardType.BUCKET, c.getType());
	}
	
	@Test
	public void testDecodeCastleCard() {
		CastleCard c = Cards.decodeCastleCard("s", "4", 1);
		
		assertEquals(CardType.SEAGULL, c.getType());
		assertEquals(Network.toCardForm(4), c.getCardForm());
	}
	
	@Test (expected = InvalidParameterException.class)
	public void testDecodeCastleCardInvalid() {
		Cards.decodeCastleCard("s", "5", 1);
	}
	
	@Test
	public void testDecodeCard() {
		Card card = Cards.decodeCard("c-b-1", 1);
		assertTrue(card instanceof CastleCard);
		
		card = Cards.decodeCard("gw", 1);
		assertTrue(card instanceof BigWave);
		
		card = Cards.decodeCard("a-b", 1);
		assertTrue(card instanceof CombatCard);
	}
	

}
