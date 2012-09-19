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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * <p>
 * Implementation of the {@link EList} interface.
 * </p>
 * 
 * @param <T>
 * @see EList
 */
public class EListImpl<T> implements EList<T> {
	protected List<T> delegate;

	protected EListImpl(int size) {
		super();
		this.delegate = new ArrayList<T>(size);
	}

	public EListImpl() {
		super();
		this.delegate = new ArrayList<T>();
	}

	public EListImpl(List<T> delegate) {
		super();
		this.delegate = new ArrayList<T>(delegate);
	}

	public EListImpl(EList<T> elist) {
		super();
		if (elist instanceof EListImpl<?>) {
			this.delegate = new ArrayList<T>(((EListImpl<T>) elist).delegate);
		} else {
			this.delegate = new ArrayList<T>(elist);
		}
	}

	public EListImpl(T... values) {
		super();
		// TODO - The efficiency of this is based on the efficiency of the extra
		// list creation, and how it inserts
		// array items.
		this.delegate = new ArrayList<T>(Arrays.asList(values));
	}

	@Override
	public EList<T> duplicate() {
		return new EListImpl<T>(this);
	}

	@Override
	public EList<T> addItems(T... values) {
		if (values.length == 1) {
			delegate.add(values[0]);
		} else if (values.length > 1) {
			Collections.addAll(delegate, values);
		}

		return this;
	}

	@Override
	public EList<T> addItems(Collection<? extends T>... values) {
		for (Collection<? extends T> collection : values) {
			if (collection != null) {
				delegate.addAll(collection);
			}
		}
		return this;
	}

	@Override
	public EList<T> insertItems(int index, T... values) {
		if (values.length == 1) {
			delegate.add(index, values[0]);
		} else if (values.length > 1) {
			delegate.addAll(index, Arrays.asList(values));
		}
		return this;
	}

	@Override
	public EList<T> insertItems(int index, Collection<? extends T>... value) {
		// TODO - Expressive - Currently this throws array index out of bounds
		// when index is out of range,
		// but is that stupid, maybe clamping in the range of 0 to size() - 1
		// makes more sense.
		for (int i = value.length - 1; i >= 0; i--) {
			if (value[i] != null) {
				delegate.addAll(index, value[i]);
			}
		}
		return this;
	}

	@Override
	public EList<T> removeItems(T... values) {
		// even with one element, we must invoke removeAll because remove only
		// removes the first matching element, not all matching elements.
		delegate.removeAll(Arrays.asList(values));
		return this;
	}

	@Override
	public EList<T> removeItems(Collection<? extends T>... values) {
		for (Collection<? extends T> collection : values) {
			if (collection != null) {
				delegate.removeAll(collection);
			}
		}

		return this;
	}

	@Override
	public EList<T> retainItems(Collection<? extends T>... values) {
		Collection<T> combined = Expressive.flatten(Arrays.asList(values));
		delegate.retainAll(combined);

		return this;
	}

	@Override
	public EList<T> retainItems(T... values) {
		delegate.retainAll(Arrays.asList(values));
		return this;
	}

	@Override
	public T at(int index) {
		return index > -1 && index < size() ? get(index) : null;
	}

	@Override
	public T first() {
		return at(0);
	}

	@Override
	public T last() {
		return at(size() - 1);
	}

	public void add(int index, T element) {
		delegate.add(index, element);
	}

	public boolean add(T e) {
		return delegate.add(e);
	}

	public boolean addAll(Collection<? extends T> c) {
		return delegate.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends T> c) {
		return delegate.addAll(index, c);
	}

	public void clear() {
		delegate.clear();
	}

	public boolean contains(Object o) {
		return delegate.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return delegate.containsAll(c);
	}

	public T get(int index) {
		return delegate.get(index);
	}

	public int indexOf(Object o) {
		return delegate.indexOf(o);
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public Iterator<T> iterator() {
		return delegate.iterator();
	}

	public int lastIndexOf(Object o) {
		return delegate.lastIndexOf(o);
	}

	public ListIterator<T> listIterator() {
		return delegate.listIterator();
	}

	public ListIterator<T> listIterator(int index) {
		return delegate.listIterator(index);
	}

	public T remove(int index) {
		return delegate.remove(index);
	}

	public boolean remove(Object o) {
		return delegate.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return delegate.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return delegate.retainAll(c);
	}

	public T set(int index, T element) {
		return delegate.set(index, element);
	}

	public int size() {
		return delegate.size();
	}

	public EList<T> subList(int fromIndex, int toIndex) {
		return new EListImpl<T>(delegate.subList(fromIndex, toIndex));
	}

	public Object[] toArray() {
		return delegate.toArray();
	}

	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a) {
		return delegate.toArray(a);
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof List<?>))
			return false;

		ListIterator<T> e1 = listIterator();
		ListIterator<?> e2 = ((List<?>) o).listIterator();
		while (e1.hasNext() && e2.hasNext()) {
			T o1 = e1.next();
			Object o2 = e2.next();
			if (!(o1 == null ? o2 == null : o1.equals(o2)))
				return false;
		}
		return !(e1.hasNext() || e2.hasNext());
	}

	public int hashCode() {
		int hashCode = 1;
		Iterator<T> i = iterator();
		while (i.hasNext()) {
			T obj = i.next();
			hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	/**
	 * @see Collections#sort(List)
	 * @param comparator
	 * @return
	 */
	public EList<T> sort(Comparator<T> comparator) {
		Collections.sort(this.delegate, comparator);
		return this;
	}

}
