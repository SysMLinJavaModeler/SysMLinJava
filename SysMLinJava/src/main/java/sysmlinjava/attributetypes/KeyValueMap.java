/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.attributetypes;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sysmlinjava.javaannotations.attributes.Operation;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava representation of an key-value map as a SysML value type. It
 * emulates the jave {@code Map} interface and extends the
 * {@code SysMLAttributeType} to provide a familiar mapping construct that can be
 * used as an attribute in a part (an extension of the {@code SysMLPart} class.
 * <p>
 * As an extension of the {@code SysMLAttributeType}, the {@code KeyValueMap} can be
 * used for parameters in parametric analysis. It can also by used as
 * attributes in parts or ports. SysMLinJava includes other attribute types for
 * collections such as the {@code ListOrdered} and {@code QueueFIFO}.
 * 
 * @author ModelerOne
 *
 * @param <K> class of the keys in the map
 * @param <V> class of the values in the map
 */
@SuppressWarnings("javadoc")
public class KeyValueMap<K, V> extends SysMLAttributeType
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

	@Operation
	public int size()
	{
		return map.size();
	}

	@Operation
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	@Operation
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
	@Operation
	public V putAndNotify(K key, V value)
	{
		V result = map.put(key, value);
		notifyAttributeObservers();
		return result;
	}

	@Operation
	public V remove(K key)
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
	public V removeAndNotify(K key)
	{
		V result = map.remove(key);
		notifyAttributeObservers();
		return result;
	}

	@Operation
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
		notifyAttributeObservers();
	}

	/**
	 * Clears the map of any key/value pairs
	 */
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
		notifyAttributeObservers();
	}

	@Operation
	public Set<K> keySet()
	{
		return map.keySet();
	}

	@Operation
	public Collection<V> values()
	{
		return map.values();
	}

	/**
	 * Returns whether or not map contains a value for specified key
	 * @param key key mapped to value
	 * @return true if map contains value for key, false otherwise
	 */
	@Operation
	public boolean containsValueFor(K key)
	{
		return map.containsKey(key);
	}

	/**
	 * Returns whether or not map contains specified value
	 * @param value mapped value
	 * @return true if map contains value, false otherwise
	 */
	@Operation
	public boolean contains(V value)
	{
		return map.containsValue(value);
	}

	/**
	 * Returns value for specified key
	 * @param key key mapped to value
	 * @return value for mapped key
	 */
	@Operation
	public V get(K key)
	{
		return map.get(key);
	}

	/**
	 * Returns an instance of the map containing up to 25 specified key-value entries
	 * 
	 * @param <K> type of the key
	 * @param <V> type of the value
	 * @param k0 instance of key 0, or null if no key-value entry
	 * @param v0 instance of value 0, ignored if key 0 is null
	 * @return instance of KeyValueMap for specified key-value entries
	 */
	public static <K,V> KeyValueMap<K,V> of(
		K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4,
		K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9,
		K k10, V v10, K k11, V v11, K k12, V v12, K k13, V v13, K k14, V v14,
		K k15, V v15, K k16, V v16, K k17, V v17, K k18, V v18, K k19, V v19,
		K k20, V v20, K k21, V v21, K k22, V v22, K k23, V v23, K k24, V v24)
	{
		KeyValueMap<K,V> result = new KeyValueMap<>();
		if(k0 != null) result.map.put(k0,v0);
		if(k1 != null) result.map.put(k1,v1);
		if(k2 != null) result.map.put(k2,v2);
		if(k3 != null) result.map.put(k3,v3);
		if(k4 != null) result.map.put(k4,v4);
		if(k5 != null) result.map.put(k5,v5);
		if(k6 != null) result.map.put(k6,v6);
		if(k7 != null) result.map.put(k7,v7);
		if(k8 != null) result.map.put(k8,v8);
		if(k9 != null) result.map.put(k9,v9);
		if(k10 != null) result.map.put(k10,v10);
		if(k11 != null) result.map.put(k11,v11);
		if(k12 != null) result.map.put(k12,v12);
		if(k13 != null) result.map.put(k13,v13);
		if(k14 != null) result.map.put(k14,v14);
		if(k15 != null) result.map.put(k15,v15);
		if(k16 != null) result.map.put(k16,v16);
		if(k17 != null) result.map.put(k17,v17);
		if(k18 != null) result.map.put(k18,v18);
		if(k19 != null) result.map.put(k19,v19);
		if(k20 != null) result.map.put(k20,v20);
		if(k21 != null) result.map.put(k21,v21);
		if(k22 != null) result.map.put(k22,v22);
		if(k23 != null) result.map.put(k22,v23);
		if(k24 != null) result.map.put(k24,v24);
		
		return result;
	}

	/**
	 * Returns an instance of the map containing unlimited number of specified key-value entries
	 * 
	 * @param <K> type of the key
	 * @param <V> type of the value
	 * @param keys list of the keys, each corresponding to a value in {@code values} with same index
	 * @param values list of the values, each corresponding to a key in {@code keys} with same index
	 * @return instance of KeyValueMap for specified key-value entries
	 */
	public static <K,V> KeyValueMap<K,V> of(List<K> keys, List<V> values)
	{
		KeyValueMap<K,V> result = new KeyValueMap<>();
		int size = Math.max(keys.size(), values.size());
		for(int i=0; i<size; i++)
			result.put(keys.get(i), values.get(i));
		return result;
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.KeyValuePair;
	}

	@Operation
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("KeyValueMap [map=");
		builder.append(map);
		builder.append("]");
		return builder.toString();
	}
}
