package share;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CabinetVeterinaire extends UnicastRemoteObject implements ICabinetVeterinaire {
	private ArrayList<Animal> listeAnimaux = new ArrayList<Animal>();

	public CabinetVeterinaire() throws RemoteException{
		
	}
	
	public ArrayList<Animal> getListeAnimaux() {
		return listeAnimaux;
	}

	public void setListeAnimaux(ArrayList<Animal> listeAnimaux) {
		this.listeAnimaux = listeAnimaux;
	}
	
	public void addAnimal(Animal a) throws RemoteException{
		this.listeAnimaux.add(a);
	}

	public void creationAnimal(String nom, String nomMaitre, Espece espece, String race) throws RemoteException{
		this.addAnimal(new Animal(nom, nomMaitre, espece, race));
	}

	@Override
	public Animal recherche(String nom) throws RemoteException {
		// TODO Auto-generated method stub
		for(Animal a : listeAnimaux){
			if(a.getNom().equals(nom)) return a;
		}
		return null;
	}
	
	
	
}
