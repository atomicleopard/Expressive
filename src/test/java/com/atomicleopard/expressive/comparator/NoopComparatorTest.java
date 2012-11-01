package com.atomicleopard.expressive.comparator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NoopComparatorTest {
	@Test
	public void shouldAlwaysReturnZero() {
		assertThat(new NoopComparator<String>().compare("A", "B"), is(0));
		assertThat(new NoopComparator<String>().compare("B", "A"), is(0));
		assertThat(new NoopComparator<String>().compare("A", null), is(0));
		assertThat(new NoopComparator<String>().compare(null, "B"), is(0));
		assertThat(new NoopComparator<String>().compare(null, null), is(0));
	}
}
