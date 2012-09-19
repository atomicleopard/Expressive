package com.atomicleopard.expressive.filter;

import static com.atomicleopard.expressive.Expressive.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

import com.atomicleopard.expressive.EList;
import com.atomicleopard.expressive.Expressive;
import com.atomicleopard.expressive.collection.Pair;

public class EFiltersTest {
	@Test
	public void shouldRetainMatchingItems() {
		EList<String> original = list("This", "is", null, "", null, "stuff");
		EFilter<String> retain = EFilters.retain(EPredicates.<String> notNull());
		assertThat(retain.filter(original), is(list("This", "is", "", "stuff")));
		assertThat(retain.filter(Collections.<String> emptyList()).isEmpty(), is(true));
	}

	@Test
	public void shouldRemoveMatchingItems() {
		EList<String> original = list("This", "is", null, "", null, "stuff");
		EFilter<String> remove = EFilters.remove(EPredicates.<String> notNull());
		assertThat(remove.filter(original), is(Expressive.<String> list(null, null)));
		assertThat(remove.filter(Collections.<String> emptyList()).isEmpty(), is(true));
	}

	@Test
	public void shouldFilterItemsUsingPredicate() {
		EList<String> original = list("This", "is", null, "", null, "stuff");
		assertThat(EFilters.filter(original, EPredicates.is("This")), is(list("This")));
		assertThat(EFilters.filter(original, EPredicates.<String> all()), is(original));
		assertThat(EFilters.filter(original, EPredicates.<String> none()), is(Collections.<String> emptyList()));
	}
	
	@Test
	public void shouldSplitListUsingFilter() {
		EList<String> original = list("This", "is", null, "", null, "stuff");
		Pair<EList<String>, EList<String>> split = EFilters.split(original, EFilters.retain(EPredicates.<String>notNull()));
		assertThat(split.getA(), is(list("This", "is", "", "stuff")));
		assertThat(split.getB(), is(Expressive.<String>list(null, null)));
	}
}
