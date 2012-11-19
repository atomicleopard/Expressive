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
package com.atomicleopard.expressive.comparator;

import java.util.Comparator;

/**
 * A comparator which allows one or both arguments to be null.
 * Whether a single null argument comes before or after a non-null (i.e. -1 or 1) is controlled at construction time.
 * 
 * @param <T>
 */
public class NullsafeComparator<T> extends DelegateComparator<T> {
	private int nullLast = 1;

	public NullsafeComparator(Comparator<T> delegate, boolean nullLast) {
		super(delegate);
		this.nullLast = nullLast ? -1 : 1;
	}

	@Override
	public int compare(T arg0, T arg1) {
		return arg0 != null && arg1 != null ? super.compare(arg0, arg1) : nullComparison(arg0, arg1);
	}

	private int nullComparison(T arg0, T arg1) {
		if (arg0 == arg1) {
			return 0;
		}
		if (arg0 == null) {
			return nullLast;
		} else {
			return -1 * nullLast;
		}
	}
}
