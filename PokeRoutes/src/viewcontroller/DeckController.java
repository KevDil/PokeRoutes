package viewcontroller;

import java.util.List;

import controller.AUIdeckArea;
import controller.GameController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.BonusCard;
import model.Card;
import model.DrawPile;
import model.Player;
import model.Table;

public class DeckController extends ViewController implements AUIdeckArea {

	private ObservableList<PlayerTableEntry> playerTable;
	private EventHandler<ClickPileEvent> onClickPile;

	@FXML
	private ImageView imgViewDeck;

	@FXML
	private ImageView imgViewBigWave;

	@FXML
	private Pane openCard1;

	@FXML
	private Pane openCard2;

	@FXML
	private Pane openCard3;

	@FXML
	private HBox hboxFormBonus;

	@FXML
	private HBox hboxCountBonus;

	@FXML
	private TableView<PlayerTableEntry> tvPlayer;

	@FXML
	private TableColumn<PlayerTableEntry, Integer> tcPlayerOrder;

	@FXML
	private TableColumn<PlayerTableEntry, String> tcPlayerName;

	@FXML
	private TableColumn<PlayerTableEntry, Integer> tcPlayerPoints;


	@FXML
	void onDeckClick(MouseEvent event) {
		DrawPile drawPile = DrawPile.STACK;
		
		if(onClickPile != null)
			onClickPile.handle(new ClickPileEvent(drawPile));
	}
	
	@FXML
	void openCardClick(MouseEvent event) {
		DrawPile drawPile = DrawPile.STACK;
		if(event.getSource() == openCard1) {
			drawPile = DrawPile.OPEN_1;
		} else if(event.getSource() == openCard2) {
			drawPile = DrawPile.OPEN_2;
		} else if(event.getSource() == openCard3) {
			drawPile = DrawPile.OPEN_3;
		} else {
			//Should never happen
			throw new RuntimeException("Invalid card source");
		}
		
		if(onClickPile != null)
			onClickPile.handle(new ClickPileEvent(drawPile));
		
	}

	@FXML
	void onOnePeebleBonusClick(MouseEvent event) {

	}

	@FXML
	void onTwoPeebleBonusClick(MouseEvent event) {

	}
	
	@FXML
	void onCountBonusClick(MouseEvent event) {

	}

	public void initialize() {
		playerTable = FXCollections.observableArrayList();

		tvPlayer.setItems(playerTable);
		tcPlayerOrder.setCellValueFactory(new PropertyValueFactory<PlayerTableEntry, Integer>("playerOrder"));
		tcPlayerName.setCellValueFactory(new PropertyValueFactory<PlayerTableEntry, String>("playerName"));
		tcPlayerPoints.setCellValueFactory(new PropertyValueFactory<PlayerTableEntry, Integer>("playerScore"));
	}

	public void update() {
		// Deck
		ResourceController resourceController = masterViewController.getResourceController();
		Table table = gameController.getTable();

		imgViewDeck.setImage(resourceController.getBack());

		if (gameController.getTable().hasDrawnBigWave())
			imgViewBigWave.setImage(resourceController.getBigWave());
		else
			imgViewBigWave.setImage(null);

		// Open
		Card[] openCards = table.getOpenCards();
		openCard1.getChildren().clear();
		if (openCards[0] != null)
			openCard1.getChildren().add(masterViewController.createCardView(openCards[0], true, false).getPane());

		openCard2.getChildren().clear();
		if (openCards[1] != null)
			openCard2.getChildren().add(masterViewController.createCardView(openCards[1], true, false).getPane());

		openCard3.getChildren().clear();
		if (openCards[2] != null)
			openCard3.getChildren().add(masterViewController.createCardView(openCards[2], true, false).getPane());

		// Bonus
		List<BonusCard> bonusCards = table.getBonusCards();
		hboxCountBonus.getChildren().clear();
		hboxFormBonus.getChildren().clear();

		for (BonusCard bonusCard : bonusCards) {
			if (bonusCard.isFormBonusCard()) {
				ViewContext<CardViewController> cardViewController = masterViewController.createCardView(bonusCard,
						true);
				hboxFormBonus.getChildren().add(cardViewController.getPane());
			} else if (bonusCard.isCountBonusCard()) {
				ViewContext<CardViewController> cardViewController = masterViewController.createCardView(bonusCard,
						true);
				hboxCountBonus.getChildren().add(cardViewController.getPane());
			}
			// Ignore height + size
		}
		
		//Player
		playerTable.clear();
		int i = 1;
		for(Player player : table.getPlayers())
			playerTable.add(new PlayerTableEntry(gameController ,i++, player));

	}
	


	public EventHandler<ClickPileEvent> getOnClickPile() {
		return onClickPile;
	}

	public void setOnClickPile(EventHandler<ClickPileEvent> onClickPile) {
		this.onClickPile = onClickPile;
	}

	public static class PlayerTableEntry {
		private final Integer playerOrder;
		private final String playerName;
		private final Integer playerScore;

		private PlayerTableEntry(GameController gameController, int order, Player player) {
			this.playerOrder = order;
			this.playerName = player.getPlayerData().getName();
			this.playerScore = gameController.getPointsController().getPlayerPoints(player);
		}

		public Integer getPlayerOrder() {
			return playerOrder;
		}

		public String getPlayerName() {
			return playerName;
		}

		public int getPlayerScore() {
			return playerScore;
		}
	}
}
