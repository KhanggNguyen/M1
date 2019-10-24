package HMIN102.TP2;

import java.util.ArrayList;

public abstract class ElementStockage {
	private String nom;
	private ElementStockage parent = null;
	
	public int size(){
		return basicSize();
	}
	
	public String absolueAdress(){
		if(parent != null){
			return parent.getNom() + nom;
		}else{
			return nom;
		}
	}
	
	public abstract int basicSize();

	public String getNom(){
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public ElementStockage getParent(){
		return parent;
	}
	
	public void setParent(ElementStockage p){
		parent = p;
	}
	
}
