package HMIN102.TP7;

public class ProduitSolde extends Produit{

	public ProduitSolde(String titre, float prixLocation) {
		super(titre, prixLocation);
	}

	public float getPrixLocation(){
		return super.getPrixLocation() * 0.5f;
	}
}
