package network;

import java.util.ArrayList;

import model.BigWave;
import model.Card;
import model.CastleCard;
import model.CombatCard;

public class InitStackMessage extends Message {
	
	public static final String CMD = "INITSTACK";
	
	ArrayList<Card> cards;
	
	public InitStackMessage() {
		cards = new ArrayList<Card>();
	}
	
	@Override
	public void decode(String[] param) {
		for(String cardStr : param) {
			cards.add(Cards.decodeCard(cardStr, 0));
		}
		
	}

	@Override
	public String encode(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append(CMD);
		
		for(Card card : cards) {
			sb.append(' ');
			
			if(card.isBigWave()) {
				Cards.encode(sb, (BigWave)card);
			} else if(card.isCastleCard()) {
				Cards.encode(sb, (CastleCard)card);
			} else if(card.isCombatCard()) {
				Cards.encode(sb, (CombatCard)card);
			}
		}
		
		return sb.toString();
	}

}
