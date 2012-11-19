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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ComparableComparatorTest {
	@Test
	public void shouldCompareUsingComparable() {
		ComparableComparator<Integer> comparator = new ComparableComparator<Integer>();
		assertThat(comparator.compare(1, 2), is(new Integer(1).compareTo(2)));
		assertThat(comparator.compare(1, 100), is(new Integer(1).compareTo(100)));
		assertThat(comparator.compare(2, 1), is(new Integer(2).compareTo(1)));
		assertThat(comparator.compare(100, 1), is(new Integer(100).compareTo(1)));
	}
}
