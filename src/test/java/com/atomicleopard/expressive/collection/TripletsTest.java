package com.atomicleopard.expressive.collection;

import static com.atomicleopard.expressive.Expressive.list;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TripletsTest {

	@Test
	public void shouldCreateATripletsAndRetainAndAllowLookupOfGivenValues() {
		Triplets<Integer, Integer, String> triplets = new Triplets<Integer, Integer, String>();
		assertThat(triplets.isEmpty(), is(true));
		assertThat(triplets.size(), is(0));

		triplets.put(1, 2, "12");
		assertThat(triplets.isEmpty(), is(false));
		assertThat(triplets.size(), is(1));
		assertThat(triplets.get(1, 2), is("12"));
		assertThat(triplets.get(1, 1), is(nullValue()));

		triplets.put(1, 3, "13");
		assertThat(triplets.size(), is(2));
		assertThat(triplets.get(1, 2), is("12"));
		assertThat(triplets.get(1, 3), is("13"));

		triplets.put(1, 2, "13");
		assertThat(triplets.get(1, 2), is("13"));
		assertThat(triplets.get(1, 3), is("13"));

		triplets.remove(1, 2);
		assertThat(triplets.isEmpty(), is(false));
		assertThat(triplets.size(), is(1));
		assertThat(triplets.get(1, 3), is("13"));
		assertThat(triplets.get(1, 2), is(nullValue()));
	}

	@Test
	public void shouldAllowRemoveOfItemsPutIn() {
		Triplets<Integer, Integer, String> triplets = new Triplets<Integer, Integer, String>();
		triplets.put(1, 2, "12");
		triplets.put(1, 3, "13");
		assertThat(triplets.get(1, 2), is("12"));
		assertThat(triplets.get(1, 3), is("13"));

		triplets.remove(1, 2);
		assertThat(triplets.get(1, 2), is(nullValue()));
		assertThat(triplets.get(1, 3), is("13"));

		triplets.remove(1, 3);
		assertThat(triplets.get(1, 2), is(nullValue()));
		assertThat(triplets.get(1, 3), is(nullValue()));
	}

	@Test
	public void shouldClearAllItems() {
		Triplets<Integer, Integer, String> triplets = new Triplets<Integer, Integer, String>();
		triplets.put(1, 2, "12");
		triplets.put(1, 3, "13");
		assertThat(triplets.get(1, 2), is("12"));
		assertThat(triplets.get(1, 3), is("13"));
		assertThat(triplets.size(), is(2));
		assertThat(triplets.isEmpty(), is(false));

		triplets.clear();

		assertThat(triplets.get(1, 2), is(nullValue()));
		assertThat(triplets.get(1, 3), is(nullValue()));
		assertThat(triplets.size(), is(0));
		assertThat(triplets.isEmpty(), is(true));
	}

	@Test
	public void shouldPutAll() {
		Triplets<Integer, Integer, String> triplets = new Triplets<Integer, Integer, String>();
		triplets.put(1, 2, "12");
		triplets.put(1, 3, "13");
		assertThat(triplets.get(1, 2), is("12"));
		assertThat(triplets.get(1, 3), is("13"));
		assertThat(triplets.size(), is(2));

		Triplets<Integer, Integer, String> receiver = new Triplets<Integer, Integer, String>();
		assertThat(receiver.size(), is(0));

		receiver.putAll(triplets);

		assertThat(receiver.get(1, 2), is("12"));
		assertThat(receiver.get(1, 3), is("13"));
		assertThat(receiver.size(), is(2));
	}

	@Test
	public void shouldReturnTrueForContainsKey() {
		Triplets<Integer, Integer, String> triplets = new Triplets<Integer, Integer, String>();
		assertThat(triplets.containsKey(1, 2), is(false));
		triplets.put(1, 2, "12");
		assertThat(triplets.containsKey(1, 2), is(true));
		assertThat(triplets.containsKey(1, 3), is(false));
		triplets.put(1, 3, "13");
		assertThat(triplets.containsKey(1, 3), is(true));

		triplets.remove(1, 2);
		assertThat(triplets.containsKey(1, 2), is(false));

		triplets.clear();
		assertThat(triplets.containsKey(1, 3), is(false));
	}

	@Test
	public void shouldReturnTrueForContainsValue() {
		Triplets<Integer, Integer, String> triplets = new Triplets<Integer, Integer, String>();
		assertThat(triplets.containsValue("12"), is(false));
		triplets.put(1, 2, "12");
		assertThat(triplets.containsValue("12"), is(true));
		assertThat(triplets.containsValue("13"), is(false));
		triplets.put(1, 3, "13");
		assertThat(triplets.containsValue("13"), is(true));

		triplets.remove(1, 2);
		assertThat(triplets.containsValue("12"), is(false));

		triplets.clear();
		assertThat(triplets.containsValue("13"), is(false));
	}

	@Test
	public void shouldReturnValues() {
		Triplets<Integer, Integer, String> triplets = new Triplets<Integer, Integer, String>();
		triplets.put(1, 1, "1");
		triplets.put(3, 3, "3");
		triplets.put(5, 5, "5");
		assertThat(triplets.values(), hasItems("1", "3", "5"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnKeys() {
		Triplets<Integer, Integer, String> triplets = new Triplets<Integer, Integer, String>();
		triplets.put(1, 1, "1");
		triplets.put(3, 3, "3");
		triplets.put(5, 5, "5");
		assertThat(triplets.keySet(), hasItems(pair(1, 1), pair(3, 3), pair(5, 5)));
	}

	@Test
	public void shouldReturnEntrySet() {
		Triplets<Integer, Integer, String> triplets = new Triplets<Integer, Integer, String>();
		triplets.put(1, 1, "1");
		triplets.put(3, 3, "3");
		triplets.put(5, 5, "5");
		assertThat(triplets.entrySet().size(), is(3));
		for (Map.Entry<Pair<Integer, Integer>, String> entry : triplets.entrySet()) {
			assertThat(entry.getKey().getA(), is(entry.getKey().getB()));
			assertThat(entry.getKey().getA().toString(), is(entry.getValue()));
		}
	}

	private <A, B> Pair<A, B> pair(A a, B b) {
		return new Pair<A, B>(a, b);
	}

	@Test
	public void shouldUseGivenMapToBackTriplets() {
		Triplets<Integer, Integer, String> triplets = new Triplets<Integer, Integer, String>(new LinkedHashMap<Pair<Integer, Integer>, String>());
		triplets.put(4, 4, "4");
		triplets.put(1, 1, "1");
		triplets.put(3, 3, "3");
		triplets.put(2, 2, "2");
		List<String> orderedValues = list(triplets.values());
		assertThat(orderedValues, is(Arrays.<String> asList("4", "1", "3", "2")));
	}

	@Test
	public void shouldDelegateEqualsHashcodeAndToStringToUnderlyingMap() {
		Map<Pair<Integer, Integer>, String> delegate = new HashMap<Pair<Integer, Integer>, String>();

		Triplets<Integer, Integer, String> triplets = new Triplets<Integer, Integer, String>(delegate);

		assertThat(triplets.hashCode(), is(delegate.hashCode()));
		assertThat(triplets.toString(), is(delegate.toString()));

	}
}
