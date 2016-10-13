package network;

import java.security.InvalidParameterException;

import model.BigWave;
import model.BonusCard;
import model.Card;
import model.CastleCard;
import model.CombatCard;
import model.CountBonusCard;
import model.FormBonusCard;

public class Cards {
	public static void encode(StringBuilder sb, CombatCard combatCard) {
		sb.append('a');
		sb.append('-');
		sb.append(Network.cardTypeToNetworkCode(combatCard.getType()));
	}
	
	public static void encode(StringBuilder sb, CastleCard castleCard) {
		sb.append('c');
		sb.append('-');
		sb.append(Network.cardTypeToNetworkCode(castleCard.getType()));
		sb.append('-');
		sb.append(Network.cardFormToNetworkCode(castleCard.getCardForm()));
	}
	
	public static void encode(StringBuilder sb, BigWave bigWave) {
		sb.append("gw");
	}
	
	public static void encode(StringBuilder sb, CountBonusCard countBonusCard) {
		sb.append("bs");
		sb.append('-');
		sb.append(Network.cardTypeToNetworkCode(countBonusCard.getType()));
		sb.append('-');
		sb.append(countBonusCard.getRequired());//TODO: ?
	}
	
	public static void encode(StringBuilder sb, FormBonusCard formBonusCard) {
		sb.append("bf");
		sb.append('-');
		sb.append(Network.castleFormToNetworkCode(formBonusCard.getForm()));
	}
	
	
	public static CastleCard decodeCastleCard(String symbol, String form, int id) {
		int formCode = Util.parseCode(form, "form");
		
		return new CastleCard(id,
				Util.parseCardType(symbol),
				Network.toCardForm(formCode));
	}
	
	public static CombatCard decodeCombatCard(String symbol, int id) {
		return new CombatCard(id, Util.parseCardType(symbol));
	}
	
	public static Card decodeCard(String str, int id) {
		String[] param = Util.splitCardParameter(str);
		Util.checkMinParameterSize(param, 1, "card");
		
		String cardType = param[0];
		if(cardType.equals("gw")) {
			Util.checkExactParameterSize(param, 1, "big wave card");
			return new BigWave(id);
		} else if(cardType.equals("a")) {
			Util.checkExactParameterSize(param, 2, "combat card");
			return decodeCombatCard(param[1], id);
		} else if(cardType.equals("c")) {
			Util.checkExactParameterSize(param, 3, "castle card");
			return decodeCastleCard(param[1], param[2], id);
		}
		
		throw new InvalidParameterException("Invalid card type: " + cardType);//TODO: protocol exception	
	}
	
	public static CountBonusCard decodeCountBonusCard(String symbol, String count, int id) {
		int required = Util.parseCode(count, "count");
		
		return new CountBonusCard(id,
				Util.parseCardType(symbol), required, 0);
	}
	
	public static FormBonusCard decodeFormBonusCard(String form, int id) {
		@SuppressWarnings("unused")
		int formCode = Util.parseCode(form, "form");
		
		return new FormBonusCard(0, null, 0); 
//		return new FormBonusCard(id,
//				Network.toCastleForm(formCode));
	}
	
	
	public static BonusCard decodeBonusCard(String str, int id) {
		String[] param = Util.splitCardParameter(str);
		Util.checkMinParameterSize(param, 1, "bonus card");
		
		String cardType = param[0];
		if(cardType.equals("bs")) {
			Util.checkExactParameterSize(param, 2, "bonus card count");
			return decodeCountBonusCard(param[1], param[2], id);
		} else if(cardType.equals("bf")) {
			Util.checkExactParameterSize(param, 1, "bonus card form");
			return decodeFormBonusCard(param[1], id);
		}
		
		throw new InvalidParameterException("Invalid bonus card type: " + cardType);//TODO: protocol exception	
	}
}
