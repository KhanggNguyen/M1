package versionSimple;

public class DossierSuivi {
	private String texte;

	public DossierSuivi() {
		this.texte = "défaut";
	}
	
	public DossierSuivi(String texte) {
		this.texte = texte;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}
}
