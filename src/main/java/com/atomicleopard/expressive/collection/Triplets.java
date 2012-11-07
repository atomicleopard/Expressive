package com.atomicleopard.expressive.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * {@link Triplets} is a simple implementation allowing three correlated values to be stored against eachother. It is identical in behaviour to a {@link Map} with two key values, rather than one.
 * 
 * @param <K1>
 * @param <K2>
 * @param <V>
 */
public class Triplets<K1, K2, V> {
	private Map<Pair<K1, K2>, V> delegate;

	public Triplets() {
		this(new HashMap<Pair<K1, K2>, V>());
	}

	public Triplets(Map<Pair<K1, K2>, V> delegate) {
		if (delegate == null) {
			throw new NullPointerException(String.format("Cannot create a %s with a null delegate", Triplets.class.getSimpleName()));
		}
		this.delegate = delegate;
	}

	public void put(K1 k1, K2 k2, V v) {
		delegate.put(pair(k1, k2), v);
	}

	public void putAll(Triplets<K1, K2, V> triplets) {
		delegate.putAll(triplets.delegate);
	}

	public void remove(K1 k1, K2 k2) {
		delegate.remove(pair(k1, k2));
	}

	public V get(K1 k1, K2 k2) {
		return delegate.get(pair(k1, k2));
	}

	public void clear() {
		delegate.clear();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public int size() {
		return delegate.size();
	}

	public boolean containsKey(K1 k1, K2 k2) {
		return delegate.containsKey(pair(k1, k2));
	}

	public boolean containsValue(Object value) {
		return delegate.containsValue(value);
	}

	public Collection<V> values() {
		return delegate.values();
	}

	public Set<Pair<K1, K2>> keySet() {
		return delegate.keySet();
	}

	public Set<Entry<Pair<K1, K2>, V>> entrySet() {
		return delegate.entrySet();
	}

	private Pair<K1, K2> pair(K1 k1, K2 k2) {
		return new Pair<K1, K2>(k1, k2);
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triplets other = (Triplets) obj;
		if (delegate == null) {
			if (other.delegate != null)
				return false;
		} else if (!delegate.equals(other.delegate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return delegate.toString();
	}
}
