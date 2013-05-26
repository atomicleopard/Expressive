package com.atomicleopard.expressive.transform;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CastTransformerTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldCastFromTypeToType() {
		CastTransformer<String, Object> castTransformer = new CastTransformer<String, Object>(Object.class);
		Object to = castTransformer.from("String");
		assertThat(to, is(notNullValue()));
	}

	@Test
	public void shouldReturnNullIfCannotCast() {
		CastTransformer<String, Double> castTransformer = new CastTransformer<String, Double>(Double.class);
		Double d = castTransformer.from("String");
		assertThat(d, is(nullValue()));
	}

	@Test
	public void shouldHaveAUsefulToString() {
		CastTransformer<String, Double> castTransformer = new CastTransformer<String, Double>(Double.class);
		assertThat(castTransformer.toString(), is("CastTransformer to Double"));
	}
}
