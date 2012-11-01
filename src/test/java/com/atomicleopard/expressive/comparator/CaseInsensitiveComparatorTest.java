package com.atomicleopard.expressive.comparator;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CaseInsensitiveComparatorTest {

	@Test
	public void shouldCompareStringsIgnoringCase() {
		CaseInsensitiveComparator c = new CaseInsensitiveComparator();
		assertThat(c.compare("A", "A"), is(0));
		assertThat(c.compare("A", "a"), is(0));
		assertThat(c.compare("a", "A"), is(0));
		assertThat(c.compare("A", "b"), lessThan(0));
		assertThat(c.compare("A", "B"), lessThan(0));
		assertThat(c.compare("a", "b"), lessThan(0));
		assertThat(c.compare("a", "B"), lessThan(0));
		assertThat(c.compare("b", "A"), greaterThan(0));
		assertThat(c.compare("B", "A"), greaterThan(0));
		assertThat(c.compare("b", "a"), greaterThan(0));
		assertThat(c.compare("B", "a"), greaterThan(0));

	}
}
