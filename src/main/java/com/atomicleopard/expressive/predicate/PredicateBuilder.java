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
package com.atomicleopard.expressive.predicate;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.cglib.core.ReflectUtils;

import com.atomicleopard.expressive.ETransformer;
import com.atomicleopard.expressive.Expressive;
import com.atomicleopard.expressive.transform.ETransformers;

/**
 * <p>
 * {@link PredicateBuilder} allows the creation of a predicate for a specified type.
 * </p>
 * <p>
 * The predicate is created by assembling predicates for the individual javabean properties of the type.
 * </p>
 * 
 * @param <T>
 * @see Expressive.Predicate#on(Class)
 */
public class PredicateBuilder<T> implements EPredicate<T> {
	private Class<T> type;
	protected Map<String, PropertyDescriptor> getters;
	protected LinkedHashMap<String, EPredicate<?>> propertyPredicates = new LinkedHashMap<String, EPredicate<?>>();

	private static Map<Class<?>, Map<String, PropertyDescriptor>> PropertyDescriptorCache = new HashMap<Class<?>, Map<String, PropertyDescriptor>>();
	private static ETransformer<Collection<PropertyDescriptor>, Map<String, PropertyDescriptor>> PropertyDescriptorLookupTransformer = ETransformers.toKeyBeanLookup("name", PropertyDescriptor.class);

	public PredicateBuilder(Class<T> type) {
		this(type, false);
	}

	public PredicateBuilder(Class<T> type, boolean noCache) {
		this.type = type;
		this.getters = getPropertyDescriptions(type, noCache);
	}

	private PredicateBuilder(PredicateBuilder<T> predicateBuilder) {
		this.type = predicateBuilder.type;
		this.getters = predicateBuilder.getters;
		this.propertyPredicates = new LinkedHashMap<String, EPredicate<?>>(predicateBuilder.propertyPredicates);
	}

	protected void put(String property, EPredicate<?> predicate) {
		if (!this.getters.containsKey(property)) {
			throw new RuntimeException(String.format("Unable to create predicate for %s on the property %s, there is no accessible bean property with that name", type.getName(), property));
		}
		propertyPredicates.put(property, predicate);
	}

	public PredicateUsing where(String property) {
		return new PredicateUsing(property);
	}

	/**
	 * We cache property descriptors for types
	 * 
	 * @param type
	 * @param noCache
	 * @return
	 */
	private static Map<String, PropertyDescriptor> getPropertyDescriptions(Class<?> type, boolean noCache) {
		Map<String, PropertyDescriptor> existing = noCache ? null : PropertyDescriptorCache.get(type);
		if (existing == null) {
			PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(type);
			existing = PropertyDescriptorLookupTransformer.from(Arrays.asList(getters));
			if (!noCache) {
				PropertyDescriptorCache.put(type, existing);
			}
		}
		return existing;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean pass(T input) {
		if (input == null) {
			return false;
		}
		try {
			for (Entry<String, EPredicate<?>> entry : propertyPredicates.entrySet()) {
				String property = entry.getKey();
				Object value = getters.get(property).getReadMethod().invoke(input);
				EPredicate<Object> predicate = (EPredicate<Object>) entry.getValue();
				boolean pass = predicate.pass(value);
				if (!pass) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("a " + type.getName());
		for (Entry<String, EPredicate<?>> entry : propertyPredicates.entrySet()) {
			String property = entry.getKey();
			sb.append(", where " + property + " " + entry.getValue());
		}
		return sb.toString();
	}

	private <S> PredicateBuilder<T> copyAndAdd(String property, EPredicate<S> predicate) {
		PredicateBuilder<T> copy = new PredicateBuilder<T>(this);
		copy.put(property, predicate);
		return copy;
	}

	public class PredicateUsing {
		private String property;

		public PredicateUsing(String property) {
			this.property = property;
		}

		public <S> PredicateBuilder<T> passes(EPredicate<S> propertyPredicate) {
			return copyAndAdd(property, propertyPredicate);
		}

		public <S> PredicateBuilder<T> is(S value) {
			return copyAndAdd(property, Expressive.Predicate.is(value));
		}

		public <S> PredicateBuilder<T> is(S... value) {
			return copyAndAdd(property, Expressive.Predicate.anyOf(value));
		}

		public <S> PredicateBuilder<T> isNot(S value) {
			return copyAndAdd(property, Expressive.Predicate.not(value));
		}

		public <S> PredicateBuilder<T> isNot(S... value) {
			return copyAndAdd(property, Expressive.Predicate.noneOf(value));
		}
	}
}
