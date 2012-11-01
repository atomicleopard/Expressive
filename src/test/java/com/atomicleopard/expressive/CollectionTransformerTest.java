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
package com.atomicleopard.expressive;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

@SuppressWarnings("deprecation")
public class CollectionTransformerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private EBidiTransformer<BigDecimal, Double> bidiTransformer;
	private ETransformer<Integer, String> simpleTransformer;

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
	public void shouldReverseTransformArray() {
		CollectionTransformer<BigDecimal, Double> transformer = new CollectionTransformer<BigDecimal, Double>(bidiTransformer);

		Double[] input = new Double[] { 1.1, 1.2, 2.1, 3.1 };
		EList<BigDecimal> output = transformer.from(input);
		assertThat(output.size(), is(4));
		assertThat(output.get(0), is(BigDecimal.valueOf(1.1)));
		assertThat(output.get(1), is(BigDecimal.valueOf(1.2)));
		assertThat(output.get(2), is(BigDecimal.valueOf(2.1)));
		assertThat(output.get(3), is(BigDecimal.valueOf(3.1)));
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
	public void shouldReverseTransformList() {
		CollectionTransformer<BigDecimal, Double> transformer = new CollectionTransformer<BigDecimal, Double>(bidiTransformer);

		List<Double> input = Arrays.asList(0.0, 1.0, 2.2, 3.3);
		EList<BigDecimal> output = transformer.from(input);
		assertThat(output.size(), is(4));
		assertThat(output.get(0), is(BigDecimal.valueOf(0.0)));
		assertThat(output.get(1), is(BigDecimal.valueOf(1.0)));
		assertThat(output.get(2), is(BigDecimal.valueOf(2.2)));
		assertThat(output.get(3), is(BigDecimal.valueOf(3.3)));
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

	@Test
	public void shouldReverseTransformSet() {
		CollectionTransformer<BigDecimal, Double> transformer = new CollectionTransformer<BigDecimal, Double>(bidiTransformer);

		Set<Double> input = new HashSet<Double>();
		input.add(1.1);
		input.add(2.2);
		input.add(3.3);
		EList<BigDecimal> output = transformer.from(input);
		assertThat(output.size(), is(3));
		assertThat(output, hasItems(BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2), BigDecimal.valueOf(3.3)));
	}

	@Test
	public void shouldForwardTransformListWithBidiTransformer() {
		CollectionTransformer<BigDecimal, Double> transformer = new CollectionTransformer<BigDecimal, Double>(bidiTransformer);

		EList<BigDecimal> input = Expressive.list(BigDecimal.valueOf(1.1), BigDecimal.valueOf(90.90), BigDecimal.valueOf(1.1));
		EList<Double> output = transformer.to(input);
		assertThat(output.size(), is(3));
		assertThat(output.get(0), is(1.1));
		assertThat(output.get(1), is(90.90));
		assertThat(output.get(2), is(1.1));
	}

	@Test
	public void shouldTransformArrayWithBidiTransformer() {
		CollectionTransformer<BigDecimal, Double> transformer = new CollectionTransformer<BigDecimal, Double>(bidiTransformer);

		BigDecimal[] input = new BigDecimal[] { BigDecimal.valueOf(1.1), BigDecimal.valueOf(90.90), BigDecimal.valueOf(1.1) };
		EList<Double> output = transformer.to(input);
		assertThat(output.size(), is(3));
		assertThat(output.get(0), is(1.1));
		assertThat(output.get(1), is(90.90));
		assertThat(output.get(2), is(1.1));
	}

	@Test
	public void shouldTransformArrayWithStaticMethods() {
		EList<String> transformed = CollectionTransformer.to(simpleTransformer, 1, 2, 6, 5);
		assertThat(transformed.size(), is(4));
		assertThat(transformed.get(0), is("1"));
		assertThat(transformed.get(1), is("2"));
		assertThat(transformed.get(2), is("6"));
		assertThat(transformed.get(3), is("5"));
	}

	@Test
	public void shouldTransformListWithStaticMethods() {
		EList<String> transformed = CollectionTransformer.to(simpleTransformer, Arrays.asList(1, 2, 6, 5));
		assertThat(transformed.size(), is(4));
		assertThat(transformed.get(0), is("1"));
		assertThat(transformed.get(1), is("2"));
		assertThat(transformed.get(2), is("6"));
		assertThat(transformed.get(3), is("5"));
	}

	@Test
	public void shouldReverseTransformArrayWithStaticMethods() {
		EList<BigDecimal> output = CollectionTransformer.from(bidiTransformer, 1.1, 2.1, 6.1, 5.1);
		assertThat(output.size(), is(4));
		assertThat(output.get(0), is(BigDecimal.valueOf(1.1)));
		assertThat(output.get(1), is(BigDecimal.valueOf(2.1)));
		assertThat(output.get(2), is(BigDecimal.valueOf(6.1)));
		assertThat(output.get(3), is(BigDecimal.valueOf(5.1)));
	}

	@Test
	public void shouldReverseTransformListWithStaticMethods() {
		EList<BigDecimal> output = CollectionTransformer.from(bidiTransformer, Arrays.asList(1.1, 2.1, 6.1, 5.1));
		assertThat(output.size(), is(4));
		assertThat(output.get(0), is(BigDecimal.valueOf(1.1)));
		assertThat(output.get(1), is(BigDecimal.valueOf(2.1)));
		assertThat(output.get(2), is(BigDecimal.valueOf(6.1)));
		assertThat(output.get(3), is(BigDecimal.valueOf(5.1)));
	}

	@Test
	public void shouldThrowUnsupportedOperationExceptionWhenReverseTransformArrayWithForwardOnlyTransformer() {
		thrown.expect(UnsupportedOperationException.class);
		CollectionTransformer<Integer, String> transformer = new CollectionTransformer<Integer, String>(simpleTransformer);

		String[] input = new String[] { "1.1", "1.2", "2.1", "3.1" };
		transformer.from(input);
	}

	@Test
	public void shouldThrowUnsupportedOperationExceptionWhenReverseTransformListWithForwardOnlyTransformer() {
		thrown.expect(UnsupportedOperationException.class);
		CollectionTransformer<Integer, String> transformer = new CollectionTransformer<Integer, String>(simpleTransformer);

		List<String> input = Arrays.asList("1.1", "1.2", "2.1", "3.1");
		transformer.from(input);
	}

	@Before
	public void before() {

		simpleTransformer = new ETransformer<Integer, String>() {
			public String to(Integer in) {
				return in.toString();
			}
		};
		bidiTransformer = new EBidiTransformer<BigDecimal, Double>() {
			@Override
			public BigDecimal from(Double from) {
				return BigDecimal.valueOf(from);
			}

			@Override
			public Double to(BigDecimal from) {
				return from.doubleValue();
			}

		};
	}
}
