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

/**
 * An {@link EPredicate} that always returns a specified value, true or false.
 * 
 * Useful in scenarios using the Null Object and Chain of Command patterns and
 * in testing.
 * 
 * @param <T>
 */
public class ConstantPredicate<T> implements EPredicate<T> {
	private boolean result = false;

	public ConstantPredicate(boolean result) {
		this.result = result;
	}

	@Override
	public boolean pass(T input) {
		return result;
	}

	@Override
	public String toString() {
		return result ? "always passes" : "never passes";
	}
}
