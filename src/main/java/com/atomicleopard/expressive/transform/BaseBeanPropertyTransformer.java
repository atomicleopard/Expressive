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
package com.atomicleopard.expressive.transform;

import static com.atomicleopard.expressive.Cast.is;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.SortedSet;

import net.sf.cglib.core.ReflectUtils;

public abstract class BaseBeanPropertyTransformer<V, K> {
	protected String propertyName;
	protected PropertyDescriptor propertyDescriptor;

	public BaseBeanPropertyTransformer(String propertyName) {
		this(null, propertyName);
	}

	public BaseBeanPropertyTransformer(Class<V> clazz, String propertyName) {
		this.propertyName = propertyName;
		this.propertyDescriptor = clazz == null ? null : findDescriptorForProperty(propertyName, clazz);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getPropertyValue(V from, Method readMethod) {
		try {
			return (T) readMethod.invoke(from);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Unable to read the bean property '%s' from the given object: %s", propertyName, e.getMessage()), e);
		}
	}

	protected Method getReadMethod(V from) {
		PropertyDescriptor propertyDescriptor = this.propertyDescriptor == null ? findDescriptorForProperty(propertyName, from.getClass()) : this.propertyDescriptor;
		Method readMethod = propertyDescriptor.getReadMethod();
		return readMethod;
	}

	static <T> PropertyDescriptor findDescriptorForProperty(String propertyName, Class<T> clazz) {
		PropertyDescriptor[] beanGetters = ReflectUtils.getBeanGetters(clazz);
		for (PropertyDescriptor propertyDescriptor : beanGetters) {
			if (propertyDescriptor.getName().equals(propertyName)) {
				return propertyDescriptor;
			}
		}
		throw new RuntimeException(String.format("There is no accessible property named '%s' for the class %s, cannot create a LookupTransformer", propertyName, clazz.getName()));
	}

	/**
	 * Creates a {@link Map} to hold items within the given collection. This is
	 * a factory method which tries to determine the most appropriate map for
	 * the given collection. If the given collection has ordering, it will
	 * provide a map which respected the ordering, otherwise it returns a
	 * regular map.
	 * 
	 * Ordering here means a {@link List}, {@link Queue} or {@link SortedSet}.
	 * 
	 * @param collection
	 * @return
	 */
	static <K, V, T> Map<K, V> createMapRetainingOrder(Collection<T> collection) {
		boolean ordered = is(collection, List.class, SortedSet.class, Queue.class);
		return ordered ? new LinkedHashMap<K, V>(collection.size()) : new HashMap<K, V>();
	}
}
