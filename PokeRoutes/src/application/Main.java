package application;
	
import java.io.File;
import java.net.URL;

import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;
import viewcontroller.MasterViewController;



public class Main extends Application {
//	private static String path = "file:///sopra/sopgr07/sopr071/workspace/Sandcastle/resources";
//	private static String path = "file:///C:/Users/micha/git/PokeRoutes/PokeRoutes/resources";
	private static String pathGlobal = "file:" + File.separator + System.getProperty("user.dir") + File.separator + "resources";
	@Override
	public void start(Stage primaryStage) {
		try {
			GameController gameController = 
					new  GameController();
			URL baseUrl = new URL(pathGlobal);
			MasterViewController masterViewController =
					new MasterViewController(primaryStage, gameController, baseUrl,
							getHostServices());
			
			masterViewController.createDummyGame();
	//		masterViewController.loadMainscreen();

			masterViewController.loadMenu();
//			masterViewController.loadCombatPlayerSelection();
//			masterViewController.loadCombatStage();

		//masterViewController.loadMenu();
//			masterViewController.loadCombatPlayerSelection();


			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
