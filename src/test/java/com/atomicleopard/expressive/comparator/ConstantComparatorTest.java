package com.atomicleopard.expressive.comparator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ConstantComparatorTest {
	@Test
	public void shouldAlwaysReturnConstantValue() {
		assertThat(new ConstantComparator<Object>(0).compare(null, null), is(0));
		assertThat(new ConstantComparator<Object>(0).compare("B", "A"), is(0));
		assertThat(new ConstantComparator<Object>(0).compare("A", "B"), is(0));
		assertThat(new ConstantComparator<Object>(1).compare(null, null), is(1));
		assertThat(new ConstantComparator<Object>(1).compare("B", "A"), is(1));
		assertThat(new ConstantComparator<Object>(1).compare("A", "B"), is(1));
		assertThat(new ConstantComparator<Object>(-1).compare(null, null), is(-1));
		assertThat(new ConstantComparator<Object>(-1).compare("B", "A"), is(-1));
		assertThat(new ConstantComparator<Object>(-1).compare("A", "B"), is(-1));
	}
}
