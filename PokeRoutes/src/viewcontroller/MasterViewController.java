package viewcontroller;

import java.io.IOException;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import controller.AUIView;
import controller.GameController;
import controller.PlayerController;
import javafx.application.HostServices;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.BonusCard;
import model.Card;
import model.CardForm;
import model.CardType;
import model.Castle;
import model.GameParameter;
import model.PlayedCardHolder;
import model.Player;
import model.PlayerData;
import model.Position;
import model.Rotation;
import model.Table;
import model.CastleCard;
import model.Direction;

public class MasterViewController implements AUIView {
	private Stage stage;
	private HostServices hostServices;
	
	private ViewContext<MenuController> menuView;
	private ViewContext<MainscreenController> mainscreenView;
	private ViewContext<ManagePlayerController> managePlayerView;
	private ViewContext<AboutController> aboutView;
	private ViewContext<HighscoreController> highscoreView;
	private ViewContext<AvatarController> avatarView;
	private ViewContext<DummyCastleController> dummyCastleView;
	private ViewContext<CombatPlayerSelectionController> combatPlayerSelectionView;
	private ViewContext<CombatStageController> combatStageView;
	private String css;
	
	private GameController gameController;
	private ResourceController resourceController;
	
	private String menuMusicPath = "resources/sound/menu.mp3";     
	private String battleMusicPath = "resources/sound/battle.mp3";
	private String mainMusicPath = "resources/sound/main.mp3";

	private Media menuMusic = new Media(new File(menuMusicPath).toURI().toString());
	private Media battleMusic = new Media(new File(battleMusicPath).toURI().toString());
	private Media mainMusic = new Media(new File(mainMusicPath).toURI().toString());
	private MediaPlayer menuPlayer = new MediaPlayer(menuMusic);
	private MediaPlayer battlePlayer = new MediaPlayer(battleMusic);
	private MediaPlayer mainPlayer = new MediaPlayer(mainMusic);
	
	
	public MasterViewController(Stage stage, GameController gameController,
			URL resourceBaseUrl, HostServices hostServices) throws IOException, URISyntaxException {
		this.stage = stage;
		this.gameController = gameController;
		this.hostServices = hostServices;

		this.loadResources(resourceBaseUrl);
		this.loadFxml();
	}
	
	private void loadResources(URL baseUrl) throws URISyntaxException, IOException {
		this.resourceController = new ResourceController(baseUrl);
		this.resourceController.load();
		this.resourceController.loadGame();
		
		this.stage.getIcons().add(this.resourceController.getIcon());
		this.stage.setTitle("PokéRoutes");
	}
	
	private void loadFxml() throws IOException {
		css = getClass().getResource("/application/application.css").toExternalForm();
		
		this.menuView = 
				new ViewContext<MenuController>(getClass().getResource("/view/Menu.fxml"), true, css);
		this.menuView.setController(this, gameController);
		this.menuView.getController().onLoadResources();
		
		this.mainscreenView = 
				new ViewContext<MainscreenController>(getClass().getResource("/view/Mainscreen.fxml"), true, css);
		this.mainscreenView.setController(this, gameController);
		this.mainscreenView.getController().onLoadResources();
		
		this.managePlayerView = 
				new ViewContext<ManagePlayerController>(getClass().getResource("/view/ManagePlayer.fxml"), true, css);
		this.managePlayerView.setController(this, gameController);
		this.managePlayerView.getController().onLoadResources();
		
		this.aboutView = 
				new ViewContext<AboutController>(getClass().getResource("/view/About.fxml"), true, css);
		this.aboutView.setController(this, gameController);
		this.aboutView.getController().onLoadResources();
		
		this.highscoreView = 
				new ViewContext<HighscoreController>(getClass().getResource("/view/Highscore.fxml"), true, css);
		this.highscoreView.setController(this, gameController);
		this.highscoreView.getController().onLoadResources();
		
		this.avatarView = 
				new ViewContext<AvatarController>(getClass().getResource("/view/Avatar.fxml"), true, css);
		this.avatarView.setController(this, gameController);
		this.avatarView.getController().onLoadResources();
		
		this.dummyCastleView = 
				new ViewContext<DummyCastleController>(getClass().getResource("/view/DummyCastle.fxml"), true, css);
		this.dummyCastleView.setController(this, gameController);
		this.dummyCastleView.getController().onLoadResources();
		
		this.combatPlayerSelectionView = 
				new ViewContext<CombatPlayerSelectionController>(getClass().getResource("/view/CombatPlayerSelection.fxml"), true, css);
		this.combatPlayerSelectionView.setController(this, gameController);
		this.combatPlayerSelectionView.getController().onLoadResources();
		
		this.combatStageView = 
				new ViewContext<CombatStageController>(getClass().getResource("/view/CombatStage.fxml"), true, css);
		this.combatStageView.setController(this, gameController);
		this.combatStageView.getController().onLoadResources();
		
		
		linkAuis();
	}
	
	public void loadMenu() {
		stage.setScene(menuView.getScene());
		stage.centerOnScreen();
		stage.sizeToScene();
		
		mainPlayer.stop();
		menuPlayer.play();
		
		menuPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				menuPlayer.seek(Duration.ZERO);
			}
		}
		);
	}
	
	public void loadMainscreen() {
		menuPlayer.stop();
		battlePlayer.stop();
		
		mainPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				mainPlayer.seek(Duration.ZERO);
			}
		}
		);
		mainPlayer.play();
		mainscreenView.getController().update();
		stage.setScene(mainscreenView.getScene());
		stage.centerOnScreen();
	}
	
	public void loadManagePlayer() {
		stage.setScene(managePlayerView.getScene());
		stage.centerOnScreen();
	}
	
	public void loadAbout() {
		stage.setScene(aboutView.getScene());
		stage.centerOnScreen();
	}
	
	public void loadHighscore() {
		highscoreView.getController().loadHighscore();
		stage.setScene(highscoreView.getScene());
		stage.centerOnScreen();
	}
	
	public void loadAvatarView() {
		stage.setScene(avatarView.getScene());
		stage.centerOnScreen();
	}
	
	public void loadDummyCastleView() {
		stage.setScene(dummyCastleView.getScene());
	}
	
	public void loadCombatPlayerSelection() {
		
		combatPlayerSelectionView.getController().load();
		stage.setScene(combatPlayerSelectionView.getScene());
		//stage.centerOnScreen();

	}
	
	public void loadCombatStage() {
		mainPlayer.stop();
		combatStageView.getController().update();
		stage.setScene(combatStageView.getScene());
		
		battlePlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				battlePlayer.seek(Duration.ZERO);
			}
		}
		);
		
		battlePlayer.play();
	}
	
	
	public ViewContext<CastleViewController> createCastleView(Castle castle, boolean allowAdd, boolean small)  {
		ViewContext<CastleViewController> result = null;
		try {
			result = new ViewContext<CastleViewController>(getClass().getResource("/view/CastleView.fxml"), true, css);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.setController(this, this.gameController);
		result.getController().loadCastle(castle, allowAdd, small);
		
		
		return result;
	}
	
	public ViewContext<CastleCardViewController> createCastleCardView(PlayedCardHolder playedCardHolder, boolean allowAdd)  {
		ViewContext<CastleCardViewController> result = null;
		try {
			result = new ViewContext<CastleCardViewController>(getClass().getResource("/view/CastleCardView.fxml"), true, css);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.setController(this, this.gameController);
		result.getController().loadPlayedCardHolder(playedCardHolder,allowAdd);
		
		
		return result;
	}
	
	public ViewContext<CardViewController> createCardView(Card card, boolean showAlways, boolean hasCtxMenu)  {
		ViewContext<CardViewController> result = null;
		try {
			result = new ViewContext<CardViewController>(getClass().getResource("/view/CardView.fxml"), true, css);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.setController(this, this.gameController);
		result.getController().loadCard(card, showAlways, hasCtxMenu);
		
		
		return result;
	}
	
	public ViewContext<CardViewController> createCardView(BonusCard bonusCard, boolean showAlways) {
		ViewContext<CardViewController> result = null;
		try {
			result = new ViewContext<CardViewController>(getClass().getResource("/view/CardView.fxml"), true, css);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.setController(this, this.gameController);
		result.getController().loadBonusCard(bonusCard, showAlways);
		
		
		return result;
	}
	
	public void closeStage() {
		stage.close();
	}
	
	public void createDummyGame() throws Exception {
		PlayerData pd1 = new PlayerData("Niklas", 0, 1, false);
		PlayerData pd2 = new PlayerData("Kevin", 0, 2, false);
		PlayerData pd3 = new PlayerData("Jonas", 0, 3, false);
		PlayerData pd4 = new PlayerData("Thomas", 0, 4, false);
		PlayerData pd5 = new PlayerData("Moussa", 0, 5, false);
		PlayerData pd6 = new PlayerData("Robin", 0, 5, false);
		
		ArrayList<PlayerData> pdList = new ArrayList<PlayerData>();
		pdList.add(pd1);
		pdList.add(pd2);
		pdList.add(pd3);
		pdList.add(pd4);
		pdList.add(pd5);
		pdList.add(pd6);
		
		
		GameParameter gameParameter = new GameParameter(false, 1, pdList);
		
		
		PlayerController playerController = gameController.getPlayerController();
		
		gameController.initialize(gameParameter);
		
		Table table = gameController.getTable();
		ArrayList<Player> players = table.getPlayers();
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
	
		
		player1.setPoints(10);
		player2.setPoints(20);
		
		//player1.getBonusCards().add(table.getBonusCards().get(0));

		CastleCard card2 = new CastleCard(2, CardType.CRAB, CardForm.T_CROSS);
		CastleCard card3 = new CastleCard(3, CardType.SEAGULL, CardForm.CURVE);
		CastleCard card4 = new CastleCard(4, CardType.BUCKET, CardForm.STRAIGHT);
		CastleCard card5 = new CastleCard(5, CardType.CRAB, CardForm.END);
		CastleCard card6 = new CastleCard(6, CardType.SEAGULL, CardForm.STRAIGHT);
		
		CastleCard card7 = new CastleCard(7, CardType.BUCKET, CardForm.CROSS);
		CastleCard card8 = new CastleCard(8, CardType.CRAB, CardForm.T_CROSS);
		CastleCard card9 = new CastleCard(9, CardType.SEAGULL, CardForm.CURVE);
		CastleCard card10 = new CastleCard(10, CardType.BUCKET, CardForm.STRAIGHT);
		CastleCard card11 = new CastleCard(11, CardType.CRAB, CardForm.END);
		CastleCard card12 = new CastleCard(12, CardType.SEAGULL, CardForm.STRAIGHT);
		
		CastleCard card13 = new CastleCard(13, CardType.SEAGULL, CardForm.CROSS);
		CastleCard card14 = new CastleCard(14, CardType.BUCKET, CardForm.CROSS);
		CastleCard card15 = new CastleCard(15, CardType.SEAGULL, CardForm.CROSS);
		CastleCard card16 = new CastleCard(16, CardType.BUCKET, CardForm.CROSS);
		CastleCard card17 = new CastleCard(17, CardType.SEAGULL, CardForm.CROSS);
		CastleCard card18 = new CastleCard(18, CardType.BUCKET, CardForm.CROSS);
		CastleCard card19 = new CastleCard(19, CardType.CRAB, CardForm.CROSS);
		CastleCard card20 = new CastleCard(20, CardType.CRAB, CardForm.CROSS);
		
		playerController.createNewCastle(card13, Rotation.NORTH);
		Castle castle1 = table.getCurrentPlayer().getCastles().get(0);
		playerController.createNewCastle(card2, Rotation.NORTH);
		Castle castle2 = table.getCurrentPlayer().getCastles().get(1);
		playerController.endTurn();
		playerController.createNewCastle(card3, Rotation.NORTH);
		Castle castle3 = table.getCurrentPlayer().getCastles().get(0);
		playerController.endTurn();
		playerController.createNewCastle(card4, Rotation.NORTH);
		Castle castle4 = table.getCurrentPlayer().getCastles().get(0);
		playerController.endTurn();
		playerController.createNewCastle(card5, Rotation.NORTH);
		Castle castle5 = table.getCurrentPlayer().getCastles().get(0);
		playerController.endTurn();
		playerController.createNewCastle(card6, Rotation.NORTH);
		Castle castle6 = table.getCurrentPlayer().getCastles().get(0);
		playerController.endTurn();
		
		playerController.endTurn();
//		playerController.endTurn();
//		playerController.endTurn();

		
		PlayedCardHolder base1 = castle1.getCardByPosition(Position.Zero);
		PlayedCardHolder base2 = castle2.getCardByPosition(Position.Zero);
		PlayedCardHolder base3 = castle3.getCardByPosition(Position.Zero);
		PlayedCardHolder base4 = castle4.getCardByPosition(Position.Zero);
		PlayedCardHolder base5 = castle5.getCardByPosition(Position.Zero);
		PlayedCardHolder base6 = castle6.getCardByPosition(Position.Zero);
		
		
		playerController.addToExistingCastle(base1, Direction.RIGHT, card7, Rotation.NORTH);
		playerController.addToExistingCastle(base2, Direction.RIGHT, card8, Rotation.NORTH);
		playerController.addToExistingCastle(base3, Direction.RIGHT, card9, Rotation.SOUTH);
		playerController.addToExistingCastle(base4, Direction.TOP, card10, Rotation.NORTH);
		playerController.addToExistingCastle(base5, Direction.TOP, card11, Rotation.SOUTH);
		playerController.addToExistingCastle(base6, Direction.BOTTOM, card12, Rotation.NORTH);
		
		playerController.addToExistingCastle(base1, Direction.UPPER, card13, Rotation.NORTH);
		
		playerController.addToExistingCastle(castle1.getCardByPosition(new Position(1, 0)), Direction.RIGHT, card14, Rotation.NORTH);
		playerController.addToExistingCastle(castle1.getCardByPosition(new Position(2, 0)), Direction.RIGHT, card15, Rotation.NORTH);
		playerController.addToExistingCastle(castle1.getCardByPosition(new Position(3, 0)), Direction.RIGHT, card16, Rotation.NORTH);
		playerController.addToExistingCastle(castle1.getCardByPosition(new Position(4, 0)), Direction.RIGHT, card17, Rotation.NORTH);
		playerController.addToExistingCastle(castle1.getCardByPosition(new Position(5, 0)), Direction.RIGHT, card18, Rotation.NORTH);
		playerController.addToExistingCastle(castle1.getCardByPosition(new Position(6, 0)), Direction.RIGHT, card19, Rotation.NORTH);
		playerController.addToExistingCastle(castle1.getCardByPosition(new Position(7, 0)), Direction.RIGHT, card20, Rotation.NORTH);
		
	}
	
	private void linkAuis() {
		gameController.setMainUI(mainscreenView.getController());
		gameController.setBattleAreaUI(combatStageView.getController());
		
		gameController.setAuiView(this);
	}
	
	public void openLink(String uri) {
		hostServices.showDocument(uri);
	}
	
	public void print(Parent parent) {
		if(Printer.getAllPrinters().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Drucken");
			alert.setHeaderText("Fehler!");
			alert.setContentText("Kein Drucker gefunden");

			alert.showAndWait();
			return;
		}
		
		Printer selPrinter = Printer.getAllPrinters().iterator().next();
		
		PrinterJob job = PrinterJob.createPrinterJob(selPrinter);
		 if (job != null) {
			if(job.showPrintDialog(stage.getOwner())) {
				if(job.printPage(parent)) {
					job.endJob();
				}
			}
		 }
	}
	
	public void load() throws Exception {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load Game");
		File file = fileChooser.showOpenDialog(stage);
		gameController.getIOController().load(file);
	}
	
	public void save() throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Game");
		File file = fileChooser.showSaveDialog(stage);
		gameController.getIOController().save(file);
	}
	
	public void undoTurn() throws Exception {
		gameController.getPlayerController().undoTurn();
	}
	
	public void loadMainView() {
		this.loadMainscreen();
	}
	
	public void loadCombatView() {
		loadCombatStage();
	}
	public void loadEndGameView() {
		loadHighscore();
	}

	/**
	 * @return the resourceController
	 */
	public ResourceController getResourceController() {
		return resourceController;
	}
}
