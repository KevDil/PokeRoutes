package controller;

import java.io.File;
import java.util.List;

import model.BonusCard;
import model.Card;
import model.Castle;
import model.CastleCard;
import model.CombatCard;
import model.CombatContext;
import model.Direction;
import model.DrawPile;
import model.PlayedCardHolder;
import model.Player;
import model.Rotation;
import model.Table;
import model.TurnStage;

public class PlayerController {

	private GameController gameController;

	public PlayerController(GameController gameController) {
		this.gameController = gameController;
	}

	/**
	 * Create a new Castle and set the card as first castle card.
	 * 
	 * @param card
	 *            First card of the Castle
	 * @param rotation
	 *            Indicates the rotation of the card
	 */
	public void createNewCastle(CastleCard card, Rotation rotation) {
		card.setRotation(rotation);
		int id = gameController.getTable().nextId();

		Table table = gameController.getTable();

		Castle castle = new Castle(id, card);
		table.getCurrentPlayer().getCastles().add(castle);
		castle.setPlayer(table.getCurrentPlayer());
		
		gameController.updateMainUI();
	}

	/**
	 * Adds a card to an existing castle
	 * 
	 * @see CastleController#addToCastle(PlayedCardHolder, Direction,
	 *      CastleCard, Rotation)
	 * @param target
	 * @param direction
	 * @param newCard
	 * @param rotation
	 * @throws Exception
	 */
	public void addToExistingCastle(PlayedCardHolder target, Direction direction, CastleCard newCard, Rotation rotation)
			throws Exception {
		gameController.getCastleController().addToCastle(target, direction, newCard, rotation);
		
		if(target.getCastle().isCompleted())
			claimBonusCard(target.getCastle());
		
		gameController.updateMainUI();
	}

	public Card getCardFromPile(DrawPile pile, Card replaceWith) {
		Table table = gameController.getTable();
		Card card = null;
		int index = 2;

		switch (pile) {
		case OPEN_1:
			index--;
		case OPEN_2:
			index--;
		case OPEN_3:
			card = table.getOpenCards()[index];
			table.getOpenCards()[index] = replaceWith;
			if (replaceWith == null)
				gameController.refillPile();
			break;
		case STACK:
			card = table.getDrawPile().pop();
			if (replaceWith != null)
				table.getDrawPile().push(replaceWith);

			if (card.isBigWave()) {
				table.setRemainingTurns(table.getPlayers().size());
			}
			break;
		default:
			break;
		}

		return card;
	}

	/**
	 * Trade the hand card from the current player with a card from the pile.
	 * 
	 * @param pile
	 *            Indicates the draw pile or the open cards.
	 * @param cardIndex
	 *            Is the index of the hand card that will be trade.
	 */
	public void tradeCard(DrawPile pile, int cardIndex) {
		Table table = gameController.getTable();
		Card[] handCards = table.getCurrentPlayer().getCards();

		Card tradeCard = handCards[cardIndex];
		Card pileCard = getCardFromPile(pile, tradeCard);

		if (!pileCard.isBigWave())
			handCards[cardIndex] = pileCard;
		
		gameController.updateMainUI();
	}

	/**
	 * Draw a card from the draw pile or the open cards.
	 * 
	 * @param pile
	 *            Indicates the draw pile or the open cards.
	 */
	public void drawCard(DrawPile pile) {
		Table table = gameController.getTable();
		Card[] handCards = table.getCurrentPlayer().getCards();

		if (handCards[0] != null && handCards[1] != null && handCards[2] != null)
			return;

		if (table.hasDrawnBigWave())
			return;

		Card pileCard = getCardFromPile(pile, null);
		if (!pileCard.isBigWave())
			gameController.addCardToHand(table.getCurrentPlayer(), pileCard);
		
		gameController.updateMainUI();
	}

	/**
	 * Attack again or defend the attacker card.
	 * 
	 * @return True if the action was successful
	 * @throws Exception
	 */
	public boolean reattackOrDefend() throws Exception {
		CombatContext currentContext = gameController.getTable().getCombatContextStack().peek();

		if (currentContext.isFinished())
			return false;

		CombatCard lastCombatCard = currentContext.getLastCombatCard();
		Player player = currentContext.getCurrentPlayer();

		for (Card handCard : player.getCards()) {
			if (handCard == null || !handCard.isCombatCard())
				continue;

			CombatCard combatCard = (CombatCard) handCard;

			if (!combatCard.getType().beats(lastCombatCard.getType()))
				continue;
			
			removeHandCard(combatCard);
			currentContext.addCombatCard(combatCard);
			gameController.updateBattleAreaUI();
			gameController.getCurrentPlayerController().onDefend();
			return true;
		}

		return false;
	}

	/**
	 * A Player forfeits a combat
	 * 
	 * @throws Exception
	 */
	public void forfeit() {
		CombatContext currentContext = gameController.getTable().getCombatContextStack().peek();

		if (currentContext.isFinished())
			throw new RuntimeException("kann einen beendeten combat nicht aufgeben");

		currentContext.setFinished();
		TurnStage nextStage = TurnStage.REDRAW;
		if (currentContext.getWinner() == currentContext.getAttacker()) {
			nextStage = TurnStage.BUILD_ONLY;
			
			PlayedCardHolder card = currentContext.getPlayedCardHolder();
			if(!gameController.getCastleController().canRemove(card))
				throw new RuntimeException("Karte sollte entfernbar sein");
			
			
			Castle castle = card.getCastle();
			castle.removeCard(card);
			gameController.getCastleController().isComplete(castle);
			if(castle.isCompleted())
				claimBonusCard(castle);
			
			onWonCombat();
		}
		
		if(!currentContext.getDefender().hasFullHandCards()) {
			gameController.getTable().addImmediatePlayerTurn(currentContext.getDefender(), TurnStage.REDRAW);
		}
		

		gameController
			.getTable()
			.getCurrentTurn()
			.setTurnStage(nextStage);
		
		gameController.updateBattleAreaUI();
		gameController.updateMainUI();
		
		gameController.loadMainView();
	}

	/**
	 * Create a new combat context and attack the castle card.
	 * 
	 * @param attackedCard
	 *            Castle card that will be attacked.
	 * @return True if the attack was successful.
	 * @throws Exception
	 */
	public boolean attack(PlayedCardHolder attackedCard)  {
		Table table = gameController.getTable();
		Player attacker = table.getCurrentPlayer();
		Castle castle = attackedCard.getCastle();

		if (castle.getPlayer() == attacker) {
			throw new RuntimeException("Eigene Burg nicht angreifbar");
		}

		for (Card handCard : attacker.getCards()) {
			if (handCard == null || !handCard.isCombatCard())
				continue;

			CombatCard combatCard = (CombatCard) handCard;

			if (!combatCard.getType().beats(attackedCard.getTopCard().getType()))
				continue;
			
			removeHandCard(combatCard);
			CombatContext combatContext = new CombatContext(attackedCard, attacker);
			combatContext.addCombatCard(combatCard);
			table.getCombatContextStack().push(combatContext);
			
			gameController.updateBattleAreaUI();
			gameController.getCurrentPlayerController().onDefend();
			return true;
		}

		return false;

	}

	/**
	 * combined the player with his won bonus cards.
	 * 
	 * @return the count of won bonus cards
	 * @throws Exception
	 */
	public int claimBonusCard(Castle castle)  {
		if (!castle.isCompleted())
			return 0;// TODO: Exception

		List<BonusCard> list = gameController.getCastleController().listAvailableBonusCards(castle);

		for (BonusCard card : list) {
			if(card.getPlayer() != null)
				card.getPlayer().removeBonusCard(card);
			
			card.setPlayer(castle.getPlayer());
		}

		return list.size();
	}

	/**
	 * Ends the current players turn
	 * 
	 * @throws Exception
	 *             {@link GameController#nextTurn()}
	 */
	public void endTurn() {
		gameController.nextTurn();
		
		gameController.updateMainUI();
	}

	/**
	 * Undoes an already played turn Can not be used in a network game
	 * 
	 * @throws Exception
	 *             {@link IOController#load(File)}
	 */
	public void undoTurn() throws Exception {
		Table table = gameController.getTable();

		if (!(table.isNetworkGame())) {
			gameController.getIOController().load(new File("Game_turn_" + (table.getTurn() - 1)));
		} else {
			throw new Exception("Im Netzwerkspiel nicht möglich");
		}
		
		gameController.updateMainUI();

	}
	
	public void removeHandCard(Card card) {
		Card[] cards = gameController.getActivePlayer().getCards();
		for(int i = 0; i < cards.length; i++) {
			if(cards[i] == card) {
				cards[i] = null;
				gameController.updateMainUI();
				return;
			}
		}
		
		throw new RuntimeException("Hand card does not exist");
	}
	
	public void finishTurnStage() {
		Table table = gameController.getTable();
		
		switch(table.getCurrentTurn().getTurnStage()) {
		case ACTION:
		case BUILD_ONLY:
			table
				.getCurrentTurn()
				.setTurnStage(TurnStage.REDRAW);
			 gameController.updateMainUI();
			 break;
		case REDRAW:
			if(table.getCurrentPlayer().hasFullHandCards())
				endTurn();
			break;
		}
	}

	public void update(long deltaMs) {

	}

	public void onCastleComplete(Castle castle) {

	}

	public void onTurn()  {

	}

	public void showTip() {

	}

	public void onDefend()  {
		gameController.loadCombatView();

	}

	public void onWonCombat() {
	}

}
