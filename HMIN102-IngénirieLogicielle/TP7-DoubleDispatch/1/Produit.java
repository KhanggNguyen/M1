package HMIN102.TP7;

public class Produit {
	private String titre;
	private float prixLocation;

	public Produit(String titre, float prixLocation) {
		super();
		this.titre = titre;
		this.prixLocation = prixLocation;
	}
	
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public float getPrixLocation() {
		return this.prixLocation;
	}

	public void setPrixLocation(float prixLocation) {
		this.prixLocation = prixLocation;
	}
}
