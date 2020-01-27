package TP1;

public abstract class AbstractDictionary implements IDictionary {
	protected Object[] keys;
	protected Object[] values;
	private int size;
	
	protected abstract int indexOf(Object key);	
	
	protected abstract  int newIndexOf(Object key);
	
	public Object get(Object key){
		if(containsKey(key))
			return values[indexOf(key)];
		else{
			return -1;
		}
	}
	
	public int size(){
		return this.size;
	}
	
	public void setSize(int s){
		size = s;
	}
	
	public AbstractDictionary put(Object key, Object value){
		if(containsKey(key)){
			values[indexOf(key)] = value;
			return this;
		}else{
			int index = newIndexOf(key);
			keys[index] = key;
			values[index] = value;
			this.size++;
			return this;
		}
	}
	
	public Boolean isEmpty(){
		return (size == 0);
	}
	
	public Boolean containsKey(Object key){
		return (indexOf(key) != -1);
	}
}
