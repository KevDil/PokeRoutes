package viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import model.BigWave;
import model.BonusCard;
import model.Card;
import model.CardForm;
import model.CardType;
import model.Castle;
import model.CastleCard;
import model.CountBonusCard;
import model.Position;
import model.Rotation;

public class DummyCastleController extends ViewController {

	@FXML
	private Pane paneCard1;

	@FXML
	private Pane paneCard2;

	@FXML
	private Pane paneCastle;

	@Override
	public void onLoadResources() {
		CastleCard cc1 = new CastleCard(1, CardType.BUCKET, CardForm.CROSS);
		cc1.setRotation(Rotation.NORTH);

		Castle c = new Castle(1, cc1);
		CastleCard cc2 = new CastleCard(2, CardType.CRAB, CardForm.STRAIGHT);
		cc2.setRotation(Rotation.SOUTH);
		c.addCard(Position.Zero.add(0, -1), cc2);

		CastleCard cc3 = new CastleCard(2, CardType.SEAGULL, CardForm.T_CROSS);
		cc3.setRotation(Rotation.SOUTH);
		c.addCard(Position.Zero.add(1, 0), cc3);

		CastleCard cc4 = new CastleCard(2, CardType.SEAGULL, CardForm.T_CROSS);
		cc4.setRotation(Rotation.SOUTH);
		c.addCard(Position.Zero.add(0, 1), cc4);

		CastleCard cc5 = new CastleCard(2, CardType.SEAGULL, CardForm.T_CROSS);
		cc5.setRotation(Rotation.SOUTH);
		c.addCard(Position.Zero.add(0, 2), cc5);

		CastleCard cc6 = new CastleCard(2, CardType.SEAGULL, CardForm.T_CROSS);
		cc6.setRotation(Rotation.SOUTH);
		c.addCard(Position.Zero.add(0, 3), cc6);
		paneCastle.getChildren().add(masterViewController.createCastleView(c, true, true).getPane());

		Card bigWave = new BigWave(1);
		BonusCard bonusCard = new CountBonusCard(1, CardType.BUCKET, 3, 10);

		paneCard1.getChildren().add(masterViewController.createCardView(bonusCard, true).getPane());
		// paneCard2.getChildren().add(masterViewController.createCardView(cc5,
		// false).getPane());
	}

}
