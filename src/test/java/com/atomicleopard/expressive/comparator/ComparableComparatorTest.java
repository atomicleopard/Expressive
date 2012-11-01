package com.atomicleopard.expressive.comparator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ComparableComparatorTest {
	@Test
	public void shouldCompareUsingComparable() {
		ComparableComparator<Integer> comparator = new ComparableComparator<Integer>();
		assertThat(comparator.compare(1, 2), is(new Integer(1).compareTo(2)));
		assertThat(comparator.compare(1, 100), is(new Integer(1).compareTo(100)));
		assertThat(comparator.compare(2, 1), is(new Integer(2).compareTo(1)));
		assertThat(comparator.compare(100, 1), is(new Integer(100).compareTo(1)));
	}
}
