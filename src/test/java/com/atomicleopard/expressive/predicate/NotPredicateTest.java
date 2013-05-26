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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.atomicleopard.expressive.Expressive.Predicate;

public class NotPredicateTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldPassFalseAndFailTrue() {
		assertThat(new NotPredicate<String>(new ConstantPredicate<String>(false)).pass("something"), is(true));
		assertThat(new NotPredicate<String>(new ConstantPredicate<String>(false)).pass(""), is(true));
		assertThat(new NotPredicate<String>(new ConstantPredicate<String>(false)).pass(null), is(true));

		assertThat(new NotPredicate<String>(new ConstantPredicate<String>(true)).pass("something"), is(false));
		assertThat(new NotPredicate<String>(new ConstantPredicate<String>(true)).pass(""), is(false));
		assertThat(new NotPredicate<String>(new ConstantPredicate<String>(true)).pass(null), is(false));
	}

	@Test
	public void shouldHaveAToString() {
		assertThat(new NotPredicate<String>(Predicate.is("A")).toString(), is("not is A"));
	}

	@Test
	public void shouldThrowNullIfGivenNullPredicate() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Null EPredicate passed to the NotPredicate constructor");
		new NotPredicate<Integer>(null);
	}
}
