class Client{
	protected String nom;
	protected Compte compte;
	public void setCompte(Compte c){
		compte = c;
	}
	public Compte getCompte(){
		return compte;
	}
		
	public Client(String nom){
		this(nom, null);
	}
		
	public Client(String nom, Compte c){
		this.nom = nom;
		this.compte = c;
	}
}