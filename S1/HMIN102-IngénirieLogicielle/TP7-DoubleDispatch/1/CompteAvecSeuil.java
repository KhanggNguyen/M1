package HMIN102.TP7;

public class CompteAvecSeuil extends Compte {
	private int seuil = 0;

	public CompteAvecSeuil(Client cl) {
		super(cl);
	}
	
	public int getSeuil() {
		return seuil;
	}

	public void setSeuil(int seuil) {
		this.seuil = seuil;
	}

	//offre gratuit à tous les x locations
	public float prixLocation(Produit p){
		if(seuil == 2){
			setSeuil(0);
			return 0f;
		}else{
			seuil++;
			return super.prixLocation(p);
		}
		
	}

}
