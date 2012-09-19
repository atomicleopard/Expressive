package com.atomicleopard.expressive.filter;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

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
}
