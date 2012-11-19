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
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.atomicleopard.expressive.EList;
import com.atomicleopard.expressive.predicate.EPredicate;
import com.atomicleopard.expressive.predicate.EqualsPredicate;

public class EqualsPredicateTest {
	@Test
	public void shouldReturnTrueWhenEquals() {
		BigDecimal bigDecimal = new BigDecimal("1.234");
		EqualsPredicate<BigDecimal> equalsPredicate = new EqualsPredicate<BigDecimal>(bigDecimal);
		assertThat(equalsPredicate.pass(bigDecimal), is(true));
		assertThat(equalsPredicate.pass(new BigDecimal("1.234")), is(true));
		assertThat(equalsPredicate.pass(new BigDecimal(1.234)), is(false));
		assertThat(equalsPredicate.pass(null), is(false));
	}

	@Test
	public void shouldReturnTrueWhenEqualsNull() {
		EqualsPredicate<BigDecimal> equalsPredicate = new EqualsPredicate<BigDecimal>(null);
		assertThat(equalsPredicate.pass(null), is(true));
		assertThat(equalsPredicate.pass(new BigDecimal(1.234)), is(false));
	}

	@Test
	public void shouldReturnVarArgsValuesAsEqualsPredicates() {
		EList<EPredicate<String>> asPredicates = EqualsPredicate.asPredicates("A", "B", "C");
		assertThat(asPredicates.size(), is(3));
		assertThat(asPredicates.at(0).pass("A"), is(true));
		assertThat(asPredicates.at(1).pass("B"), is(true));
		assertThat(asPredicates.at(2).pass("C"), is(true));
	}

	@Test
	public void shouldReturnListValuesAsEqualsPredicates() {
		EList<EPredicate<String>> asPredicates = EqualsPredicate.asPredicates(list("A", "B", "C"));
		assertThat(asPredicates.size(), is(3));
		assertThat(asPredicates.at(0).pass("A"), is(true));
		assertThat(asPredicates.at(1).pass("B"), is(true));
		assertThat(asPredicates.at(2).pass("C"), is(true));
	}

	@Test
	public void shouldNotHaveAccessibleCtorOnTransformerStaticClass() {
		assertThat(new EqualsPredicate.Transformer(), is(notNullValue()));
	}

	@Test
	public void shouldHaveAToString() throws MalformedURLException {
		assertThat(new EqualsPredicate<String>(null).toString(), is("is null"));
		assertThat(new EqualsPredicate<String>("a").toString(), is("is a"));
		assertThat(new EqualsPredicate<Integer>(1).toString(), is("is 1"));
		assertThat(new EqualsPredicate<URL>(new URL("http://google.com")).toString(), is("is http://google.com"));
	}
}
