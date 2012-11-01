/*
 *  Copyright (c) 2011 Nicholas Okunew
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
package com.atomicleopard.expressive.transform;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.atomicleopard.expressive.EList;
import com.atomicleopard.expressive.ETransformer;

public class CollectionTransformerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private ETransformer<Integer, String> simpleTransformer;

	@Before
	public void before() {
		simpleTransformer = new ETransformer<Integer, String>() {
			public String to(Integer in) {
				return in.toString();
			}
		};
	}

	@Test
	public void shouldTransformArray() {
		CollectionTransformer<Integer, String> transformer = new CollectionTransformer<Integer, String>(simpleTransformer);

		Integer[] input = new Integer[] { 1, 1, 2, 3 };
		EList<String> output = transformer.to(input);
		assertThat(output.size(), is(4));
		assertThat(output.get(0), is("1"));
		assertThat(output.get(1), is("1"));
		assertThat(output.get(2), is("2"));
		assertThat(output.get(3), is("3"));
	}

	@Test
	public void shouldTransformList() {
		CollectionTransformer<Integer, String> transformer = new CollectionTransformer<Integer, String>(simpleTransformer);

		List<Integer> input = Arrays.asList(1, 1, 2, 3);
		List<String> output = transformer.to(input);
		assertThat(output.size(), is(4));
		assertThat(output.get(0), is("1"));
		assertThat(output.get(1), is("1"));
		assertThat(output.get(2), is("2"));
		assertThat(output.get(3), is("3"));
	}

	@Test
	public void shouldTransformSet() {
		CollectionTransformer<Integer, String> transformer = new CollectionTransformer<Integer, String>(simpleTransformer);
		Set<Integer> input = new HashSet<Integer>();
		input.add(1);
		input.add(2);
		input.add(3);
		List<String> output = transformer.to(input);
		assertThat(output.size(), is(3));
		assertThat(output, hasItems("1", "2", "3"));
	}

}
