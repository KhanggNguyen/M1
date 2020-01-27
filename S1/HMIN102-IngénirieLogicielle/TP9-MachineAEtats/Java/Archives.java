package HMIN102.TP9;

import java.util.ArrayList;

public class Archives {
	private ArrayList<CycleSeminaires> anciensSeminaires = new ArrayList<CycleSeminaires>();
	private ArrayList<Adherent> anciensAdherents = new ArrayList<Adherent>();
	
	public Archives(){
		
	}

	public ArrayList<CycleSeminaires> getAnciensSeminaires() {
		return anciensSeminaires;
	}

	public void setAnciensSeminaires(ArrayList<CycleSeminaires> anciensSeminaires) {
		this.anciensSeminaires = anciensSeminaires;
	}

	public ArrayList<Adherent> getAnciensAdherents() {
		return anciensAdherents;
	}

	public void setAnciensAdherents(ArrayList<Adherent> anciensAdherents) {
		this.anciensAdherents = anciensAdherents;
	}
}
