package versionSimple;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDossierSuivi extends Remote{
	String getTexte() throws RemoteException;
	void setTexte(String s) throws RemoteException;
}
