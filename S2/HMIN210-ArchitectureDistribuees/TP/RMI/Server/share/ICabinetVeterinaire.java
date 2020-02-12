package share;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICabinetVeterinaire extends Remote {
	IAnimal recherche(String nom) throws RemoteException;
	void addAnimal(Animal a) throws RemoteException;
	void creationAnimal(String nom, String nomMaitre, Espece espece, String race) throws RemoteException;
}
