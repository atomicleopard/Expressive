package com.atomicleopard.expressive;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.atomicleopard.expressive.Expressive.Predicate;
import com.atomicleopard.expressive.predicate.AllOfPredicate;
import com.atomicleopard.expressive.predicate.AnyOfPredicate;
import com.atomicleopard.expressive.predicate.ConstantPredicate;
import com.atomicleopard.expressive.predicate.EPredicate;
import com.atomicleopard.expressive.predicate.EqualsPredicate;
import com.atomicleopard.expressive.predicate.NotPredicate;
import com.atomicleopard.expressive.predicate.PredicateBuilder;

public class ExpressivePredicateTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldPassAll() {
		EPredicate<Integer> all = Predicate.any();
		assertThat(Cast.is(all, ConstantPredicate.class), is(true));
		assertThat(all.pass(null), is(true));
		assertThat(all.pass(2), is(true));
		assertThat(all.pass(1), is(true));
		assertThat(all.pass(Integer.MAX_VALUE), is(true));
	}

	@Test
	public void shouldPassNone() {
		EPredicate<Integer> none = Predicate.none();
		assertThat(Cast.is(none, ConstantPredicate.class), is(true));
		assertThat(none.pass(null), is(false));
		assertThat(none.pass(2), is(false));
		assertThat(none.pass(1), is(false));
		assertThat(none.pass(Integer.MAX_VALUE), is(false));
	}

	@Test
	public void shouldPassNotNull() {
		EPredicate<Integer> notNull = Predicate.notNull();
		assertThat(notNull.pass(null), is(false));
		assertThat(notNull.pass(2), is(true));
		assertThat(notNull.pass(Integer.MAX_VALUE), is(true));
	}

	@Test
	public void shouldPassNot() {
		EPredicate<Integer> notNull = Predicate.not(Predicate.<Integer> notNull());
		assertThat(Cast.is(notNull, NotPredicate.class), is(true));
		assertThat(notNull.pass(null), is(true));
		assertThat(notNull.pass(2), is(false));
		assertThat(notNull.pass(Integer.MAX_VALUE), is(false));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnAndPredicate() {
		AllOfPredicate<Integer> notNull = Predicate.allOf(Predicate.<Integer> notNull());
		assertThat(Cast.is(notNull, AllOfPredicate.class), is(true));
		assertThat(notNull.pass(null), is(false));
		assertThat(notNull.pass(2), is(true));
	}

	@Test
	public void shouldReturnEqualsPredicate() {
		EPredicate<Integer> is = Predicate.is(123);
		assertThat(Cast.is(is, EqualsPredicate.class), is(true));
		assertThat(is.pass(null), is(false));
		assertThat(is.pass(2), is(false));
		assertThat(is.pass(123), is(true));
	}

	@Test
	public void shouldReturnOrPredicate() {
		AnyOfPredicate<Integer> is = Predicate.anyOf(123, 456);
		assertThat(Cast.is(is, AnyOfPredicate.class), is(true));
		assertThat(is.pass(null), is(false));
		assertThat(is.pass(2), is(false));
		assertThat(is.pass(123), is(true));
		assertThat(is.pass(456), is(true));
	}

	@Test
	public void shouldReturnTrueOnlyWhenNoneOf() {
		EPredicate<String> predicate = Predicate.noneOf("A", "B", "C");
		assertThat(predicate.pass("A"), is(false));
		assertThat(predicate.pass("B"), is(false));
		assertThat(predicate.pass("C"), is(false));
		assertThat(predicate.pass("D"), is(true));
		assertThat(predicate.pass(""), is(true));
		assertThat(predicate.pass(null), is(true));
	}

	@Test
	public void shouldReturnNotPredicate() {
		EPredicate<String> predicate = Predicate.not("A");
		assertThat(predicate.pass("A"), is(false));
		assertThat(predicate.pass("B"), is(true));
		assertThat(predicate.pass(""), is(true));
		assertThat(predicate.pass(null), is(true));
	}

	@Test
	public void shouldCreateAPredicateBuilder() {
		PredicateBuilder<Date> predicateBuilder = Predicate.on(Date.class).where("time").is(0L);
		assertThat(predicateBuilder, is(notNullValue()));

		assertThat(predicateBuilder.pass(new Date(0)), is(true));
		assertThat(predicateBuilder.pass(new Date(1)), is(false));
		assertThat(predicateBuilder.pass(null), is(false));
	}

	@Test
	public void shouldHaveANonPublicCtorWhichIsCoveredSoIStopCheckingTheClassToSeeWhatDoesntHaveCoverage() {
		assertThat(new Expressive.Predicate(), is(notNullValue()));
	}

}
