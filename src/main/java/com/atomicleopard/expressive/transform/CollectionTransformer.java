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

import java.util.Arrays;

import com.atomicleopard.expressive.EList;
import com.atomicleopard.expressive.EListImpl;
import com.atomicleopard.expressive.ETransformer;

/**
 * {@link CollectionTransformer} enables succinct transformation of collections
 * of objects utilising the {@link ETransformer} pattern.
 * 
 * {@link CollectionTransformer}s are stateless and can be instantiated directly
 * and reused, or for convenience created using the static methods available on {@link ETransformers}.
 * 
 * On top of the regular {@link #from(Iterable)} override this class offers
 * another method ({@link #from(Object...)} which accepts varargs for convenience
 * in invocation.
 * 
 * @see #from(Iterable)
 * @see #from(Object...)
 * 
 * @param <In>
 * @param <Out>
 */
public class CollectionTransformer<In, Out> implements ETransformer<Iterable<In>, EList<Out>> {

	private ETransformer<In, Out> transformer;

	/**
	 * <p>
	 * Creates a new {@link CollectionTransformer} which can be used to transform collections.
	 * </p>
	 * <p>
	 * Reverse transformations, that is using the {@link #from(Iterable)} and {@link #from(Object...)} methods will only work if the given {@link ETransformer} is an {@link EBidiTransformer}.
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
	public EList<Out> from(In... in) {
		return in == null ? new EListImpl<Out>() : from(Arrays.asList(in));
	}

	/**
	 * @param in
	 *            values to transform
	 * @return an {@link EList} containing the transformed objects for the given {@link Iterable} in the same order as they are supplied
	 */
	public EList<Out> from(Iterable<In> in) {
		EList<Out> result = new EListImpl<Out>();
		if (in != null) {
			for (In entry : in) {
				result.add(transformer.from(entry));
			}
		}
		return result;
	}
}
