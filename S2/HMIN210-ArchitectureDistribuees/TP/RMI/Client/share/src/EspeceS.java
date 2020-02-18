//Participants : TRAN Thi Tra My - NGUYEN Huu Khang

package share;

import java.io.*;
import java.rmi.Remote;

public class EspeceS extends Espece {
	private static final long serialVersionUID = 1L;
	public EspeceS(String nom_espece, int duree_de_vie){
		super(nom_espece, duree_de_vie);
	}

}