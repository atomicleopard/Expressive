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

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.atomicleopard.expressive.ETransformer;

public class BeanPropertyLookupTransformer<V, K> extends BaseBeanPropertyTransformer<V, K> implements ETransformer<Collection<V>, Map<K, List<V>>> {

	public BeanPropertyLookupTransformer(String propertyName) {
		super(propertyName);
	}

	public BeanPropertyLookupTransformer(Class<V> clazz, String propertyName) {
		super(clazz, propertyName);
	}

	public Map<K, List<V>> from(Collection<V> from) {
		Map<K, List<V>> results = createMapRetainingOrder(from);
		if (from != null && !from.isEmpty()) {
			try {
				Method readMethod = getReadMethod(from.iterator().next());
				for (V v : from) {
					K key = getPropertyValue(v, readMethod);
					List<V> existing = results.get(key);
					if (existing == null) {
						existing = new LinkedList<V>();
						results.put(key, existing);
					}
					existing.add(v);
				}
			} catch (Exception e) {
				throw new RuntimeException(String.format("Failed to transform a Collection to a lookup Map using property '%s': %s", propertyName, e.getMessage()), e);
			}
		}

		return results;
	}
}
