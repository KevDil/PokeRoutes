package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CardTypeTest {

	/**
	 * Tests if card types beat each other correctly
	 */
	@Test
	public void testBeats() {
		assertTrue(CardType.BUCKET.beats(CardType.SEAGULL));
		assertFalse(CardType.BUCKET.beats(CardType.CRAB));
		assertFalse(CardType.BUCKET.beats(CardType.BUCKET));
		
		assertTrue(CardType.SEAGULL.beats(CardType.CRAB));
		assertFalse(CardType.SEAGULL.beats(CardType.BUCKET));
		assertFalse(CardType.SEAGULL.beats(CardType.SEAGULL));

		assertTrue(CardType.CRAB.beats(CardType.BUCKET));
		assertFalse(CardType.CRAB.beats(CardType.SEAGULL));
		assertFalse(CardType.CRAB.beats(CardType.CRAB));
	}

}
