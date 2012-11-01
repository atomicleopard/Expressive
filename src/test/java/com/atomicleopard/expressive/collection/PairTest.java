package com.atomicleopard.expressive.collection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

public class PairTest {
	@Test
	public void shouldRetainValuesOnConstruction() {
		Pair<String, Integer> pair = new Pair<String, Integer>("Hi", 1);
		assertThat(pair.getA(), is("Hi"));
		assertThat(pair.getB(), is(1));
	}

	@Test
	public void shouldHaveHashcodeAndEqualsBasedOnContent() {
		Date date = new Date();
		BigDecimal bigDecimal = new BigDecimal("1.234");
		Pair<Date, BigDecimal> pair1 = new Pair<Date, BigDecimal>(date, bigDecimal);
		Pair<Date, BigDecimal> pair2 = new Pair<Date, BigDecimal>(date, bigDecimal);
		Pair<Date, BigDecimal> pair3 = new Pair<Date, BigDecimal>(date, null);
		Pair<Date, BigDecimal> pair4 = new Pair<Date, BigDecimal>(null, bigDecimal);
		Pair<Date, BigDecimal> pair5 = new Pair<Date, BigDecimal>(null, null);
		Pair<Date, BigDecimal> pair6 = new Pair<Date, BigDecimal>(new Date(date.getTime()), new BigDecimal("1.234"));
		Pair<Date, BigDecimal> pair7 = new Pair<Date, BigDecimal>(null, new BigDecimal("1.234"));
		Pair<Date, BigDecimal> pair8 = new Pair<Date, BigDecimal>(new Date(date.getTime()), null);

		assertThat(pair1.equals(pair1), is(true));
		assertThat(pair1.equals(pair2), is(true));
		assertThat(pair2.equals(pair1), is(true));
		assertThat(pair1.equals(pair6), is(true));
		assertThat(pair1.equals(pair3), is(false));
		assertThat(pair1.equals(pair4), is(false));
		assertThat(pair1.equals(pair5), is(false));
		assertThat(pair1.equals(null), is(false));
		assertThat(pair1.equals("A different object type"), is(false));

		assertThat(pair4.equals(pair7), is(true));
		assertThat(pair8.equals(pair3), is(true));
		assertThat(pair4.equals(pair1), is(false));
		assertThat(pair8.equals(pair1), is(false));

		assertThat(pair1.hashCode() == pair2.hashCode(), is(true));
		assertThat(pair1.hashCode() == pair6.hashCode(), is(true));
		assertThat(pair3.hashCode() == pair4.hashCode(), is(false));
	}

	@Test
	public void shouldHaveSensibleToString() {
		assertThat(new Pair<String, String>("First", "Second").toString(), is("Pair of First and Second"));
		assertThat(new Pair<String, String>(null, null).toString(), is("Pair of null and null"));
	}
}
