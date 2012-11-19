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
package com.atomicleopard.expressive;

import static com.atomicleopard.expressive.Expressive.list;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class MapKeysTest {

	@Test
	public void shouldCreateMapBasedOnSuppliedKeysWithVarArgs() {
		MapKeys<Integer> keySet = new MapKeys<Integer>(1, 2, 3, 4, 5);
		Map<Integer, Integer> map = keySet.to(5, 4, 3, 2, 1);
		assertThat(map.size(), is(5));
		assertThat(map.get(1), is(5));
		assertThat(map.get(2), is(4));
		assertThat(map.get(3), is(3));
		assertThat(map.get(4), is(2));
		assertThat(map.get(5), is(1));
	}

	@Test
	public void shouldCreateMapBasedOnSuppliedKeysWithList() {
		MapKeys<Integer> keySet = new MapKeys<Integer>(1, 2, 3, 4, 5);
		Map<Integer, Integer> map = keySet.to(list(5, 4, 3, 2, 1));
		assertThat(map.size(), is(5));
		assertThat(map.get(1), is(5));
		assertThat(map.get(2), is(4));
		assertThat(map.get(3), is(3));
		assertThat(map.get(4), is(2));
		assertThat(map.get(5), is(1));
	}

	@Test
	public void shouldCreateMapExtraKeysToNullWithVarArgs() {
		MapKeys<Integer> keySet = new MapKeys<Integer>(1, 2, 3, 4, 5);
		Map<Integer, Integer> map = keySet.to(9, 8);
		assertThat(map.size(), is(5));
		assertThat(map.get(1), is(9));
		assertThat(map.get(2), is(8));
		assertThat(map.get(3), is(nullValue()));
		assertThat(map.get(4), is(nullValue()));
		assertThat(map.get(5), is(nullValue()));
	}

	@Test
	public void shouldCreateMapExtraKeysToNullWithList() {
		MapKeys<Integer> keySet = new MapKeys<Integer>(1, 2, 3, 4, 5);
		Map<Integer, Integer> map = keySet.to(list(9, 8));
		assertThat(map.size(), is(5));
		assertThat(map.get(1), is(9));
		assertThat(map.get(2), is(8));
		assertThat(map.get(3), is(nullValue()));
		assertThat(map.get(4), is(nullValue()));
		assertThat(map.get(5), is(nullValue()));
	}

	@Test
	public void shouldCreateMapWithoutExtraValuesWithVarArgs() {
		MapKeys<Integer> keySet = new MapKeys<Integer>(1, 2);
		Map<Integer, Integer> map = keySet.to(6, 7, 8, 9);
		assertThat(map.size(), is(2));
		assertThat(map.get(1), is(6));
		assertThat(map.get(2), is(7));
	}

	@Test
	public void shouldCreateMapWithoutExtraValuesWithList() {
		MapKeys<Integer> keySet = new MapKeys<Integer>(1, 2);
		Map<Integer, Integer> map = keySet.to(list(6, 7, 8, 9));
		assertThat(map.size(), is(2));
		assertThat(map.get(1), is(6));
		assertThat(map.get(2), is(7));
	}

	@Test
	public void shouldAllowDuplicateValuesWithVarArgs() {
		MapKeys<Integer> keySet = new MapKeys<Integer>(1, 2, 3);
		Map<Integer, Integer> map = keySet.to(1, 1, 1);
		assertThat(map.size(), is(3));
		assertThat(map.get(1), is(1));
		assertThat(map.get(2), is(1));
		assertThat(map.get(3), is(1));
	}

	@Test
	public void shouldAllowDuplicateValuesWithList() {
		MapKeys<Integer> keySet = new MapKeys<Integer>(1, 2, 3);
		Map<Integer, Integer> map = keySet.to(1, 1, 1);
		assertThat(map.size(), is(3));
		assertThat(map.get(1), is(1));
		assertThat(map.get(2), is(1));
		assertThat(map.get(3), is(1));
	}

	@Test
	public void shouldRetainLastKeyWhenDuplicateKeysExistWithVarArgs() {
		MapKeys<Integer> keySet = new MapKeys<Integer>(1, 2, 1, 2, 1, 2);
		Map<Integer, Integer> map = keySet.to(9, 8, 7, 6, 5, 4);
		assertThat(map.size(), is(2));
		assertThat(map.get(1), is(5));
		assertThat(map.get(2), is(4));
	}

	@Test
	public void shouldRetainLastKeyWhenDuplicateKeysExistWithList() {
		MapKeys<Integer> keySet = new MapKeys<Integer>(1, 2, 1, 2, 1, 2);
		Map<Integer, Integer> map = keySet.to(list(9, 8, 7, 6, 5, 4));
		assertThat(map.size(), is(2));
		assertThat(map.get(1), is(5));
		assertThat(map.get(2), is(4));
	}

	@Test
	public void shouldConstructEquivalentlyWithVarArgsAndList() {
		MapKeys<Integer> keySet = new MapKeys<Integer>(1, 2, 3, 4, 5);
		MapKeys<Integer> keySet2 = new MapKeys<Integer>(list(1, 2, 3, 4, 5));
		assertThat(keySet, is(keySet2));
	}

	@Test
	public void shouldDoHashcodeAndEqualsStuff() {
		MapKeys<Integer> keySet = new MapKeys<Integer>(1, 2, 3);
		MapKeys<Integer> keySet2 = new MapKeys<Integer>(1, 2, 3);
		MapKeys<Integer> keySet3 = new MapKeys<Integer>(3, 2, 1);
		assertTrue(keySet.equals(keySet));
		assertTrue(keySet.equals(keySet2));
		assertTrue(keySet2.equals(keySet));
		assertTrue(keySet2.equals(keySet2));

		assertFalse(keySet.equals(null));
		assertFalse(keySet.equals(keySet3));
		assertFalse(keySet.equals("Different"));
		assertFalse("Different".equals(keySet));

		assertTrue(keySet.hashCode() == keySet2.hashCode());
		assertFalse(keySet.hashCode() == keySet3.hashCode());
		assertFalse("Different".hashCode() == keySet.hashCode());
	}

	@Test
	public void shouldReturnCorrectSize() {
		assertThat(new MapKeys<Integer>().size(), is(0));
		assertThat(new MapKeys<Integer>(1).size(), is(1));
		assertThat(new MapKeys<Integer>(1, 2).size(), is(2));
		assertThat(new MapKeys<Integer>(1, 2, 2, 2).size(), is(4));
	}

	@Test
	public void shouldHaveSensibleToString() {
		MapKeys<Integer> keySet = new MapKeys<Integer>(1, 2, 3);
		assertThat(keySet.toString(), is("[1, 2, 3]"));
	}
}
