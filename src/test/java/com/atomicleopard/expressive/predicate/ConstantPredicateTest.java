package com.atomicleopard.expressive.predicate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.atomicleopard.expressive.predicate.ConstantPredicate;

public class ConstantPredicateTest {
	@Test
	public void shouldAlwaysReturnTrue() {
		ConstantPredicate<String> constantPredicate = new ConstantPredicate<String>(true);
		assertThat(constantPredicate.pass("input"), is(true));
		assertThat(constantPredicate.pass("input"), is(true));
		assertThat(constantPredicate.pass("input 2"), is(true));
		assertThat(constantPredicate.pass(null), is(true));
	}

	@Test
	public void shouldAlwaysReturnFalse() {
		ConstantPredicate<String> constantPredicate = new ConstantPredicate<String>(false);
		assertThat(constantPredicate.pass("input"), is(false));
		assertThat(constantPredicate.pass("input"), is(false));
		assertThat(constantPredicate.pass("input 2"), is(false));
		assertThat(constantPredicate.pass(null), is(false));
	}

	@Test
	public void shouldHaveAToString() {
		assertThat(new ConstantPredicate<String>(true).toString(), is("always passes"));
		assertThat(new ConstantPredicate<String>(false).toString(), is("never passes"));
	}
}
