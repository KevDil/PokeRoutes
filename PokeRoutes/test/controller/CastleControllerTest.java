package controller;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.BonusCard;
import model.CardForm;
import model.CardType;
import model.Castle;
import model.CastleCard;
import model.CastleForm;
import model.CountBonusCard;
import model.Direction;
import model.FormBonusCard;
import model.GameParameter;
import model.PatternMatrix;
import model.PlayedCardHolder;
import model.PlayerData;
import model.Position;
import model.Rotation;

public class CastleControllerTest {
	
	private GameController gc=new GameController();
	private CastleController cc= new CastleController(gc);
	
	private CastleCard card1;
	private CastleCard card2;
	private CastleCard card3;
	private CastleCard card4;
	private CastleCard card5;
	private CastleCard card6;
	private CastleCard card7;
	private CastleCard card8;
	private CastleCard card9;
	private CastleCard card10;
	
	private CastleCard card11;
	private CastleCard card12;
	private CastleCard card13;
	private CastleCard card14;
	private CastleCard card15;
	private CastleCard card16;
	private CastleCard card17;
	
	private Position pos1;
	private Position pos2;
	private Position pos3;
	private Position pos4;
	
	
	private Castle castle;
	private Castle castle2;
	
	private Rotation rotation1;
	private Rotation rotation2;
	private Rotation rotation3;
	
	private ArrayList<PlayerData> playerList = new ArrayList<PlayerData>();
	private PlayerData pd = new PlayerData("Hans", 10, 3, false);
	private PlayerData pd2 = new PlayerData("Peter", 10, 3, false);
	
	private CountBonusCard cbc;
	private FormBonusCard fbc;
	
	@Before
	public void startup() throws Exception {
		
		playerList.add(pd);
		playerList.add(pd2);
		gc.initialize(new GameParameter(false, 0, playerList));
		
		cbc = new CountBonusCard(gc.getTable().nextId(), CardType.BUCKET, 3, 1);
		fbc = new FormBonusCard(gc.getTable().nextId(), CastleForm.KEEP, 2);
		
		gc.getTable().getBonusCards().clear();
		gc.getTable().getBonusCards().add(cbc);
		gc.getTable().getBonusCards().add(fbc);
		
		rotation1 = Rotation.EAST;
		rotation2 = Rotation.SOUTH;
		rotation3 = Rotation.NORTH;
		
		card1 = new CastleCard(1, CardType.BUCKET, CardForm.CROSS);
		card2 = new CastleCard(2, CardType.SEAGULL, CardForm.STRAIGHT);
		card3 = new CastleCard(3, CardType.BUCKET, CardForm.T_CROSS);
		card4 = new CastleCard(4, CardType.CRAB, CardForm.CURVE);
		card5 = new CastleCard(5, CardType.SEAGULL, CardForm.T_CROSS);
		card6 = new CastleCard(6, CardType.SEAGULL, CardForm.T_CROSS);
		card7 = new CastleCard(7, CardType.SEAGULL, CardForm.T_CROSS);
		card8 = new CastleCard(8, CardType.CRAB, CardForm.CURVE);
		card9 = new CastleCard(9, CardType.CRAB, CardForm.CURVE);
		card10 = new CastleCard(10, CardType.CRAB, CardForm.CURVE);
		
		
		card11 = new CastleCard(11, CardType.SEAGULL, CardForm.CURVE);
		card12 = new CastleCard(12, CardType.BUCKET, CardForm.CURVE);
		card13 = new CastleCard(13, CardType.SEAGULL, CardForm.CURVE);
		card14 = new CastleCard(14, CardType.SEAGULL, CardForm.CURVE);
		card15 = new CastleCard(15, CardType.BUCKET, CardForm.CURVE);
		card16 = new CastleCard(16, CardType.BUCKET, CardForm.CURVE);
		card17 = new CastleCard(17, CardType.BUCKET, CardForm.CURVE);
		
		
		card1.setRotation(rotation3);
		card2.setRotation(rotation1);
		card3.setRotation(rotation2);
		card4.setRotation(rotation3);
		card5.setRotation(rotation2);
		card6.setRotation(rotation1);
		card7.setRotation(rotation2);
		
		//West
		pos1 = new Position(-1, 0);
		//East
		pos2 = new Position(1, 0);
		//South
		pos3 = new Position(1, 1);
		//North
		pos4 = new Position(0, -1);		
		
		castle = new Castle(1, card1);
		
		gc.getTable().getCurrentPlayer().getCastles().add(castle);
		gc.getTable().getCastleByID(1).setPlayer(gc.getTable().getCurrentPlayer());
		
		castle.addCard(pos1, card2);
		castle.addCard(pos2, card3);
		castle.addCard(pos3, card4);
		castle.addCard(pos4, card5);
		castle.addCard(pos4, card6);
		castle.addCard(pos4, card5);
		castle.addCard(pos4, card6);
		
		card12.setRotation(Rotation.NORTH);
		
		castle2 = new Castle(2, card12);
		
		gc.getTable().getCurrentPlayer().getCastles().add(castle2);
		gc.getTable().getCastleByID(2).setPlayer(gc.getTable().getCurrentPlayer());
		
		cc.addToCastle(castle2.getCardByPosition(Position.Zero), Direction.RIGHT, card15, Rotation.WEST);
		cc.addToCastle(castle2.getCardByPosition(Position.Zero), Direction.TOP, card16, Rotation.EAST);
		cc.addToCastle(castle2.getCardByPosition(pos2), Direction.TOP, card17, Rotation.SOUTH);
		cc.addToCastle(castle2.getCardByPosition(pos2), Direction.UPPER, card11, Rotation.WEST);
		cc.addToCastle(castle2.getCardByPosition(pos2), Direction.UPPER, card13, Rotation.WEST);
		cc.addToCastle(castle2.getCardByPosition(pos2), Direction.UPPER, card14, Rotation.WEST);
	}

	/**
	 * Tests if a card can be removed from a given castle
	 */
	@Test
	public void testCanRemove() {
		PlayedCardHolder pch = castle.getCardByPosition(pos1);
		assertTrue(cc.canRemove(castle.getCardByPosition(pos1)));
		assertFalse(cc.canRemove(castle.getCardByPosition(Position.Zero)));
		assertTrue(cc.canRemove(castle.getCardByPosition(pos4)));

		card2.setRotation(rotation3);
		castle.addCard(new Position(0, -2), card2);
		
		assertFalse(cc.canRemove(castle.getCardByPosition(pos4)));
	}

	/**
	 * Tests if a castle can be successfully converted 
	 * @throws Exception 
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testConvertToMatrix() throws Exception {
		int[][] testBarrack = PatternMatrix.Bergfried;
		
		card4.setRotation(Rotation.NORTH);
		Castle castle2 = new Castle(3, card4);
		
		gc.getTable().getPlayers().get(1).getCastles().add(castle2);
		gc.getTable().getCastleByID(3).setPlayer(gc.getTable().getPlayers().get(1));
		
		cc.addToCastle(castle2.getCardByPosition(Position.Zero), Direction.RIGHT, card8, Rotation.WEST);
		cc.addToCastle(castle2.getCardByPosition(Position.Zero), Direction.TOP, card9, Rotation.EAST);
		cc.addToCastle(castle2.getCardByPosition(pos2), Direction.TOP, card10, Rotation.SOUTH);
		
		int[][] castleMatrix = cc.convertToMatrix(castle2);
		
		assertEquals(testBarrack, castleMatrix);
									
	}

	/**
	 * Tests if a Castle fits to a given pattern
	 * @throws Exception
	 * 				{@link CastleController#addToCastle(model.PlayedCardHolder, Direction, CastleCard, Rotation)}
	 */
	@Test
	public void testCheckPattern() throws Exception {
		card4.setRotation(Rotation.NORTH);
		Castle castle2 = new Castle(3, card4);
		
		gc.getTable().getPlayers().get(1).getCastles().add(castle2);
		gc.getTable().getCastleByID(3).setPlayer(gc.getTable().getPlayers().get(1));
		
		cc.addToCastle(castle2.getCardByPosition(Position.Zero), Direction.RIGHT, card8, Rotation.WEST);
		cc.addToCastle(castle2.getCardByPosition(Position.Zero), Direction.TOP, card9, Rotation.EAST);
		cc.addToCastle(castle2.getCardByPosition(pos2), Direction.TOP, card10, Rotation.SOUTH);
		
		//Something wrong with the matrix utility
		assertTrue(cc.checkPattern(castle2, CastleForm.KEEP));
	}

	@Test
	public void testListAvailableBonusCards() throws Exception {
		
		List<BonusCard> cards = cc.listAvailableBonusCards(castle2);
		assertEquals(cards.get(0), fbc);
		assertEquals(cards.get(1), cbc);
	}
	
	/**
	 * Tests if an exception is correctly thrown when Bonus Cards should be listed for an open castle
	 * @throws Exception
	 * 				{@link CastleController#listAvailableBonusCards(Castle)}
	 */
	@Test (expected = Exception.class)
	public void testListAvailableBonusCardsOpenCastle() throws Exception {
		cc.listAvailableBonusCards(castle);
	
	}

	/**
	 * Tests if the method successfully counts cards of a given type
	 */
	@Test
	public void testCountCardType() {
		assertEquals(2, cc.countCardType(castle, CardType.SEAGULL));
		assertEquals(2, cc.countCardType(castle, CardType.BUCKET));
		assertEquals(1, cc.countCardType(castle, CardType.CRAB));
		
		castle.addCard(pos3, card7);
		
		assertEquals(0, cc.countCardType(castle, CardType.CRAB));
		assertEquals(3, cc.countCardType(castle, CardType.SEAGULL));
	}
	
	/**
	 * Tests the successful addition of a card to a castle
	 * @throws Exception
	 * 				{@link CastleController#addToCastle(model.PlayedCardHolder, Direction, CastleCard, Rotation)}
	 */
	@Test
	public void testAddToCastle() throws Exception {
		PlayedCardHolder card = castle.getCardByPosition(pos1);
		
		cc.addToCastle(card, Direction.LEFT, card8, Rotation.NORTH);
		
		assertEquals(card8, castle.getCardByPosition(new Position(-2, 0)).getTopCard());
		
		cc.addToCastle(card, Direction.UPPER, card7, Rotation.NORTH);
		assertEquals(card, castle.getCardByPosition(pos1));
		assertEquals(card7, card.getTopCard());
		
		cc.addToCastle(card, Direction.TOP, card3, Rotation.WEST);
		assertEquals(card3, castle.getCardByPosition(new Position(-1, -1)).getTopCard());
		
	}

	/**
	 * Tests the addition of a not fitting tower to a castle
	 * @throws Exception
	 * 				{@link CastleController#addToCastle(model.PlayedCardHolder, Direction, CastleCard, Rotation)}
	 */
	@Test (expected = Exception.class)
	public void testAddFalseUpperToCastle() throws Exception {
		cc.addToCastle(castle.getCardByPosition(pos4), Direction.UPPER, card3, Rotation.NORTH);
	}
	
	
	/**
	 * Tests the addition of a not fitting card to a castle
	 * @throws Exception
	 * 				{@link CastleController#addToCastle(model.PlayedCardHolder, Direction, CastleCard, Rotation)}
	 */
	@Test (expected = Exception.class)
	public void testAddFalseToCastle() throws Exception {
		cc.addToCastle(castle.getCardByPosition(Position.Zero), Direction.BOTTOM, card7, Rotation.NORTH);
	}

}
