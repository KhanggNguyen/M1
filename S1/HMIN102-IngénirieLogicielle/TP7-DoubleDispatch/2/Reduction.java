class Reduction extends Forfait{

    private double reduction = 0.10;
    
    public Reduction(Compte c){
        super(c);
    }
    
    public double prixLocation(Produit p){
        return(super.prixLocation(p) * (1-reduction));
    }
}