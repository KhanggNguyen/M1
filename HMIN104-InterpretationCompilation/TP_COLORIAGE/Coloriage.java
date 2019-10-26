//Coloriage.java
//NGUYEN HUU KHANG
//TRAN THI TRA MY

package HMIN104.TP_Coloriage;

import java.util.*;

class Sommet {
    private int couleur; //-1 si pas de couleur
    private String nom;
    private boolean actif;
    private boolean spille;
    private boolean hasPref;
    private HashMap < Sommet, Boolean > voisins = new HashMap < Sommet, Boolean > ();
    private HashMap < Sommet, Boolean > prefs = new HashMap < Sommet, Boolean > ();


    public Sommet(String n) {
        setNom(n);
        setActif(true);
        setSpille(false);
        setCouleur(-1);
        setHasPref(false);
    }

    public boolean estSpille() {
        return spille;
    }

    public void setSpille(boolean spille) {
        this.spille = spille;
    }

    public HashMap < Sommet, Boolean > getVoisins() {
        return this.voisins;
    }

    public void addVoisin(Sommet s) {
        voisins.put(s, true);
    }

    public HashMap < Sommet, Boolean > getPrefs() {
        return this.prefs;
    }

    public void addPref(Sommet s) {
        prefs.put(s, true);
    }

    public int getCouleur() {
        return couleur;
    }

    public String getNom() {
        return nom;
    }

    public void setCouleur(int c) {
        couleur = c;
    }

    public void setNom(String n) {
        nom = n;
    }

    public int getDegre() {
        int total = 0;
        for (Boolean b : voisins.values()) {
            if (b.booleanValue()) {
            	total++;
            } 
        }
        return total;
    }

    public boolean getActif() {
        return actif;
    }

    public void setActif(boolean status) {
        actif = status;
    }

    public void desactiverTousAretes() {
        for (Map.Entry < Sommet, Boolean > s: voisins.entrySet()) {
            if (s.getKey().getActif()) {
                this.desactiver(s.getKey());
                s.getKey().desactiver(this);
            }
        }
    }

    public void activerTousAretes() {
        for (Map.Entry<Sommet, Boolean> s: voisins.entrySet()) {
            if (s.getKey().getActif()) {
                this.activer(s.getKey());
                s.getKey().activer(this);
            }
        }
    }

    public void desactiver(Sommet s) {
        voisins.put(s, false);
    }
    public void activer(Sommet s) {
        voisins.put(s, true);
    }
    
    @Override
	public String toString(){
		return "Sommet " + nom + " | Actif? " + actif + " | Spiller? " + spille + " | Couleur " + couleur;
	}
    
    public boolean isHasPref() {
		return hasPref;
	}

	public void setHasPref(boolean hasPref) {
		this.hasPref = hasPref;
	}
}

class Coloriage{
	private ArrayList<Sommet> listeSommet = new ArrayList<Sommet>();
  
	public Coloriage(){
	}
	
	public ArrayList<Sommet> getListeSommet(){
		return listeSommet;
	}
  
	public void addSommet(Sommet s){
		listeSommet.add(s);
	}
	
	public void reinitialiser() {
		for(Sommet s : listeSommet) {
	        s.setActif(true);
	        s.setSpille(false);
	        s.setCouleur(-1);
		}
	}
	  
	public void colorier(int k){
		reinitialiser();
		colorierLesSommets(k);
		colorierSommetSpille(k);
	}
	
	public void colorierLesSommets(int k){
		Sommet s = rechercheSommetTrivial(k);
		
		if(s==null) {
			s = plusGrandSommet();
			if(s == null) {
				return;
			}else {
				s.setSpille(true);
			}
		}
		
		s.setActif(false);
		s.desactiverTousAretes();
		colorierLesSommets(k);
		if(!s.estSpille()) {
			s.activerTousAretes();
			colorierSommet(s,k);
			s.setActif(true);
		}
	}
	
	protected void colorierSommet(Sommet s, int k){
		ArrayList<Boolean> couleurVoisins = new ArrayList<Boolean>(k);
		
		for(int i=0; i<k; ++i){
			couleurVoisins.add(false);
		}
		
		for(Map.Entry<Sommet,Boolean> s2 : s.getVoisins().entrySet()){
			if(s2.getKey().getActif()){
				if(s2.getValue().booleanValue()){
					couleurVoisins.set(s2.getKey().getCouleur(),true);
				}
			}
		}
		
		Boolean contientCouleur=false;
		if(s.isHasPref()) {
			colorierSommetPref(s,k, couleurVoisins);
		}
		
		if(!contientCouleur) {
			for(int i=0;i<k;++i){
				if(!couleurVoisins.get(i).booleanValue()){
					s.setCouleur(i);
					break;
				}
			}
		}
	}
	
	public boolean colorierSommetPref(Sommet s, int k, ArrayList<Boolean> couleurVoisins) {
		ArrayList<Boolean> couleurPrefs;
		couleurPrefs=new ArrayList<Boolean>(k);
		for(int i=0;i<k;++i){
			couleurPrefs.add(false);
		}
			
		for(Map.Entry<Sommet,Boolean> s2 : s.getPrefs().entrySet()){	
			if(s2.getKey().getCouleur()!=-1){
				couleurPrefs.set(s2.getKey().getCouleur(),true);
			}
		}
		for(int i=0;i<k;++i){
			if(couleurPrefs.get(i).booleanValue() && !couleurVoisins.get(i).booleanValue()){
				s.setCouleur(i);
				return true;		
			}
		}
		return false;
	}
	
	public void colorierSommetSpille(int k) {
		for(Sommet s : listeSommet) {
			if(s.estSpille()) {
				s.activerTousAretes();
				colorierSommet(s, k);
				if(s.getCouleur() == -1) {
					s.desactiverTousAretes();
				}else {
					s.setActif(true);
					s.setSpille(false);
				}
			}
		}
	}
	
	private Sommet plusGrandSommet(){
		Sommet max=listeSommet.get(0);
		for(Sommet s : listeSommet){
			if(s.getActif() && (s.getDegre()>max.getDegre())){
				max=s;
			}
		}
		if(max.getActif()){
			return max;
		}
		else{
			return null; 
		}
	}
	
	private Sommet rechercheSommetTrivial(int k){
		for(Sommet s : listeSommet){
			if(s.getActif() && (s.getDegre() < k)){
				return s;
			}
		}
		return null;
	}
	
	public void separation() {
		System.out.println("----------------------------------------------------");
	}
	
	public void affichage(){
		for(int i=0; i<listeSommet.size(); i++){
			System.out.println(listeSommet.get(i));
		}
	}
	
	public Coloriage graphe1() {
		Sommet x = new Sommet("x"); 
		Sommet y = new Sommet("y");
		Sommet v = new Sommet("v");
		Sommet t = new Sommet("t");
		Sommet u = new Sommet("u");
		Sommet z = new Sommet("z");
		
		x.addVoisin(v);x.addVoisin(y);x.addVoisin(u);
		y.addVoisin(x);y.addVoisin(u);y.addVoisin(t);
		v.addVoisin(x);v.addVoisin(t);v.addVoisin(z);
		t.addVoisin(v);t.addVoisin(y);
		u.addVoisin(x);u.addVoisin(y);
		z.addVoisin(v);
		
		t.setHasPref(true);
		u.setHasPref(true);
		u.addPref(t);
		t.addPref(u);
		
		Coloriage graphe = new Coloriage();
		graphe.addSommet(x);
		graphe.addSommet(y);
		graphe.addSommet(v);
		graphe.addSommet(t);
		graphe.addSommet(u);
		graphe.addSommet(z);
		
		return graphe;
	}
	
	public Coloriage graphe2() {
		Sommet x = new Sommet("x");
		Sommet y = new Sommet("y");
		Sommet z = new Sommet("z");
		Sommet t = new Sommet("t");
		
		x.addVoisin(y);x.addVoisin(t);
		y.addVoisin(x);y.addVoisin(z);
		z.addVoisin(y);z.addVoisin(t);
		t.addVoisin(x);t.addVoisin(z);
		
		Coloriage graphe = new Coloriage();
		graphe.addSommet(x);
		graphe.addSommet(y);
		graphe.addSommet(z);
		graphe.addSommet(t);
		
		return graphe;
	}
	
	public static void main(String[] args){
		Coloriage graphe1 = new Coloriage().graphe1();
		
		graphe1.colorier(3);
		graphe1.affichage();
		
		graphe1.separation();
		
		graphe1.colorier(2);
		graphe1.affichage();
		
		graphe1.separation();
		
		Coloriage graphe2 = new Coloriage().graphe2();
		
		graphe2.colorier(2);
		graphe2.affichage();
		
		
		
	}
}
