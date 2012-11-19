/*
 *  Copyright (c) 2012 Nicholas Okunew
 *  All rights reserved.
 *  
 *  This file is part of the com.atomicleopard.expressive library
 *  
 *  The com.atomicleopard.expressive library is free software: you 
 *  can redistribute it and/or modify it under the terms of the GNU
 *  Lesser General Public License as published by the Free Software Foundation, 
 *  either version 3 of the License, or (at your option) any later version.
 *  
 *  The com.atomicleopard.expressive library is distributed in the hope
 *  that it will be useful, but WITHOUT ANY WARRANTY; without even
 *  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with the com.atomicleopard.expressive library.  If not, see
 *  http://www.gnu.org/licenses/lgpl-3.0.html.
 */
package com.atomicleopard.expressive.predicate;

import static com.atomicleopard.expressive.Expressive.list;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.junit.Test;

import com.atomicleopard.expressive.Expressive;
import com.atomicleopard.expressive.Expressive.Predicate;
import com.atomicleopard.expressive.predicate.AnyOfPredicate;
import com.atomicleopard.expressive.predicate.ConstantPredicate;
import com.atomicleopard.expressive.predicate.EPredicate;

public class AnyOfPredicateTest {
	@SuppressWarnings("unchecked")
	@Test
	public void shouldOnlyPassWhenAllPredicatesPass() {
		EPredicate<String> none = Expressive.Predicate.<String> none();
		EPredicate<String> all = Expressive.Predicate.<String> any();

		assertThat(new AnyOfPredicate<String>(all).pass("something"), is(true));
		assertThat(new AnyOfPredicate<String>(none).pass("something"), is(false));

		assertThat(new AnyOfPredicate<String>(all).or(none).pass("something"), is(true));
		assertThat(new AnyOfPredicate<String>(all).or(all).or(all).or(none).pass("something"), is(true));
		assertThat(new AnyOfPredicate<String>(all).or(all).or(all).or(all).pass("something"), is(true));
		assertThat(new AnyOfPredicate<String>(none).or(none).or(none).pass("something"), is(false));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldStopAtFirstPassedPredicate() {
		MementoPredicate<String> first = new MementoPredicate<String>(false);
		MementoPredicate<String> second = new MementoPredicate<String>(true);
		MementoPredicate<String> third = new MementoPredicate<String>(true);

		new AnyOfPredicate<String>(first).or(second).or(third).pass("This");
		assertThat(first.executed, is(true));
		assertThat(second.executed, is(true));
		assertThat(third.executed, is(false));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldRetainPredicatesFromAllOrMethods() {
		EPredicate<String> p1 = new MementoPredicate<String>(false);
		EPredicate<String> p2 = new MementoPredicate<String>(false);

		AnyOfPredicate<String> predicate = new AnyOfPredicate<String>(Expressive.<EPredicate<String>> list(p1));
		predicate = predicate.or(p2).or("A").or(list("C", "D"));
		assertThat(predicate.predicates.size(), is(5));
		assertThat(predicate.predicates.get(0), is(p1));
		assertThat(predicate.predicates.get(1), is(p2));
		assertThat(predicate.predicates.get(2).pass("A"), is(true));
		assertThat(predicate.predicates.get(3).pass("C"), is(true));
		assertThat(predicate.predicates.get(4).pass("D"), is(true));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldHaveAToString() {
		AnyOfPredicate<String> predicate = new AnyOfPredicate<String>(Collections.<EPredicate<String>> emptyList());
		assertThat(predicate.toString(), is("any of []"));
		predicate = predicate.or("a");
		assertThat(predicate.toString(), is("any of [is a]"));
		predicate = predicate.or("b", "c");
		assertThat(predicate.toString(), is("any of [is a, is b, is c]"));
		predicate = predicate.or(Predicate.not("d"));
		assertThat(predicate.toString(), is("any of [is a, is b, is c, not is d]"));

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
