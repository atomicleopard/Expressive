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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CaseInsensitiveComparatorTest {

	@Test
	public void shouldCompareStringsIgnoringCase() {
		CaseInsensitiveComparator c = new CaseInsensitiveComparator();
		assertThat(c.compare("A", "A"), is(0));
		assertThat(c.compare("A", "a"), is(0));
		assertThat(c.compare("a", "A"), is(0));
		assertThat(c.compare("A", "b"), lessThan(0));
		assertThat(c.compare("A", "B"), lessThan(0));
		assertThat(c.compare("a", "b"), lessThan(0));
		assertThat(c.compare("a", "B"), lessThan(0));
		assertThat(c.compare("b", "A"), greaterThan(0));
		assertThat(c.compare("B", "A"), greaterThan(0));
		assertThat(c.compare("b", "a"), greaterThan(0));
		assertThat(c.compare("B", "a"), greaterThan(0));

	}
}
