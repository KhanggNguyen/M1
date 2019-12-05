package HMIN102.TP9;

public class Adherent {
	private String nom;
	private String prenom;
	private int age;
	
	public Adherent(String n, String pn, int a){
		setNom(n);
		setPrenom(pn);
		setAge(a);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}

