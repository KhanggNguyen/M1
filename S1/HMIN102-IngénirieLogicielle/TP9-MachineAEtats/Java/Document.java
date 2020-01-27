package HMIN102.TP9;

import java.util.ArrayList;

public class Document {
	private ArrayList<CycleSeminaires> listeCycleSeminaires = new ArrayList<CycleSeminaires>();
	private String titre;
	
	public Document(String t){
		setTitre(t);
	}

	private void setTitre(String t) {
		this.titre = t;
	}
	
	public String getTitre() {
		return titre;
	}

	public ArrayList<CycleSeminaires> getListeCycleSeminaires() {
		return listeCycleSeminaires;
	}
	
	public void addCycleSeminaires(CycleSeminaires cs){
		listeCycleSeminaires.add(cs);
	}

	public void setListeCycleSeminaires(ArrayList<CycleSeminaires> listeCycleSeminaires) {
		this.listeCycleSeminaires = listeCycleSeminaires;
	}

	
	
}
