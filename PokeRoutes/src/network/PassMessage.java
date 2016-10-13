package network;

public class PassMessage extends Message {
	public static final String CMD = "PASS";

	@Override
	public void decode(String[] param) {
		//Nothing to decode
		Util.checkExactParameterSize(param, 0, "pass message");
	}

	@Override
	public String encode(String str) {
		return CMD;
	}

}
