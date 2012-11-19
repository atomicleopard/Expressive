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

import static com.atomicleopard.expressive.Expressive.list;

import java.util.Arrays;
import java.util.List;

/**
 * An {@link EPredicate} which will pass any input which is considered equal to
 * one of the specified objects, using the {@link Object#equals(Object)} implementation.
 * 
 * @param <T>
 */
public class AnyOfPredicate<T> implements EPredicate<T> {
	protected List<EPredicate<T>> predicates;

	public AnyOfPredicate(EPredicate<T>... predicates) {
		this(Arrays.asList(predicates));
	}

	public AnyOfPredicate(List<EPredicate<T>> predicates) {
		this.predicates = predicates;
	}

	@Override
	public boolean pass(T input) {
		for (EPredicate<T> predicate : predicates) {
			if (predicate.pass(input)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "any of " + predicates;
	}

	public AnyOfPredicate<T> or(EPredicate<T>... predicates) {
		return new AnyOfPredicate<T>(list(this.predicates).addItems(predicates));
	}

	public AnyOfPredicate<T> or(List<T> values) {
		return new AnyOfPredicate<T>(list(this.predicates).addItems(EqualsPredicate.<T> asPredicates(values)));
	}

	public AnyOfPredicate<T> or(T... values) {
		return new AnyOfPredicate<T>(list(this.predicates).addItems(EqualsPredicate.<T> asPredicates(values)));
	}
}
