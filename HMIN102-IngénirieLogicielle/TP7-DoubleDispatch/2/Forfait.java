class Forfait extends Compte{
    private Compte decore;

    public Forfait(Compte c){
        decore = c;
    }

    public double prixLocation(Produit p){
        return decore.prixLocation(p);
    }
}