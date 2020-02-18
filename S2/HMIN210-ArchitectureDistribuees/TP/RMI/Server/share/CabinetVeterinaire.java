//Participants : TRAN Thi Tra My - NGUYEN Huu Khang

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
		if(listeAnimaux.size() > 10){
			alerterTous();
		}

		this.addAnimal(new Animal(nom, nomMaitre, espece, race));
		System.out.println("Un patient a été ajouté à la base de données. Taille actuellement : " + listeAnimaux.size() );
	}

	@Override
	public IAnimal recherche(String nom) throws RemoteException {
		// TODO Auto-generated method stub
		for(IAnimal a : listeAnimaux){
			if(a.getNom().equals(nom)) return a;
		}
		return null;
	}

	public void afficherTousPatients() throws RemoteException{
		if(!listeAnimaux.isEmpty())
			for(IAnimal a : listeAnimaux){
				System.out.println(a.getInfos());
			}
	}
	
	public void register(IVeterinaire v) throws RemoteException {
		vetosConnectes.add(v);
		System.out.println("Un vétérinaire est connecté");
	}

	public void deconnecter (IVeterinaire v) throws RemoteException {
		vetosConnectes.remove(v);
		System.out.println("Un vétérinaire s'est déconnecté");
	}

	public void alerterTous() {
		for(IVeterinaire veto : vetosConnectes){
			veto.alert("Attention : Total des patients actuellement" + listeAnimaux.size() + "!!!!!");
		}
	}
	
}
