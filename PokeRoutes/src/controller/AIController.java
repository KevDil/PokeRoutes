package controller;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.AIPlayerData;
import model.Card;
import model.CardForm;
import model.CardType;
import model.Castle;
import model.CastleCard;
import model.CombatCard;
import model.CountBonusCard;
import model.DrawPile;
import model.PlayedCardHolder;
import model.Player;
import model.Position;
import model.Rotation;
import model.Table;

public class AIController extends PlayerController {

	private GameController gameController;

	private Player currentPlayer;
	private CastleController castleController;
	private Table table;


	private Card[] openCards;

	private Position north;
	private Position south;
	private Position east;
	private Position west;

	private Rotation northRot;
	private Rotation southRot;
	private Rotation eastRot;
	private Rotation westRot;

	ArrayList<Position> positions;
	ArrayList<Rotation> rotations;

	/**
	 *  
	 *  
	 */
	public AIController(GameController gameController) {
		super(gameController);
	}

	/**
	 * Sets important variables
	 */
	public void initialize() {
		currentPlayer = gameController.getTable().getCurrentPlayer();
		castleController = gameController.getCastleController();

		table = gameController.getTable();

		openCards = gameController.getTable().getOpenCards();

		north = new Position(0, -1);
		south = new Position(0, 1);
		east = new Position(1, 0);
		west = new Position(-1, 0);

		northRot = Rotation.NORTH;
		southRot = Rotation.SOUTH;
		eastRot = Rotation.EAST;
		westRot = Rotation.WEST;

		rotations.add(northRot);
		rotations.add(southRot);
		rotations.add(eastRot);
		rotations.add(westRot);

		positions.add(north);
		positions.add(west);
		positions.add(south);
		positions.add(east);
	}

	public void update(long deltaMs) {

	}

	@Override
	public void onTurn() {
		
		if (table.getCurrentPlayer().getCastles().isEmpty()) newCastle();
		if (table.getCurrentPlayer().getCastles().size() == 1 
				&& table.getCurrentPlayer().getCastles().get(0).isCompleted()) newCastle();
		if (table.getCurrentPlayer().getCastles().size() == 2 
				&& table.getCurrentPlayer().getCastles().get(1).isCompleted()) newCastle();
		if (table.getCurrentPlayer().getCastles().size() == 3 
				&& table.getCurrentPlayer().getCastles().get(2).isCompleted()) newCastle();
		if (table.getCurrentPlayer().getCastles().size() == 4 
				&& table.getCurrentPlayer().getCastles().get(3).isCompleted()) newCastle();
		if (table.getCurrentPlayer().getCastles().size() == 5 
				&& table.getCurrentPlayer().getCastles().get(4).isCompleted()) newCastle();
		
		try {
			if (table.getCurrentPlayer().getPlayerData() instanceof AIPlayerData) {
				AIPlayerData aipd = (AIPlayerData) table.getCurrentPlayer().getPlayerData();
				switch (aipd.getLevel()) {
				case 1:
					doEasyTurn();
					break;
				case 2:
					doMediumTurn();
					break;
				case 3:
					doHardTurn();
				default:
					break;
				}
			}
		} catch (Exception e) {
			// TODO
		}

	}

	/**
	 * Does a Turn for an AI Player if no Castle exists yet
	 * 
	 */
	public void newCastle() {
		boolean success = false;
		boolean swapped = false;

		initialize();

		for (CastleCard card : getCastleCards(table.getCurrentPlayer().getCards())) {
			createNewCastle(card, northRot);
			success = true;
		}

		if (!success) {
			for (int i = 0; i < 3; i++) {
				if (openCards[i].isCastleCard()) {
					tradeCard(DrawPile.getPile(i), 0);
					swapped = true;
				}
			}
		}

		if (!success && !swapped) {
			tradeCard(DrawPile.STACK, 0);
		}

	}

	/**
	 * Executes an easy turn for an AI player
	 * 
	 * @throws Exception
	 */
	public void doEasyTurn() throws Exception {
		boolean added = false;

		initialize();

		for (CastleCard card : getCastleCards(table.getCurrentPlayer().getCards())) {
			if (!added) {
				added = canAddSomewhere(true, currentPlayer.getCastles().get(0), card);
			}
		}

		if (!added) {
			for (int i = 0; i < 3; i++) {
				if (table.getCurrentPlayer().getCards()[i].isCombatCard()) {
					tradeCard(DrawPile.getPile(swapAnyCard()), i);
				} else {
					tradeCard(DrawPile.getPile(swapAnyCard()), 0);
				}
			}
		}


		drawCard(DrawPile.STACK);
		drawCard(DrawPile.STACK);
		drawCard(DrawPile.STACK);
		endTurn();
	}

	/**
	 * Executes a medium turn for an AI player
	 * 
	 * @throws Exception
	 */
	public void doMediumTurn() throws Exception {
		boolean added = false;
		boolean attacked = false;

		Castle thisCastle = currentPlayer.getCastles().get(0);

		initialize();

		int max = -1;
		CastleCard toAdd = null;
		for (CastleCard card : getCastleCards(table.getCurrentPlayer().getCards())) {
			if (evaluateMediumTurn(thisCastle, card) > max) {
				toAdd = card;
				max = evaluateMediumTurn(thisCastle, card);
			}
		}

		if (toAdd != null) {
			added = canAddSomewhere(true, thisCastle, toAdd);
		}

		if (!added) {
			for (Player player : table.getPlayers()) {
				if (player == currentPlayer)
					continue;

				for (Castle castle : player.getCastles()) {
					for (PlayedCardHolder cardHolder : castle.getPlayedCards().values()) {
						if (castleController.canRemove(cardHolder)
								&& canAddSomewhere(false, thisCastle, cardHolder.getTopCard())
								&& hasBeatingCard(cardHolder, table.getCurrentPlayer().getCards())) {
							attack(cardHolder);
							attacked = true;
						}
					}
				}
			}
		}

		if (!added && !attacked) {
			tradeCard(DrawPile.getPile(swapAnyCard()), 0);
		}


		drawCard(DrawPile.STACK);
		drawCard(DrawPile.STACK);
		drawCard(DrawPile.STACK);
		endTurn();
	}

	/**
	 * Executes a hard turn for an AI player
	 * 
	 * @throws Exception
	 */
	public void doHardTurn() throws Exception {
		boolean added = false;
		boolean attacked = false;

		Castle thisCastle = currentPlayer.getCastles().get(0);

		initialize();

		int max = -1;
		CastleCard toAdd = null;
		for (CastleCard card : getCastleCards(table.getCurrentPlayer().getCards())) {
			if (evaluateHardTurn(thisCastle, card) > max) {
				toAdd = card;
				max = evaluateHardTurn(thisCastle, card);
			}
		}

		if (toAdd != null) {
			added = canAddSomewhere(true, thisCastle, toAdd);
		}

		if (!added) {
			for (Player player : table.getPlayers()) {
				if (player == currentPlayer)
					continue;
				for (Castle castle : player.getCastles()) {
					for (PlayedCardHolder cardHolder : castle.getPlayedCards().values()) {
						if (castleController.canRemove(cardHolder)
								&& canAddSomewhere(false, thisCastle, cardHolder.getTopCard())
								&& hasBeatingCard(cardHolder, table.getCurrentPlayer().getCards())) {
							attack(cardHolder);
							attacked = true;
						}
					}
				}
			}
		}

		if (!added && !attacked) {
			tradeCard(DrawPile.getPile(swapAnyCard()), 0);
		}


		drawCard(DrawPile.STACK);
		drawCard(DrawPile.STACK);
		drawCard(DrawPile.STACK);
		endTurn();
	}

	/**
	 * Checks if a given card can be added to a castle and adds it if specified
	 * 
	 * @param add
	 *            weather to add the card
	 * @param castle
	 *            the castle to add to
	 * @param card
	 *            the card to add
	 * @return weather the card can be added
	 * @throws Exception
	 */
	public boolean canAddSomewhere(boolean add, Castle castle, CastleCard card) throws Exception {
		for (PlayedCardHolder playedCardHolder : castle.getPlayedCards().values()) {
			for (Position position : positions) {
				for (Rotation rotation : rotations) {
					card.setRotation(rotation);
					if (castleController.fitsInCastle(castle, playedCardHolder.getTopCard(),
							playedCardHolder.getPosition().add(position))) {
						if (add) {
							try {
								addToExistingCastle(playedCardHolder, position.toDirection(), card, rotation);
							} catch (Exception e) {
								throw e;
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks if a cardArray beats a given card
	 * 
	 * @param card
	 *            the cardHolder to beat
	 * @param cards
	 *            the cards to check for a beating card
	 * @return true if the card array contains a card that beats the card
	 */
	public boolean hasBeatingCard(PlayedCardHolder card, Card[] cards) {
		List<CombatCard> combatCards = getCombatCards(cards);

		for (CombatCard combatCard : combatCards) {
			if (combatCard.getType().beats(card.getTopCard().getType())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Filters a Card array into a list that contains all its CasleCards
	 * 
	 * @param cards
	 *            the card array to look at
	 * @return list of cards that are castle cards
	 */
	public List<CastleCard> getCastleCards(Card[] cards) {
		return Arrays.stream(cards).filter(c -> c.isCastleCard()).map(c -> (CastleCard) c).collect(Collectors.toList());
	}

	/**
	 * Filters a Card array into a list that contains all its CombatCards
	 * 
	 * @param cards
	 *            the card array to look at
	 * @return list of cards that are CombatCards
	 */
	public List<CombatCard> getCombatCards(Card[] cards) {
		return Arrays.stream(cards).filter(c -> c.isCombatCard()).map(c -> (CombatCard) c).collect(Collectors.toList());
	}

	/**
	 * Searches for a card that fits to the AIs castle in the open cards
	 * 
	 * @return the index of the card that fits
	 * @throws Exception
	 */
	public int swapAnyCard() throws Exception {
		for (int i = 1; i <= 3; i++) {
			if (openCards[i].isCastleCard()) {
				CastleCard card = (CastleCard) openCards[i];
				for (Castle castle : currentPlayer.getCastles()) {
					for (PlayedCardHolder playedCardHolder : castle.getPlayedCards().values()) {
						for (Position position : positions) {
							for (Rotation rotation : rotations) {
								card.setRotation(rotation);
								if (castleController.fitsInCastle(castle, playedCardHolder.getTopCard(),
										playedCardHolder.getPosition().add(position))) {
									return i;
								}
							}
						}
					}
				}
			}
		}
		return 0;
	}

	/**
	 * Computes a value a medium KI turn has
	 * 
	 * @param castle
	 *            the castle to add to
	 * @param card
	 *            the card to add
	 * @return the value this turn has
	 * @throws Exception
	 */
	public int evaluateMediumTurn(Castle castle, CastleCard card) throws Exception {
		if (canAddSomewhere(false, castle, card)) {

			if (card.getCardForm() == CardForm.END) {
				return 5;
			}
			if (card.getCardForm() == CardForm.STRAIGHT) {
				return 4;
			}
			if (card.getCardForm() == CardForm.CURVE) {
				return 3;
			}
			if (card.getCardForm() == CardForm.T_CROSS) {
				return 2;
			}
			if (card.getCardForm() == CardForm.CROSS) {
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Computes a value a hard KI turn has
	 * 
	 * @param castle
	 *            the castle to add to
	 * @param card
	 *            the card to add
	 * @return the value this turn has
	 * @throws Exception
	 */
	public int evaluateHardTurn(Castle castle, CastleCard card) throws Exception {
		// int[] counts = new int[3];

		HashMap<CardType, Integer> counts = new HashMap<>();

		counts.put(CardType.SEAGULL, 0);
		counts.put(CardType.BUCKET, 0);
		counts.put(CardType.CRAB, 0);

		for (PlayedCardHolder playedCardHolder : castle.getPlayedCards().values()) {
			if (playedCardHolder.getTopCard().getType() == CardType.SEAGULL) {
				counts.put(CardType.SEAGULL, counts.get(CardType.SEAGULL) + 1);
			}
			if (playedCardHolder.getTopCard().getType() == CardType.BUCKET) {
				counts.put(CardType.BUCKET, counts.get(CardType.BUCKET) + 1);
			}
			if (playedCardHolder.getTopCard().getType() == CardType.CRAB) {
				counts.put(CardType.CRAB, counts.get(CardType.CRAB) + 1);
			}
		}

		int maxValue = Collections.max(counts.values());
		CardType type = null;

		int bonus = 0;

		for (Map.Entry<CardType, Integer> entry : counts.entrySet()) {
			if (entry.getValue() == maxValue) {
				type = entry.getKey();
			}
		}

		for (CountBonusCard bonusCard : table.getCountBonusCards()) {
			if (bonusCard.getType() == type) {
				if (canAddSomewhere(false, castle, card)) {
					if (card.getType() == type) {
						bonus = 3;
					}
				}
			}
		}

		if (bonus != 3) {
			counts.remove(type);

			for (Map.Entry<CardType, Integer> entry : counts.entrySet()) {
				if (entry.getValue() == maxValue) {
					type = entry.getKey();
				}
			}

			for (CountBonusCard bonusCard : table.getCountBonusCards()) {
				if (bonusCard.getType() == type) {
					if (canAddSomewhere(false, castle, card)) {
						if (card.getType() == type) {
							bonus = 2;
						}
					}
				}
			}
		}

		return evaluateMediumTurn(castle, card) + bonus;

		// List<BonusCard> cards = gameController.getTable().getBonusCards();
	}

	/**
	 * The AI defends itself
	 * 
	 * @return if the defend was successful
	 * @throws Exception
	 */
	@Override
	public void onDefend() {
		if (table.getCurrentPlayer().getPlayerData() instanceof AIPlayerData) {
			AIPlayerData aipd = (AIPlayerData) table.getCurrentPlayer().getPlayerData();
			if (aipd.getLevel() == 1) {
				forfeit();
			} else {
				try {
					reattackOrDefend();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * The AI adds a Card it won in combat
	 * 
	 * @throws Exception
	 */
	@Override
	public void onWonCombat() {
		try {
			canAddSomewhere(true, gameController.getActivePlayer().getCastles().get(0),
					gameController.getTable().getCombatContextStack().peek().getPlayedCardHolder().getTopCard());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
