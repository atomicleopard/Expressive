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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BeanPropertyTransformerTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldTransformToPropertyValueWhenClassIsDeterminedOnTheFly() {
		BeanPropertyTransformer<TestBean, String> propertyTransformer = new BeanPropertyTransformer<TestBean, String>("named");
		assertThat(propertyTransformer.to(new TestBean(1, "one")), is("one"));
		assertThat(propertyTransformer.to(new TestBean(2, "two")), is("two"));
		assertThat(propertyTransformer.to(new TestBean(3, null)), is(nullValue()));
		assertThat(propertyTransformer.to(null), is(nullValue()));
	}

	@Test
	public void shouldTransformToPropertyValueWhenValueIsBasicTypeWhenClassIsDeterminedOnTheFly() {
		BeanPropertyTransformer<TestBean, Integer> propertyTransformer = new BeanPropertyTransformer<TestBean, Integer>("pk");
		assertThat(propertyTransformer.to(new TestBean(1, "one")), is(1));
		assertThat(propertyTransformer.to(new TestBean(2, "two")), is(2));
	}

	@Test
	public void shouldThrowAnExceptionIfBeanPropertyNotAvailableWhenClassIsDeterminedOnTheFly() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("There is no accessible property named 'somethingElse'");
		BeanPropertyTransformer<TestBean, Integer> propertyTransformer = new BeanPropertyTransformer<TestBean, Integer>("somethingElse");
		assertThat(propertyTransformer.to(new TestBean(4, "four")), is(4));
	}

	@Test
	public void shouldTransformToPropertyValue() {
		BeanPropertyTransformer<TestBean, String> propertyTransformer = new BeanPropertyTransformer<TestBean, String>(TestBean.class, "named");
		assertThat(propertyTransformer.to(new TestBean(1, "one")), is("one"));
		assertThat(propertyTransformer.to(new TestBean(2, "two")), is("two"));
		assertThat(propertyTransformer.to(new TestBean(3, null)), is(nullValue()));
		assertThat(propertyTransformer.to(null), is(nullValue()));
	}

	@Test
	public void shouldTransformToPropertyValueWhenValueIsBasicType() {
		BeanPropertyTransformer<TestBean, Integer> propertyTransformer = new BeanPropertyTransformer<TestBean, Integer>(TestBean.class, "pk");
		assertThat(propertyTransformer.to(new TestBean(1, "one")), is(1));
		assertThat(propertyTransformer.to(new TestBean(2, "two")), is(2));

	}

	@Test
	public void shouldThrowAnExceptionIfBeanPropertyNotAvailableWhenClassIsProvidedForConstruction() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("There is no accessible property named 'somethingElse'");
		new BeanPropertyTransformer<TestBean, Integer>(TestBean.class, "somethingElse");
	}
}
