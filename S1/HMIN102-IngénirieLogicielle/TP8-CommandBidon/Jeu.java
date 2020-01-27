package HMIN102.TP8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


public class Jeu {
	private ArrayList<Bidon> listeBidon = new ArrayList<Bidon>();
	private HashMap<String, Command> historique = new HashMap<>();

	public ArrayList<Bidon> getListeBidon() {
		return listeBidon;
	}

	public void setListeBidon(ArrayList<Bidon> listeBidon) {
		this.listeBidon = listeBidon;
	}

	public HashMap<String, Command> getHistorique() {
		return historique;
	}

	public void setHistorique(HashMap<String, Command> historique) {
		this.historique = historique;
	}

	public void run(){
		Scanner myObj = new Scanner(System.in);
		while(true){
			String commandes = getChoixJoueur();
			effectuerChoixJoueur(commandes);
		}
	}
	
	public void init(){
		Scanner myObj = new Scanner(System.in);
		System.out.println("Veuillez saisir le nombre de bidons voulu");
		int total = myObj.nextInt();
		for(int i=0; i<total;i++){
			System.out.println("Veuillez saisir le capacite max de bidon " + (i+1));
			Float capacite = myObj.nextFloat();
			Bidon b = new Bidon(capacite, 0, "bidon"+(i+1));  
			listeBidon.add(b);
		}
	}
	
	public String getChoixJoueur(){
		Scanner myObj = new Scanner(System.in);
		System.out.println("Veuillez saisir votre command de type : 'nomBidon commande'");
		String commande = myObj.nextLine();
		return commande;
	}
	
	public void effectuerChoixJoueur(String commandes){
		String[] str_commande = commandes.split("\\s+");
		System.out.println(str_commande.length);
		String nomBidon = null;
		String commande = null;
		if(str_commande.length > 1){
			nomBidon = str_commande[0];
			commande = str_commande[1];
		}else{
			commande = str_commande[0];
		}
		
		Bidon b = null;
		for(int i=0;i<listeBidon.size();i++){
			if(listeBidon.get(i).getNomBidon().equals(nomBidon)){
				b = listeBidon.get(i);
			}
		}
		if(commande.equals("remplir")){
			if(b != null){
				Command c = new Remplir(b);
				c._do();
				historique.put(nomBidon, c);
			}
		}else if(commande.equals("vider")){
			if(b != null){
				Command c = new Vider(b);
				c._do();
				historique.put(nomBidon, c);
			}
		}else if(commande.equals("transvaser")){
			String nomBidon2 = str_commande[2];
			Bidon b2 = null;
			for(int i=0;i<listeBidon.size();i++){
				if(listeBidon.get(i).getNomBidon().equals(nomBidon2)){
					b2 = listeBidon.get(i);
				}
			}
			if(b != null){
				Command c = new Transvaser(b, b2);
				c._do();
				historique.put(nomBidon, c);
			}
		}else if(commande.equals("undo")){
			Command lastValue = null;
			for(Entry<String, Command> entry : historique.entrySet()) {
	            lastValue = entry.getValue();
	        }
			if(lastValue != null){
				lastValue.undo();
			}
		}
	}
	
	public boolean testGagne(){
		
		return false;
	}
	
	public static void main(String[] args){
		Jeu j = new Jeu();
		j.init();
		j.run();
	}
	
}
