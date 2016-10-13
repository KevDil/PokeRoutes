package viewcontroller;

import java.util.ArrayList;

import controller.AUIinactivePlayers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.BonusCard;
import model.Player;

public class InactivePlayersController extends ViewController implements AUIinactivePlayers{

    @FXML private Label lblNickname;
    @FXML private Label lblStatus;
    @FXML private Label lblPoints;
    @FXML private Label lblBonusCards;
    @FXML private ImageView imgAvatar;
    @FXML private VBox vboxCastles;
    
    
    
    public void update(Player player){
    	imgAvatar.setImage(masterViewController.getResourceController().getAvatar(player.getPlayerData().getAvatar()));
    	
    	lblNickname.setText(player.getPlayerData().getName());
		
		Integer pkt = new Integer(player.getPoints());
		lblPoints.setText(pkt.toString());
		
		
		ArrayList<BonusCard> countBonusCards = player.getBonusCards();
		Integer count = countBonusCards.size();
		lblBonusCards.setText(count.toString());
		
		if (player.isAI()){
			lblStatus.setText("Computer");
    	}else{
    		lblStatus.setText("Mensch");
    	}
		
		
    }


	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
    
    
    

}
