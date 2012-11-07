package com.atomicleopard.expressive;

import static com.atomicleopard.expressive.Expressive.list;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.atomicleopard.expressive.Expressive.Filter;
import com.atomicleopard.expressive.Expressive.Predicate;
import com.atomicleopard.expressive.collection.Pair;

public class ExpressiveFilterTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldRemoveItemsUsingPredicate() {
		EList<String> original = list("This", "is", null, "", null, "stuff");
		EList<String> remove = Filter.remove(original, Predicate.<String> isNull());
		assertThat(remove, is(list("This", "is", "", "stuff")));
	}

	@Test
	public void shouldReturnEmptyListWhenRemovingFromEmptyOrNullListUsingPredicate() {
		EList<String> remove = Filter.remove(null, Predicate.<String> isNull());
		assertThat(remove, is(notNullValue()));
		assertThat(remove.isEmpty(), is(true));

		EList<String> remove2 = Filter.remove(Collections.<String> emptyList(), Predicate.<String> isNull());
		assertThat(remove2, is(notNullValue()));
		assertThat(remove2.isEmpty(), is(true));
	}

	@Test
	public void shouldRetainItemsUsingPredicate() {
		EList<String> original = list("This", "is", null, "", null, "stuff");
		assertThat(Filter.retain(original, Predicate.is("This")), is(list("This")));
		assertThat(Filter.retain(original, Predicate.<String> any()), is(original));
		assertThat(Filter.retain(original, Predicate.<String> none()), is(Collections.<String> emptyList()));
	}

	@Test
	public void shouldRetainEmptyListWhenRemovingFromEmptyOrNullListUsingPredicate() {
		EList<String> retain = Filter.retain(null, Predicate.<String> notNull());
		assertThat(retain, is(notNullValue()));
		assertThat(retain.isEmpty(), is(true));

		EList<String> retain2 = Filter.retain(Collections.<String> emptyList(), Predicate.<String> notNull());
		assertThat(retain2, is(notNullValue()));
		assertThat(retain2.isEmpty(), is(true));
	}

	@Test
	public void shouldSplitListUsingPredicate() {
		EList<String> original = list("This", "is", null, "", null, "stuff");
		Pair<EList<String>, EList<String>> split = Filter.split(original, Predicate.<String> notNull());
		assertThat(split.getA(), is(list("This", "is", "", "stuff")));
		assertThat(split.getB(), is(Expressive.<String> list(null, null)));
	}

	@Test
	public void shouldSplitNullAndEmptyListReturingEmptyListUsingPredicate() {
		Pair<EList<String>, EList<String>> split = Filter.split(Collections.<String> emptyList(), Predicate.<String> notNull());
		assertThat(split.getA(), is(notNullValue()));
		assertThat(split.getA().isEmpty(), is(true));
		assertThat(split.getB(), is(notNullValue()));
		assertThat(split.getB().isEmpty(), is(true));
	}

	@Test
	public void shouldHaveANonPublicCtorWhichIsCoveredSoIStopCheckingTheClassToSeeWhatDoesntHaveCoverage() {
		assertThat(new Expressive.Filter(), is(notNullValue()));
	}
}
