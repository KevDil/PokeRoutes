package network;

import model.Position;
import model.Rotation;

public class BuildMessage extends Message {
	
	public static final String CMD = "BUILD";
	
	private int castleId;
	private Position position;
	private Rotation rotation;
	
	public BuildMessage() {
	}
	
	@Override
	public void decode(String[] param) {
		Util.checkExactParameterSize(param, 3, "build message");
		castleId = Util.parseCode(param[0], "castle id");
		position = Util.decodePosition(param[1]);
		rotation = Network.toRotation(Util.parseCode(param[2], "rotation"));
		
	}

	@Override
	public String encode(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append(CMD);
		
		sb.append(' ');
		sb.append(castleId);
		
		sb.append(' ');
		Util.encodePosition(sb, position);
		
		sb.append(' ');
		sb.append(Network.rotationToNetworkCode(rotation));
		
		return sb.toString();
	}

}
