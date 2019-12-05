package HMIN102.TP9;

import java.util.ArrayList;
import java.util.Stack;

public class CycleSeminaires{

	private String titre;
	private String resume;
	private int nbInscrits;
	private int capacite;
	private boolean estOuvert = true;
	private ArrayList<Adherent> listeInscrits = new ArrayList<Adherent>();
	private ArrayList<Document> listeDocuments = new ArrayList<Document>();
	private Stack<Adherent> listeAttente = new Stack<Adherent>();
	private Stack<Creneau> creneaux = new Stack<Creneau>();
	
	public CycleSeminaires(String titre, String resume, int c){
		setNbInscrits(0);
		setCapacite(c);
	}
	
	public String getTitre() {
		return titre;
	}



	public void setTitre(String titre) {
		this.titre = titre;
	}



	public String getResume() {
		return resume;
	}



	public void setResume(String resume) {
		this.resume = resume;
	}



	public int getNbInscrits() {
		return nbInscrits;
	}



	public void setNbInscrits(int nbInscrits) {
		this.nbInscrits = nbInscrits;
	}



	public int getCapacite() {
		return capacite;
	}



	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	
	public boolean isEstOuvert() {
		return estOuvert;
	}

	public void setEstOuvert(boolean estOuvert) {
		this.estOuvert = estOuvert;
	}

	public ArrayList<Adherent> getListeInscrits() {
		return listeInscrits;
	}

	public void setListeInscrits(ArrayList<Adherent> listeInscrits) {
		this.listeInscrits = listeInscrits;
	}

	public Stack<Adherent> getListeAttente() {
		return listeAttente;
	}

	public void setListeAttente(Stack<Adherent> listeAttente) {
		this.listeAttente = listeAttente;
	}

	public Stack<Creneau> getCreneaux() {
		return creneaux;
	}
	
	public void addCreneaux(Creneau c){
		creneaux.addElement(c);
	}

	public void setCreneaux(Stack<Creneau> creneaux) {
		this.creneaux = creneaux;
	}

	private void ajouterListeAttente(Adherent a){
		listeAttente.push(a);
	}
	
	private Adherent popListeAttente(){
		Adherent attente = listeAttente.lastElement();
		listeAttente.pop();
		return attente;
	}
	
	private void ajouterInscrit(Adherent a){
		listeInscrits.add(a);
		nbInscrits++;
	}
	
	private void retireInscrit(Adherent a){
		listeInscrits.remove(a);
		nbInscrits--;
	}
	
	public void Inscription(Adherent a){
		if(isEstOuvert()){
			if(listeInscrits.size() == capacite){
				ajouterListeAttente(a);
			}else{
				ajouterInscrit(a);
			}
		}else{
			System.out.println("La reservation est cloturé");
		}
	}
	
	public void desistement(Adherent a){
		if(isEstOuvert()){
			retireInscrit(a);
			if(nbInscrits > 0){
				ajouterInscrit(popListeAttente());
			}
		}else{
			abandon(a);
		}
		
	}

	public void archiver(){
		
	}
	
	public void abandon(Adherent a){
		if(!isEstOuvert()){
			retireInscrit(a);
			if(nbInscrits <= 5 ){
				annuler();
			}
		}else{
			desistement(a);
		}
		
	}
	
	public void ouvertureReservation(){
		this.estOuvert = true;
	}
	
	public void clotureReservation(){
		this.estOuvert = false;
		if(nbInscrits < 10){
			System.out.println("Nb inscrits sont < 10. On va donc annuler le cycle");
			annuler();
		}
	}
	
	public void annuler(){
		System.out.println("Le cycle est annulé");
		archiver();
		for(int i=0; i < nbInscrits; i++){
			listeInscrits.remove(i);
		}
	}
	
	public void ajouterDocument(Document d){
		if(!isEstOuvert()){
			listeDocuments.add(d);
		}else{
			System.out.println("Nous sommes toujours en période de reservation");
		}
	}
}
