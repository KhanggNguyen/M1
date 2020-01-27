public class test{
    public static void main(String[] args){
			
        Produit lgv = new Produit("La grande vadrouille", 10.0);
        Client cl = new Client("Dupont");
    
        Compte cmt = new Normal(cl);
        System.out.println("Normal : " + cmt.prixLocation(lgv));
        
        Compte cmt2 = new Seuil (new Reduction (new Normal (cl)));
        System.out.println("Seuil+Reduction : " + cmt2.prixLocation(lgv));
        System.out.println("Seuil+Reduction : " + cmt2.prixLocation(lgv));
        System.out.println("Seuil+Reduction : " + cmt2.prixLocation(lgv));
        System.out.println("Seuil+Reduction : " + cmt2.prixLocation(lgv));
        System.out.println("Seuil+Reduction : " + cmt2.prixLocation(lgv));
    
        Produit r4 = new ProduitSolde("RockyIV", 10.0);
        System.out.println("Seuil+Reduction+Solde : " + cmt2.prixLocation(r4));
        System.out.println("Seuil+Reduction+Solde : " + cmt2.prixLocation(r4));
    }
}
