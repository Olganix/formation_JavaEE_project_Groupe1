package fr.dawan.nogashi.beans;

public class Association extends Buyer {

	private static final long serialVersionUID = 1L;

	private String codeSiren;
	private String codeAssociation;
	
	
	//--------------------------------
	
	public Association(String codeSiren, String codeAssociation) {
		super(true);
		this.codeSiren = codeSiren;
		this.codeAssociation = codeAssociation;
	}
	public Association() {
		super();
	}
	@Override
	public String toString() {
		return "Association [codeSiren=" + codeSiren + ", codeAssociation=" + codeAssociation + "]";
	}
	public String getCodeSiren() {
		return codeSiren;
	}
	public void setCodeSiren(String codeSiren) {
		this.codeSiren = codeSiren;
	}
	public String getCodeAssociation() {
		return codeAssociation;
	}
	public void setCodeAssociation(String codeAssociation) {
		this.codeAssociation = codeAssociation;
	}

}
