package controller;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.GameParameter;
import model.PlayerData;
import model.Table;

public class IOControllerTest {
	
	private PlayerData player1;
	private PlayerData player2;
	private PlayerData player3;
	
	private GameParameter gParameter;
	
	private GameController gameController;
	private  IOController ioController;
	
	@Before
	public void setUp() throws Exception {
		gameController = new GameController();
		ioController = new IOController(gameController);
		player1 = new PlayerData("player1", 0, 1, false);
		player2 = new PlayerData("player2", 0, 2, false);
		player3 = new PlayerData("player3", 0, 3, false);
		ArrayList<PlayerData> playerList = new ArrayList<>();
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		gParameter = new GameParameter(false, 42, playerList);
		gameController.initialize(gParameter);
		gameController.getTable().increaseTurn();
	}
	
	/**
	 * Tests the save and load operations of a gameState or Table Object
	 * @throws Exception
	 * 				{@link IOController#save(File)}
	 * 				{@link IOController#load(File)}
	 */
	@Test
	public void testSaveAndLoad() throws Exception {
		Table table = gameController.getTable();
		ioController.save(new File("highscore.ser"));
		gameController.setTable(null);
		ioController.load(new File("highscore.ser"));
		Table table2 = gameController.getTable();
		assertEquals(table.getTurn(), table2.getTurn());
	}
	
	/**
	 * Tests the save and load operations of a highscore
	 * @throws Exception
	 * 				{@link IOController#saveHighscore(File)}
	 * 				{@link IOController#loadHighscore(File)}
	 */
	@Test
	public void testSaveHighscoreAndLoadHighscore() throws Exception {
		ArrayList<PlayerData> players = gameController.getHighscoreController().getPlayerData();
		ioController.saveHighscore(new File("highscore.ser"));
		gameController.getHighscoreController().setPlayerData(null);
		ioController.loadHighscore(new File("highscore.ser"));
		ArrayList<PlayerData> players2 = gameController.getHighscoreController().getPlayerData();
		assertEquals(players, players2);
	}
}
