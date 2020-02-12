package client;

import share.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;


public class Client {

	private Client() {}

	public static void main(String[] args) {
		
		String host = (args.length < 1) ? null : args[0]; //port par défaut 1099 si host = null

		System.setProperty("java.security.policy", "client.policy");
		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}

		try {
			Registry registry = LocateRegistry.getRegistry(host);
			ICabinetVeterinaire stub = (ICabinetVeterinaire) registry.lookup("CabinetVeterinaire");//Proxy
			//Espece chien = (Espece) registry.lookup("Chien");
			Espece chien = new Espece("Chien", 10);
			stub.creationAnimal("Pluto", "Toto", chien, "Alaska");
			IAnimal monChien = stub.recherche("Pluto");
			System.out.println(monChien.getInfos());
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}	
}
