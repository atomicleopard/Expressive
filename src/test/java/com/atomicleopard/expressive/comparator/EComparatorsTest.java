package com.atomicleopard.expressive.comparator;

import static com.atomicleopard.expressive.Expressive.list;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Comparator;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Test;

public class EComparatorsTest {
	@Test
	public void shouldReturnComparatorBuilderForGivenType() {
		ComparatorBuilder<Date> comparatorBuilder = EComparators.compare(Date.class);
		assertThat(comparatorBuilder, is(notNullValue()));
		comparatorBuilder.on("time").naturally();

		assertThat(comparatorBuilder.compare(new Date(), new Date()), is(lessThanOrEqualTo(0)));
	}

	@Test
	public void shouldCreateNullSafeComparatorWithNullsLast() {
		Comparator<Integer> nullSafe = EComparators.nullSafe(EComparators.as(Integer.class));
		assertThat(nullSafe, Matchers.is(NullsafeComparator.class));
		assertThat(nullSafe.compare(null, null), is(0));
		assertThat(nullSafe.compare(1, null), is(1));
		assertThat(nullSafe.compare(null, 1), is(-1));
		assertThat(nullSafe.compare(0, 1), is(-1));
		assertThat(nullSafe.compare(1, 1), is(0));
	}

	@Test
	public void shouldCreateNullSafeComparatorWithNullLastOrFirstAsSpecified() {
		Comparator<Integer> nullSafe = EComparators.nullSafe(EComparators.as(Integer.class), false);
		assertThat(nullSafe, Matchers.is(NullsafeComparator.class));
		assertThat(nullSafe.compare(null, null), is(0));
		assertThat(nullSafe.compare(1, null), is(-1));
		assertThat(nullSafe.compare(null, 1), is(1));
		assertThat(nullSafe.compare(0, 1), is(-1));
		assertThat(nullSafe.compare(1, 1), is(0));

		nullSafe = EComparators.nullSafe(EComparators.as(Integer.class), true);
		assertThat(nullSafe, Matchers.is(NullsafeComparator.class));
		assertThat(nullSafe.compare(null, null), is(0));
		assertThat(nullSafe.compare(1, null), is(1));
		assertThat(nullSafe.compare(null, 1), is(-1));
		assertThat(nullSafe.compare(0, 1), is(-1));
		assertThat(nullSafe.compare(1, 1), is(0));
	}

	@Test
	public void shouldCreateAComparatorComparingComparableObjects() {
		Comparator<Double> comparator = EComparators.as(Double.class);
		assertThat(comparator, is(notNullValue()));
		assertThat(comparator, is(ComparableComparator.class));
		assertThat(comparator.compare(0d, 0d), is(0));
		assertThat(comparator.compare(10d, 0d), is(1));
		assertThat(comparator.compare(0d, 10d), is(-1));
	}

	@Test
	public void shouldReturnACaseInsensitiveComparator() {
		Comparator<String> comparator = EComparators.caseInsensitive();
		assertThat(comparator, is(notNullValue()));
		assertThat(comparator, is(CaseInsensitiveComparator.class));
		assertThat(comparator.compare("", ""), is(0));
		assertThat(comparator.compare("a", "a"), is(0));
		assertThat(comparator.compare("A", "A"), is(0));
		assertThat(comparator.compare("a", "A"), is(0));
		assertThat(comparator.compare("A", "a"), is(0));
		assertThat(comparator.compare("A", "b"), is(-1));
		assertThat(comparator.compare("a", "b"), is(-1));
		assertThat(comparator.compare("a", "B"), is(-1));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldComposeComparatorsFromVarArgs() {
		Comparator<String> c1 = mock(Comparator.class);
		Comparator<String> c2 = mock(Comparator.class);
		Comparator<String> c3 = mock(Comparator.class);
		Comparator<String> all = EComparators.all(c1, c2, c3);
		assertThat(all, is(notNullValue()));
		assertThat(all, is(CompositeComparator.class));

		when(c3.compare(anyString(), anyString())).thenReturn(100);
		assertThat(all.compare("A", "B"), is(100));
		verify(c1).compare("A", "B");
		verify(c2).compare("A", "B");
		verify(c3).compare("A", "B");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldComposeComparatorsromIterable() {
		Comparator<String> c1 = mock(Comparator.class);
		Comparator<String> c2 = mock(Comparator.class);
		Comparator<String> c3 = mock(Comparator.class);
		Comparator<String> all = EComparators.all(list(c1, c2, c3));
		assertThat(all, is(notNullValue()));
		assertThat(all, is(CompositeComparator.class));

		when(c2.compare(anyString(), anyString())).thenReturn(100);
		assertThat(all.compare("A", "B"), is(100));
		verify(c1).compare("A", "B");
		verify(c2).compare("A", "B");
		verify(c3, times(0)).compare("A", "B");
	}
}
