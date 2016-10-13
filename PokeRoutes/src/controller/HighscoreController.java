package controller;

import java.util.ArrayList;
import java.util.Collections;

import model.PlayerData;

public class HighscoreController {

	private ArrayList <PlayerData> playerData;
	@SuppressWarnings("unused")
	private GameController gameController;

	/**
	 *  
	 */
	public HighscoreController(GameController gameController) {
		this.gameController = gameController;
		playerData = new ArrayList<PlayerData>();
	}

	/**
	 * Adds a highscore for a Player
	 * @param score the playerData to be entered in the highscore
	 */
	public void addHighscore(PlayerData score) {
		playerData.add(score);
		Collections.sort(playerData);
	}

	/**
	 * Computes the highsore entries with the highest points
	 * @param count how many entries should be displayed
	 * @return a list of playerData with the highest scores
	 */
	public ArrayList<PlayerData> showTop(int count) {
		ArrayList<PlayerData> result=new ArrayList<>();
		if (!(count > playerData.size())) {
			for(int i=0;i<count;i++){
				result.add(playerData.get(i));
			}
		}
		else {
			for(int i=0;i<playerData.size();i++){
				result.add(playerData.get(i));
			}
		}
		return result;
	}
	
	/**
	 * resets all highscore entries
	 */
	public void resetScore() {
		playerData = null;
	}

	/**
	 * @return the playerData
	 */
	public ArrayList<PlayerData> getPlayerData() {
		return playerData;
	}

	/**
	 * @param playerData the playerData to set
	 */
	public void setPlayerData(ArrayList<PlayerData> playerData) {
		this.playerData = playerData;
	}

}
