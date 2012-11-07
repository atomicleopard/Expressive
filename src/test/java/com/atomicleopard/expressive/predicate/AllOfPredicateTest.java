package com.atomicleopard.expressive.predicate;

import static com.atomicleopard.expressive.Expressive.list;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.junit.Test;

import com.atomicleopard.expressive.Expressive;
import com.atomicleopard.expressive.Expressive.Predicate;
import com.atomicleopard.expressive.predicate.AllOfPredicate;
import com.atomicleopard.expressive.predicate.ConstantPredicate;
import com.atomicleopard.expressive.predicate.EPredicate;

public class AllOfPredicateTest {
	@SuppressWarnings("unchecked")
	@Test
	public void shouldOnlyPassWhenAllPredicatesPass() {
		EPredicate<String> none = Predicate.<String> none();
		EPredicate<String> all = Predicate.<String> any();

		assertThat(new AllOfPredicate<String>(all).pass("something"), is(true));
		assertThat(new AllOfPredicate<String>(none).pass("something"), is(false));

		assertThat(new AllOfPredicate<String>(all).and(none).pass("something"), is(false));
		assertThat(new AllOfPredicate<String>(all).and(all).and(all).and(none).pass("something"), is(false));
		assertThat(new AllOfPredicate<String>(all).and(all).and(all).and(all).pass("something"), is(true));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldChainAndWithVarArgsReturningNewAndPredicate() {
		AllOfPredicate<String> initial = new AllOfPredicate<String>(Predicate.<String> any());
		AllOfPredicate<String> second = initial.and("A");
		assertThat(initial, is(not(sameInstance(second))));

		assertThat(initial.pass("A"), is(true));
		assertThat(second.pass("A"), is(true));
		assertThat(second.pass("B"), is(false));

		AllOfPredicate<String> third = second.and("B");
		assertThat(initial, is(not(sameInstance(third))));
		assertThat(second, is(not(sameInstance(third))));
		assertThat(third.pass("A"), is(false));
		assertThat(third.pass("B"), is(false));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldChainAndWithListReturningNewAndPredicate() {
		AllOfPredicate<String> initial = new AllOfPredicate<String>(Predicate.<String> any());
		AllOfPredicate<String> second = initial.and(Expressive.list("A"));
		assertThat(initial, is(not(sameInstance(second))));

		assertThat(initial.pass("A"), is(true));
		assertThat(second.pass("A"), is(true));
		assertThat(second.pass("B"), is(false));

		AllOfPredicate<String> third = second.and(Expressive.list("B"));
		assertThat(initial, is(not(sameInstance(third))));
		assertThat(second, is(not(sameInstance(third))));
		assertThat(third.pass("A"), is(false));
		assertThat(third.pass("B"), is(false));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldChainAndWithPredicatesReturningNewAndPredicate() {
		AllOfPredicate<String> initial = new AllOfPredicate<String>(Predicate.<String> any());
		AllOfPredicate<String> second = initial.and(Expressive.Predicate.<String> notNull());
		assertThat(initial, is(not(sameInstance(second))));

		assertThat(initial.pass("A"), is(true));
		assertThat(second.pass("A"), is(true));
		assertThat(second.pass("B"), is(true));

		AllOfPredicate<String> third = second.and(Predicate.anyOf("A", "B"), Predicate.noneOf("C"));
		assertThat(initial, is(not(sameInstance(third))));
		assertThat(second, is(not(sameInstance(third))));
		assertThat(third.pass("A"), is(true));
		assertThat(third.pass("B"), is(true));

		AllOfPredicate<String> fourth = second.and(Predicate.is("A"));
		assertThat(fourth.pass("A"), is(true));
		assertThat(fourth.pass("B"), is(false));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldStopAtFirstFailedPredicate() {
		MementoPredicate<String> first = new MementoPredicate<String>(true);
		MementoPredicate<String> second = new MementoPredicate<String>(false);
		MementoPredicate<String> third = new MementoPredicate<String>(false);

		new AllOfPredicate<String>(first).and(second).and(third).pass("This");
		assertThat(first.executed, is(true));
		assertThat(second.executed, is(true));
		assertThat(third.executed, is(false));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldHaveAToString() {
		AllOfPredicate<String> predicate = new AllOfPredicate<String>(Collections.<EPredicate<String>> emptyList());
		assertThat(predicate.toString(), is("all of []"));
		predicate = predicate.and("a");
		assertThat(predicate.toString(), is("all of [is a]"));
		predicate = predicate.and(list("b", "c"));
		assertThat(predicate.toString(), is("all of [is a, is b, is c]"));
		predicate = predicate.and(Predicate.not("d"));
		assertThat(predicate.toString(), is("all of [is a, is b, is c, not is d]"));
	}

	private class MementoPredicate<T> extends ConstantPredicate<T> {
		public boolean executed = false;

		public MementoPredicate(boolean result) {
			super(result);
		}

		@Override
		public boolean pass(T input) {
			executed = true;
			return super.pass(input);
		}

	}
}
