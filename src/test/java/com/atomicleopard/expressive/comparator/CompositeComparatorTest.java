package com.atomicleopard.expressive.comparator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Comparator;

import org.junit.Test;

import com.atomicleopard.expressive.Expressive;

public class CompositeComparatorTest {
	private Comparator<String> noop = new NoopComparator<String>();
	private Comparator<String> one = new ConstantComparator<String>(1);
	private Comparator<String> minusOne = new ConstantComparator<String>(-1);
	private Comparator<String> two = new ConstantComparator<String>(2);

	@SuppressWarnings("unchecked")
	@Test
	public void shouldCompareWithComparatorsUntilOneReturnsANonZeroValue() {
		assertThat(create(noop, noop, one).compare("a", "b"), is(1));
		assertThat(create(noop, noop, one).compare(null, null), is(1));

		assertThat(create(noop, one, two).compare(null, null), is(1));
		assertThat(create(noop, one, two).compare("A", "B"), is(1));

		assertThat(create(noop, two, one).compare("A", "B"), is(2));
		assertThat(create(noop, two, one).compare(null, null), is(2));

		assertThat(create(noop, minusOne, one).compare("A", "B"), is(-1));
		assertThat(create(noop, minusOne, one).compare(null, null), is(-1));

		assertThat(create(noop).compare(null, null), is(0));
		assertThat(create(noop, noop).compare(null, null), is(0));
		assertThat(create(noop, noop, noop).compare(null, null), is(0));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnZeroIfNotGivenComparators() {
		assertThat(create().compare(null, null), is(0));
		assertThat(create().compare("A", "B"), is(0));

	}

	private <T> CompositeComparator<T> create(Comparator<T>... comparators) {
		return new CompositeComparator<T>(Expressive.<Comparator<T>> list(comparators));
	}
}
