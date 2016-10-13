package network;

import java.security.InvalidParameterException;

public abstract class Message {
	public abstract void decode(String[] param);

	public abstract String encode(String str);

	public static Message parseMessage(String str) {
		String cmd = Util.getCmd(str);
		String paramStr = Util.getParameter(str);
		String[] param = Util.splitParameter(paramStr);
		Message m = null;
		switch (cmd) {
		case AttackMessage.CMD:
			m = new AttackMessage();
			m.decode(param);
			break;

		case BuildMessage.CMD:
			m = new BuildMessage();
			m.decode(param);
			break;

		case CapitulateMessage.CMD:
			m = new CapitulateMessage();
			m.decode(param);
			break;

		case CounterAttackMessage.CMD:
			m = new CounterAttackMessage();
			m.decode(param);
			break;

		case DrawCardMessage.CMD:
			m = new DrawCardMessage();
			m.decode(param);
			break;

		case ExchangeMessage.CMD:
			m = new ExchangeMessage();
			m.decode(param);
			break;

		case InitBoniMessage.CMD:
			m = new InitBoniMessage();
			m.decode(param);
			break;

		case InitStackMessage.CMD:
			m = new InitStackMessage();
			m.decode(param);
			break;

		case PassMessage.CMD:
			m = new PassMessage();
			m.decode(param);
			break;

		default:
			throw new InvalidParameterException("Unknown cmd type");
		}
		
		return m;
	}
}
