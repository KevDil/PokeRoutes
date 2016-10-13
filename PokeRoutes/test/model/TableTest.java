package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TableTest {
	private Table testTable;
	
	private CastleCard card1;
	private BonusCard bonusCard1;
	private Castle castle1;
	private Player player1;
	
	@Before
    public void setUp() {
		testTable = new Table(0);
		
		card1 = new CastleCard(1, CardType.SEAGULL, CardForm.STRAIGHT);
		bonusCard1 = new FormBonusCard(1, CastleForm.AXE, 666);
		castle1 = new Castle(1, card1);
		player1 = new Player(new PlayerData());
		
		testTable.getCards().add(card1);
		testTable.getBonusCards().add(bonusCard1);
		
		testTable.getPlayers().add(player1);
		player1.getCastles().add(castle1);
		
		
		testTable.getOpenCards()[1] = card1;
    }
	
	@Test
	public void testGetBonusCardByID() {
		BonusCard bonusCard = testTable.getBonusCardByID(1);
		assertEquals(bonusCard1, bonusCard);
	}
	
	@Test (expected=NullPointerException.class)
	public void testBonusGetCardByIDInvalid() {
		testTable.getBonusCardByID(1000);
	}
	
	@Test
	public void testGetCardByID() {
		Card card = testTable.getCardByID(1);
		assertEquals(card1, card);
	}
	
	@Test (expected=NullPointerException.class)
	public void testGetCardByIDInvalid() {
		testTable.getCardByID(1000);
	}
	
	@Test
	public void testGetCastleByID() {
		Castle castle = testTable.getCastleByID(1);
		assertEquals(castle1, castle);
	}
	
	@Test (expected=NullPointerException.class)
	public void testGetCastleByIDInvalid() {
		testTable.getCastleByID(1000);
	}
	
	@Test (expected=NullPointerException.class)
	public void testTakeOpenCardInvalid1() {
		testTable.takeOpenCard(DrawPile.OPEN_1);
	}
	
	@Test (expected=NullPointerException.class)
	public void testTakeOpenCardInvalid3() {
		testTable.takeOpenCard(DrawPile.OPEN_3);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testTakeOpenCardInvalidStack() {
		testTable.takeOpenCard(DrawPile.STACK);
	}
	
	@Test
	public void testTakeOpenCard() {
		Card card = testTable.takeOpenCard(DrawPile.OPEN_2);
		assertEquals(card1, card);
		
		assertEquals(null, testTable.getOpenCards()[1]);
		
	}
}
