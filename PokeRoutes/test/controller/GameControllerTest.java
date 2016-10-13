package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Card;
import model.GameParameter;
import model.Player;
import model.PlayerData;
import model.Table;

public class GameControllerTest {

	private PlayerData player1;
	private PlayerData player2;
	private PlayerData player3;
	
	private GameParameter gParameter;
	
	private GameController gameController;
	
	@Before
	public void setUp() throws Exception {
		gameController = new GameController();
		player1 = new PlayerData("player1", 0, 1, false);
		player2 = new PlayerData("player2", 0, 2, false);
		player3 = new PlayerData("player3", 0, 3, false);
		ArrayList<PlayerData> playerList = new ArrayList<>();
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		gParameter = new GameParameter(false, 42, playerList);
	}
	
	/**
	 * Tests if the current player is changed correctly and if the queue is edited correctly for the next turn.
	 * @throws Exception
	 * 				{@link GameController#nextTurn()}
	 */
	@Test
	public void testNextTurn() throws Exception {
		gameController.initialize(gParameter);
		Table table = gameController.getTable();
		
		assertEquals(player1,table.getCurrentPlayer().getPlayerData());
		gameController.nextTurn();
		assertEquals(player2,table.getCurrentPlayer().getPlayerData());
		gameController.nextTurn();
		assertEquals(player3,table.getCurrentPlayer().getPlayerData());
		gameController.nextTurn();
		assertEquals(player1,table.getCurrentPlayer().getPlayerData());
	}
	
	/**
	 * Tests if the open cards pile is refilled correctly.
	 * 				{@link GameController#refillPile()}
	 */
	@Test
	public void testRefillPile() {
		gameController.initialize(gParameter);
		Table table = gameController.getTable();
		assertNotEquals(table.getOpenCards()[0], null);
		assertNotEquals(table.getOpenCards()[1], null);
		assertNotEquals(table.getOpenCards()[2], null);
	}
	
	/**
	 * Tests if the players of the current game are listed in the highscore at the end of a game. 
	 * @throws Exception
	 * 				{@link GameController#endGame()}
	 */
	@Test
	public void testEndGame() throws Exception {
		gameController.initialize(gParameter);
		gameController.endGame();
		
		ArrayList<PlayerData> players = gameController.getHighscoreController().getPlayerData();
		
		ArrayList<Player> players2 = gameController.getTable().getPlayers();
		
		assertTrue(players.contains(players2.get(0).getPlayerData()));
		assertTrue(players.contains(players2.get(1).getPlayerData()));
		assertTrue(players.contains(players2.get(2).getPlayerData()));
	}
	
	/**
	 * Tests if a player gets 3 handcards from the draw pile.
	 * 				{@link GameController#refillHandCards(Player)}
	 */
	@Test
	public void testRefillHandCards() {
		gameController.initialize(gParameter);
		Player player = gameController.getTable().getPlayers().get(0);
		
		assertNotEquals(player.getCards()[0], null);
		assertNotEquals(player.getCards()[1], null);
		assertNotEquals(player.getCards()[2], null);
	}
	
	/**
	 * Test if a selected card is added to the handcards of the player.
	 * 				{@link GameController#addCardToHand(Player, Card)}
	 */
	@Test
	public void testAddCardToHand() {
		gameController.initialize(gParameter);
		Player player = gameController.getTable().getPlayers().get(0);
		
		assertNotEquals(player.getCards()[0], null);
		assertNotEquals(player.getCards()[1], null);
		assertNotEquals(player.getCards()[2], null);
	}

}
