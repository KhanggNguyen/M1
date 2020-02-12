package server;

import java.util.HashMap;
import java.rmi.registry.Registry;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.RMISecurityManager;
import share.*;

public class Server {

	public Server() {}

	public static void main(String args[]) {
		//creation d'une list\e d'espece
		/*
		HashMap<String, Espece> listeEspeces = new HashMap<String, Espece>();
		Espece chien = new Espece("Chien", 10);
		Espece chat = new Espece("Chat", 12);
		listeEspeces.put("Chien", chien);
		listeEspeces.put("Chat", chat);
		*/
		
		System.setProperty("java.security.policy", "security.policy");

		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}
		
		System.setProperty( "java.rmi.server.codebase", "file://home//e20150004923//Documents//M1//M1-AIGLE//S2//HMIN210-ArchitectureDistribuees//TP//RMI//versionSimple/");

		try {
			CabinetVeterinaire cv = new CabinetVeterinaire();
			Registry registry = LocateRegistry.createRegistry(1099);
			//Registry registry = LocateRegistry.getRegistry();
			if (registry==null){
				System.err.println("RmiRegistry not found");
			}else{
				//registry.bind("Chien", (Remote) chien);
				//registry.bind("Chat", (Remote) chat);
				registry.bind("CabinetVeterinaire", (Remote) cv);
				System.out.println("Server ready");
			}
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
