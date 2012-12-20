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
package com.atomicleopard.expressive;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IteratorTransformerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldTransformIterator() {
		ETransformer<Integer, String> transformer = new ETransformer<Integer, String>() {
			public String from(Integer in) {
				return in.toString();
			}
		};
		EList<Integer> values = Expressive.list(1, 1, 2, 3, 4);
		Iterator<String> iterator = new IteratorTransformer<Integer, String>(transformer, values.iterator());
		assertThat(iterator.next(), is("1"));
		assertThat(iterator.next(), is("1"));
		assertThat(iterator.next(), is("2"));
		assertThat(iterator.next(), is("3"));
		assertThat(iterator.next(), is("4"));
		assertThat(iterator.hasNext(), is(false));
	}

	@Test
	public void shouldAllowRemoveOnIteratorsThatSupportRemove() {
		ETransformer<Integer, String> transformer = new ETransformer<Integer, String>() {
			public String from(Integer in) {
				return in.toString();
			}
		};
		EList<Integer> values = Expressive.list(1, 1, 2, 3, 4);
		Iterator<String> iterator = new IteratorTransformer<Integer, String>(transformer, values.iterator());
		assertThat(iterator.next(), is("1"));
		iterator.remove();
		assertThat(iterator.next(), is("1"));
		iterator.remove();
		assertThat(iterator.next(), is("2"));
		assertThat(iterator.next(), is("3"));
		assertThat(iterator.next(), is("4"));
		assertThat(iterator.hasNext(), is(false));

		assertThat(values.size(), is(3));
		assertThat(values, hasItems(2, 3, 4));
	}

	@Test
	public void shouldThrowExceptionOnRemoveOnIteratorsThatDoesntSupportRemove() {
		thrown.expect(UnsupportedOperationException.class);
		ETransformer<Integer, String> transformer = new ETransformer<Integer, String>() {
			public String from(Integer in) {
				return in.toString();
			}
		};
		List<Integer> values = Arrays.asList(1, 1, 2, 3, 4);
		Iterator<String> iterator = new IteratorTransformer<Integer, String>(transformer, values.iterator());
		iterator.next();
		iterator.remove();
	}
}
