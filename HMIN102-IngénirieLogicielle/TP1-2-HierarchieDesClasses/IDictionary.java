package TP1;
import java.lang.Object;

public interface IDictionary {
	public Object get(Object key);
	public IDictionary put(Object key, Object value);
	public Boolean isEmpty();
	public Boolean containsKey(Object key);

}
