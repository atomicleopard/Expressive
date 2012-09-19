/*
 *  Copyright (c) 2011 Nicholas Okunew
 *  All rights reserved.
 *  
 *  This file is part of the com.atomicleopard.expressive library
 *  
 *  The com.atomicleopard.expressive library is free software: you 
 *  can redistribute it and/or modify it under the terms of the GNU
 *  Lesser General Public License as published by the Free Software Foundation, 
 *  either version 3 of the License, or (at your option) any later version.
 *  
 *  The com.atomicleopard.expressive library is distributed in the hope
 *  that it will be useful, but WITHOUT ANY WARRANTY; without even
 *  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with the com.atomicleopard.expressive library.  If not, see 
 *  http://www.gnu.org/licenses/lgpl-3.0.html.
 */
package com.atomicleopard.expressive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * {@link MapKeys} is used to construct maps in a fluid manner. They can be
 * created using {@link Expressive#mapKeys(Object...)} and
 * {@link Expressive#mapKeys(List)}.
 * </p>
 * <p>
 * A map is created by invoking {@link #to(List)} or {@link #to(Object...)},
 * which will create a map by pairing the ordered list of keys supplied at
 * construction time against the ordered set of values passed to the 'to'
 * method.
 * </p>
 * <p>
 * The map created will have as many entries as keys. That is if more keys were
 * added than values, those keys without a value pairing will be mapped to null.
 * If more values are supplied than keys, these values will not be present in
 * the resulting map.
 * </p>
 * <p>
 * When both keys and values are added they are added as an ordered collection,
 * not as a set. This means that it is possible to add keys which are duplicates
 * as considered by their hashcode and equals methods. If this happens, the
 * resulting map will contain the key/value pair that was added last. for
 * example the map created by:
 * 
 * <pre>
 * map = Expressive.mapKeys(1, 2, 3, 1).to(&quot;a&quot;, &quot;b&quot;, &quot;c&quot;, &quot;d&quot;);
 * </pre>
 * 
 * will have three entries, 1 -> "d", 2 -> "b", 3 -> "c"
 * </p>
 * 
 * @param <K>
 */
public final class MapKeys<K> {
	private EList<K> keys;

	MapKeys(K... keys) {
		this.keys = new EListImpl<K>(keys);
	}

	MapKeys(List<K> keys) {
		this.keys = new EListImpl<K>(keys);
	}

	/**
	 * <p>
	 * A map is created by invoking {@link #to(List)} or {@link #to(Object...)},
	 * which will create a map by pairing the ordered list of keys supplied at
	 * construction time against the ordered set of values passed to the 'to'
	 * method.
	 * </p>
	 * <p>
	 * The map created will have as many entries as keys. That is if more keys
	 * were added than values, those keys without a value pairing will be mapped
	 * to null. If more values are supplied than keys, these values will not be
	 * present in the resulting map.
	 * </p>
	 * <p>
	 * When both keys and values are added they are added as an ordered
	 * collection, not as a set. This means that it is possible to add keys
	 * which are duplicates as considered by their hashcode and equals methods.
	 * If this happens, the resulting map will contain the key/value pair that
	 * was added last. for example the map created by:
	 * 
	 * <pre>
	 * map = Expressive.mapKeys(1, 2, 3, 1).to(&quot;a&quot;, &quot;b&quot;, &quot;c&quot;, &quot;d&quot;);
	 * </pre>
	 * 
	 * will have three entries, 1 -> "d", 2 -> "b", 3 -> "c"
	 * </p>
	 * 
	 * 
	 * @param <V>
	 * @param values
	 * @return
	 */
	public <V> Map<K, V> to(List<V> values) {
		Map<K, V> map = new HashMap<K, V>();
		for (int i = 0; i < keys.size(); i++) {
			K key = keys.get(i);
			V value = values.size() - 1 < i ? null : values.get(i);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * <p>
	 * A map is created by invoking {@link #to(List)} or {@link #to(Object...)},
	 * which will create a map by pairing the ordered list of keys supplied at
	 * construction time against the ordered set of values passed to the 'to'
	 * method.
	 * </p>
	 * <p>
	 * The map created will have as many entries as keys. That is if more keys
	 * were added than values, those keys without a value pairing will be mapped
	 * to null. If more values are supplied than keys, these values will not be
	 * present in the resulting map.
	 * </p>
	 * <p>
	 * When both keys and values are added they are added as an ordered
	 * collection, not as a set. This means that it is possible to add keys
	 * which are duplicates as considered by their hashcode and equals methods.
	 * If this happens, the resulting map will contain the key/value pair that
	 * was added last. for example the map created by:
	 * 
	 * <pre>
	 * map = Expressive.mapKeys(1, 2, 3, 1).to(&quot;a&quot;, &quot;b&quot;, &quot;c&quot;, &quot;d&quot;);
	 * </pre>
	 * 
	 * will have three entries, 1 -> "d", 2 -> "b", 3 -> "c"
	 * </p>
	 * 
	 * @param <V>
	 * @param values
	 * @return
	 */
	public <V> Map<K, V> to(V... values) {
		Map<K, V> map = new HashMap<K, V>();
		for (int i = 0; i < keys.size(); i++) {
			K key = keys.get(i);
			V value = values.length - 1 < i ? null : values[i];
			map.put(key, value);
		}
		return map;
	}

	/**
	 * @return the number of keys in this {@link MapKeys}
	 */
	public int size() {
		return keys.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keys == null) ? 0 : keys.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapKeys<?> other = (MapKeys<?>) obj;
		if (keys == null) {
			if (other.keys != null)
				return false;
		} else if (!keys.equals(other.keys))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return keys.toString();
	}

}
