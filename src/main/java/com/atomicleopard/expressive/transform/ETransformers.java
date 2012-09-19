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
package com.atomicleopard.expressive.transform;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.atomicleopard.expressive.ETransformer;

/**
 * <p>
 * Provides convenience access to {@link ETransformer}s that are of common use.
 * </p>
 * <p>
 * Java type and collection manipulation in general falls into a very few small
 * categories, such as reading properties of a homogeneous collection of types,
 * reordering of collections or changing the representation of a data set (such
 * as from list to a lookup map), or doing lookups in maps/dictionaries.
 * </p>
 * <p>
 * The {@link ETransformer}s provided by this class attempt to meet the needs of
 * many of these typical use cases:
 * </p>
 * <ul>
 * <li>{@link #usingLookup(Map)} provides a simple map lookup through a uniform
 * interface</li>
 * <li>{@link #toProperty(String)} and variants provide a bean to bean property
 * transformation. Used in conjunction with {@link CollectionTransformer} this
 * provides a succinct and naturally expressed way of extracting values from
 * standard java DTO.</li>
 * <li>{@link #toBeanLookup(String)} and variants provides a transformer which
 * will create a one-to-many lookup map based on a bean property</li>
 * <li>{@link #toKeyBeanLookup(String)} and variants provides a transformer
 * which will create a one-to-one lookup map based on a bean property</li>
 * </ul>
 * 
 * @see CollectionTransformer
 */
public class ETransformers {
	ETransformers() {
	}

	/**
	 * <p>
	 * Given a lookup table in the form of a {@link Map} returns an
	 * {@link ETransformer}. The returned {@link ETransformer} will provide a
	 * transformation to input objects by performing a lookup in the given
	 * lookup map.
	 * </p>
	 * <p>
	 * If {@link ETransformer#to(Object)} is invoked with a value which is not a
	 * key in the map, null will be returned.
	 * </p>
	 */
	public static <From, To> ETransformer<From, To> usingLookup(Map<From, To> lookup) {
		return new MappingTransformer<From, To>(lookup);
	}

	/**
	 * <p>
	 * Given a the name of a javabean property returns an {@link ETransformer}.
	 * The returned {@link ETransformer} will provide a transformation to input
	 * javabean objects by returning the the value of the identified property
	 * contained within the bean.
	 * </p>
	 * <p>
	 * If a null object is passed to the resulting {@link ETransformer}, it will
	 * return null.
	 * </p>
	 * <p>
	 * If an object which does not provide access to the named property is
	 * passed to the resulting {@link ETransformer} it will throw a
	 * {@link RuntimeException}.
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
	 * Given a {@link Class} and the name of a javabean property returns an
	 * {@link ETransformer}. The returned {@link ETransformer} will provide a
	 * transformation to input javabean objects whose type matches the given
	 * class by returning the the value of the identified property contained
	 * within the bean.
	 * </p>
	 * <p>
	 * If a null object is passed to the resulting {@link ETransformer}, it will
	 * return null.
	 * </p>
	 * <p>
	 * If an object which does not provide access to the named property is
	 * passed to the resulting {@link ETransformer} it will throw a
	 * {@link RuntimeException}.
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
	 * Given a the name of a javabean property returns an {@link ETransformer}.
	 * The returned {@link ETransformer} will provide a transformation across a
	 * collection of input javabean objects by returning a map which can be used
	 * as a lookup table. The lookup table maps from property value to the list
	 * of beans containing that value.
	 * </p>
	 * <p>
	 * If a null collection is passed to the resulting {@link ETransformer}, it
	 * will return an empty map.
	 * </p>
	 * <p>
	 * If any object in the collection provided to the resulting transformer
	 * does not provide access to the named property it will throw a
	 * {@link RuntimeException}.
	 * </p>
	 * <p>
	 * If the given property is known to be unique across the set of input beans
	 * (for example an id, a pk, or a key) then {@link #toKeyBeanLookup(String)}
	 * is a better transformer.
	 * </p>
	 * <p>
	 * This transformer will retain the order (if any) of the given collection
	 * in both the resulting map and of individual beans in a mapped to a
	 * property value. That is, if the collection is ordered, the map keys will
	 * be ordered based on the order of the beans they are first matched in.
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
	 * Given a {@link Class} and the name of a javabean property returns an
	 * {@link ETransformer}. The returned {@link ETransformer} will provide a
	 * transformation across a collection of input javabean objects of the given
	 * class by returning a map which can be used as a lookup table. The lookup
	 * table maps from property value to the list of beans containing that
	 * value.
	 * </p>
	 * <p>
	 * If a null collection is passed to the resulting {@link ETransformer}, it
	 * will return an empty map.
	 * </p>
	 * <p>
	 * If any object in the collection provided to the resulting transformer
	 * does not provide access to the named property it will throw a
	 * {@link RuntimeException}.
	 * </p>
	 * <p>
	 * If the given property is known to be unique across the set of input beans
	 * (for example an id, a pk, or a key) then
	 * {@link #toKeyBeanLookup(String, Class)} is a better transformer.
	 * </p>
	 * <p>
	 * This transformer will retain the order (if any) of the given collection
	 * in both the resulting map and of individual beans in a mapped to a
	 * property value. That is, if the collection is ordered, the map keys will
	 * be ordered based on the order of the beans they are first matched in.
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
	 * Given a the name of a javabean property returns an {@link ETransformer}.
	 * The returned {@link ETransformer} will provide a transformation across a
	 * collection of input javabean objects by returning a map which can be used
	 * as a lookup table. The lookup table maps from property value to the bean
	 * containing that value.
	 * </p>
	 * <p>
	 * If a null collection is passed to the resulting {@link ETransformer}, it
	 * will return an empty map.
	 * </p>
	 * <p>
	 * If any object in the collection provided to the resulting transformer
	 * does not provide access to the named property it will throw a
	 * {@link RuntimeException}.
	 * </p>
	 * <p>
	 * If the given property is known to be unique across the set of input beans
	 * (for example an id, a pk, or a key) then {@link #toKeyBeanLookup(String)}
	 * is a better transformer.
	 * </p>
	 * <p>
	 * If more than one bean contains the same value for the property (or more
	 * specifically they return true for their equals and hashcode), only the
	 * last entry (based on the iteration order of the given collection) will be
	 * present.
	 * </p>
	 * <p>
	 * This transformer will retain the order (if any) of the given collection
	 * in both the resulting map and of individual beans in a mapped to a
	 * property value. That is, if the collection is ordered, the map keys will
	 * be ordered based on the order of the beans they are first matched in.
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
	 * Given a {@link Class} and the name of a javabean property returns an
	 * {@link ETransformer}. The returned {@link ETransformer} will provide a
	 * transformation across a collection of input javabean objects of the given
	 * type by returning a map which can be used as a lookup table. The lookup
	 * table maps from property value to the bean containing that value.
	 * </p>
	 * <p>
	 * If a null collection is passed to the resulting {@link ETransformer}, it
	 * will return an empty map.
	 * </p>
	 * <p>
	 * If any object in the collection provided to the resulting transformer
	 * does not provide access to the named property it will throw a
	 * {@link RuntimeException}.
	 * </p>
	 * <p>
	 * If the given property is known to be unique across the set of input beans
	 * (for example an id, a pk, or a key) then {@link #toKeyBeanLookup(String)}
	 * is a better transformer.
	 * </p>
	 * <p>
	 * If more than one bean contains the same value for the property (or more
	 * specifically they return true for their equals and hashcode), only the
	 * last entry (based on the iteration order of the given collection) will be
	 * present.
	 * </p>
	 * <p>
	 * This transformer will retain the order (if any) of the given collection
	 * in both the resulting map and of individual beans in a mapped to a
	 * property value. That is, if the collection is ordered, the map keys will
	 * be ordered based on the order of the beans they are first matched in.
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
	 * Creates a {@link CollectionTransformer} for the given
	 * {@link ETransformer}.
	 * </p>
	 * <p>
	 * {@link CollectionTransformer}s use a transformer to transform a
	 * collection rather than an individual object.
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
