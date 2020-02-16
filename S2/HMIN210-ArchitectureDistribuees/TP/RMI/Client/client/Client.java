package client;

import share.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Scanner;


public class Client {

	private Client() {}

	public static void createAnimal(ICabinetVeterinaire stub, Scanner sc) throws RemoteException{
		System.out.print("Veuillez saisir le nom de l'animal : ");
		sc.nextLine();//eviter le systeme ignore le scanner 
		String nom_animal = sc.nextLine();
		System.out.print("Veuillez saisir le nom de l'espèce : ");
		String nom_espece = sc.nextLine();
		System.out.print("Veuillez saisir la durée de vie de l'espèce : ");
		int duree_de_vie = sc.nextInt();
		System.out.print("Veuillez saisir le nom du maitre : ");
		sc.nextLine();//eviter le systeme ignore le scanner 
		String nom_maitre = sc.nextLine();
		System.out.print("Veuillez saisir son race : ");
		String race = sc.nextLine();
		stub.creationAnimal(nom_animal, nom_maitre, new Espece(nom_espece, duree_de_vie), race);
	}

	public static void rechercher(ICabinetVeterinaire stub, Scanner sc) throws RemoteException{
		System.out.print("Veuillez saisir le nom de l'animal : ");
		sc.nextLine();//eviter le systeme ignore le scanner 
		String nom_animal = sc.nextLine();
		IAnimal mon_animal = stub.recherche(nom_animal);
		System.out.println("1 - récupérer ses informations");
		System.out.println("2 - récupérer son dossier");
		System.out.println("3 - récupérer son espèce");
		System.out.println("4 - récupérer son maitre");
		System.out.println("5 - revenir au menu précédent");
		System.out.print("Veuillez choisir, par leur numérotation, un des options suivants : ");
		int opt_recherche = sc.nextInt();
		while(opt_recherche != 5 ){
			switch (opt_recherche) {
				case 1 : 
				System.out.println(mon_animal.getInfos());
				System.out.print("Veuillez choisir un option : ");
				opt_recherche = sc.nextInt();
				break;

				case 2 : 
				System.out.println(mon_animal.getDossier());
				System.out.print("Veuillez choisir un option : ");
				opt_recherche = sc.nextInt();
				break;

				case 3 : 
				System.out.println(mon_animal.getEspece());
				System.out.print("Veuillez choisir un option : ");
				opt_recherche = sc.nextInt();
				break;

				case 4 : 
				System.out.println(mon_animal.getNomMaitre());
				System.out.print("Veuillez choisir un option : ");
				opt_recherche = sc.nextInt();
				break;

				case 5 :
				break;

				default : 
				break;
			}
		}
	}

	public static int affichage_menu(Scanner sc){
		System.out.println("1 - Ajouter un animal");
		System.out.println("2 - Rechercher un animal par leur nom");
		System.out.println("3 - Quitter");
		System.out.print("Veuillez choisir par leur numérotation des options suivants : ");
		int opt = sc.nextInt();
		return opt;
	}

	public static void main(String[] args) {
		
		String host = (args.length < 1) ? null : args[0]; //port par défaut 1099 si host = null

		System.setProperty("java.security.policy", "client.policy");
		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}
		Scanner sc = new Scanner(System.in);

		try {
			Registry registry = LocateRegistry.getRegistry(host);
			ICabinetVeterinaire stub = (ICabinetVeterinaire) registry.lookup("CabinetVeterinaire");
			int opt = affichage_menu(sc);
			while(opt != 3){
				switch(opt){
					case 1 :
					createAnimal(stub, sc);
					opt = affichage_menu(sc);

					case 2 :
					rechercher(stub, sc);
					opt = affichage_menu(sc);

					case 3 : 
					break;

				}
			}
			
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}	
}
