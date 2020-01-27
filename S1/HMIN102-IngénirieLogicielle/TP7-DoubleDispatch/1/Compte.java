package HMIN102.TP7;

public class Compte {
	private Client cl;

	public Compte(Client cl) {
		super();
		this.cl = cl;
	}

	public Client getCl() {
		return cl;
	}

	public void setCl(Client cl) {
		this.cl = cl;
	}
	
	public float prixLocation(Produit p){
		return p.getPrixLocation();
	}
	
	
	
}
