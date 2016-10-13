package network;

public class CapitulateMessage extends Message {
	public static final String CMD = "CAPITULATE";

	@Override
	public void decode(String[] param) {
		//Nothing to decode
		Util.checkExactParameterSize(param, 0, "capitulate message");
	}

	@Override
	public String encode(String str) {
		return CMD;
	}

}
