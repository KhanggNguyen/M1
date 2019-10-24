package TP1;


public class OrderedDico extends AbstractDictionary{
	
	//tableau toujours plein => pas de paramètre == mieux
	public OrderedDico(){
		setSize(0);
		values = new Object[0];
		keys = new Object[0];
	}
	
	protected int indexOf(Object key){
		for(int i=0; i<keys.length; i++){
			if(key.equals(keys[i])){
				return i;
			}
		}
		return -1;
	}	
	
	protected int newIndexOf(Object key){
		int s = this.size();
		if(s == keys.length){
			this.grow();
			return keys.length -1;
		}else{
			return s;
		}
		
	}
	
	public void grow(){
		Object[] keysTempo = new Object[keys.length+1];
		Object[] valuesTempo = new Object[values.length+1];
		for(int i=0; i<keys.length; i++){
			keysTempo[i] = keys[i];
			valuesTempo[i] = values[i];
		}
		keys = keysTempo;
		values = valuesTempo;
	}
	
	public static void main (String[] args) 
    { 
		OrderedDico od = new OrderedDico();
		od.put("key1", "value1");
		od.put("key2", "value2");
		od.put("key2", "value3");
		System.out.println(od.size());
		
    }
}
