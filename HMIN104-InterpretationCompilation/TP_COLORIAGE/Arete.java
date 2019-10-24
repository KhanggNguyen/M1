package HMIN104.TP_Coloriage;

public class Arete {
	private Sommet x;
	private Sommet y;
	private Boolean actif = true;
	
	public Arete(Sommet s1, Sommet s2){
		x = s1;
		y = s2;
	}
	
	public Sommet getX(){
		return x;
	}
	
	public Sommet getY(){
		return y;
	}
	
	public void setX(Sommet s){
		x = s;
	}
	
	public void setY(Sommet s){
		y = s;
	}
}
