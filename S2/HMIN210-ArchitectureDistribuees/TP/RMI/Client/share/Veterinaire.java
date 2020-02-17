package share;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Veterinaire extends UnicastRemoteObject implements IVeterinaire{

	public Veterinaire()throws RemoteException{
		
	}

	public void alert(String message){
		System.out.println(message);
	}
}