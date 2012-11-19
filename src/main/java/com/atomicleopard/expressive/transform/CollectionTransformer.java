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

import java.util.ArrayList;

import com.atomicleopard.expressive.EBidiTransformer;
import com.atomicleopard.expressive.EList;
import com.atomicleopard.expressive.EListImpl;
import com.atomicleopard.expressive.ETransformer;

/**
 * {@link CollectionTransformer} enables succinct transformation of collections
 * of objects utilising the {@link ETransformer} pattern.
 * 
 * {@link CollectionTransformer}s are stateless and can be instantiated directly
 * and reused, or for convenience created using the static methods available on
 * {@link ETransformers}.
 * 
 * On top of the regular {@link #to(Iterable)} override this class offers
 * another method ({@link #to(Object...)} which accepts varargs for convenience
 * in invocation.
 * 
 * @see #to(Iterable)
 * @see #to(Object...)
 * 
 * @param <In>
 * @param <Out>
 */
public class CollectionTransformer<In, Out> implements ETransformer<Iterable<In>, EList<Out>> {

	private ETransformer<In, Out> transformer;

	/**
	 * <p>
	 * Creates a new {@link CollectionTransformer} which can be used to
	 * transform collections.
	 * </p>
	 * <p>
	 * Reverse transformations, that is using the {@link #from(Iterable)} and
	 * {@link #from(Object...)} methods will only work if the given
	 * {@link ETransformer} is an {@link EBidiTransformer}.
	 * </p>
	 * 
	 * @param transformer
	 */
	public CollectionTransformer(ETransformer<In, Out> transformer) {
		this.transformer = transformer;
	}

	/**
	 * @param in
	 *            values to transform
	 * @return an {@link EList} containing the transformed objects for the given
	 *         in parameters in the same order as they are supplied
	 */
	public EList<Out> to(In... in) {
		EList<Out> result = new EListImpl<Out>(new ArrayList<Out>(in.length));
		for (In entry : in) {
			result.add(transformer.to(entry));
		}
		return result;
	}

	/**
	 * @param in
	 *            values to transform
	 * @return an {@link EList} containing the transformed objects for the given
	 *         {@link Iterable} in the same order as they are supplied
	 */
	public EList<Out> to(Iterable<In> in) {
		EList<Out> result = new EListImpl<Out>();
		for (In entry : in) {
			result.add(transformer.to(entry));
		}
		return result;
	}
}
