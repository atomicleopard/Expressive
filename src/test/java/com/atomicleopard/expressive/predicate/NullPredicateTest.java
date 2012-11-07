package com.atomicleopard.expressive.predicate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.atomicleopard.expressive.predicate.NullPredicate;

public class NullPredicateTest {
	@Test
	public void shouldPassNull() {
		assertThat(new NullPredicate<String>().pass("something"), is(false));
		assertThat(new NullPredicate<String>().pass(""), is(false));
		assertThat(new NullPredicate<String>().pass(null), is(true));
	}

	@Test
	public void shouldHaveToString() {
		assertThat(new NullPredicate<Integer>().toString(), is("is null"));
	}
}
