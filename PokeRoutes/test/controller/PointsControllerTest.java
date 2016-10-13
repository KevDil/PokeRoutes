package controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.CardForm;
import model.CardType;
import model.Castle;
import model.CastleCard;
import model.CombatCard;
import model.CombatContext;
import model.CountBonusCard;
import model.Direction;
import model.GameParameter;
import model.Player;
import model.PlayerData;
import model.Position;
import model.Rotation;

public class PointsControllerTest {
	private GameController gameCon = new GameController();
	private PointsController poCon = gameCon.getPointsController();
	private PlayerData pd = new PlayerData("Hans", 10, 3, false);
	private PlayerData pd2 = new PlayerData("Peter", 15, 2, false);
	private CastleController caCon = gameCon.getCastleController();
	private PlayerController plaCon = gameCon.getPlayerController();
	
	private CastleCard card2;
	private CastleCard card4;
	private CastleCard card5;
	private CastleCard card6;
	private CastleCard card8;
	private CastleCard card9;
	private CastleCard card10;
	
	private CombatCard card11;
	private CombatCard card12;
	
	private Position pos2;
	
	private CombatContext comCon;
	
	private Castle castle;
	
	private Rotation rotation1;
	private Rotation rotation2;
	private Rotation rotation3;
	
	private Player player;
	private Player player2;
	
	private ArrayList<PlayerData> playerList = new ArrayList<PlayerData>();

	
	@Before
	public void startup() throws Exception {
		
		player = new Player(pd);
		player2 = new Player(pd2);
		
		rotation1 = Rotation.EAST;
		rotation2 = Rotation.SOUTH;
		rotation3 = Rotation.NORTH;
		
		//card1 = new CastleCard(1, CardType.BUCKET, CardForm.CROSS);
		card2 = new CastleCard(2, CardType.SEAGULL, CardForm.CURVE);
		card4 = new CastleCard(4, CardType.BUCKET, CardForm.CURVE);
		card5 = new CastleCard(5, CardType.SEAGULL, CardForm.CURVE);
		card6 = new CastleCard(6, CardType.SEAGULL, CardForm.CURVE);
		card8 = new CastleCard(8, CardType.BUCKET, CardForm.CURVE);
		card9 = new CastleCard(9, CardType.BUCKET, CardForm.CURVE);
		card10 = new CastleCard(10, CardType.BUCKET, CardForm.CURVE);
		
		card11 = new CombatCard(11, CardType.SEAGULL);
		card12 = new CombatCard(12, CardType.SEAGULL);
		
		//card1.setRotation(rotation3);
		card2.setRotation(rotation3);
		//card3.setRotation(rotation2);
		card4.setRotation(rotation3);
		card5.setRotation(rotation2);
		card6.setRotation(rotation1);
		//card7.setRotation(rotation2);
		
		pos2 = new Position(1, 0);
		
		playerList.add(player.getPlayerData());
		
		gameCon.initialize(new GameParameter(false, 0, playerList));
		//gameCon.getTable().setCurrentPlayer(player);
		
		CountBonusCard bc = new CountBonusCard(gameCon.getTable().nextId(), CardType.BUCKET, 3, 1);
		
		gameCon.getTable().getBonusCards().add(bc);
		
		plaCon.createNewCastle(card4, rotation3);
		
		castle = gameCon.getTable().getCurrentPlayer().getCastles().get(0);
		
		card4.setRotation(Rotation.NORTH);
		
		caCon.addToCastle(castle.getCardByPosition(Position.Zero), Direction.RIGHT, card8, Rotation.WEST);
		caCon.addToCastle(castle.getCardByPosition(Position.Zero), Direction.TOP, card9, Rotation.EAST);
		caCon.addToCastle(castle.getCardByPosition(pos2), Direction.TOP, card10, Rotation.SOUTH);
		caCon.addToCastle(castle.getCardByPosition(pos2), Direction.UPPER, card2, Rotation.WEST);
		caCon.addToCastle(castle.getCardByPosition(pos2), Direction.UPPER, card5, Rotation.WEST);
		caCon.addToCastle(castle.getCardByPosition(pos2), Direction.UPPER, card6, Rotation.WEST);
		

		
		comCon = new CombatContext(castle.getCardByPosition(Position.Zero), player2);
		comCon.addCombatCard(card11);
		comCon.addCombatCard(card12);
		gameCon.getTable().getCombatContextStack().add(comCon);
		

	}
	
	@Test
	public void addPointsCastleTest() throws Exception {
		poCon.addPoints(castle);
		System.out.println(castle.getPoints() + "+" + gameCon.getTable().getCurrentPlayer().getPoints());
		System.out.println(caCon.listAvailableBonusCards(castle));
		assertEquals (4, castle.getPoints());
		assertEquals (24, gameCon.getTable().getCurrentPlayer().getPoints());
	}
	
	@Test
	public void addPointsCombatTest() {
		poCon.addPoints(comCon);
		assertEquals(2, gameCon.getTable().getCurrentCombatContext().getPoints());
		assertEquals(20, gameCon.getTable().getCurrentCombatContext().getPoints()+gameCon.getTable().getCurrentPlayer().getPoints());
	}
	
	/*@Test
	public void calculatePointsTest() {
	fail("Not yet implemented");	
	}*/

}
