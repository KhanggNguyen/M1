package share;

import java.io.*;
import java.rmi.Remote;

public class EspeceS extends Espece implements Remote, Serializable{
	private static int counter = 1L;

	public EspeceS(String nom){
		this.nom = nom;
	}
	
}