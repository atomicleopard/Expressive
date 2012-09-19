package com.atomicleopard.expressive.filter;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class NotNullPredicateTest {
	@Test
	public void shouldPassAnythingNotNull() {
		assertThat(new NotNullPredicate<String>().pass("something"), is(true));
		assertThat(new NotNullPredicate<String>().pass(""), is(true));
		assertThat(new NotNullPredicate<String>().pass(null), is(false));

		assertThat(new NotNullPredicate<Object>().pass(new Object()), is(true));
		assertThat(new NotNullPredicate<String>().pass(null), is(false));
	}
}
