package HMIN102.TP7;

public class CompteAvecReduction extends Compte {
	private int reduc;
	
	public CompteAvecReduction(Client cl, int r) {
		super(cl);
		setReduc(r);
	}

	public int getReduc() {
		return reduc;
	}

	public void setReduc(int reduc) {
		this.reduc = reduc;
	}
	
	public float prixLocation(Produit p){
		return super.prixLocation(p) * (1 - reduc/100f); 
	}

}
