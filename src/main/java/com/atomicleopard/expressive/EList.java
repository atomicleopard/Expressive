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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.atomicleopard.expressive.collection.Pair;
import com.atomicleopard.expressive.predicate.EPredicate;

/**
 * <p>
 * {@link EList} is an extension of the Java Collections {@link List} interface designed to allow for easy use and manipulation.
 * </p>
 * <p>
 * It implements convenience methods, and defines new methods analagous to the Java Collections operations to support a fluid, or chainable, interface.
 * </p>
 * <p>
 * Convenience methods defined by {@link EList}:
 * <ul>
 * <li>{@link #first()}</li>
 * <li>{@link #last()}</li>
 * <li>{@link #at(int)}</li>
 * <li>{@link #duplicate()}</li>
 * </ul>
 * </p>
 * <p>
 * Methods that redefine standard {@link Collection} and {@link List} methods to support method call chaining:
 * <ul>
 * <li>{@link #addItems(Object...)}</li>
 * <li>{@link #addItems(Collection...)}</li>
 * <li>{@link #insertItems(int, Object...)}</li>
 * <li>{@link #insertItems(int, Collection...)}</li>
 * <li>{@link #removeItems(Object...)}</li>
 * <li>{@link #removeItems(Collection...)}</li>
 * <li>{@link #retainItems(Object...)}</li>
 * <li>{@link #retainItems(Collection...)}</li>
 * </ul>
 * </p>
 * 
 * 
 * @param <T>
 * @see Expressive#list(Object...)
 * @see Expressive#list(Collection...)
 */
public interface EList<T> extends List<T> {
	/**
	 * Returns the first element of this list, or null if none exists.
	 * 
	 * @return
	 */
	public T first();

	/**
	 * Returns the last element of this list, or null if none exists.
	 * 
	 * @return
	 */
	public T last();

	/**
	 * Returns the element at the given index, or null if the element does not
	 * exist. That is this method will not throw an exception for entries
	 * outside the bounds of the list.
	 * 
	 * @param index
	 * @return
	 */
	public T at(int index);

	/**
	 * Inserts all of the elements in the specified elements into this list at
	 * the specified position. Shifts the element currently at that position (if
	 * any) and any subsequent elements to the right (increases their indices).
	 * The new elements will appear in this list in the order that they
	 * specified.
	 * 
	 * @param index
	 *            index at which to insert the first element from the specified
	 *            collection
	 * @param values
	 *            element(s) to be added to this list
	 * @return this list for method chaining
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range ( <tt>index &lt; 0 || index &gt; size()</tt>)
	 */
	public EList<T> insertItems(int index, T... values);

	/**
	 * Inserts all of the elements in the specified collections of elements into
	 * this list at the specified position. Shifts the element currently at that
	 * position (if any) and any subsequent elements to the right (increases
	 * their indices). The new elements will appear in this list in the order
	 * that the collections they reside in specify.
	 * 
	 * @param index
	 *            index at which to insert the first element from the specified
	 *            collection
	 * @param values
	 *            collections containing the element(s) to be added to this list
	 * @return this list for method chaining
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range ( <tt>index &lt; 0 || index &gt; size()</tt>)
	 */
	public EList<T> insertItems(int index, Collection<? extends T> values);

	/**
	 * Appends all of the specified elements onto the end of this list. The new
	 * elements will appear in this list in the order that the collections they
	 * reside in specify.
	 * 
	 * @param values
	 *            element(s) to be appended to this list
	 * @return this list for method chaining
	 */
	public EList<T> addItems(T... values);

	/**
	 * <p>
	 * Appends all of the elements in the specific collections onto the end of this list.
	 * </p>
	 * <p>
	 * The new elements will appear in this list in the order that the collections they reside in specify.
	 * </p>
	 * 
	 * @param values
	 *            element(s) to be appended to this list
	 * @return this list for method chaining
	 */
	public EList<T> addItems(Collection<? extends T> collection);

	/**
	 * Removes from this list all of the specified elements.
	 * 
	 * @param values
	 *            the elements to be removed from this list
	 * @return this list for method chaining
	 */
	public EList<T> removeItems(T... values);

	/**
	 * Removes from this list all of the elements in the specified collections.
	 * 
	 * @param values
	 *            collections containing the elements to be removed from this
	 *            list
	 * @return this list for method chaining
	 */
	public EList<T> removeItems(Collection<? extends T> values);

	public EList<T> removeItems(EPredicate<T> predicate);

	/**
	 * Retains only the elements in this list that are specified.
	 * 
	 * @param values
	 * @return this list for method chaining
	 * @see List#retainAll(Collection)
	 */
	public EList<T> retainItems(T... values);

	/**
	 * Retains only the elements in this list that are contained in the
	 * specified collections.
	 * 
	 * @param values
	 * @return this list for method chaining
	 * @see List#retainAll(Collection)
	 */
	public EList<T> retainItems(Collection<? extends T> values);

	public EList<T> retainItems(EPredicate<T> predicate);

	/**
	 * Creates a copy of this {@link EList} containing the same elements.
	 * 
	 * @return a duplicate of this list, that is one which contains the same
	 *         elements
	 */
	public EList<T> duplicate();

	/**
	 * Redefines {@link List#subList(int, int)} to return an {@link EList}
	 * 
	 * @see List#subList(int, int)
	 */
	public EList<T> subList(int fromIndex, int toIndex);

	/**
	 * Returns a list of the items from the given index for the given size. If the index + size is out of bounds, the returned list will be shorter
	 * than the specified size, including only existing elements. If size if negative, an empty list is returned.
	 * If the given index is negative, the returned list will contain results as though it wasn't. Any 'negative' elements will be omitted, but the resulting
	 * size will match them as though they exists.
	 * 
	 * e.g. The following test snippet would pass
	 * 
	 * <pre>
	 * <code>
	 * EList&lt;String&gt; list = Expressive.list("A","B","C");
	 * EList&lt;String&gt; items = list.getItems(-2,3);
	 * assertThat(items.size(), is(1));
	 * assertThat(items, is(list("A")));
	 * </code>
	 * </pre>
	 * 
	 * @param index
	 * @param size
	 * @return a list of 'size' items starting from the given index, or as many as can be provided based on the list contents.
	 */
	public EList<T> getItems(int index, int size);

	public EList<T> getItems(EPredicate<T> predicate);

	/**
	 * Sorts this {@link EList} in place using the given comparator.
	 * 
	 * @param comparator
	 * @return this list
	 * @see Collections#sort(List)
	 */
	public EList<T> sort(Comparator<T> comparator);

	public Pair<EList<T>, EList<T>> split(EPredicate<T> predicate);
}
