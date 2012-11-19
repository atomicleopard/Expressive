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

public class NotPredicate<T> implements EPredicate<T> {
	private EPredicate<T> predicate;

	public NotPredicate(EPredicate<T> predicate) {
		if (predicate == null) {
			throw new NullPointerException(String.format("Null %s passed to the %s constructor", EPredicate.class.getSimpleName(), NotPredicate.class.getSimpleName()));
		}
		this.predicate = predicate;
	}

	@Override
	public boolean pass(T input) {
		return !predicate.pass(input);
	}

	@Override
	public String toString() {
		return "not " + predicate;
	}
}