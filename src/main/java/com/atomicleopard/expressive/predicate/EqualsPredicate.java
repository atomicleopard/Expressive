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

import java.util.List;

import com.atomicleopard.expressive.EList;
import com.atomicleopard.expressive.ETransformer;
import com.atomicleopard.expressive.Expressive;
import com.atomicleopard.expressive.transform.CollectionTransformer;

/**
 * An {@link EPredicate} which will pass any input which is considered equal to
 * the specified object, using the {@link Object#equals(Object)} implementation.
 * 
 * @param <T>
 */
public class EqualsPredicate<T> implements EPredicate<T> {
	private T value;

	public EqualsPredicate(T value) {
		this.value = value;
	}

	@Override
	public boolean pass(T input) {
		return value == null ? input == null : value.equals(input);
	}

	@Override
	public String toString() {
		return "is " + value;
	}

	public static <T> EList<EPredicate<T>> asPredicates(T... values) {
		return EqualsPredicate.Transformer.<T> ForValues().to(values);
	}

	public static <T> EList<EPredicate<T>> asPredicates(List<T> values) {
		return EqualsPredicate.Transformer.<T> ForValues().from(values);
	}

	public static final class Transformer {
		Transformer() {

		}

		public static <T> ETransformer<T, EPredicate<T>> ForValue() {
			return new ETransformer<T, EPredicate<T>>() {
				@Override
				public EPredicate<T> from(T from) {
					return new EqualsPredicate<T>(from);
				}
			};
		}

		public static <T> CollectionTransformer<T, EPredicate<T>> ForValues() {
			return Expressive.Transformers.transformAllUsing(Transformer.<T> ForValue());
		}
	}
}
