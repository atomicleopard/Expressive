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

import static com.atomicleopard.expressive.Expressive.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.atomicleopard.expressive.transform.EnumFromStringTransformerTest.TestEnum;
import com.atomicleopard.expressive.transform.TestBean;

public class ExpressiveTransformersTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldTransformUsingLookup() {
		Map<Integer, String> map = map(1, "one", 2, "two", 3, "three");
		ETransformer<Integer, String> transformer = Expressive.Transformers.usingLookup(map);
		assertThat(transformer.from(1), is("one"));
		assertThat(transformer.from(2), is("two"));
		assertThat(transformer.from(3), is("three"));
		assertThat(transformer.from(4), is(nullValue()));
	}

	@Test
	public void shouldReturnATransformerToMapBeanProperties() {
		ETransformer<TestBean, Integer> transformer = Expressive.Transformers.toProperty("pk");
		assertThat(transformer.from(new TestBean(1, "one")), is(1));
	}

	@Test
	public void shouldReturnATransformerToMapBeanPropertiesForASpecificClass() {
		ETransformer<TestBean, String> transformer = Expressive.Transformers.toProperty("named", TestBean.class);
		assertThat(transformer.from(new TestBean(1, "one")), is("one"));
	}

	@Test
	public void shouldReturnATransformerToMapBeanToLookup() {
		ETransformer<Collection<TestBean>, Map<String, List<TestBean>>> transformer = Expressive.Transformers.toBeanLookup("pk");
		TestBean first = new TestBean(1, "one");
		TestBean second = new TestBean(1, "one");
		TestBean third = new TestBean(2, "two");
		Map<String, List<TestBean>> map = transformer.from(list(first, second, third));
		assertThat(map.get(1), hasItems(first, second));
		assertThat(map.get(2), hasItems(third));
	}

	@Test
	public void shouldReturnATransformerToMapBeanToLookupForASpecificClass() {
		ETransformer<Collection<TestBean>, Map<String, List<TestBean>>> transformer = Expressive.Transformers.toBeanLookup("named", TestBean.class);
		TestBean first = new TestBean(1, "one");
		TestBean second = new TestBean(1, "one");
		TestBean third = new TestBean(2, "two");
		Map<String, List<TestBean>> map = transformer.from(list(first, second, third));
		assertThat(map.get("one"), hasItems(first, second));
		assertThat(map.get("two"), hasItems(third));
	}

	@Test
	public void shouldReturnATransformerToMapBeanToUniqueLookup() {
		ETransformer<Collection<TestBean>, Map<String, TestBean>> transformer = Expressive.Transformers.toKeyBeanLookup("pk");
		TestBean first = new TestBean(1, "one");
		TestBean second = new TestBean(1, "one");
		TestBean third = new TestBean(2, "two");
		Map<String, TestBean> map = transformer.from(list(first, second, third));
		assertThat(map.get(1), is(second));
		assertThat(map.get(2), is(third));
	}

	@Test
	public void shouldReturnATransformerToMapBeanToUniqueLookupForASpecificClass() {
		ETransformer<Collection<TestBean>, Map<String, TestBean>> transformer = Expressive.Transformers.toKeyBeanLookup("named", TestBean.class);
		TestBean first = new TestBean(1, "one");
		TestBean second = new TestBean(1, "one");
		TestBean third = new TestBean(2, "two");
		Map<String, TestBean> map = transformer.from(list(first, second, third));
		assertThat(map.get("one"), is(second));
		assertThat(map.get("two"), is(third));
	}

	@Test
	public void shouldHaveANonPublicCtorWhichIsCoveredSoIStopCheckingTheClassToSeeWhatDoesntHaveCoverage() {
		assertThat(new Expressive.Transformers(), is(notNullValue()));
	}

	@Test
	public void shouldTransformACollectionUsingAGivenTransformer() {
		com.atomicleopard.expressive.transform.CollectionTransformer<TestBean, Integer> transformer = Expressive.Transformers.transformAllUsing(Expressive.Transformers
				.<TestBean, Integer> toProperty("pk"));
		assertThat(transformer.to(new TestBean(1, "one"), new TestBean(2, "two")), is(list(1, 2)));
		assertThat(transformer.to(new TestBean(2, "two"), new TestBean(1, "one")), is(list(2, 1)));
	}

	@Test
	public void shouldTransformAnObjectToStringValues() {
		ETransformer<StringBuilder, String> transformer = Expressive.Transformers.<StringBuilder>toString();
		assertThat(transformer, is(notNullValue()));
		assertThat(transformer.from(new StringBuilder("expected")), is("expected"));
		assertThat(transformer.from(null), is(nullValue()));
	}
	
	@Test
	public void shouldTransformFromAnEnumToStringValues() {
		ETransformer<TestEnum, String> transformer = Expressive.Transformers.fromEnum(TestEnum.class);
		assertThat(transformer, is(notNullValue()));
		assertThat(transformer.from(TestEnum.TestVal1), is("TestVal1"));
		assertThat(transformer.from(null), is(nullValue()));
	}

	@Test
	public void shouldTransformFromAStringToAnEnum() {
		ETransformer<String, TestEnum> transformer = Expressive.Transformers.toEnum(TestEnum.class);
		assertThat(transformer, is(notNullValue()));
		assertThat(transformer.from("TestVal1"), is(TestEnum.TestVal1));
		assertThat(transformer.from(null), is(nullValue()));
		assertThat(transformer.from("junk"), is(nullValue()));
	}
}
