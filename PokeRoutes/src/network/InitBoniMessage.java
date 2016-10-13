package network;

import java.util.ArrayList;

import model.BonusCard;
import model.CountBonusCard;
import model.FormBonusCard;
import model.HeightBonusCard;
import model.SizeBonusCard;

public class InitBoniMessage extends Message {
	
	public static final String CMD = "INITBONI";
	
	ArrayList<BonusCard> bonusCards;
	
	public InitBoniMessage() {
		bonusCards = new ArrayList<BonusCard>();
	}
	
	@Override
	public void decode(String[] param) {
		for(String cardStr : param) {
			bonusCards.add(Cards.decodeBonusCard(cardStr, 0));
		}
		
		bonusCards.add(new HeightBonusCard(0));
		bonusCards.add(new SizeBonusCard(0));
		
	}

	@Override
	public String encode(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append(CMD);
		
		for(BonusCard card : bonusCards) {
			sb.append(' ');
			
			if(card.isCountBonusCard()) {
				Cards.encode(sb, (CountBonusCard)card);
			} else if(card.isFormBonusCard()) {
				Cards.encode(sb, (FormBonusCard)card);
			}
		}
		
		return sb.toString();
	}

}
