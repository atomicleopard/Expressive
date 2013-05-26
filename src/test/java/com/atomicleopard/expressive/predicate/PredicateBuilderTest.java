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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.atomicleopard.expressive.Expressive.Predicate;

public class PredicateBuilderTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldReturnNewPredicateBuilderAndNotMutateOriginal() {
		PredicateBuilder<TestBean> builder1 = new PredicateBuilder<TestBean>(TestBean.class);
		PredicateBuilder<TestBean> builder2 = builder1.where("str1").is("A");
		PredicateBuilder<TestBean> builder3 = builder2.where("str2").is("A", "B");
		PredicateBuilder<TestBean> builder4 = builder3.where("int1").passes(Predicate.is(1));
		PredicateBuilder<TestBean> builder5 = builder4.where("dbl1").isNot(0d, 1d);
		assertThat(builder1, is(not(sameInstance(builder2))));
		assertThat(builder1, is(not(sameInstance(builder3))));
		assertThat(builder1, is(not(sameInstance(builder4))));
		assertThat(builder1, is(not(sameInstance(builder5))));
		assertThat(builder2, is(not(sameInstance(builder3))));
		assertThat(builder2, is(not(sameInstance(builder4))));
		assertThat(builder2, is(not(sameInstance(builder5))));
		assertThat(builder3, is(not(sameInstance(builder4))));
		assertThat(builder3, is(not(sameInstance(builder5))));
		assertThat(builder4, is(not(sameInstance(builder5))));

		assertThat(builder1.propertyPredicates.size(), is(not(builder2.propertyPredicates.size())));
		assertThat(builder1.propertyPredicates.size(), is(not(builder3.propertyPredicates.size())));
		assertThat(builder1.propertyPredicates.size(), is(not(builder4.propertyPredicates.size())));
		assertThat(builder1.propertyPredicates.size(), is(not(builder5.propertyPredicates.size())));
		assertThat(builder2.propertyPredicates.size(), is(not(builder3.propertyPredicates.size())));
		assertThat(builder2.propertyPredicates.size(), is(not(builder4.propertyPredicates.size())));
		assertThat(builder2.propertyPredicates.size(), is(not(builder5.propertyPredicates.size())));
		assertThat(builder3.propertyPredicates.size(), is(not(builder4.propertyPredicates.size())));
		assertThat(builder3.propertyPredicates.size(), is(not(builder5.propertyPredicates.size())));
		assertThat(builder4.propertyPredicates.size(), is(not(builder5.propertyPredicates.size())));
	}

	@Test
	public void shouldBuildAPredicateWhereAllFieldsMustMatchTheirIsCondition() {
		PredicateBuilder<TestBean> originalBuilder = new PredicateBuilder<TestBean>(TestBean.class);
		PredicateBuilder<TestBean> builder = originalBuilder.where("str1").is("a");
		assertThat(builder, not(sameInstance(originalBuilder)));
		builder = builder.where("str2").is("b");
		builder = builder.where("int1").is(1);
		builder = builder.where("dbl1").is(2d);
		assertThat(builder, not(sameInstance(originalBuilder)));

		assertThat(builder.pass(tb("a", "b", 1, 2d)), is(true));

		assertThat(builder.pass(tb("c", "b", 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", "a", 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 0, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 1, 0d)), is(false));

		assertThat(builder.pass(tb(null, "b", 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", null, 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", null, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 1, null)), is(false));
	}

	@Test
	public void shouldBuildAPredicateWhereAllFieldsMustMatchTheirVarArgsIsCondition() {
		PredicateBuilder<TestBean> originalBuilder = new PredicateBuilder<TestBean>(TestBean.class);
		PredicateBuilder<TestBean> builder = originalBuilder.where("str1").is("a", "b");
		builder = builder.where("str2").is("b", "c");
		builder = builder.where("int1").is(1, 2);
		builder = builder.where("dbl1").is(1d, 2d);
		assertThat(builder, not(sameInstance(originalBuilder)));

		assertThat(builder.pass(tb("a", "b", 1, 2d)), is(true));
		assertThat(builder.pass(tb("b", "c", 2, 1d)), is(true));

		assertThat(builder.pass(tb("c", "b", 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", "a", 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 0, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 1, 0d)), is(false));

		assertThat(builder.pass(tb(null, "b", 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", null, 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", null, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 1, null)), is(false));
	}

	@Test
	public void shouldBuildAPredicateWhereAllFieldsMustMatchTheirIsNotCondition() {
		PredicateBuilder<TestBean> originalBuilder = new PredicateBuilder<TestBean>(TestBean.class);
		PredicateBuilder<TestBean> builder = originalBuilder.where("str1").isNot("c");
		builder = builder.where("str2").isNot("a");
		builder = builder.where("int1").isNot(0);
		builder = builder.where("dbl1").isNot(0d);
		assertThat(builder, not(sameInstance(originalBuilder)));

		assertThat(builder.pass(tb("a", "b", 1, 2d)), is(true));
		assertThat(builder.pass(tb("b", "c", 2, 1d)), is(true));

		assertThat(builder.pass(tb("c", "b", 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", "a", 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 0, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 1, 0d)), is(false));
	}

	@Test
	public void shouldBuildAPredicateWhereAllFieldsMustMatchTheirIsNotVarArgsCondition() {
		PredicateBuilder<TestBean> originalBuilder = new PredicateBuilder<TestBean>(TestBean.class);
		PredicateBuilder<TestBean> builder = originalBuilder.where("str1").isNot(null, "c");
		builder = builder.where("str2").isNot(null, "a");
		builder = builder.where("int1").isNot(null, 0);
		builder = builder.where("dbl1").isNot(null, 0d);
		assertThat(builder, not(sameInstance(originalBuilder)));

		assertThat(builder.pass(tb("a", "b", 1, 2d)), is(true));
		assertThat(builder.pass(tb("b", "c", 2, 1d)), is(true));

		assertThat(builder.pass(tb("c", "b", 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", "a", 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 0, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 1, 0d)), is(false));

		assertThat(builder.pass(tb(null, "b", 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", null, 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", null, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 1, null)), is(false));
	}

	@Test
	public void shouldBuildAPredicateWhereAllFieldsMustMatchTheirGivenPredicate() {
		PredicateBuilder<TestBean> originalBuilder = new PredicateBuilder<TestBean>(TestBean.class);
		PredicateBuilder<TestBean> builder = originalBuilder.where("str1").passes(Predicate.notNull());
		builder = builder.where("str2").passes(Predicate.anyOf("b", "c"));
		builder = builder.where("int1").passes(Predicate.anyOf(1, 2));
		builder = builder.where("dbl1").passes(Predicate.is(2d));
		assertThat(builder, not(sameInstance(originalBuilder)));

		assertThat(builder.pass(tb("a", "b", 1, 2d)), is(true));
		assertThat(builder.pass(tb("b", "c", 2, 2d)), is(true));

		assertThat(builder.pass(tb("a", "a", 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 0, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 1, 0d)), is(false));

		assertThat(builder.pass(tb(null, "b", 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", null, 1, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", null, 2d)), is(false));
		assertThat(builder.pass(tb("a", "b", 1, null)), is(false));
	}

	@Test
	public void shouldNotPassANullObject() {
		PredicateBuilder<TestBean> predicate = new PredicateBuilder<TestBean>(TestBean.class);
		assertThat(predicate.pass(null), is(false));
	}

	@Test
	public void shouldCatchExceptionsAndRethrowAsRuntimeException() {
		thrown.expect(RuntimeException.class);
		PredicateBuilder<TestBean> predicate = new PredicateBuilder<TestBean>(TestBean.class).where("str1").passes(Predicate.any());
		TestBean testCompare = mock(TestBean.class);
		when(testCompare.getStr1()).thenThrow(new RuntimeException("Expected"));
		predicate.pass(testCompare);
	}

	@Test
	public void shouldThrowExceptionWhenAttemptingToCreatePredicateOnANonPropertyValue() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Unable to create predicate for com.atomicleopard.expressive.predicate.PredicateBuilderTest$TestBean on the property doesntExist, there is no accessible bean property with that name");
		PredicateBuilder<TestBean> builder = new PredicateBuilder<TestBean>(TestBean.class);
		builder.where("doesntExist").is((String) null);
	}

	@Test
	public void shouldBuildAPredicateWithoutCachingProperties() {
		PredicateBuilder<TestBean> builder = new PredicateBuilder<TestBean>(TestBean.class, true);
		builder.where("str1").passes(Predicate.any());
		builder.where("str2").passes(Predicate.any());

		assertThat(builder.pass(tb("a", "a", 0, 0d)), is(true));
	}

	@Test
	public void shouldReturnToStringForPredicateBuilder() {
		PredicateBuilder<TestBean> builder = new PredicateBuilder<TestBean>(TestBean.class, true);
		assertThat(builder.toString(), is("a com.atomicleopard.expressive.predicate.PredicateBuilderTest$TestBean"));

		builder = builder.where("str1").passes(Predicate.any());
		assertThat(builder.toString(), is("a com.atomicleopard.expressive.predicate.PredicateBuilderTest$TestBean, where str1 always passes"));

		builder = builder.where("str2").passes(Predicate.none());
		assertThat(builder.toString(), is("a com.atomicleopard.expressive.predicate.PredicateBuilderTest$TestBean, where str1 always passes, where str2 never passes"));

		builder = builder.where("int1").is(1);
		assertThat(builder.toString(), is("a com.atomicleopard.expressive.predicate.PredicateBuilderTest$TestBean, where str1 always passes, where str2 never passes, where int1 is 1"));

		builder = builder.where("dbl1").is(Predicate.anyOf("a", "b"));
		assertThat(builder.toString(), is("a com.atomicleopard.expressive.predicate.PredicateBuilderTest$TestBean, where str1 always passes, where str2 never passes, where int1 is 1, where dbl1 is any of [is a, is b]"));

	}

	private TestBean tb(String str1, String str2, Integer int1, Double dbl1) {
		return new TestBean(str1, str2, int1, dbl1);
	}

	@SuppressWarnings("unused")
	private static class TestBean {
		private String str1;
		private String str2;
		private Integer int1;
		private Double dbl1;

		public TestBean(String str1, String str2, Integer int1, Double dbl1) {
			super();
			this.str1 = str1;
			this.str2 = str2;
			this.int1 = int1;
			this.dbl1 = dbl1;
		}

		public String getStr1() {
			return str1;
		}

		public String getStr2() {
			return str2;
		}

		public Integer getInt1() {
			return int1;
		}

		public Double getDbl1() {
			return dbl1;
		}
	}
}
