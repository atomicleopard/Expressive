package com.atomicleopard.expressive.predicate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.atomicleopard.expressive.predicate.ConstantPredicate;
import com.atomicleopard.expressive.predicate.EPredicate;
import com.atomicleopard.expressive.predicate.NotPredicate;

public class InversePredicateTest {
	private EPredicate<String> returnsTrue = new ConstantPredicate<String>(true);
	private EPredicate<String> returnsFalse = new ConstantPredicate<String>(false);

	@Test
	public void shouldInvertResult() {
		assertThat(new NotPredicate<String>(returnsTrue).pass(null), is(false));
		assertThat(new NotPredicate<String>(returnsTrue).pass("input"), is(false));
		assertThat(new NotPredicate<String>(returnsTrue).pass("other input"), is(false));

		assertThat(new NotPredicate<String>(returnsFalse).pass(null), is(true));
		assertThat(new NotPredicate<String>(returnsFalse).pass("input"), is(true));
		assertThat(new NotPredicate<String>(returnsFalse).pass("other input"), is(true));
	}
}
