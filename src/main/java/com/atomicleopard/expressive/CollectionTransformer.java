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

import java.util.ArrayList;

import com.atomicleopard.expressive.transform.ETransformers;

/**
 * {@link CollectionTransformer} enables succinct transformation of collections
 * of objects utilising the {@link ETransformer} and {@link EBidiTransformer}
 * pattern.
 * 
 * {@link CollectionTransformer}s are stateless and can be instantiated directly
 * and reused, or for convenience the static from and to methods can be used.
 * 
 * Reverse transformations (CollectionTransformer.from(...) methods) only
 * function when a {@link EBidiTransformer} is supplied, otherwise an
 * {@link UnsupportedOperationException} will be thrown. This can be avoided by
 * utilising the static methods.
 * 
 * @see #from(EBidiTransformer, Iterable)
 * @see #from(EBidiTransformer, Object...)
 * @see #to(Iterable)
 * @see #to(Object...)
 * 
 * @param <In>
 * @param <Out>
 * 
 * @deprecated Please use
 *             {@link com.atomicleopard.expressive.transform.CollectionTransformer}
 *             or the static methods available on {@link ETransformers} instead.
 *             To be removed in a future version.
 */
public class CollectionTransformer<In, Out> {

	private ETransformer<In, Out> transformer;
	private EBidiTransformer<In, Out> bidiTransformer = null;

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
	@SuppressWarnings("unchecked")
	public CollectionTransformer(ETransformer<In, Out> transformer) {
		this.transformer = transformer;
		this.bidiTransformer = Cast.as(transformer, EBidiTransformer.class);
	}

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
	public CollectionTransformer(EBidiTransformer<In, Out> transformer) {
		this.transformer = transformer;
		this.bidiTransformer = transformer;
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

	/**
	 * @param out
	 *            values to transform
	 * @return an {@link EList} containing the reverse transformed objects for
	 *         the given out parameters in the same order as they are supplied
	 */
	public EList<In> from(Out... out) throws UnsupportedOperationException {
		if (bidiTransformer == null) {
			throw new UnsupportedOperationException(String.format("Unable to reverse transform, the transforer (%s) supplied is not an %s", transformer.getClass().getName(),
					EBidiTransformer.class.getName()));
		}
		EList<In> result = new EListImpl<In>(new ArrayList<In>(out.length));
		for (Out entry : out) {
			result.add(bidiTransformer.from(entry));
		}
		return result;
	}

	/**
	 * @param out
	 *            values to transform
	 * @return an {@link EList} containing the reverse transformed objects for
	 *         the given out {@link Iterable} in the same order as they are
	 *         supplied
	 */
	public EList<In> from(Iterable<Out> out) throws UnsupportedOperationException {
		if (bidiTransformer == null) {
			throw new UnsupportedOperationException(String.format("Unable to reverse transform, the transforer (%s) supplied is not an %s", transformer.getClass().getName(),
					EBidiTransformer.class.getName()));
		}

		EList<In> result = new EListImpl<In>();
		for (Out entry : out) {
			result.add(bidiTransformer.from(entry));
		}
		return result;
	}

	/**
	 * Convenience method to transform the given {@link Iterable} using the
	 * given {@link ETransformer}
	 * 
	 * @param <In>
	 * @param <Out>
	 * @param transformer
	 *            the transformer to transform the given {@link Iterable} with
	 * @param in
	 *            an {@link Iterable} containing the values to transform
	 * @return an {@link EList} containing the transformed values
	 */
	public static <In, Out> EList<Out> to(ETransformer<In, Out> transformer, Iterable<In> in) {
		CollectionTransformer<In, Out> collectionTransformer = new CollectionTransformer<In, Out>(transformer);
		return collectionTransformer.to(in);
	}

	/**
	 * Convenience method to transform the given items using the given
	 * {@link ETransformer}
	 * 
	 * @param <In>
	 * @param <Out>
	 * @param transformer
	 *            the transformer to transform the given {@link Iterable} with
	 * @param in
	 *            the values to transform
	 * @return an {@link EList} containing the transformed values
	 */
	public static <In, Out> EList<Out> to(ETransformer<In, Out> transformer, In... in) {
		CollectionTransformer<In, Out> collectionTransformer = new CollectionTransformer<In, Out>(transformer);
		return collectionTransformer.to(in);
	}

	/**
	 * Convenience method to reverse transform the given {@link Iterable} using
	 * the given {@link EBidiTransformer}
	 * 
	 * @param <In>
	 * @param <Out>
	 * @param transformer
	 *            the transformer to reverse transform the given
	 *            {@link Iterable} with
	 * @param out
	 *            an {@link Iterable} containing the values to reverse transform
	 * @return an {@link EList} containing the transformed values
	 */
	public static <In, Out> EList<In> from(EBidiTransformer<In, Out> transformer, Iterable<Out> out) {
		CollectionTransformer<In, Out> collectionTransformer = new CollectionTransformer<In, Out>(transformer);
		return collectionTransformer.from(out);
	}

	/**
	 * Convenience method to reverse transform the given value using the given
	 * {@link EBidiTransformer}
	 * 
	 * @param <In>
	 * @param <Out>
	 * @param transformer
	 *            the transformer to reverse transform the given value with
	 * @param out
	 *            the values to reverse transform
	 * @return an {@link EList} containing the transformed values
	 */
	public static <In, Out> EList<In> from(EBidiTransformer<In, Out> transformer, Out... out) {
		CollectionTransformer<In, Out> collectionTransformer = new CollectionTransformer<In, Out>(transformer);
		return collectionTransformer.from(out);
	}
}
