package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * @author sopr074
 *
 */
public class Table  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6385003600892196852L;
	private int remainingTurns;
	private int turn;
	private int lastId;
	
	private boolean networkGame;
	private boolean canHighscore;
	
	private ArrayList<Player> players;
	private Deque<Turn> pendingPlayerTurns;
	private Turn currentTurn;

	private Random random;

	private Stack<CombatContext> combatContextStack;

	private Stack<Card> drawPile;
	private Card[] openCards;
	private ArrayList<Card> cards;
	private ArrayList<BonusCard> bonusCards;
	
	/**
	 * Creates a new table and creates a Random with the given seed
	 * @param seed for the Random
	 */
	public Table(long seed) {
		this.openCards = new Card[3];
		this.players = new ArrayList<Player>();
		this.drawPile = new Stack<Card>();
		this.cards = new ArrayList<Card>();
		this.bonusCards = new ArrayList<BonusCard>();
		this.combatContextStack = new Stack<CombatContext>();
		this.random = new Random(seed);
		this.lastId = 1;
		this.canHighscore = true;
		this.remainingTurns = -1;
		
		this.currentTurn = null;
		this.pendingPlayerTurns = new LinkedList<Turn>();
	}

	/**
	 *  
	 */
	public BonusCard getBonusCardByID(int id) {
		for(BonusCard bonusCard : bonusCards)
			if(bonusCard.getID() == id)
				return bonusCard;
		
		throw new NullPointerException("No bonus card for the id: " + id);
	}

	/**
	 *  
	 */
	public Card getCardByID(int id) {
		for(Card card : cards)
			if(card.getID() == id)
				return card;
		
		throw new NullPointerException("No bonus card for the id: " + id);
	}

	/**
	 *  
	 */
	public Castle getCastleByID(int id) {
		for(Player player : players)
			for(Castle castle : player.getCastles())
				if(castle.getID() == id)
					return castle;
		
		throw new NullPointerException("No castle for the id: " + id); 
	}

	public CombatContext getCurrentCombatContext() {
		return this.combatContextStack.peek();
	}

	
	/**
	 * Returns an open card and sets the index to null so the card it taken
	 * @param drawPile the pile to get the open card from
	 * @return the card
	 * @throws IllegalArgumentException if draw pile is not an open pile
	 * @throws NullPointerException if the card does not exist
	 */
	public Card takeOpenCard(DrawPile drawPile) {
		int index = drawPile.getIndex();
		if(index == 0)
			throw new IllegalArgumentException("Draw pile is not an open pile");
		
		//map to array index
		index--;
		
		Card result = this.openCards[index];
		if(result == null)
			throw new NullPointerException("Card does not exist");
		
		this.openCards[index] = null;
		return result;
	}

	//ToDo: into controller
	/**
	 *  
	 */
	public void increaseTurn() {
		turn++;
		if(remainingTurns > 0)
			remainingTurns--;
	}
	
	public List<CountBonusCard> getCountBonusCards() {
		return getBonusCards().stream()
				.filter(c -> c.isCountBonusCard())
				.map(c -> (CountBonusCard) c)
				.collect(Collectors.toList());
	}
	
	public int nextId() {
		return this.lastId++;
	}
	
	public void addPlayerTurn(Player player, TurnStage turnStage) {
		this.pendingPlayerTurns.addLast(new Turn(player, turnStage));
	}
	
	public void addImmediatePlayerTurn(Player player, TurnStage turnStage) {
		this.pendingPlayerTurns.addFirst(new Turn(player, turnStage));
	}
	
	/**
	 * @return true if the big wave was drawn
	 */
	public boolean hasDrawnBigWave() {
		return remainingTurns >= 0;
	}

	/**
	 * @return the remainingTurns
	 */
	public int getRemainingTurns() {
		return remainingTurns;
	}

	/**
	 * @param remainingTurns the remainingTurns to set
	 */
	public void setRemainingTurns(int remainingTurns) {
		this.remainingTurns = remainingTurns;
	}

	/**
	 * @return the turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * @param turn the turn to set
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}

	/**
	 * @return the currentPlayer
	 */
	public Player getCurrentPlayer() {
		return currentTurn.getPlayer();
	}

	/**
	 * @return the random
	 */
	public Random getRandom() {
		return random;
	}

	/**
	 * @param random the random to set
	 */
	public void setRandom(Random random) {
		this.random = random;
	}

	/**
	 * @return the combatContextStack
	 */
	public Stack<CombatContext> getCombatContextStack() {
		return combatContextStack;
	}

	/**
	 * @param combatContextStack the combatContextStack to set
	 */
	public void setCombatContextStack(Stack<CombatContext> combatContextStack) {
		this.combatContextStack = combatContextStack;
	}

	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * @return the drawPile
	 */
	public Stack<Card> getDrawPile() {
		return drawPile;
	}

	/**
	 * @param drawPile the drawPile to set
	 */
	public void setDrawPile(Stack<Card> drawPile) {
		this.drawPile = drawPile;
	}

	/**
	 * @return the openCards
	 */
	public Card[] getOpenCards() {
		return openCards;
	}

	/**
	 * @param openCards the openCards to set
	 */
	public void setOpenCards(Card[] openCards) {
		this.openCards = openCards;
	}

	/**
	 * @return the cards
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}

	/**
	 * @return the bonusCards
	 */
	public ArrayList<BonusCard> getBonusCards() {
		return bonusCards;
	}

	/**
	 * @return the canHighscore
	 */
	public boolean isCanHighscore() {
		return canHighscore;
	}

	/**
	 * @param canHighscore the canHighscore to set
	 */
	public void setCanHighscore(boolean canHighscore) {
		this.canHighscore = canHighscore;
	}

	/**
	 * @return the networkGame
	 */
	public boolean isNetworkGame() {
		return networkGame;
	}

	/**
	 * @param networkGame the networkGame to set
	 */
	public void setNetworkGame(boolean networkGame) {
		this.networkGame = networkGame;
	}

	/**
	 * @return the pendingPlayerTurns
	 */
	public Deque<Turn> getPendingPlayerTurns() {
		return pendingPlayerTurns;
	}

	/**
	 * @param pendingPlayerTurns the pendingPlayerTurns to set
	 */
	public void setPendingPlayerTurns(Deque<Turn> pendingPlayerTurns) {
		this.pendingPlayerTurns = pendingPlayerTurns;
	}

	/**
	 * @return the currentTurn
	 */
	public Turn getCurrentTurn() {
		return currentTurn;
	}

	/**
	 * @param currentTurn the currentTurn to set
	 */
	public void setCurrentTurn(Turn currentTurn) {
		this.currentTurn = currentTurn;
	}

}
