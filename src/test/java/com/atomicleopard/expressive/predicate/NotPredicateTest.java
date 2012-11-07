package com.atomicleopard.expressive.predicate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.atomicleopard.expressive.Expressive.Predicate;
import com.atomicleopard.expressive.predicate.ConstantPredicate;
import com.atomicleopard.expressive.predicate.NotPredicate;

public class NotPredicateTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldPassFalseAndFailTrue() {
		assertThat(new NotPredicate<String>(new ConstantPredicate<String>(false)).pass("something"), is(true));
		assertThat(new NotPredicate<String>(new ConstantPredicate<String>(false)).pass(""), is(true));
		assertThat(new NotPredicate<String>(new ConstantPredicate<String>(false)).pass(null), is(true));

		assertThat(new NotPredicate<String>(new ConstantPredicate<String>(true)).pass("something"), is(false));
		assertThat(new NotPredicate<String>(new ConstantPredicate<String>(true)).pass(""), is(false));
		assertThat(new NotPredicate<String>(new ConstantPredicate<String>(true)).pass(null), is(false));
	}

	@Test
	public void shouldHaveAToString() {
		assertThat(new NotPredicate<String>(Predicate.is("A")).toString(), is("not is A"));
	}

	@Test
	public void shouldThrowNullIfGivenNullPredicate() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Null EPredicate passed to the NotPredicate constructor");
		new NotPredicate<Integer>(null);
	}
}
