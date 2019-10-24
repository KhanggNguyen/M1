package HMIN102.TP2;

import java.util.ArrayList;

public class Directory extends ElementStockage {
	private int basicSize = 4;
	private ArrayList<ElementStockage> liste;
	
	public Directory(String n){
		this.setNom(n);
	}
	
	@Override
	public int basicSize() {
		// TODO Auto-generated method stub
		return basicSize();
	}
	
	@Override
	public int size(){
		int total = basicSize();
		for(int i=0;i<liste.size();i++){
			total += liste.get(i).basicSize();
		}
		return total;
	}
	
	public void ls(){
		for(int i=0; i<liste.size(); i++){
			System.out.println(liste.get(i));
		}
	}
	
	public int nbElem(){
		return liste.size();
	}
	
	public String[] find(String name){
		String[] res = {};
		int cpt = 0;
		for(int i=0;i<liste.size();i++){
			if(liste.get(i).getNom().equals(name)){
				res[cpt] = liste.get(i).absolueAdress();
			}
		}
		return res;
	}
	
	public String[] findR(String name){
		String[] res = {};
		int cpt = 0;
		for(int i=0; i<liste.size();i++){
			if(liste.get(i).getNom().equals(name) && liste.get(i).basicSize() == 4){
				res[cpt] = liste.get(i).absolueAdress();
			}
		}
		return res;
	}

	public void addElem(ElementStockage e){
		liste.add(e);
	}
	
	public void removeElem(ElementStockage e){
		liste.remove(e);
	}
	
}
