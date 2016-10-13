package network;

import model.DrawPile;

public class DrawCardMessage extends Message {
	
	public static final String CMD = "DRAWCARD";
	
	private DrawPile pile;
	
	public DrawCardMessage() {
	}
	
	@Override
	public void decode(String[] param) {
		Util.checkExactParameterSize(param, 1, "draw card message");
		pile = Network.toDrawPile(Util.parseCode(param[0], "pile"));
	}

	@Override
	public String encode(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append(CMD);
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
