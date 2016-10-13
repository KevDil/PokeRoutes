package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.BonusCard;
import model.Castle;
import model.CombatContext;
import model.HeightBonusCard;
import model.Player;
import model.SizeBonusCard;

public class PointsController {

	private GameController gameController;
	
	public PointsController(GameController gameController) {
		this.gameController = gameController;
	}

	/**
	 * Calculates the points a castle is worth and adds them to its owner
	 * Also adds points for bonus cards if applicable
	 * @param castle the castle which points should be added to the player
	 * @throws Exception
	 */
	public void addPoints(Castle castle) {
		castle.getPlayer().addPoints(castle.getPoints());
	}

	/**
	 * Calculates the points a player gets from a combat
	 * @param combatContext the combat which points should be added to the player
	 */
	public void addPoints(CombatContext combatContext) {
		combatContext.getWinner().addPoints(combatContext.getPoints());
	}

	/**
	 * Calculates the points a BonusCard is worth given a castle
	 * @param bonus the BonusCard which points to get
	 * @param castle the castle which qualifies for the bonus card
	 * @return the points this BonusCard is worth
	 */
	private int calculateBonusCardPoints(BonusCard bonus) {
		if (bonus instanceof HeightBonusCard) {
			switch (((HeightBonusCard)bonus).getCurrentHighest()) {
			case 1: case 2: 
				return 0;
			
			case 3: 
				return 3;
				
			case 4: 
				return 4;
			
			case 5: 
				return 5;

			default: 
				return 7;
			}
		}
		if (bonus instanceof SizeBonusCard) {
			switch (((SizeBonusCard)bonus).getCurrentBiggest()){
			case 1: case 2: case 3: case 4: case 5: case 6:
				return 0;
				
			case 7:
				return 6;
				
			case 8:
				return 7;
				
			case 9:
				return 9;
				
			default:
				return 11;
			}
		}
		return bonus.getValue();
	}
	
	public void calculateBonusPoints() {
		gameController.getTable().getBonusCards()
			.stream()
			.filter(x -> x.getPlayer() != null)
			.forEach(x -> x.getPlayer().addPoints(calculateBonusCardPoints(x)));
			
	}
	
	/**
	 * Calculates the list if players with the most points
	 * @return the list of winners
	 */
	public List<Player> getWinners() {

		ArrayList<Player> players = gameController.getTable().getPlayers();
		Integer maxPoints = players
				.stream()
				.map(x -> x.getPoints())
				.max(Integer::max).get();
		
		return players.stream()
				.filter(x -> x.getPoints() == maxPoints.intValue())
				.collect(Collectors.toList());	
	}
	
	public int getPlayerPoints(Player player) {
		int points = player.getPoints();
		
		for (BonusCard bc : player.getBonusCards()) {
			points += bc.getValue();
		}
		
		return points;
		
	}

}
