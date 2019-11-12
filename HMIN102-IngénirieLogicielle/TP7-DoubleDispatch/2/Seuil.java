class Seuil extends Forfait{
    //donne une location gratuite aprs $compteur payantes
    static int init = 2;
    int compteur = init;
    
    public Seuil(Compte c){
        super(c);
    }
    
    public double prixLocation(Produit p){
        if (compteur-- == 0) {
            compteur = init; 
            return 0.0;
        }
        else 
            return super.prixLocation(p);
    }
}