package HMIN102.TP9;

import java.util.ArrayList;
import java.util.Stack;

public class CentreCulturel {
	private ArrayList<CycleSeminaires> listeCycleSeminaires = new ArrayList<CycleSeminaires>();
	private ArrayList<Adherent> listeAdherents = new ArrayList<Adherent>();
	
	public CycleSeminaires creationCycleSeminaire(String titre, String resume, int capacite, Stack<Creneau> creneaux){
		CycleSeminaires cs = new CycleSeminaires(titre, resume, capacite);
		cs.setCreneaux(creneaux);
		listeCycleSeminaires.add(cs);
		return cs;
	}
}
