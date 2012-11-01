/*
 *  Copyright (c) 2011 Nicholas Okunew
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

import static com.atomicleopard.expressive.Cast.as;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

public class CastTest {

	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnObjectAsCastType() {
		Collection<String> collection = new ArrayList<String>();

		ArrayList<String> cast = as(collection, ArrayList.class);
		assertThat(cast, Matchers.is(notNullValue()));
	}

	@Test
	public void shouldReturnInvalidCastAsNull() {
		Collection<String> collection = new ArrayList<String>();

		String nullValue = as(collection, String.class);
		assertThat(nullValue, Matchers.is(nullValue()));
	}

	@Test
	public void shouldReturnNullIfNullPassedInToCast() {
		assertThat(as(null, String.class), Matchers.is(nullValue()));
	}

	@Test
	public void shouldReturnTrueWhenInstanceMatchesType() {
		ArrayList<String> arrayList = new ArrayList<String>();
		assertThat(Cast.is(arrayList, ArrayList.class), Matchers.is(true));
		assertThat(Cast.is(arrayList, List.class), Matchers.is(true));
		assertThat(Cast.is(arrayList, Collection.class), Matchers.is(true));
		assertThat(Cast.is(arrayList, Iterable.class), Matchers.is(true));
		assertThat(Cast.is(arrayList, Object.class), Matchers.is(true));
		assertThat(Cast.is(arrayList, String.class), Matchers.is(false));
		assertThat(Cast.is(arrayList, Integer.class), Matchers.is(false));
	}

	@Test
	public void shouldReturnFalseWhenInstanceIsNull() {
		assertThat(Cast.is(null, List.class), Matchers.is(false));
	}

	@Test
	public void shouldReturnTrueWhenInstanceMatchesOneOfTheVarargsTypes() {
		ArrayList<String> arrayList = new ArrayList<String>();

		assertThat(Cast.is(arrayList, String.class, Integer.class), Matchers.is(false));
		assertThat(Cast.is(arrayList, String.class, Integer.class, ArrayList.class), Matchers.is(true));
		assertThat(Cast.is(arrayList, String.class, Integer.class, List.class), Matchers.is(true));
		assertThat(Cast.is(arrayList, String.class, Integer.class, Collection.class), Matchers.is(true));
		assertThat(Cast.is(arrayList, String.class, Integer.class, Iterable.class), Matchers.is(true));
		assertThat(Cast.is(arrayList, String.class, Integer.class, Object.class), Matchers.is(true));
		assertThat(Cast.is(null, Object.class, List.class), Matchers.is(false));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnTrueWhenInstanceMatchesOneOfTheIterableTypes() {
		ArrayList<String> arrayList = new ArrayList<String>();

		assertThat(Cast.is(arrayList, Arrays.<Class<?>> asList(String.class, Integer.class)), Matchers.is(false));
		assertThat(Cast.is(arrayList, Arrays.<Class<?>> asList(String.class, Integer.class, ArrayList.class)), Matchers.is(true));
		assertThat(Cast.is(arrayList, Arrays.asList(String.class, Integer.class, List.class)), Matchers.is(true));
		assertThat(Cast.is(arrayList, Arrays.asList(String.class, Integer.class, Collection.class)), Matchers.is(true));
		assertThat(Cast.is(arrayList, Arrays.asList(String.class, Integer.class, Iterable.class)), Matchers.is(true));
		assertThat(Cast.is(arrayList, Arrays.asList(String.class, Integer.class, Object.class)), Matchers.is(true));
		assertThat(Cast.is(null, Arrays.asList(Object.class, List.class)), Matchers.is(false));
	}

	@Test
	public void notReallyMuchOfATest() {
		new Cast();
	}
}
