package network;

import java.security.InvalidParameterException;

import model.CardType;
import model.Position;

public class Util {
	public static int parseCode(String str, String name) {
		//TODO: Exception
		return Integer.parseInt(str);
	}
	
	public static CardType parseCardType(String str) {
		if(str.length() != 1)
			throw new InvalidParameterException("Symbol can only contain one char");
		
		char symbolCode = str.charAt(0);
		return Network.toCardType(symbolCode);
	}
	
	public static String[] splitCardParameter(String str) {
		return str.split("-");
	}
	
	public static String[] splitParameter(String str) {
		return str.split(" ");
	}
	
	public static String getCmd(String str) {
		int index = str.indexOf(' ');
		if(index == -1)
			index = str.length()-1;
		
		return str.substring(0, index);
	}
	
	public static String getParameter(String str) {
		int index = str.indexOf(' ');
		return str.substring(index+1);//TODO: Exception + test
	}
	
	public static void checkMinParameterSize(String[] parameter, int size, String name) {
		if(parameter.length < size)
			throw new InvalidParameterException("Invalid parameter size for " + name);
	}
	
	public static void checkExactParameterSize(String[] parameter, int size, String name) {
		if(parameter.length != size)
			throw new InvalidParameterException("Invalid parameter size for " + name);
	}
	
	public static Position decodePosition(String str) {
		String[] split = str.split("/");
		checkExactParameterSize(split, 2, "position");
		
		int x = parseCode(split[0], "position x"),
				y = parseCode(split[1], "position y");
		return new Position(x, y);
	}
	
	public static void encodePosition(StringBuilder sb, Position pos) {
		sb.append(pos.getX());
		sb.append('/');
		sb.append(pos.getY());
	}
}
