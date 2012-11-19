/*
 *  Copyright (c) 2012 Nicholas Okunew
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
import java.util.Collections;
import java.util.Comparator;
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

import com.atomicleopard.expressive.collection.Pair;
import com.atomicleopard.expressive.comparator.CaseInsensitiveComparator;
import com.atomicleopard.expressive.comparator.ComparableComparator;
import com.atomicleopard.expressive.comparator.ComparatorBuilder;
import com.atomicleopard.expressive.comparator.CompositeComparator;
import com.atomicleopard.expressive.comparator.NullsafeComparator;
import com.atomicleopard.expressive.predicate.AllOfPredicate;
import com.atomicleopard.expressive.predicate.AnyOfPredicate;
import com.atomicleopard.expressive.predicate.ConstantPredicate;
import com.atomicleopard.expressive.predicate.EPredicate;
import com.atomicleopard.expressive.predicate.EqualsPredicate;
import com.atomicleopard.expressive.predicate.NotPredicate;
import com.atomicleopard.expressive.predicate.NullPredicate;
import com.atomicleopard.expressive.predicate.PredicateBuilder;
import com.atomicleopard.expressive.transform.BeanPropertyLookupTransformer;
import com.atomicleopard.expressive.transform.BeanPropertyTransformer;
import com.atomicleopard.expressive.transform.CollectionTransformer;
import com.atomicleopard.expressive.transform.KeyBeanPropertyLookupTransformer;
import com.atomicleopard.expressive.transform.MappingTransformer;

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
		if (values != null) {
			for (T t : values) {
				list.add(t);
			}
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
		if (values != null) {
			while (values.hasNext()) {
				list.add(values.next());
			}
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

	/**
	 * Filter operations return views on an Iterable or Collection by applying a given {@link EPredicate} to them.
	 * 
	 * The operation performed is dependent on the method invoked.
	 * 
	 * @see EPredicate
	 * @see Predicate
	 */
	public static class Filter {
		Filter() {
		}

		/**
		 * Return a new {@link EList} including only the items that pass the given {@link EPredicate}.
		 * 
		 * @param items
		 * @param predicate
		 * @return
		 */
		public static <T, I extends Iterable<T>> EList<T> retain(I items, EPredicate<T> predicate) {
			return list(items).retainItems(predicate);
		}

		/**
		 * Return a new {@link EList} removing the items that pass the given {@link EPredicate}.
		 * 
		 * @param items
		 * @param predicate
		 * @return
		 */
		public static <T, I extends Iterable<T>> EList<T> remove(I items, EPredicate<T> predicate) {
			return list(items).removeItems(predicate);
		}

		/**
		 * Returns a pair of lists split using the supplied {@link EPredicate}.
		 * The first list contains all the items passing the given predicate, the second list the rest.
		 * 
		 * @param items
		 * @param predicate
		 * @return
		 */
		public static <T> Pair<EList<T>, EList<T>> split(Collection<T> items, EPredicate<T> predicate) {
			return list(items).split(predicate);
		}
	}

	/**
	 * <p>
	 * Provides common implementations of {@link EPredicate}.
	 * </p>
	 * <p>
	 * To promote reusability and reduce code clutter, this class provides implementations of predicates that are commonly used in code.
	 * </p>
	 */
	public static class Predicate {
		Predicate() {
		}

		/**
		 * Creates a {@link PredicateBuilder} for the specified type.
		 * 
		 * @param type the class type to create a predicate for
		 * @return a predicate builder which will allow a predicate to be created based on the properties of the specified type
		 * @see PredicateBuilder
		 */
		public static <T> PredicateBuilder<T> on(Class<T> type) {
			return new PredicateBuilder<T>(type);
		}

		/**
		 * The returned predicate will return true for {@link EPredicate#pass(Object)} for all input.
		 * 
		 * @return a predicate which will pass any value
		 */
		public static <T> EPredicate<T> any() {
			return new ConstantPredicate<T>(true);
		}

		/**
		 * The returned predicate will return false for {@link EPredicate#pass(Object)} for all input.
		 * 
		 * @return a predicate which will not pass any value
		 */
		public static <T> EPredicate<T> none() {
			return new ConstantPredicate<T>(false);
		}

		/**
		 * The returned predicate will return true for {@link EPredicate#pass(Object)} only for objects whose {@link Object#equals(Object)} method returns true for the given argument.
		 * 
		 * @param object any object, or null
		 * @return a predicate only passing objects equal to the given object, or null if given null
		 */
		public static <T> EPredicate<T> is(T object) {
			return new EqualsPredicate<T>(object);
		}

		/**
		 * The returned predicate will return true for {@link EPredicate#pass(Object)} only for objects whose {@link Object#equals(Object)} method
		 * returns true for any of the the given arguments.
		 * 
		 * @param objects any objects, including null
		 * @return a predicate only passing objects equal to any of the given objects
		 */
		public static <T> AnyOfPredicate<T> anyOf(T... objects) {
			return new AnyOfPredicate<T>(Collections.<EPredicate<T>> emptyList()).or(objects);
		}

		/**
		 * The returned predicate will return true for {@link EPredicate#pass(Object)} for any objects whose {@link Object#equals(Object)} method
		 * does not return true for any of the the given arguments.
		 * 
		 * @param objects any objects, including null
		 * @return a predicate only passing objects not equal to any of the given objects
		 */
		public static <T> EPredicate<T> noneOf(T... objects) {
			return not(anyOf(objects));
		}

		/**
		 * The returned predicate will return true for {@link EPredicate#pass(Object)} only for null.
		 * 
		 * @return a predicate which will pass null, and fail any non-null value
		 */
		public static <T> EPredicate<T> isNull() {
			return new NullPredicate<T>();
		}

		/**
		 * The returned predicate will return true for {@link EPredicate#pass(Object)} for any non-null object.
		 * 
		 * @return a predicate which will pass any object, and fail null
		 */
		public static <T> EPredicate<T> notNull() {
			return not(Predicate.<T> isNull());
		}

		/**
		 * The returned predicate will return true for {@link EPredicate#pass(Object)} for any objects whose {@link Object#equals(Object)} method
		 * returns false for the given argument.
		 * 
		 * @param object any object, or null
		 * @return a predicate passing all objects not equal to the given object, or not null if given null
		 */
		public static <T> EPredicate<T> not(T value) {
			return new NotPredicate<T>(is(value));
		}

		/**
		 * The returned predicate will return the opposite of the given {@link EPredicate}.
		 * 
		 * @param predicate any predicate, must not be null
		 * @return a predicate which will pass any object the given predicate would fail, and vice-versa
		 */
		public static <T> EPredicate<T> not(EPredicate<T> predicate) {
			return new NotPredicate<T>(predicate);
		}

		/**
		 * The returned predicate will return true for {@link EPredicate#pass(Object)} for any value that would pass all
		 * of the given {@link EPredicate} instances.
		 * 
		 * @param predicates
		 * @return a predicate which will pass any object which passes all of the given predicates
		 */
		public static <T> AllOfPredicate<T> allOf(EPredicate<T>... predicates) {
			return new AllOfPredicate<T>(predicates);
		}
	}

	/**
	 * <p>
	 * Provides convenience access to {@link ETransformer}s that are of common use.
	 * </p>
	 * <p>
	 * Java type and collection manipulation in general falls into a very few small categories, such as reading properties of a homogeneous collection of types, reordering of collections or changing
	 * the representation of a data set (such as from list to a lookup map), or doing lookups in maps/dictionaries.
	 * </p>
	 * <p>
	 * The {@link ETransformer}s provided by this class attempt to meet the needs of many of these typical use cases:
	 * </p>
	 * <ul>
	 * <li>{@link Transformers#usingLookup(Map)} provides a simple map lookup through a uniform interface</li>
	 * <li>{@link Transformers#toProperty(String)} and variants provide a bean to bean property transformation. Used in conjunction with {@link CollectionTransformer} this provides a succinct and
	 * naturally expressed way of extracting values from standard java DTO.</li>
	 * <li>{@link Transformers#toBeanLookup(String)} and variants provides a transformer which will create a one-to-many lookup map based on a bean property</li>
	 * <li>{@link Transformers#toKeyBeanLookup(String)} and variants provides a transformer which will create a one-to-one lookup map based on a bean property</li>
	 * <li> {@link Transformers#transformAllUsing(ETransformer)} provides a transformer capable of transforming an entire collection</li>
	 * </ul>
	 * 
	 * 
	 * @see CollectionTransformer
	 */
	public static class Transformers {
		Transformers() {
		}

		/**
		 * <p>
		 * Given a lookup table in the form of a {@link Map} returns an {@link ETransformer}. The returned {@link ETransformer} will provide a transformation to input objects by performing a lookup in
		 * the given lookup map.
		 * </p>
		 * <p>
		 * If {@link ETransformer#to(Object)} is invoked with a value which is not a key in the map, null will be returned.
		 * </p>
		 */
		public static <From, To> ETransformer<From, To> usingLookup(Map<From, To> lookup) {
			return new MappingTransformer<From, To>(lookup);
		}

		/**
		 * <p>
		 * Given a the name of a javabean property returns an {@link ETransformer}. The returned {@link ETransformer} will provide a transformation to input javabean objects by returning the the value
		 * of the identified property contained within the bean.
		 * </p>
		 * <p>
		 * If a null object is passed to the resulting {@link ETransformer}, it will return null.
		 * </p>
		 * <p>
		 * If an object which does not provide access to the named property is passed to the resulting {@link ETransformer} it will throw a {@link RuntimeException}.
		 * </p>
		 * 
		 * @param propertyName
		 *            The name of the javabean property to read
		 * 
		 * @see #toProperty(String, Class)
		 */
		public static <Bean, Property> ETransformer<Bean, Property> toProperty(String propertyName) {
			return new BeanPropertyTransformer<Bean, Property>(propertyName);
		}

		/**
		 * <p>
		 * Given a {@link Class} and the name of a javabean property returns an {@link ETransformer}. The returned {@link ETransformer} will provide a transformation to input javabean objects whose
		 * type matches the given class by returning the the value of the identified property contained within the bean.
		 * </p>
		 * <p>
		 * If a null object is passed to the resulting {@link ETransformer}, it will return null.
		 * </p>
		 * <p>
		 * If an object which does not provide access to the named property is passed to the resulting {@link ETransformer} it will throw a {@link RuntimeException}.
		 * </p>
		 * 
		 * @param propertyName
		 *            The name of the javabean property to read
		 * @param clazz
		 *            The {@link Class} type of javabeans which this transformer can
		 *            apply to.
		 * 
		 * @return
		 * @see #toProperty(String)
		 */
		public static <Bean, Property> ETransformer<Bean, Property> toProperty(String propertyName, Class<Bean> clazz) {
			return new BeanPropertyTransformer<Bean, Property>(clazz, propertyName);
		}

		/**
		 * <p>
		 * Given a the name of a javabean property returns an {@link ETransformer}. The returned {@link ETransformer} will provide a transformation across a collection of input javabean objects by
		 * returning a map which can be used as a lookup table. The lookup table maps from property value to the list of beans containing that value.
		 * </p>
		 * <p>
		 * If a null collection is passed to the resulting {@link ETransformer}, it will return an empty map.
		 * </p>
		 * <p>
		 * If any object in the collection provided to the resulting transformer does not provide access to the named property it will throw a {@link RuntimeException}.
		 * </p>
		 * <p>
		 * If the given property is known to be unique across the set of input beans (for example an id, a pk, or a key) then {@link #toKeyBeanLookup(String)} is a better transformer.
		 * </p>
		 * <p>
		 * This transformer will retain the order (if any) of the given collection in both the resulting map and of individual beans in a mapped to a property value. That is, if the collection is
		 * ordered, the map keys will be ordered based on the order of the beans they are first matched in.
		 * </p>
		 * 
		 * @param propertyName
		 * 
		 * @see #toBeanLookup(String, Class)
		 */
		public static <Bean, Property> ETransformer<Collection<Bean>, Map<Property, List<Bean>>> toBeanLookup(String propertyName) {
			return new BeanPropertyLookupTransformer<Bean, Property>(propertyName);
		}

		/**
		 * <p>
		 * Given a {@link Class} and the name of a javabean property returns an {@link ETransformer}. The returned {@link ETransformer} will provide a transformation across a collection of input
		 * javabean objects of the given class by returning a map which can be used as a lookup table. The lookup table maps from property value to the list of beans containing that value.
		 * </p>
		 * <p>
		 * If a null collection is passed to the resulting {@link ETransformer}, it will return an empty map.
		 * </p>
		 * <p>
		 * If any object in the collection provided to the resulting transformer does not provide access to the named property it will throw a {@link RuntimeException}.
		 * </p>
		 * <p>
		 * If the given property is known to be unique across the set of input beans (for example an id, a pk, or a key) then {@link #toKeyBeanLookup(String, Class)} is a better transformer.
		 * </p>
		 * <p>
		 * This transformer will retain the order (if any) of the given collection in both the resulting map and of individual beans in a mapped to a property value. That is, if the collection is
		 * ordered, the map keys will be ordered based on the order of the beans they are first matched in.
		 * </p>
		 * 
		 * @param propertyName
		 * @param clazz
		 * @see #toBeanLookup(String)
		 */
		public static <Bean, Property> ETransformer<Collection<Bean>, Map<Property, List<Bean>>> toBeanLookup(String propertyName, Class<Bean> clazz) {
			return new BeanPropertyLookupTransformer<Bean, Property>(clazz, propertyName);
		}

		/**
		 * <p>
		 * Given a the name of a javabean property returns an {@link ETransformer}. The returned {@link ETransformer} will provide a transformation across a collection of input javabean objects by
		 * returning a map which can be used as a lookup table. The lookup table maps from property value to the bean containing that value.
		 * </p>
		 * <p>
		 * If a null collection is passed to the resulting {@link ETransformer}, it will return an empty map.
		 * </p>
		 * <p>
		 * If any object in the collection provided to the resulting transformer does not provide access to the named property it will throw a {@link RuntimeException}.
		 * </p>
		 * <p>
		 * If the given property is known to be unique across the set of input beans (for example an id, a pk, or a key) then {@link #toKeyBeanLookup(String)} is a better transformer.
		 * </p>
		 * <p>
		 * If more than one bean contains the same value for the property (or more specifically they return true for their equals and hashcode), only the last entry (based on the iteration order of
		 * the given collection) will be present.
		 * </p>
		 * <p>
		 * This transformer will retain the order (if any) of the given collection in both the resulting map and of individual beans in a mapped to a property value. That is, if the collection is
		 * ordered, the map keys will be ordered based on the order of the beans they are first matched in.
		 * </p>
		 * 
		 * @param propertyName
		 * 
		 * @see #toKeyBeanLookup(String, Class)
		 */
		public static <Bean, Property> ETransformer<Collection<Bean>, Map<Property, Bean>> toKeyBeanLookup(String propertyName, Class<Bean> clazz) {
			return new KeyBeanPropertyLookupTransformer<Bean, Property>(clazz, propertyName);
		}

		/**
		 * <p>
		 * Given a {@link Class} and the name of a javabean property returns an {@link ETransformer}. The returned {@link ETransformer} will provide a transformation across a collection of input
		 * javabean objects of the given type by returning a map which can be used as a lookup table. The lookup table maps from property value to the bean containing that value.
		 * </p>
		 * <p>
		 * If a null collection is passed to the resulting {@link ETransformer}, it will return an empty map.
		 * </p>
		 * <p>
		 * If any object in the collection provided to the resulting transformer does not provide access to the named property it will throw a {@link RuntimeException}.
		 * </p>
		 * <p>
		 * If the given property is known to be unique across the set of input beans (for example an id, a pk, or a key) then {@link #toKeyBeanLookup(String)} is a better transformer.
		 * </p>
		 * <p>
		 * If more than one bean contains the same value for the property (or more specifically they return true for their equals and hashcode), only the last entry (based on the iteration order of
		 * the given collection) will be present.
		 * </p>
		 * <p>
		 * This transformer will retain the order (if any) of the given collection in both the resulting map and of individual beans in a mapped to a property value. That is, if the collection is
		 * ordered, the map keys will be ordered based on the order of the beans they are first matched in.
		 * </p>
		 * 
		 * @param propertyName
		 * 
		 * @see #toKeyBeanLookup(String)
		 */
		public static <Bean, Property> ETransformer<Collection<Bean>, Map<Property, Bean>> toKeyBeanLookup(String propertyName) {
			return new KeyBeanPropertyLookupTransformer<Bean, Property>(propertyName);
		}

		/**
		 * <p>
		 * Creates a {@link CollectionTransformer} for the given {@link ETransformer}.
		 * </p>
		 * <p>
		 * {@link CollectionTransformer}s use a transformer to transform a collection rather than an individual object.
		 * </p>
		 * 
		 * @param transformer
		 * @return a collection transformer which uses the given transformer to
		 *         perform transformations
		 */
		public static <From, To> CollectionTransformer<From, To> transformAllUsing(ETransformer<From, To> transformer) {
			return new CollectionTransformer<From, To>(transformer);
		}
	}

	/**
	 * <p>
	 * Provides common implementations of comparators.
	 * </p>
	 * <p>
	 * To promote reusability and reduce code clutter, this class provides implementations of comparators that are commonly used in code.
	 * </p>
	 */
	public static class Comparators {
		Comparators() {
		}

		/**
		 * Returns a {@link ComparatorBuilder} which allows the creation of a comparator for the given type based on the values
		 * of bean properties on that object.
		 * 
		 * @param type the type for which a comparator is being built
		 * @return a comparator builder which will allow a {@link Comparator} to be created
		 * @see ComparatorBuilder
		 */
		public static <T> ComparatorBuilder<T> compare(Class<T> type) {
			return new ComparatorBuilder<T>(type);
		}

		/**
		 * Returns a comparator that can safely compare null values. Null values are considered to be 'greater than' non-null values.
		 * 
		 * @param delegate
		 * @return a comparator which allows {@link Comparator#compare(Object, Object)} on null values
		 * @see #nullSafe(Comparator, boolean)
		 */
		public static <T> Comparator<T> nullSafe(Comparator<T> delegate) {
			return nullSafe(delegate, true);
		}

		/**
		 * Returns a comparator that can safely compare null values.
		 * The second argument controls whether nulls are 'greater than' or 'less than' non-null values
		 * 
		 * @param delegate
		 * @param nullLast if true, null values are considered 'greater than' non-null, if false null values are 'less than'
		 * @return a comparator which allows {@link Comparator#compare(Object, Object)} on null values
		 */
		public static <T> Comparator<T> nullSafe(Comparator<T> delegate, boolean nullLast) {
			return new NullsafeComparator<T>(delegate, nullLast);
		}

		/**
		 * Returns a string comparator which ignores case.
		 * 
		 * @return
		 */
		public static Comparator<String> caseInsensitive() {
			return new CaseInsensitiveComparator();
		}

		/**
		 * Returns a comparator for a given type that implements {@link Comparable}
		 * 
		 * @param comparable the type of the Comparable class
		 * @return a comparator for comparing comparable instances of the given type
		 */
		public static <T extends Comparable<T>> Comparator<T> as(Class<T> comparable) {
			return new ComparableComparator<T>();
		}

		/**
		 * Returns a comparator which compares using the given comparators.
		 * The comparison occurs using the given comparators in order, and continues until a non-zero value is returned,
		 * or all comparators have been evaluated.
		 * 
		 * @param comparators
		 * @return a comparator which compares using the given ordered comparators
		 */
		public static <T> Comparator<T> all(Comparator<T>... comparators) {
			return new CompositeComparator<T>(Arrays.asList(comparators));
		}

		/**
		 * Returns a comparator which compares using the given comparators.
		 * The comparison occurs using the given comparators in order, and continues until a non-zero value is returned,
		 * or all comparators have been evaluated.
		 * 
		 * @param comparators
		 * @return a comparator which compares using the given ordered comparators
		 */
		public static <T> Comparator<T> all(Iterable<Comparator<T>> comparators) {
			return new CompositeComparator<T>(comparators);
		}
	}
}
