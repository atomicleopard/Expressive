package com.atomicleopard.expressive.predicate;

import static com.atomicleopard.expressive.Expressive.list;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.atomicleopard.expressive.EList;
import com.atomicleopard.expressive.predicate.EPredicate;
import com.atomicleopard.expressive.predicate.EqualsPredicate;

public class EqualsPredicateTest {
	@Test
	public void shouldReturnTrueWhenEquals() {
		BigDecimal bigDecimal = new BigDecimal("1.234");
		EqualsPredicate<BigDecimal> equalsPredicate = new EqualsPredicate<BigDecimal>(bigDecimal);
		assertThat(equalsPredicate.pass(bigDecimal), is(true));
		assertThat(equalsPredicate.pass(new BigDecimal("1.234")), is(true));
		assertThat(equalsPredicate.pass(new BigDecimal(1.234)), is(false));
		assertThat(equalsPredicate.pass(null), is(false));
	}

	@Test
	public void shouldReturnTrueWhenEqualsNull() {
		EqualsPredicate<BigDecimal> equalsPredicate = new EqualsPredicate<BigDecimal>(null);
		assertThat(equalsPredicate.pass(null), is(true));
		assertThat(equalsPredicate.pass(new BigDecimal(1.234)), is(false));
	}

	@Test
	public void shouldReturnVarArgsValuesAsEqualsPredicates() {
		EList<EPredicate<String>> asPredicates = EqualsPredicate.asPredicates("A", "B", "C");
		assertThat(asPredicates.size(), is(3));
		assertThat(asPredicates.at(0).pass("A"), is(true));
		assertThat(asPredicates.at(1).pass("B"), is(true));
		assertThat(asPredicates.at(2).pass("C"), is(true));
	}

	@Test
	public void shouldReturnListValuesAsEqualsPredicates() {
		EList<EPredicate<String>> asPredicates = EqualsPredicate.asPredicates(list("A", "B", "C"));
		assertThat(asPredicates.size(), is(3));
		assertThat(asPredicates.at(0).pass("A"), is(true));
		assertThat(asPredicates.at(1).pass("B"), is(true));
		assertThat(asPredicates.at(2).pass("C"), is(true));
	}

	@Test
	public void shouldNotHaveAccessibleCtorOnTransformerStaticClass() {
		assertThat(new EqualsPredicate.Transformer(), is(notNullValue()));
	}

	@Test
	public void shouldHaveAToString() throws MalformedURLException {
		assertThat(new EqualsPredicate<String>(null).toString(), is("is null"));
		assertThat(new EqualsPredicate<String>("a").toString(), is("is a"));
		assertThat(new EqualsPredicate<Integer>(1).toString(), is("is 1"));
		assertThat(new EqualsPredicate<URL>(new URL("http://google.com")).toString(), is("is http://google.com"));
	}
}
