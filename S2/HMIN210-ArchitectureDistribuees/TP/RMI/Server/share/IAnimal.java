package share;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAnimal extends Remote{
	String getInfos() throws RemoteException;
	void printInfos() throws RemoteException;
	IDossierSuivi getDossier()throws RemoteException;
}
