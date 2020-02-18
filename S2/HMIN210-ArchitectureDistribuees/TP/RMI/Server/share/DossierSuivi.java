//Participants : TRAN Thi Tra My - NGUYEN Huu Khang

package share;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DossierSuivi extends UnicastRemoteObject implements IDossierSuivi{
	private String texte;

	public DossierSuivi() throws RemoteException {
		this.texte = "défaut";
	}
	
	public DossierSuivi(String texte) throws RemoteException {
		this.texte = texte;
	}

	public String getTexte() throws RemoteException{
		return texte;
	}

	public void setTexte(String texte) throws RemoteException {
		this.texte = texte;
	}

	@Override
	public String toString(){
		return this.texte;
	}
}
