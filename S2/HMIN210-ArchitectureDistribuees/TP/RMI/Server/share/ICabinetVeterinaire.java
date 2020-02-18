//Participants : TRAN Thi Tra My - NGUYEN Huu Khang

package share;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICabinetVeterinaire extends Remote {
	IAnimal recherche(String nom) throws RemoteException;
	void addAnimal(IAnimal a) throws RemoteException;
	void creationAnimal(String nom, String nomMaitre, Espece espece, String race) throws RemoteException;
	void register(IVeterinaire veto) throws RemoteException;
	void deconnecter(IVeterinaire veto) throws RemoteException;
}
