package com.atomicleopard.expressive.filter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AndPredicateTest {
	@Test
	public void shouldOnlyPassWhenAllPredicatesPass() {
		EPredicate<String> none = EPredicates.<String> none();
		EPredicate<String> all = EPredicates.<String> all();

		assertThat(new AndPredicate<String>(all).pass("something"), is(true));
		assertThat(new AndPredicate<String>(none).pass("something"), is(false));

		assertThat(new AndPredicate<String>(all).and(none).pass("something"), is(false));
		assertThat(new AndPredicate<String>(all).and(all).and(all).and(none).pass("something"), is(false));
		assertThat(new AndPredicate<String>(all).and(all).and(all).and(all).pass("something"), is(true));
	}

	@Test
	public void shouldStopAtFirstFailedPredicate() {
		MememtoPredicate<String> first = new MememtoPredicate<String>(true);
		MememtoPredicate<String> second = new MememtoPredicate<String>(false);
		MememtoPredicate<String> third = new MememtoPredicate<String>(false);

		new AndPredicate<String>(first).and(second).and(third).pass("This");
		assertThat(first.executed, is(true));
		assertThat(second.executed, is(true));
		assertThat(third.executed, is(false));
	}

	private class MememtoPredicate<T> extends ConstantPredicate<T> {
		public boolean executed = false;

		public MememtoPredicate(boolean result) {
			super(result);
		}

		@Override
		public boolean pass(T input) {
			executed = true;
			return super.pass(input);
		}

	}
}
