class ProduitSolde extends Produit{

    ProduitSolde(String titre, double pb) {
        super(titre, pb);
    }
    
    double prixVente(){
        return (super.prixVente() / 2);
    }
}