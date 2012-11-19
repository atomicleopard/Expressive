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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.atomicleopard.expressive.predicate.ConstantPredicate;

public class ConstantPredicateTest {
	@Test
	public void shouldAlwaysReturnTrue() {
		ConstantPredicate<String> constantPredicate = new ConstantPredicate<String>(true);
		assertThat(constantPredicate.pass("input"), is(true));
		assertThat(constantPredicate.pass("input"), is(true));
		assertThat(constantPredicate.pass("input 2"), is(true));
		assertThat(constantPredicate.pass(null), is(true));
	}

	@Test
	public void shouldAlwaysReturnFalse() {
		ConstantPredicate<String> constantPredicate = new ConstantPredicate<String>(false);
		assertThat(constantPredicate.pass("input"), is(false));
		assertThat(constantPredicate.pass("input"), is(false));
		assertThat(constantPredicate.pass("input 2"), is(false));
		assertThat(constantPredicate.pass(null), is(false));
	}

	@Test
	public void shouldHaveAToString() {
		assertThat(new ConstantPredicate<String>(true).toString(), is("always passes"));
		assertThat(new ConstantPredicate<String>(false).toString(), is("never passes"));
	}
}
