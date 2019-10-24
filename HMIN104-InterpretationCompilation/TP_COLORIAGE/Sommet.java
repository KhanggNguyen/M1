//Coloriage.java
package HMIN104.TP_Coloriage;



import java.util.*;

class Sommet {
    private int couleur; //-1 si pas de couleur
    private static boolean ajoute = false;
    private String nom;
    private boolean actif;
    private boolean spille;
    private HashMap < Sommet, Boolean > voisins = new HashMap < Sommet, Boolean > ();
    private HashMap < Sommet, Boolean > prefs = new HashMap < Sommet, Boolean > ();


    public Sommet(String n) {
        setNom(n);
        setActif(true);
        setSpille(false);
        setCouleur(-1);
    }

    public boolean isSpille() {
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
        if (s.ajoute) {
            s.ajoute = false;
        } else {
            s.ajoute = true;
            n.addVoisins(this);
        }
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
        for (Boolean b: voisins.values()) {
            if (b.booleanValue())
                total++;
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
                desactiver(s.getKey());
                s.getKey().desactiver(this);
            }
        }
    }

    public void activerTousAretes() {
        for (Map.Entry < Sommet, Boolean > nc: voisins.entrySet()) {
            if (nc.getKey().getActif()) {
                this.activer(nc.getKey());
                nc.getKey().activer(this);
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
		return "Sommet " + nom + " : Status " + actif + " : Spiller? " + spille + " : Couleur " + couleur;
	}
}