package server;

import java.util.HashMap;
import java.rmi.registry.Registry;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.RMISecurityManager;
import share.*;

public class Server {

	public Server() {}

	public static void main(String args[]) {
		System.out.println("Début de RMI");
		System.setProperty("java.security.policy", "security.policy");

		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
			System.out.println("Security Manager est installé");
		}else{
			System.out.println("Security Manager a été déjà installé");
		}

		try {
			Registry registry = LocateRegistry.createRegistry(1099);
			//Registry registry = LocateRegistry.getRegistry();

			if (registry==null){
				System.err.println("RmiRegistry not found");
			}else{
				CabinetVeterinaire cv = new CabinetVeterinaire();
				registry.bind("CabinetVeterinaire", (Remote) cv);
				System.out.println("Server ready");
			}

		} catch (Exception e) {
			System.err.println("Server Remote Exception: " + e.toString());
			e.printStackTrace();
		}
		
		System.setProperty( "java.rmi.server.codebase", "file://home//e20150004923//Documents//M1//M1-AIGLE//S2//HMIN210-ArchitectureDistribuees//TP//RMI//versionSimple/");

		

		
	}
}
