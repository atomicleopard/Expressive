package com.atomicleopard.expressive.filter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.atomicleopard.expressive.Cast;

public class EPredicatesTest {
	@Test
	public void shouldPassAll() {
		EPredicate<Integer> all = EPredicates.all();
		assertThat(Cast.is(all, ConstantPredicate.class), is(true));
		assertThat(all.pass(null), is(true));
		assertThat(all.pass(2), is(true));
		assertThat(all.pass(1), is(true));
		assertThat(all.pass(Integer.MAX_VALUE), is(true));
	}

	@Test
	public void shouldPassNone() {
		EPredicate<Integer> none = EPredicates.none();
		assertThat(Cast.is(none, ConstantPredicate.class), is(true));
		assertThat(none.pass(null), is(false));
		assertThat(none.pass(2), is(false));
		assertThat(none.pass(1), is(false));
		assertThat(none.pass(Integer.MAX_VALUE), is(false));
	}

	@Test
	public void shouldPassNotNull() {
		EPredicate<Integer> notNull = EPredicates.notNull();
		assertThat(Cast.is(notNull, NotNullPredicate.class), is(true));
		assertThat(notNull.pass(null), is(false));
		assertThat(notNull.pass(2), is(true));
		assertThat(notNull.pass(Integer.MAX_VALUE), is(true));
	}

	@Test
	public void shouldPassNot() {
		EPredicate<Integer> notNull = EPredicates.not(EPredicates.<Integer> notNull());
		assertThat(Cast.is(notNull, InversePredicate.class), is(true));
		assertThat(notNull.pass(null), is(true));
		assertThat(notNull.pass(2), is(false));
		assertThat(notNull.pass(Integer.MAX_VALUE), is(false));
	}

	@Test
	public void shouldReturnAndPredicate() {
		AndPredicate<Integer> notNull = EPredicates.chain(EPredicates.<Integer> notNull());
		assertThat(Cast.is(notNull, AndPredicate.class), is(true));
		assertThat(notNull.pass(null), is(false));
		assertThat(notNull.pass(2), is(true));
	}

	@Test
	public void shouldReturnEqualsPredicate() {
		EPredicate<Integer> notNull = EPredicates.is(123);
		assertThat(Cast.is(notNull, EqualsPredicate.class), is(true));
		assertThat(notNull.pass(null), is(false));
		assertThat(notNull.pass(2), is(false));
		assertThat(notNull.pass(123), is(true));
	}
}
