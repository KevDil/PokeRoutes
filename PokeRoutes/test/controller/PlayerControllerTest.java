package controller;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Card;
import model.CardForm;
import model.CardType;
import model.Castle;
import model.CastleCard;
import model.CombatCard;
import model.DrawPile;
import model.GameParameter;
import model.Player;
import model.PlayerData;
import model.Position;
import model.Rotation;
import model.Table;

/**
 * @author sopr076
 *
 */
public class PlayerControllerTest {
	
	private GameController gameCon = new GameController();
	private PlayerController playCon = gameCon.getPlayerController();
	private CastleController castleCon = gameCon.getCastleController(); 
	private PlayerData pd = new PlayerData("Hans", 10, 3, false);
	private PlayerData pd2 = new PlayerData("Peter", 10, 4, false);
	
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
	
	private Position pos1;
	private Position pos2;
	private Position pos3;
	private Position pos4;
	private Position pos5;
	private Position pos6;
	private Position pos7;
	private Position pos8;
	private Position pos9;
	private Position pos10;
	private Position pos11;
	
	
	private Castle castle;
	private Castle castle2;
	
	private Rotation rotation1;
	private Rotation rotation2;
	private Rotation rotation3;
	private Rotation rotation4;
	
	private DrawPile open1;
	
	@Before
	public void startup() throws Exception {
		
		rotation1 = Rotation.EAST;
		rotation2 = Rotation.SOUTH;
		rotation3 = Rotation.NORTH;
		rotation4 = Rotation.WEST;
		
		open1 = DrawPile.OPEN_1;
		
		card1 = new CastleCard(1, CardType.BUCKET, CardForm.CROSS);
		card2 = new CastleCard(2, CardType.SEAGULL, CardForm.STRAIGHT);
		card3 = new CastleCard(3, CardType.BUCKET, CardForm.T_CROSS);
		card4 = new CastleCard(4, CardType.CRAB, CardForm.CURVE);
		card5 = new CastleCard(5, CardType.SEAGULL, CardForm.T_CROSS);
		card6 = new CastleCard(6, CardType.SEAGULL, CardForm.T_CROSS);
		card7 = new CastleCard(7, CardType.SEAGULL, CardForm.T_CROSS);
		card8 = new CastleCard(8, CardType.CRAB, CardForm.CURVE);
		card9 = new CastleCard(9, CardType.CRAB, CardForm.END);
		card10 = new CastleCard(10, CardType.CRAB, CardForm.END);
		card11 = new CastleCard(11, CardType.BUCKET, CardForm.END);
		card12 = new CastleCard(12, CardType.BUCKET, CardForm.END);
				
		card1.setRotation(rotation3);
		card2.setRotation(rotation1);
		card3.setRotation(rotation2);
		card4.setRotation(rotation3);
		card5.setRotation(rotation2);
		card6.setRotation(rotation1);
		card7.setRotation(rotation2);
		card9.setRotation(rotation3);
		card10.setRotation(rotation4);
		card11.setRotation(rotation1);
		card12.setRotation(rotation2);
		
		pos1 = new Position(-1, 0);
		pos2 = new Position(1, 0);
		pos3 = new Position(1, 1);
		pos4 = new Position(0, -1);	
		pos5 = new Position(-2, 0);	
		pos6 = new Position(0, 1);	
		pos7 = new Position(2, 1);	
		pos8 = new Position(2, 0);
		pos9 = new Position(1, -1);
		pos10 = new Position(0, -2);
		pos11 = new Position(3, 0);
		
		castle = new Castle(1, card1);
		
		castle.addCard(pos1, card2);
		castle.addCard(pos2, card3);
		castle.addCard(pos3, card4);
		castle.addCard(pos4, card5);
		castle.addCard(pos4, card6);
		castle.addCard(pos4, card5);
		castle.addCard(pos4, card6);
		castle.addCard(pos5, card11);
		castle.addCard(pos6, card9);
		castle.addCard(pos7, card10);
		castle.addCard(pos8, card10);
		castle.addCard(pos9, card10);
		castle.addCard(pos10, card12);
		
		castle2 = new Castle(2, card1);
		
		castle2.addCard(pos1, card2);
		castle2.addCard(pos2, card3);
		castle2.addCard(pos3, card4);
		castle2.addCard(pos4, card5);
		castle2.addCard(pos4, card6);
		castle2.addCard(pos4, card5);
		castle2.addCard(pos4, card6);
		castle2.addCard(pos5, card11);
		castle2.addCard(pos6, card9);
		castle2.addCard(pos7, card10);
		castle2.addCard(pos8, card2);
		castle2.addCard(pos9, card10);
		castle2.addCard(pos10, card12);
		castle2.addCard(pos11, card10);
		
		ArrayList<PlayerData> players = new ArrayList<>();
		players.add(pd);
		players.add(pd2);
		gameCon.initialize(new GameParameter(false,0,players));
		
		gameCon.getTable().getPlayers().get(1).getCastles().add(castle);
		gameCon.getTable().getCastleByID(1).setPlayer(gameCon.getTable().getPlayers().get(1));
		
		gameCon.getTable().getPlayers().get(0).getCastles().add(castle2);
		gameCon.getTable().getCastleByID(2).setPlayer(gameCon.getTable().getPlayers().get(0));
	}
	
	
	/**
	 * Tests if a new castle is created correctly.
	 * 					{@link PlayerController#createNewCastle(CastleCard, Rotation)}
	 */
	@Test
	public void createNewCastleTest() {
		playCon.createNewCastle(card8, rotation1);
		Card card = gameCon.getTable().getCurrentPlayer().getCastles().get(1).getCardByPosition(Position.Zero).getTopCard();
		assertEquals(card, card8);
	}
	
	/**
	 * Tests if a card is drawn form one of the drawpiles and is added to the handcards of a player.
	 * 					{@link PlayerController#drawCard(DrawPile)}
	 */
	@Test
	public void drawCardTest() {
		Player player = gameCon.getTable().getCurrentPlayer();
		player.setHandCard(0,null);
		assertEquals(player.getCards()[0], null);
		playCon.drawCard(open1);
		assertNotEquals(player.getCards()[0], null);
		assertNotEquals(gameCon.getTable().getOpenCards()[0], null);
	}
	
	/**
	 * Tests if a handcard from a player is exchanged with a card of a drawpile.
	 * 					{@link PlayerController#tradeCard(DrawPile, Integer)}
	 */
	@Test
	public void tradeCardTest() {
		Card pileCard = gameCon.getTable().getOpenCards()[0];
		Card tradeCard = gameCon.getTable().getCurrentPlayer().getCards()[0];
		playCon.tradeCard(open1, 0);
		assertEquals(gameCon.getTable().getOpenCards()[0], tradeCard);
		assertEquals(gameCon.getTable().getCurrentPlayer().getCards()[0], pileCard);
	}
	
	/**
	 * Tests if a bonuscard is assigned to a player and is taken away form another player.
	 * @throws Exception 
	 * 					{@link PlayerController#claimBonusCard(Castle)}
	 */
	@Test
	public void claimBonusCardTest() throws Exception {
		Table table = gameCon.getTable();
		castleCon.isComplete(castle);
		castleCon.isComplete(castle2);
		assertEquals(playCon.claimBonusCard(castle),2);
		assertEquals(table.getBonusCards().get(0).getPlayer(), table.getPlayers().get(1));
		assertEquals(playCon.claimBonusCard(castle2),2);
		assertEquals(table.getBonusCards().get(0).getPlayer(), table.getPlayers().get(0));
		assertNotEquals(table.getBonusCards().get(0).getPlayer(), table.getPlayers().get(1));
	}
	
	
	/**
	 * Tests if a player's attack is successful and confirms it by checking the topcard of the current combatcontext.
	 * Furthermore it tests if a reattack or defend is successful in the same way.
	 * @throws Exception
	 * 					{@link PlayerController#attack(PlayedCardHolder)}
	 * 					{@link PlayerController#reattackOrDefend()}
	 */
	@Test
	public void attackAndReattackOrDefendTest() throws Exception {
		CombatCard cc = new CombatCard(11,CardType.BUCKET);
		CombatCard cc2 = new CombatCard(12,CardType.CRAB);
		gameCon.getTable().getCurrentPlayer().setHandCard(0,cc);
		assertTrue(playCon.attack(castle.getCardByPosition(pos1)));
		CombatCard cc3 = gameCon.getTable().getCombatContextStack().peek().getLastCombatCard();
		assertEquals(cc3,cc);
		gameCon.getTable().getCurrentPlayer().setHandCard(0,cc2);
		assertFalse(playCon.attack(castle.getCardByPosition(pos1)));
		
		gameCon.getTable().getPlayers().get(1).setHandCard(0,cc2);
		assertTrue(playCon.reattackOrDefend());
		CombatCard cc4 = gameCon.getTable().getCombatContextStack().peek().getLastCombatCard();
		assertEquals(cc4,cc2);
	}
	
	/**
	 * Tests if a player is not able to attack his own castle.
	 * @throws Exception
	 * 					{@link PlayerController#attack()}
	 */
	@Test (expected = Exception.class)
	public void attackErrorTest() throws Exception {
		playCon.createNewCastle(card8, rotation1);
		gameCon.getTable().getCurrentPlayer().setHandCard(0,new CombatCard(11,CardType.BUCKET));
		assertTrue(playCon.attack(gameCon.getTable().getCurrentPlayer().getCastles().get(0).getCardByPosition(Position.Zero)));
	}
}
