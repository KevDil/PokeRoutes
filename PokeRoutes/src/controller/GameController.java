package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Stack;

import model.BigWave;
import model.Card;
import model.CardForm;
import model.CardType;
import model.CastleCard;
import model.CastleForm;
import model.CombatCard;
import model.CombatContext;
import model.CountBonusCard;
import model.FormBonusCard;
import model.GameParameter;
import model.HeightBonusCard;
import model.Player;
import model.PlayerData;
import model.SizeBonusCard;
import model.Table;
import model.Turn;
import model.TurnStage;

public class GameController {

	private GameParameter gameParameter;
	private Table table;
	private IOController iOController;
	private HighscoreController highscoreController;
	private HistoryController historyController;
	private CastleController castleController;
	private PlayerController playerController;
	private AIController aIController;
	private NetworkController networkController;
	private PointsController pointsController;
	
	private AUIMain mainUI;
	private AUIBattleArea battleAreaUI;
	private AUIView auiView;
	
	private int id = 1;
	
	public GameController() {
		iOController = new IOController(this);
		highscoreController = new HighscoreController(this);
		historyController = new HistoryController(this);
		castleController = new CastleController(this);
		playerController = new PlayerController(this);
		aIController = new AIController(this);
		networkController = new NetworkController(this);
		pointsController = new PointsController(this);
		gameParameter = new GameParameter();
	}
	
	private void nextPlayerTurnQueue() {
		Turn last = table.getCurrentTurn();
		table.setCurrentTurn(table.getPendingPlayerTurns().pop());
		if(last != null)
			table.addPlayerTurn(last.getPlayer(), TurnStage.ACTION);
		
		table.setTurn(table.getTurn() + 1);
	}

	/**
	 *  
	 */
	public void initialize(GameParameter gameParameter) {
		this.id = 1;
		this.table = new Table(gameParameter.getSeed());
		
		this.gameParameter = gameParameter;
		this.initializePlayer();
		this.initializeDeck();
		this.initializeBonusCards();
		
		for(Player player : this.table.getPlayers()) {
			refillHandCards(player);
		}
		refillPile();
		
		this.updateMainUI();
		
		/*try {
			iOController.loadHighscore(new File("highscore.ser"));
		} catch (Exception e) {
			
		}*/
	}

	/**
	 *  Initialize the draw pile with 111 cards and shuffle the big wave into the last 10 cards.
	 */
	private void initializeDeck() {
		ArrayList<Card> initCards = table.getCards();
		BigWave bigWave = new BigWave(table.nextId());
		
		//CURVE
		for(int i = 1; i <= 12; i++){
			if(i < 12){
				initCards.add(new CastleCard(table.nextId(), CardType.SEAGULL, CardForm.CURVE));
			}
			initCards.add(new CastleCard(table.nextId(), CardType.BUCKET, CardForm.CURVE));
			initCards.add(new CastleCard(table.nextId(), CardType.CRAB, CardForm.CURVE));
		}
		
		//STRAIGHT
		for (int i = 1; i <= 8; i++) {
			if (i < 7) {
				initCards.add(new CastleCard(table.nextId(), CardType.SEAGULL, CardForm.STRAIGHT));
			}
			initCards.add(new CastleCard(table.nextId(), CardType.BUCKET, CardForm.STRAIGHT));
			initCards.add(new CastleCard(table.nextId(), CardType.CRAB, CardForm.STRAIGHT));
		}

		// STRAIGHT
		/*for (int i = 1; i <= 8; i++) {
			if (i < 7) {
				initCards.add(new CastleCard(table.nextId(), CardType.BUCKET, CardForm.STRAIGHT));
				initCards.add(new CastleCard(table.nextId(), CardType.CRAB, CardForm.STRAIGHT));
			}
			initCards.add(new CastleCard(table.nextId(), CardType.SEAGULL, CardForm.STRAIGHT));
		}*/

		// END
		for (int i = 1; i <= 8; i++) {
			if (i < 6) {
				initCards.add(new CastleCard(table.nextId(), CardType.BUCKET, CardForm.END));
				initCards.add(new CastleCard(table.nextId(), CardType.CRAB, CardForm.END));
			}
			initCards.add(new CastleCard(table.nextId(), CardType.SEAGULL, CardForm.END));
		}

		// CROSS and T_CROSS
		for (int i = 1; i <= 2; i++) {
			initCards.add(new CastleCard(table.nextId(), CardType.BUCKET, CardForm.T_CROSS));
			initCards.add(new CastleCard(table.nextId(), CardType.CRAB, CardForm.T_CROSS));

			initCards.add(new CastleCard(table.nextId(), CardType.BUCKET, CardForm.CROSS));
			initCards.add(new CastleCard(table.nextId(), CardType.CRAB, CardForm.CROSS));
		}

		//COMBAT CARDS
		for (int i = 1; i <= 10; i++) {
			initCards.add(new CombatCard(table.nextId(), CardType.SEAGULL));
			initCards.add(new CombatCard(table.nextId(), CardType.BUCKET));
			initCards.add(new CombatCard(table.nextId(), CardType.CRAB));
		}
		
		shuffleDeck(bigWave);		
	}
	
	/**
	 *  Initialize the player list with new players from the gameParameter.
	 */
	private void initializePlayer() {
		ArrayList<PlayerData> playerDatas = gameParameter.getPlayerList();
		
		for(PlayerData pd : playerDatas){
			Player player = new Player(pd);
			
			table.getPlayers().add(player);
//			if (player.isAI()) {
//				table.setNetworkGame(true);//TODO
//			}
			
			table.addPlayerTurn(player, TurnStage.ACTION);
		}
		
		this.nextPlayerTurnQueue();
	}

	/**
	 *  Initialize the bonus card list with form, size and height bonus cards depending on number of players.
	 */
	private void initializeBonusCards() {
		int numOfPlay = table.getPlayers().size(),
				onePebForm = 1,
				twoPebForm = 1,
				countForm = 1;
		
		ArrayList<FormBonusCard> onePebbleForms = new ArrayList<>();
		onePebbleForms.add(new FormBonusCard(table.nextId(), CastleForm.KEEP, 2));
		onePebbleForms.add(new FormBonusCard(table.nextId(), CastleForm.AXE, 3));
		onePebbleForms.add(new FormBonusCard(table.nextId(), CastleForm.WORM, 4));
		onePebbleForms.add(new FormBonusCard(table.nextId(), CastleForm.BARRACK, 5));
		onePebbleForms.add(new FormBonusCard(table.nextId(), CastleForm.STAIRWAY, 5));
		
		ArrayList<FormBonusCard> twoPebbleForms = new ArrayList<>();
		twoPebbleForms.add(new FormBonusCard(table.nextId(), CastleForm.PEACOCK, 6));
		twoPebbleForms.add(new FormBonusCard(table.nextId(), CastleForm.CACTUS, 6));
		twoPebbleForms.add(new FormBonusCard(table.nextId(), CastleForm.MONASTERY, 6));
		twoPebbleForms.add(new FormBonusCard(table.nextId(), CastleForm.GOLDFISH, 6));
		
		ArrayList<CountBonusCard> countBonusCards = new ArrayList<>();
		for(int i = 3; i <= 5; i++){
			countBonusCards.add(new CountBonusCard(table.nextId(), CardType.SEAGULL, i, i - 2));
			countBonusCards.add(new CountBonusCard(table.nextId(), CardType.BUCKET, i, i - 2));
			countBonusCards.add(new CountBonusCard(table.nextId(), CardType.CRAB, i, i - 2));
		}
		
		SizeBonusCard sizeBonusCard = new SizeBonusCard(id++);
		sizeBonusCard.setCurrentBiggest(6);
		table.getBonusCards().add(sizeBonusCard);
		
		HeightBonusCard heightBonusCard = new HeightBonusCard(id++);
		heightBonusCard.setCurrentHighest(2);
		table.getBonusCards().add(heightBonusCard);
		
		Collections.shuffle(onePebbleForms, table.getRandom());
		Collections.shuffle(twoPebbleForms, table.getRandom());
		Collections.shuffle(countBonusCards, table.getRandom());
		
		
		if(numOfPlay > 4) {
			onePebForm = 2;
			twoPebForm = 2;
		}
		
		if(numOfPlay == 3 || numOfPlay == 5) {
			countForm = 2;
		} else if(numOfPlay == 4 || numOfPlay == 6) {
			countForm = 3;
		} else if(numOfPlay == 7) {
			countForm = 4;
		}
		
		table.getBonusCards().addAll(onePebbleForms.subList(0, onePebForm));
		table.getBonusCards().addAll(twoPebbleForms.subList(0, twoPebForm));
		table.getBonusCards().addAll(countBonusCards.subList(0, countForm));
	}
	
	/**
	 * Exchange the current player with the next player from the queue.
	 * @throws Exception 
	 */
	public boolean nextTurn() {
		if (table.getRemainingTurns() != 0) {
			this.nextPlayerTurnQueue();
			
			try {
				iOController.save(new File("Game_turn_" + table.getTurn()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.updateMainUI();
			return true;
		} else {
			try {
				endGame();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}
	
	/**
	 * adds highscore entries and saves them
	 * shows winners
	 * @return the winners
	 * @throws Exception
	 */
	public void endGame() throws Exception {
		if (table.isCanHighscore()) {
			for (Player player : table.getPlayers()) {
				player.getPlayerData().setScore(pointsController.getPlayerPoints(player));
				
				
				if (!player.isAI()) {
					highscoreController.addHighscore(player.getPlayerData());
				}
			}
		}
		iOController.saveHighscore(new File("highscore.ser"));
		this.updateMainUI();
		
		this.loadEndGameView();
	}

	/**
	 *  
	 */
	public void update(long deltaMs) {

	}

	/**
	 *  Refill the open card pile with cards from draw pile, if they are available.
	 */
	public void refillPile() {
		for(int i = 0; i < table.getOpenCards().length; i++) {
			if(table.getOpenCards()[i] == null && !table.getDrawPile().isEmpty()) {
				table.getOpenCards()[i] = table.getDrawPile().pop();
				if (table.getOpenCards()[i].isBigWave()) {
					this.getTable().setRemainingTurns(this.getTable().getPlayers().size());					
				}
			}
		}
		this.updateMainUI();
	}
	
	/**
	 * Refill the empty hand cards with cards from draw pile.
	 */
	public void refillHandCards(Player player) {
		for(int i = 0; i < player.getCards().length; i++) {
			if(player.getCards()[i] == null && !table.getDrawPile().isEmpty()) {
				player.getCards()[i] = table.getDrawPile().pop();
			}
		}
		this.updateMainUI();
	}

	/**
	 *  Refill the hand cards with cards from draw pile.
	 */
	public void addCardToHand(Player player, Card card) {
		Card[] handCards = player.getCards();
		for(int i = 0; i < handCards.length; i++) {
			if(handCards[i] == null) {
				handCards[i] = card;
				break;
			}
		}
		this.updateMainUI();
	}

	/**
	 *  An private method which shuffle the  
	 */
	private void shuffleDeck(BigWave bigWave) {
		Collections.shuffle(table.getCards(), table.getRandom());
		
		int n = table.getRandom().nextInt(Math.min(10, table.getCards().size()));
		
		for(int i = 0; i < n; i++) {
			table.getDrawPile().push(table.getCards().get(i));
		}
		table.getDrawPile().push(bigWave);
		
		for(int i = n; i < table.getCards().size(); i++) {
			table.getDrawPile().push(table.getCards().get(i));
		}
	}
	
	public Player getActivePlayer() {
		Stack<CombatContext> combatContexts = table.getCombatContextStack();
		if (!combatContexts.isEmpty()) {
			if (!combatContexts.peek().isFinished()) {
				return combatContexts.peek().getCurrentPlayer();
			}
		}
		return table.getCurrentPlayer();
	}

	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public IOController getIOController() {
		return iOController;
	}
	public HighscoreController getHighscoreController() {
		return highscoreController;
	}
	public HistoryController getHistoryController() {
		return historyController;
	}
	public CastleController getCastleController() {
		return castleController;
	}
	public PlayerController getPlayerController() {
		return playerController;
	}
	public PlayerController getCurrentPlayerController() {
		if (getActivePlayer().isAI()) {
			return aIController;
		}
		return playerController;
	}
	public AIController getAIController() {
		return aIController;
	}
	public NetworkController getNetworkController() {
		return networkController;
	}
	public PointsController getPointsController() {
		return pointsController;
	}
	public GameParameter getGameParameter() {
		return gameParameter;
	}
	public void setGameParameter(GameParameter gameParameter) {
		this.gameParameter = gameParameter;
	}
	
	public void updateMainUI(){
		if(mainUI != null)
			mainUI.update();
	}
	
	public void updateBattleAreaUI(){
		if(battleAreaUI != null)
			battleAreaUI.update();
	}
	
	public void loadMainView() {
		if(auiView != null)
			auiView.loadMainView();
	}
	
	public void loadCombatView() {
		if(auiView != null)
			auiView.loadCombatView();
	}
	
	public void loadEndGameView() {
		if(auiView != null)
			auiView.loadEndGameView();
	}

	/**
	 * @return the mainUI
	 */
	public AUIMain getMainUI() {
		return mainUI;
	}

	/**
	 * @param mainUI the mainUI to set
	 */
	public void setMainUI(AUIMain mainUI) {
		this.mainUI = mainUI;
	}

	/**
	 * @return the battleAreaUI
	 */
	public AUIBattleArea getBattleAreaUI() {
		return battleAreaUI;
	}

	/**
	 * @param battleAreaUI the battleAreaUI to set
	 */
	public void setBattleAreaUI(AUIBattleArea battleAreaUI) {
		this.battleAreaUI = battleAreaUI;
	}

	/**
	 * @return the auiView
	 */
	public AUIView getAuiView() {
		return auiView;
	}

	/**
	 * @param auiView the auiView to set
	 */
	public void setAuiView(AUIView auiView) {
		this.auiView = auiView;
	}
}
