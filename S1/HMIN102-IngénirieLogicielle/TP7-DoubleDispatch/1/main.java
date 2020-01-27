package HMIN102.TP7;

public class main {

	public static void main(String[] args) {
		Produit lgv = new Produit ("La grande vadrouille", 10.0f ) ;
		Client cl = new Client("Dupont") ;
		Compte cmt = new Compte(cl) ;
		cmt.prixLocation(lgv) ;

		Compte cmt2 = new CompteAvecReduction (cl, 20) ;
		System.out.println("CompteReduction : " + cmt2.prixLocation(lgv)) ;
		Compte cmt3 = new CompteAvecSeuil(cl) ; // le seuil est à 2 par défaut
		System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv));
		System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv));
		System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv)) ; //doit afficher 0
		Produit r4 = new ProduitSolde ( "RockyIV" , 10.0f ) ;
		System.out.println( "Compte Normal + Produit Soldé : " + cmt.prixLocation(r4));
		System.out.println( "Compte Reduction +Produit Soldé : " + cmt2.prixLocation(r4));
	}

}
