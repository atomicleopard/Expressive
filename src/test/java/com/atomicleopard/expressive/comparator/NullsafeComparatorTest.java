package com.atomicleopard.expressive.comparator;

import static com.atomicleopard.expressive.Expressive.list;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Comparator;

import org.junit.Test;

import com.atomicleopard.expressive.EList;

public class NullsafeComparatorTest {

	@Test
	public void shouldReturnZeroWhenBothNull() {
		assertThat(nullFirst().compare(null, null), is(0));
		assertThat(nullLast().compare(null, null), is(0));
	}

	@Test
	public void shouldReturnMinusOneWhenFirstIsNullWhenNullFirst() {
		assertThat(nullFirst().compare(null, new Object()), is(-1));
	}

	@Test
	public void shouldReturnOneWhenFirstIsNullWhenNullLast() {
		assertThat(nullLast().compare(null, new Object()), is(1));
	}

	@Test
	public void shouldReturnOneWhenSecondsNullWhenNullFirst() {
		assertThat(nullFirst().compare(new Object(), null), is(1));
	}

	@Test
	public void shouldReturnMinusOneWhenSecondIsNullWhenNullLast() {
		assertThat(nullLast().compare(new Object(), null), is(-1));
	}

	@Test
	public void shouldDelegateToGivenComparatorWhenNeitherNull() {
		NullsafeComparator<Object> comparator = new NullsafeComparator<Object>(new ConstantComparator<Object>(11), true);
		assertThat(comparator.compare(new Object(), new Object()), is(11));
	}

	@Test
	public void shouldGiveExpectedResultsWhenSorting() {
		Object o1 = new Object();
		Object o2 = new Object();

		assertThat(sorted(list(null, null), nullFirst()), is(list(null, null)));
		assertThat(sorted(list(null, o1), nullFirst()), is(list(null, o1)));
		assertThat(sorted(list(o1, null), nullFirst()), is(list(null, o1)));
		assertThat(sorted(list(o1, o2), nullFirst()), is(list(o2, o1)));

		assertThat(sorted(list(null, null), nullLast()), is(list(null, null)));
		assertThat(sorted(list(null, o1), nullLast()), is(list(o1, null)));
		assertThat(sorted(list(o1, null), nullLast()), is(list(o1, null)));
		assertThat(sorted(list(o1, o2), nullLast()), is(list(o2, o1)));

	}

	private EList<Object> sorted(EList<Object> list, Comparator<Object> comparator) {
		Collections.sort(list, comparator);
		return list;
	}

	private NullsafeComparator<Object> nullLast() {
		return new NullsafeComparator<Object>(new ConstantComparator<Object>(1), false);
	}

	private NullsafeComparator<Object> nullFirst() {
		return new NullsafeComparator<Object>(new ConstantComparator<Object>(1), true);
	}
}
