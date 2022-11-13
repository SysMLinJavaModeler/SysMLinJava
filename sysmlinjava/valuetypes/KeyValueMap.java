package sysmlinjava.valuetypes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava representation of an key-value map as a SysML value type. It
 * uses/implements the jave {@code Map} interface and extends the
 * {@code SysMLValueType} to provide a familiar mapping construct that can be
 * used as a value in a block (extension of {@code SysMLBlock}) class.
 * <p>
 * As an extension of the {@code SysMLValueType}, the {@code KeyValueMap} can be
 * used for constraint parameters in parametric analysis. It can alse by used as
 * values and flows in blocks. SysMLinJava includes other value types for
 * collections such as the {@code List} and {@code Queue}.
 * 
 * @author ModelerOne
 *
 * @param <K> class of the keys in the map
 * @param <V> class of the values in the map
 */
public class KeyValueMap<K, V> extends SysMLValueType implements Map<K, V>
{
	/**
	 * Java map that implements the key-value map
	 */
	public Map<K, V> map;

	/**
	 * Constructor - constructs the map
	 */
	public KeyValueMap()
	{
		super();
		map = new HashMap<>();
	}

	@Override
	public int size()
	{
		return map.size();
	}

	@Override
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	@Override
	public V put(K key, V value)
	{
		return map.put(key, value);
	}

	/**
	 * Adds the specified key/value pair to the map and notifies all value change
	 * observers, if any
	 * 
	 * @param key   key of the key/value pair
	 * @param value value of the key/value pair
	 * @return value of the added key/value pair
	 */
	public V putAndNotify(K key, V value)
	{
		V result = map.put(key, value);
		notifyValueChangeObservers();
		return result;
	}

	@Override
	public V remove(Object key)
	{
		return map.remove(key);
	}

	/**
	 * Removes the key/value pair for the specified key from the map and notifies
	 * all value change observers, if any
	 * 
	 * @param key key of the key/value pair to be removed
	 * @return value of the removed key/value pair
	 */
	public V removeAndNotify(Object key)
	{
		V result = map.remove(key);
		notifyValueChangeObservers();
		return result;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m)
	{
		map.putAll(m);
	}

	/**
	 * Adds the key/value pairs in the specified map to the map and notifies all
	 * value change observers, if any
	 * 
	 * @param addedMap map containing the key/value pairs to be added to this map
	 */
	public void putAllAndNotify(Map<? extends K, ? extends V> addedMap)
	{
		map.putAll(addedMap);
		notifyValueChangeObservers();
	}

	@Override
	public void clear()
	{
		map.clear();
	}

	/**
	 * Clears the map of any key/value pairs and notifies all value change
	 * observers, if any
	 */
	public void clearAndNotify()
	{
		map.clear();
		notifyValueChangeObservers();
	}

	@Override
	public Set<K> keySet()
	{
		return map.keySet();
	}

	@Override
	public Collection<V> values()
	{
		return map.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet()
	{
		return map.entrySet();
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("KeyValueMap [map=");
		builder.append(map);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public boolean containsKey(Object key)
	{
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value)
	{
		return map.containsValue(value);
	}

	@Override
	public V get(Object key)
	{
		return map.get(key);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.KeyValuePair;
	}
}
