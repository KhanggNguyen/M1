class Normal extends Compte{
    static int compteur = 0;
    int numero;
    Client titulaire;
    
    public Normal (Client cl){
        titulaire = cl;
        cl.setCompte(this);
        this.numero = ++compteur;
    }
}