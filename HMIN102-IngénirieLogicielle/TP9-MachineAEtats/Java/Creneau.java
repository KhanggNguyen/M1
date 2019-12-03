package HMIN102.TP9;

public class Creneau {
	private int heureDebut;
	private int heureFin;
	
	public Creneau(int deb, int fin){
		setHeureDebut(deb);
		setHeureFin(fin);
	}

	public int getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(int heureDebut) {
		this.heureDebut = heureDebut;
	}

	public int getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(int heureFin) {
		this.heureFin = heureFin;
	}
	
	
}
