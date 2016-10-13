package network;

import java.security.InvalidParameterException;

import model.CardForm;
import model.CardType;
import model.CastleForm;
import model.DrawPile;
import model.Rotation;

public class Network {
	
	public static CastleForm toCastleForm(int code) {
		switch (code) {
		case 1:
			return CastleForm.KEEP;// ?
		case 2:
			return CastleForm.AXE;
		case 3:
			return CastleForm.WORM;
		case 4:
			return CastleForm.BARRACK;
		case 5:
			return CastleForm.STAIRWAY;
		case 6:
			return CastleForm.PEACOCK;
		case 7:
			return CastleForm.CACTUS;
		case 8:
			return CastleForm.MONASTERY;
		case 9:
			return CastleForm.GOLDFISH;
		default:
			throw new InvalidParameterException("Invalid castle form code");
		}
	}
	
	public static int castleFormToNetworkCode(CastleForm castleForm) {
		switch(castleForm) { 
		case KEEP:
			return 1;
		case AXE:
			return 2;
		case WORM:
			return 3;
		case BARRACK:
			return 4;
		case STAIRWAY:
			return 5;
		case PEACOCK:
			return 6;
		case CACTUS:
			return 7;
		case MONASTERY:
			return 8;
		case GOLDFISH:
			return 9;
		default:
			throw new InvalidParameterException("Invalid castle form");
		}
	}
	
	
	public static CardForm toCardForm(int code) {
		switch (code) {
		case 0:
			return CardForm.END;
		case 1:
			return CardForm.STRAIGHT;
		case 2:
			return CardForm.CURVE;
		case 3:
			return CardForm.T_CROSS;
		case 4:
			return CardForm.CROSS;
		default:
			throw new InvalidParameterException("Invalid card form code");
		}
	}
	
	public static int cardFormToNetworkCode(CardForm cardForm) {
		switch(cardForm) { 
		case END:
			return 0;
		case STRAIGHT:
			return 1;
		case CURVE:
			return 2;
		case T_CROSS:
			return 3;
		case CROSS:
			return 4;
		default:
			throw new InvalidParameterException("Invalid card form");
		}
	}
	
	
	public static CardType toCardType(char code) {
		switch (code) {
		case 'b':
			return CardType.BUCKET;
		case 'c':
			return CardType.CRAB;
		case 's':
			return CardType.SEAGULL;
		default:
			throw new InvalidParameterException("Invalid castle type code");
		}
	}
	
	public static int cardTypeToNetworkCode(CardType cardType) {
		switch(cardType) { 
		case BUCKET:
			return 'b';
		case CRAB:
			return 'c';
		case SEAGULL:
			return 's';
		default:
			throw new InvalidParameterException("Invalid card type");
		}
	}
	
	
	public static DrawPile toDrawPile(int code) {
		switch (code) {
		case 0:
			return DrawPile.STACK;
		case 1:
			return DrawPile.OPEN_1;
		case 2:
			return DrawPile.OPEN_2;
		case 3:
			return DrawPile.OPEN_3;
		default:
			throw new InvalidParameterException("Invalid draw pile code");
		}
	}
	
	public static int drawPileToNetworkCode(DrawPile toDrawPile) {
		switch(toDrawPile) { 
		case STACK:
			return 0;
		case OPEN_1:
			return 1;
		case OPEN_2:
			return 2;
		case OPEN_3:
			return 3;
		default:
			throw new InvalidParameterException("Invalid draw pile");
		}
	}
	
	//TODO
	public static Rotation toRotation(int code) {
		return Rotation.NORTH;
	}
	
	//TODO
	public static int rotationToNetworkCode(Rotation rotation) {
		return 0;
	}
}
