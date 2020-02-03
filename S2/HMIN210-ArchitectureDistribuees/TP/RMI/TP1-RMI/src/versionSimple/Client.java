package versionSimple;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

public class Client {

	private Client() {}

	public static void main(String[] args) {
		//création d'une liste d'espèce
		HashMap<String, Espece> listeEspeces = new HashMap<String, Espece>();
		Espece chien = new Espece("Chien", 10);
		Espece chat = new Espece("Chat", 12);
		listeEspeces.put("Chien", chien);
		listeEspeces.put("Chat", chat);

		String host = (args.length < 1) ? null : args[0]; //port par défaut 1099 si host = null
		
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			ICabinetVeterinaire stub = (ICabinetVeterinaire) registry.lookup("CabinetVeterinaire");
			Animal a = new Animal("Pluto", "Toto", listeEspeces.get("Chien"), "Alaska");
			stub.addAnimal(a);
			System.out.println(stub.recherche("Pluto"));
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}	
}
