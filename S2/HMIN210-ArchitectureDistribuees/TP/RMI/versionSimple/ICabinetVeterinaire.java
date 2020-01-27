package versionSimple;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICabinetVeterinaire extends Remote {
	Animal recherche(String nom) throws RemoteException;
	void addAnimal(Animal a) throws RemoteException;
}
