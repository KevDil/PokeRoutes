package network;

import model.Position;

public class AttackMessage extends Message {
	
	public static final String CMD = "ATTACK";
	
	private int castleId;
	private Position position;
	
	public AttackMessage() {
	}
	
	@Override
	public void decode(String[] param) {
		Util.checkExactParameterSize(param, 2, "attack message");
		castleId = Util.parseCode(param[0], "castle id");
		position = Util.decodePosition(param[1]);
		
	}

	@Override
	public String encode(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append(CMD);
		
		sb.append(' ');
		sb.append(castleId);
		
		sb.append(' ');
		Util.encodePosition(sb, position);
		
		return sb.toString();
	}

}
