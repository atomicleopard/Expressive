package com.atomicleopard.expressive.filter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class InversePredicateTest {
	private EPredicate<String> returnsTrue = new ConstantPredicate<String>(true);
	private EPredicate<String> returnsFalse = new ConstantPredicate<String>(false);

	@Test
	public void shouldInvertResult() {
		assertThat(new InversePredicate<String>(returnsTrue).pass(null), is(false));
		assertThat(new InversePredicate<String>(returnsTrue).pass("input"), is(false));
		assertThat(new InversePredicate<String>(returnsTrue).pass("other input"), is(false));

		assertThat(new InversePredicate<String>(returnsFalse).pass(null), is(true));
		assertThat(new InversePredicate<String>(returnsFalse).pass("input"), is(true));
		assertThat(new InversePredicate<String>(returnsFalse).pass("other input"), is(true));
	}
}
