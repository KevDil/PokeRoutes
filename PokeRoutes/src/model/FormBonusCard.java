package model;

import java.io.Serializable;

public class FormBonusCard extends BonusCard  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5449597152010663877L;
	private CastleForm form;

	public FormBonusCard(int id, CastleForm form, int value) {
		super(id);
		this.form = form;
		this.setValue(value);
	}
	
	/**
	 * @return the form
	 */
	public CastleForm getForm() {
		return form;
	}

}
