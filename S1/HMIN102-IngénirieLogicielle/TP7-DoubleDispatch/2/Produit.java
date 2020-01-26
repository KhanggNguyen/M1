public class Produit {

	double prixBase;
	double TVA = 19.6;
	double marge = 1.10;
	String titre;
		
	Produit(String titre, double pb) {
		this.titre = titre;
		this.prixBase = pb;
	}
	
	protected double prixHT(){
		return prixBase * marge;
	}

	double prixVente(){
		return (this.prixHT() * (1 + TVA));
	}

	double prixLocation(){
		return this.prixVente() * 5 /100;
	}
}	