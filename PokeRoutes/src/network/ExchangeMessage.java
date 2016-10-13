package network;

import java.security.InvalidParameterException;

import model.Card;
import model.CastleCard;
import model.CombatCard;
import model.DrawPile;

public class ExchangeMessage extends Message {
	
	public static final String CMD = "EXCHANGE";
	
	private Card card;
	private DrawPile pile;

	
	public ExchangeMessage() {
	}
	
	@Override
	public void decode(String[] param) {
		Util.checkExactParameterSize(param, 2, "exchange card message");
		card = Cards.decodeCard(param[0], 0);
		pile = Network.toDrawPile(Util.parseCode(param[1], "pile"));
	}

	@Override
	public String encode(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append(CMD);
		
		sb.append(' ');
		if(card.isCastleCard()) {
			Cards.encode(sb, (CastleCard)card);
		} else if(card.isCombatCard()) {
			Cards.encode(sb, (CombatCard)card);
		} else {
			throw new InvalidParameterException("Card is neither a combat or a castle card");
		}
				
		
		sb.append(' ');
		sb.append(Network.drawPileToNetworkCode(pile));
		
		return sb.toString();
	}
	

	
	/**
	 * @return the pile
	 */
	public DrawPile getPile() {
		return pile;
	}

	/**
	 * @param pile the pile to set
	 */
	public void setPile(DrawPile pile) {
		this.pile = pile;
	}


}
