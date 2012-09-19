package com.atomicleopard.expressive.filter;

import static com.atomicleopard.expressive.Expressive.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.atomicleopard.expressive.EList;

public class EFilterImplTest {

	@Test
	public void shouldReturnANewElistWithOnlyElementsPassingThePredicate() {
		EFilterImpl<String> filter = new EFilterImpl<String>(EPredicates.is("This"));
		EList<String> original = list("This", "And", "Other", "Things", "This");
		EList<String> result = filter.filter(original);

		// we dont want the original list altered
		assertThat(original.size(), is(5));
		assertThat(result.size(), is(2));
		assertThat(result, is(list("This", "This")));
	}
}
