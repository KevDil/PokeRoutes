package network;

public class CounterAttackMessage extends Message {
	public static final String CMD = "COUNTERATTACK";

	@Override
	public void decode(String[] param) {
		//Nothing to decode
		Util.checkExactParameterSize(param, 0, "counter attack message");
	}

	@Override
	public String encode(String str) {
		return CMD;
	}

}
