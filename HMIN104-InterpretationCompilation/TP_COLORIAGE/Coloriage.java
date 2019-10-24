//Coloriage.java

package HMIN104.TP_Coloriage;

import java.util.*;

class Coloriage{
	private ArrayList<Arete> listeArete = new ArrayList<Arete>();
	private ArrayList<Sommet> listeSommet = new ArrayList<Sommet>();
  
	public Coloriage(){
	}
	
  
	public ArrayList<Arete> getListeArete(){
		return listeArete;
	}
  
	public void addArete(Arete a){
		listeArete.add(a);
	}
	
	public ArrayList<Sommet> getListeSommet(){
		return listeSommet;
	}
  
	public void addSommet(Sommet s){
		listeSommet.add(s);
	}
	
	public void reactiverSommets(){
		for(int i=0; i<listeSommet.size(); i++){
			listeSommet.get(i).setActif(true);
			listeSommet.get(i).setCouleur(0);
		}
	}
	
	public int nbSommetActif(){
		int totalActif = 0;
		for(int i=0; i<listeSommet.size();i++){
			if(listeSommet.get(i).getActif()){
				totalActif++;
			}
		}
		return totalActif;
	}
	
	public int plusPetitSommet(){
		int index = 0;
		for(int i=0; i<listeSommet.size(); i++){
			if(listeSommet.get(i).getNbVoisin() < listeSommet.get(index).getNbVoisin() && listeSommet.get(i).getActif()){
				index = i;
			}
		}
		return index;
	}
	
	public int nbSommetColorie(){
		int totalColorie = 0;
		for(int i=0; i < listeSommet.size(); i++){
			if(listeSommet.get(i).getCouleur() != 0){
				totalColorie++;
			}
		}
		return totalColorie;
	}
  
	public void colorier(int k){
		reactiverSommets();
		int index = colorier1erElement(k);
		listeSommet.get(index).setCouleur(k-k+1);
		ArrayList<Sommet> s_choisis = new ArrayList<Sommet>();
		s_choisis.add(listeSommet.get(index));
		while(nbSommetColorie() != listeSommet.size()){
			for(Sommet s : s_choisis ){
				s_choisis.remove(s);
				for(Sommet voisin : s.getVoisins()){
					s_choisis.add(voisin);
					if(!voisin.getActif() && voisin.getCouleur() == 0){
						
					}
				}
			}
		}
	}
	
	public void spillerSommet(int k){
		int index = 0;
		boolean trouve = false;
		while(index < listeSommet.size() && !trouve){
			if(listeSommet.get(index).getNbVoisin() > k && listeSommet.get(index).getActif()){
				listeSommet.get(index).setCouleur(-1);
				listeSommet.get(index).setActif(false);
				trouve = true;
			}
			index++;
		}
	}
	
	protected void colorierSommet(Sommet s, int k){
		ArrayList<Boolean> couleurVoisins = new ArrayList<Boolean>(k);
		for(int i=0; i<k; i++){
			couleurVoisins.add(new Boolean(Boolean.FALSE));
		}
		for(Sommet voisin : s.getVoisins()){
			if(voisin.getActif()){//si actif = posseder une couleur
				
			}
		}
	}
  
	public int colorier1erElement(int k){
		Sommet s = listeSommet.get(plusPetitSommet());
		int index = plusPetitSommet();
		while(nbSommetActif() != 1){
			if(s.getNbVoisin() <= k && s.getActif()){
				s.setActif(false);
				s = listeSommet.get(plusPetitSommet());
				
			}
			index = plusPetitSommet();
			spillerSommet(k);
		}
		return index;
	}
	
	public void affichage(){
		for(int i=0; i<listeSommet.size(); i++){
			System.out.println("Sommet " + listeSommet.get(i).getNom() + " : Status " + listeSommet.get(i).getActif() + " : Couleur " + listeSommet.get(i).getCouleur());
		}
	}
	
	public static void main(String[] args){
		Sommet x = new Sommet("x"); 
		Sommet y = new Sommet("y");
		Sommet v = new Sommet("v");
		Sommet u = new Sommet("u");
		Sommet t = new Sommet("t");
		Sommet z = new Sommet("z");
		
		x.addVoisin(v);x.addVoisin(y);x.addVoisin(u);
		y.addVoisin(x);y.addVoisin(u);y.addVoisin(t);
		v.addVoisin(x);v.addVoisin(t);v.addVoisin(z);
		u.addVoisin(x);u.addVoisin(y);
		t.addVoisin(v);t.addVoisin(y);
		z.addVoisin(v);
		
		Coloriage graphe = new Coloriage();
		graphe.addSommet(x);
		graphe.addSommet(y);
		graphe.addSommet(v);
		graphe.addSommet(u);
		graphe.addSommet(t);
		graphe.addSommet(z);
		
		graphe.colorier(3);
		graphe.affichage();
		
	}
}
