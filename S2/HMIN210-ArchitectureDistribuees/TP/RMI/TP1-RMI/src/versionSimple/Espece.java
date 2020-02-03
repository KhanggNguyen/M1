package versionSimple;

public class Espece implements Cloneable {
	private String nom;
	private Integer dureeDeVie;
	
	public Espece(String nom, Integer dureeDeVie) {
		super();
		this.nom = nom;
		this.dureeDeVie = dureeDeVie;
	}
	
	public Object clone() throws CloneNotSupportedException 
	{ 
		return super.clone(); 
	} 
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public Integer getDureeDeVie() {
		return dureeDeVie;
	}
	
	public void setDureeDeVie(Integer dureeDeVie) {
		this.dureeDeVie = dureeDeVie;
	}
	
	@Override
	public String toString() {
		return "Espece [nom=" + nom + ", dureeDeVie=" + dureeDeVie + "]";
	}
	
	
}
