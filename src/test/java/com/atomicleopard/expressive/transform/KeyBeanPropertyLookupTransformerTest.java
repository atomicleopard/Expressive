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

import static com.atomicleopard.expressive.Expressive.list;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class KeyBeanPropertyLookupTransformerTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	private TestBean testBean1 = new TestBean(1, "one");
	private TestBean testBean2 = new TestBean(2, "two");
	private TestBean testBean3 = new TestBean(3, "three");
	private TestBean testBean3Again = new TestBean(3, "three");

	private KeyBeanPropertyLookupTransformer<TestBean, String> namedTransformer = new KeyBeanPropertyLookupTransformer<TestBean, String>("named");
	private KeyBeanPropertyLookupTransformer<TestBean, String> pkTransformer = new KeyBeanPropertyLookupTransformer<TestBean, String>("pk");

	@Test
	public void shouldReturnEmptyCollectionForNullAndEmptyInput() {
		assertThat(namedTransformer.to(null).isEmpty(), is(true));
		assertThat(namedTransformer.to(Collections.<TestBean> emptySet()).isEmpty(), is(true));
		assertThat(namedTransformer.to(Collections.<TestBean> emptyList()).isEmpty(), is(true));
	}

	@Test
	public void shouldCreateLookupMapForBeansFromProperty() {
		Map<String, TestBean> map = namedTransformer.to(Arrays.asList(testBean1, testBean2, testBean3, testBean3Again));

		assertThat(map.get("one"), is(testBean1));
		assertThat(map.get("two"), is(testBean2));
		assertThat(map.get("three"), is(testBean3Again));
		assertThat(map.size(), is(3));
	}

	@Test
	public void shouldCreateLookupMapForBeansFromPropertyThatsTheId() {
		Map<String, TestBean> map = pkTransformer.to(Arrays.asList(testBean1, testBean2, testBean3, testBean3Again));

		assertThat(map.get(1), is(testBean1));
		assertThat(map.get(2), is(testBean2));
		assertThat(map.get(3), is(testBean3Again));
		assertThat(map.size(), is(3));
	}

	@Test
	public void shouldRetainOrderOfCollectionInMapAndLists() {
		testBean3Again.setPk(4);

		verifyOrderIsMaintained(namedTransformer, Arrays.asList(testBean1, testBean2, testBean3, testBean3Again));
		verifyOrderIsMaintained(namedTransformer, new LinkedBlockingQueue<TestBean>(Arrays.asList(testBean1, testBean2, testBean3, testBean3Again)));
		verifyOrderIsMaintained(namedTransformer, new TreeSet<TestBean>(Arrays.asList(testBean1, testBean2, testBean3, testBean3Again)));
	}

	@Test
	public void shouldntRetainOrderOfUnorderedCollection() {
		Map<String, TestBean> map = namedTransformer.to(Arrays.asList(testBean1, testBean2, testBean3, testBean3Again));
		assertThat(map.size(), not(is(LinkedHashMap.class)));
	}

	@Test
	public void shouldCreateLookupMapForBeansFromPropertyWhenClassIsPassedToCtor() {
		namedTransformer = new KeyBeanPropertyLookupTransformer<TestBean, String>(TestBean.class, "named");
		Map<String, TestBean> map = namedTransformer.to(Arrays.asList(testBean1, testBean2, testBean3, testBean3Again));

		assertThat(map.get("one"), is(testBean1));
		assertThat(map.get("two"), is(testBean2));
		assertThat(map.get("three"), is(testBean3Again));
		assertThat(map.size(), is(3));
	}

	@Test
	public void shouldThrowAnExceptionIfBeanPropertyAccessorCreatesAnException() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Failed to transform a Collection to a lookup Map using property 'anException'");
		KeyBeanPropertyLookupTransformer<TestBean, String> transformer = new KeyBeanPropertyLookupTransformer<TestBean, String>(TestBean.class, "anException");
		transformer.to(list(new TestBean(1, "named")));
	}

	private void verifyOrderIsMaintained(KeyBeanPropertyLookupTransformer<TestBean, String> transformer, Collection<TestBean> beans) {
		Map<String, TestBean> map = transformer.to(beans);

		assertThat(map.size(), is(3));
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		assertThat(iterator.next(), is("one"));
		assertThat(iterator.next(), is("two"));
		assertThat(iterator.next(), is("three"));
	}
}
