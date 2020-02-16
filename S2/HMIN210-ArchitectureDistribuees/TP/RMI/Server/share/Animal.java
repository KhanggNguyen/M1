package share;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Animal extends UnicastRemoteObject implements IAnimal{
	private String nom;
	private String nomMaitre;
	private Espece espece;
	private String race;
	private DossierSuivi dossier;
	
	public Animal(String nom, String nomMaitre, Espece espece, String race) throws RemoteException{
		this.nom = nom;
		this.nomMaitre = nomMaitre;
		this.espece = espece;
		this.race = race;
		this.dossier = new DossierSuivi();
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNomMaitre() throws RemoteException{
		return nomMaitre;
	}
	
	public void setNomMaitre(String nomMaitre) {
		this.nomMaitre = nomMaitre;
	}
	
	public Espece getEspece() throws RemoteException{
		return espece;
	}
	
	public void setEspece(Espece espece) {
		this.espece = espece;
	}
	
	public String getRace() {
		return race;
	}
	
	public void setRace(String race) {
		this.race = race;
	}
	
	public DossierSuivi getDossier() throws RemoteException{
		return dossier;
	}
	
	public String getInfos() throws RemoteException {
		return "Animal [nom=" + nom + ", nomMaitre=" + nomMaitre + ", espece=" + espece.toString() + ", race=" + race + "]";
	}
	
	public void printInfos() throws RemoteException {
		System.out.println(getInfos());
	}
	
	
	
}
