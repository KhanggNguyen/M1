package versionSimple;

import java.util.HashMap;

import java.rmi.registry.Registry;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

public class Server {

	public Server() {}


	public static void main(String args[]) {

		try {
			CabinetVeterinaire cv = new CabinetVeterinaire();
			Registry registry = LocateRegistry.createRegistry(1099);
			//Registry registry = LocateRegistry.getRegistry();
			if (registry==null){
				System.err.println("RmiRegistry not found");
			}else{
				registry.bind("CabinetVeterinaire", (Remote) cv);
				System.out.println("Server ready");
			}
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
