package share;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CabinetVeterinaire extends UnicastRemoteObject implements ICabinetVeterinaire {
	private ArrayList<IAnimal> listeAnimaux = new ArrayList<IAnimal>();
	private ArrayList<IVeterinaire> vetosConnectes = new ArrayList<IVeterinaire>();

	public CabinetVeterinaire() throws RemoteException{
		
	}
	
	public ArrayList<IAnimal> getListeAnimaux() {
		return listeAnimaux;
	}

	public void setListeAnimaux(ArrayList<IAnimal> listeAnimaux) {
		this.listeAnimaux = listeAnimaux;
	}
	
	public void addAnimal(IAnimal a) throws RemoteException{
		this.listeAnimaux.add(a);
	}

	public void creationAnimal(String nom, String nomMaitre, Espece espece, String race) throws RemoteException{
		if(vetosConnectes.size() > 10){
			alerterTous();
		}

		this.addAnimal(new Animal(nom, nomMaitre, espece, race));
		System.out.println("Un patient a été ajouté à la base de données");
	}

	@Override
	public IAnimal recherche(String nom) throws RemoteException {
		// TODO Auto-generated method stub
		for(IAnimal a : listeAnimaux){
			if(a.getNom().equals(nom)) return a;
		}
		return null;
	}
	
	public void register(IVeterinaire v){
		vetosConnectes.add(v);
	}

	public void alerterTous(){
		for(IVeterinaire veto : vetosConnectes){
			veto.alert("Total des patient actuellement" + vetosConnectes.size());
		}
	}
	
}
