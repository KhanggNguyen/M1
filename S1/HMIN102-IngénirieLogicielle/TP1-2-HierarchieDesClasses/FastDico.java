package TP1;

public class FastDico extends AbstractDictionary {

	//tableau toujours plein => pas de paramètre == mieux
	public FastDico(){
		setSize(0);
		values = new Object[4];
		keys = new Object[4];
	}
	
	//on suppose que size n'incremente seulement quand on ajoute
	//ou on fait un parcours et compare jusqu'à 1er element null du tableau key
	public int size(){
		int s = 0;
		for(int i =0; i<keys.length; i++){
			if(keys[i] != null){
				s++;
			}
		}
		setSize(s);
		return s;
	}
	
	public boolean mustGrow(){
		return 0.75 <= (size()/keys.length);
	}
	
	public void grow(){
		Object[] keysTempo = new Object[keys.length*5/4];
		Object[] valuesTempo = new Object[values.length*5/4];
		for(int i=0; i<keys.length; i++){
			keysTempo[i] = keys[i];
			valuesTempo[i] = values[i];
		}
		keys = keysTempo;
		values = valuesTempo;
	}
	
	protected int indexOf(Object key){
		int tabSize = keys.length;
		int hash = key.hashCode();
		hash = Math.abs(hash);
		int k = hash % tabSize;
		int i = 0;
		while(!key.equals(keys[k]) && i < tabSize){
			k = (k+1) % tabSize;
			i++;
		}
		if(i >= tabSize){
			return -1;
		}else{
			return k;
		}
	}	
	
	protected int newIndexOf(Object key){
		if(mustGrow()) grow(); //garantit que tabSize > 0
		int tabSize = keys.length;
		int hash = key.hashCode();
		hash = Math.abs(hash); // cas hash < 0
		int i = hash % tabSize;
		if(keys[i] == null) return i; // pas de conflit
		else{ //conflit hachage
			do { i = ((i+1) % tabSize);} // recherche sequentielle locale
			while(keys[i] != null);
			return i;
		}
	}
	
	public static void main (String[] args) 
    { 
		FastDico fdc = new FastDico();
		fdc.put("key1", "value1");
		fdc.put("key2", "value1");
		fdc.put("key3", "value1");
		fdc.put("key4", "value1");
		fdc.put("key5", "value1");
		fdc.put("key6", "value1");
		System.out.println(fdc.size());
    }
	
}
