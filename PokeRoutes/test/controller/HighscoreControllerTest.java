package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import model.PlayerData;

public class HighscoreControllerTest {

	private GameController gameCon = new GameController();
	private HighscoreController highCon = gameCon.getHighscoreController();
	private PlayerData pd = new PlayerData("Hans", 10, 3, false);
	private PlayerData pd2 = new PlayerData("Peter", 15, 2, false);
	private PlayerData pd3 = new PlayerData("Gustav", 5, 1, true);
	private PlayerData pd4 = new PlayerData("Jens", 20, 4, true);
	private PlayerData pd5 = new PlayerData("Murat", 20, 4, true);

	
	/**
	 * Tests if a player is correctly added to the highscore.
	 * 				{@link HighscoreController#addHighscore(PlayerData)}
	 */
	@Test
	public void addHighscoreTest() {
		highCon.addHighscore(pd);
		assertEquals(pd, highCon.getPlayerData().get(0));
		highCon.addHighscore(pd2);
		assertEquals(pd2, highCon.getPlayerData().get(0));
		assertEquals(pd, highCon.getPlayerData().get(1));
		highCon.addHighscore(pd3);
		assertEquals(pd3, highCon.getPlayerData().get(2));

	}
	
	/**
	 * Tests if the first entries of the highscore are displayed.
	 * 				{@link HighscoreController#showTop(Integer)}
	 */
	@Test
	public void showTopTest() {
		highCon.addHighscore(pd);
		highCon.addHighscore(pd2);
		highCon.addHighscore(pd3);
		highCon.addHighscore(pd4);
		highCon.addHighscore(pd5);
		assertNotSame (highCon.getPlayerData(), highCon.showTop(4));
		assertEquals (highCon.getPlayerData(), highCon.showTop(5));
	}
	
	/**
	 * Tests if the highscore is cleared, so that there are no more entries.
	 * 				{@link HighscoreController#resetScore(PlayerData)}
	 */
	@Test
	public void resetScoreTest() {
		highCon.addHighscore(pd);
		highCon.addHighscore(pd2);
		highCon.addHighscore(pd3);
		highCon.addHighscore(pd4);
		highCon.addHighscore(pd5);
		assertNotNull (highCon.getPlayerData());
		highCon.resetScore();
		assertNull(highCon.getPlayerData());
	}
}
