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

import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * {@link Expressive} is designed to allow coders to write more expressive code, focusing on what they want to do with collections of objects, rather than the mechanics of the manipulation of the Java
 * Collections API.
 * </p>
 * <p>
 * In this vein, {@link Expressive} supplies static methods for the easy creation of different collection types, and returns types compatible with the Java Collections API that allow for easier use
 * and manipulation.
 * </p>
 * 
 * @see EList
 */
public class Expressive {
	Expressive() {
	}

	/**
	 * <p>
	 * Convenience method for creating an array.
	 * </p>
	 * 
	 * @param <T>
	 * @param values
	 *            the objects to be placed in an array
	 * @return an array containing of the given items in the given order
	 */
	public static <T> T[] array(T... values) {
		return (T[]) values;
	}

	/**
	 * <p>
	 * Convenience method for creating an array from a collection.
	 * </p>
	 * 
	 * @param <T>
	 * @param values
	 *            a collection of objects to be placed in an array
	 * @return an array containing of the given items in the given order
	 * @see Collection#toArray()
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] array(Collection<T> values) {
		return (T[]) values.toArray();
	}

	/**
	 * <p>
	 * Convenience method for creating a collection.
	 * </p>
	 * 
	 * @param <T>
	 * @param values
	 *            the objects to be placed in a collection
	 * @return a collection containing the given items
	 */
	public static <T> Collection<T> collection(T... values) {
		return list(values);
	}

	/**
	 * <p>
	 * Convenience method for creating a {@link Set}.
	 * </p>
	 * 
	 * @param <T>
	 * @param values
	 *            the objects to be placed in a set
	 * @return a {@link Set} containing the given items
	 */
	public static <T> Set<T> set(T... values) {
		Set<T> hashSet = new HashSet<T>(values.length);
		for (T t : values) {
			hashSet.add(t);
		}
		return hashSet;
	}

	/**
	 * <p>
	 * Returns a map based on the inputs interpreted as key-value pairs. <br/>
	 * i.e.
	 * 
	 * <pre>
	 * Map&lt;String, Integer&gt; result = map(key1, value1, key2, value2)
	 * </pre>
	 * 
	 * </p>
	 * <p>
	 * Due to the nature of mixed type arrays, any object can be used as a key or value pair, regardless of whether it matches the key or value generic types for the resulting map. Inserting the
	 * incorrect type will not result in a failure. Care should be taken, or class cast exceptions can occur at execution time. For compile time type safery, prefer {@link #mapKeys(Object...)}
	 * </p>
	 * <p>
	 * If an uneven number of values are supplied, the final key will be omitted from the map.
	 * </p>
	 * 
	 * @param values
	 *            an alternating set of key/value pairs matching the desired
	 *            resulting generic types
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> map(Object... values) {
		Map<K, V> map = new HashMap<K, V>();
		for (int i = values.length / 2 * 2; i > 0; i -= 2) {
			K key = (K) values[i - 2];
			V value = (V) values[i - 1];
			map.put(key, value);
		}
		return map;
	}

	/**
	 * <p>
	 * Supports a fluid syntax for creating maps conveniently. Unlike {@link #map(List)}, also ensures compile time type safety using generics.
	 * </p>
	 * <p>
	 * Returns a {@link MapKeys}, which will produce a {@link Map} when the {@link MapKeys#to(List)} method is invoked.
	 * </p>
	 * 
	 * @param <K>
	 * @param keys
	 *            the set of keys to be present in the map produced by the
	 *            resulting {@link MapKeys}
	 * @return a {@link MapKeys} with the specified keys
	 * 
	 * @see MapKeys#to(List)
	 */
	public static <K> MapKeys<K> mapKeys(List<K> keys) {
		return new MapKeys<K>(keys);
	}

	/**
	 * <p>
	 * Supports a fluid syntax for creating maps conveniently. Unlike {@link #map(Object...)}, also ensures compile time type safety using generics.
	 * </p>
	 * <p>
	 * Returns a {@link MapKeys}, which will produce a {@link Map} when the {@link MapKeys#to(Object...)} method is invoked.
	 * </p>
	 * 
	 * @param <K>
	 * @param keys
	 *            the set of keys to be present in the map produced by the
	 *            resulting {@link MapKeys}
	 * @return a {@link MapKeys} with the specified keys
	 * 
	 * @see MapKeys#to(List)
	 */
	public static <K> MapKeys<K> mapKeys(K... keys) {
		return new MapKeys<K>(keys);
	}

	/**
	 * <p>
	 * Convenience method for creating an array containing the concatenated contents of the given set of arrays.
	 * </p>
	 * 
	 * @param values
	 *            the set of arrays to be concatenated together. Null array
	 *            entries will be ignored.
	 * @return an {@link EList} containing of the contents of the given items in
	 *         the given order
	 */
	public static <T> EList<T> flatten(T[]... values) {
		int len = 0;
		for (T[] arr : values) {
			if (arr != null) {
				len += arr.length;
			}
		}
		EList<T> list = new EListImpl<T>(len);
		for (T[] arr : values) {
			if (arr != null) {
				list.addItems(arr);
			}
		}
		return list;
	}

	/**
	 * <p>
	 * Flattens the given collections of collections into a single list containing all entries.
	 * </p>
	 * <p>
	 * This is useful in the scenario where we have grouped or categorised collections, such as a map of lists, when we want to search, sort, or iterate all entries.
	 * </p>
	 * <p>
	 * For example:
	 * 
	 * <pre>
	 * Map&lt;String, List&lt;String&gt;&gt; referenceInformation;
	 * Collection&lt;List&lt;String&gt;&gt; values = referenceInformation.values();
	 * EList&lt;String&gt; allValues = flatten(values);
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param <T>
	 * @param collectionOfCollection
	 * @return
	 */
	public static <T> EList<T> flatten(Collection<? extends Collection<? extends T>> collectionOfCollection) {
		EList<T> eList = new EListImpl<T>();
		for (Collection<? extends T> collection : collectionOfCollection) {
			if (collection != null) {
				eList.addItems(collection);
			}
		}
		return eList;
	}

	/**
	 * <p>
	 * Flattens the given varargs of collections into a single list containing all entries.
	 * </p>
	 * <p>
	 * This scenario is useful generally in test code where we want to assemble specific sets of collections from expected and unexpected values
	 * </p>
	 * <p>
	 * For example:
	 * 
	 * <pre>
	 * <code>
	 * List&lt;String&gt; expected = list("A", "E", "Y");
	 * List&lt;String&gt; notExpected = list("B", "D", "Z");
	 * List&lt;String&gt; vowels = giveMeOnlyVowels(flatten(expected, notExpected));
	 * assertThat(vowels, is(expected));
	 * </code>
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param <T>
	 * @param collectionOfCollection
	 * @return
	 */
	public static <T> EList<T> flatten(Collection<? extends T>... collectionOfCollection) {
		EList<T> eList = new EListImpl<T>();
		for (Collection<? extends T> collection : collectionOfCollection) {
			eList.addItems(collection);
		}
		return eList;
	}

	/**
	 * <p>
	 * Convenience method for creating an {@link EList}.
	 * </p>
	 * <p>
	 * This can also be used to create a {@link List} as an alternative to {@link Arrays#asList(Object...)}.
	 * </p>
	 * 
	 * @param <T>
	 * @param values
	 *            the objects to be placed in a list
	 * @return an {@link EList} containing the given items in the given order
	 */
	public static <T> EList<T> list(T... values) {
		return new EListImpl<T>(values);
	}

	/**
	 * <p>
	 * Convenience method for creating an {@link EList} from a {@link Collection}. The resulting {@link EList} will contain all elements from the given {@link Collection}s.
	 * </p>
	 * <p>
	 * The resulting order of the items is dependent on the order defined by the supplied collection.
	 * </p>
	 * 
	 * @param <T>
	 * @param values
	 *            the objects to be placed in a collection
	 * @return an {@link EList} containing the given items in the given order
	 */
	public static <T> EList<T> list(Collection<? extends T> values) {
		return new EListImpl<T>().addItems(values);
	}

	/**
	 * <p>
	 * Convenience method for creating an {@link EList} from an {@link Iterable}. The resulting {@link EList} will contain all elements available from the {@link Iterator} obtained from the
	 * {@link Iterable}.
	 * </p>
	 * <p>
	 * The resulting order of the items is dependent on the order defined by the {@link Iterator} underlying the {@link Iterable}
	 * </p>
	 * 
	 * @param <T>
	 * @param values
	 *            the iterable containing the objects to be placed in a list
	 * @return an {@link EList} containing the given items in the given order
	 */
	public static <T> EList<T> list(Iterable<? extends T> values) {
		EListImpl<T> list = new EListImpl<T>();
		for (T t : values) {
			list.add(t);
		}
		return list;
	}

	/**
	 * <p>
	 * Convenience method for creating an {@link EList} from an {@link Iterator}.
	 * </p>
	 * <p>
	 * The resulting order of the items is dependent on the order of the {@link Iterator}.
	 * </p>
	 * <p>
	 * The given iterator should be considered stale once passed to this method. If {@link Iterator#next()} has already been called on the given iterator, those elements will not be present in the
	 * resulting list.
	 * </p>
	 * 
	 * @param <T>
	 * @param values
	 *            the iterator containing the objects to be placed in a list
	 * @return an {@link EList} containing the given items in the given order
	 */
	public static <T> EList<T> list(Iterator<? extends T> values) {
		EListImpl<T> list = new EListImpl<T>();
		while (values.hasNext()) {
			list.add(values.next());
		}
		return list;
	}

	/**
	 * Reverses a {@link Map}.
	 * Returns a new map where the values of the given map are mapped to the set of keys.
	 * 
	 * This function is similar to the {@link #reverseUnique(Map)} function, but can handle multiple keys of the input map having a single value.
	 * 
	 * @param map
	 * @return
	 * @see #reverseUnique(Map)
	 */
	public static <K, V> Map<V, Set<K>> reverse(Map<K, V> map) {
		Map<V, Set<K>> reverse = createMap(map);
		if (map != null) {
			for (Map.Entry<K, V> entry : map.entrySet()) {
				Set<K> set = reverse.get(entry.getValue());
				if (set == null) {
					set = new LinkedHashSet<K>();
					reverse.put(entry.getValue(), set);
				}
				set.add(entry.getKey());
			}
		}
		return reverse;
	}

	/**
	 * Reverses a {@link Map}, mapping each value in the given map to a single key value.
	 * If a value may appear more than once, {@link #reverse(Map)} should be used instead.
	 * 
	 * @param map
	 * @return
	 * @see #reverse(Map)
	 */
	public static <K, V> Map<V, K> reverseUnique(Map<K, V> map) {
		Map<V, K> reverse = createMap(map);
		if (map != null) {
			for (Map.Entry<K, V> entry : map.entrySet()) {
				if (!reverse.containsKey(entry.getValue())) {
					reverse.put(entry.getValue(), entry.getKey());
				}
			}
		}
		return reverse;
	}

	/**
	 * <p>
	 * Convenience method for creating an {@link Iterable} from an {@link Iterator} so that it can easily be used in a for each loop.
	 * </p>
	 * <p>
	 * Iterators used in this fashion should be discarded, as their state will be altered permanently once they are accessed (the for loop is executed). <br/>
	 * Likewise, the resulting Iterable should only have {@link Iterable#iterator()} invoked once.
	 * </p>
	 * <p>
	 * The Iterator returned from the resulting Iterable is the same as the supplied instance, and as such supports {@link Iterator#remove()} if the given iterator does.
	 * </p>
	 * <p>
	 * 
	 * <pre>
	 * e.g.
	 * public boolean supportsColorModel(ImageReader reader, ColorModel colorModel) throws IOException {
	 * Iterator&lt;ImageTypeSpecifier&gt; imageTypes = reader.getImageTypes(0);
	 * 	for (ImageTypeSpecifier imageTypeSpecifier : iterable(imageTypes)) {
	 * 		if (colorModel.equals(imageTypeSpecifier.getColorModel())) {
	 * 			return true;
	 * 		}
	 * 	}
	 * 	return false;
	 * }
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param <T>
	 * @param iterator
	 *            the Iterator to wrap in an Iterable
	 * @return
	 */
	public static <T> Iterable<T> iterable(final Iterator<T> iterator) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return iterator;
			}
		};
	}

	/**
	 * <p>
	 * Convenience method for creating an {@link Iterable} from an {@link Enumeration} so that it can easily be used in a for each loop.
	 * </p>
	 * <p>
	 * Enumerations used in this fashion should be discarded, as their state will be altered once the for loop is executed.<br/>
	 * Likewise, the resulting Iterable should only have {@link Iterable#iterator()} invoked once.
	 * </p>
	 * <p>
	 * The iterator created by the resulting iterable does not support {@link Iterator#remove()}
	 * </p>
	 * <p>
	 * e.g.
	 * 
	 * <pre>
	 * NetworkInterface networkInterface;
	 * 	Enumeration&lt;InetAddress&gt; inetAddresses = networkInterface.getInetAddresses();
	 * 	for (InetAddress inetAddress : Expressive.iterable(inetAddresses)) {
	 * 		...
	 * 	}
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param <T>
	 * @param enumeration
	 *            the enumeration to wrap in an iterable
	 * @return
	 */
	public static <T> Iterable<T> iterable(final Enumeration<T> enumeration) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				Iterator<T> iterator = new Iterator<T>() {
					@Override
					public boolean hasNext() {
						return enumeration.hasMoreElements();
					}

					@Override
					public T next() {
						return enumeration.nextElement();
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException("Unable to remove an element from this iterator, it is backed by an Enumeration.");
					}
				};
				return iterator;
			}
		};
	}

	/**
	 * Makes a best guess at what kind of map to instantiate based on the given map.
	 * 
	 * @param map
	 * @return
	 */
	private static <X, Y> Map<X, Y> createMap(Map<?, ?> map) {
		if (Cast.is(map, ConcurrentHashMap.class)) {
			return new ConcurrentHashMap<X, Y>(map.size());
		} else if (Cast.is(map, WeakHashMap.class)) {
			return new WeakHashMap<X, Y>(map.size());
		} else if (Cast.is(map, IdentityHashMap.class)) {
			return new IdentityHashMap<X, Y>(map.size());
		} else if (Cast.is(map, SortedMap.class)) {
			return new TreeMap<X, Y>();
		} else if (Cast.is(map, LinkedHashMap.class)) {
			return new LinkedHashMap<X, Y>(map.size());
		} else {
			return new HashMap<X, Y>();
		}
	}
}
