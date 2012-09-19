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

import static com.atomicleopard.expressive.Expressive.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExpressiveTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldCreateASingleEntryMap() {
		File file = new File("file");
		Map<Integer, File> map = map(5, file);

		assertThat(map.get(5), is(file));
		assertThat(map.size(), is(1));

		Expressive.map("A", "B");
	}

	@Test
	public void shouldCreateASimpleMap() {
		File file5 = new File("file5");
		File file6 = new File("file5");
		Map<Integer, File> map = map(5, file5, 6, file6);

		assertThat(map.size(), is(2));
		assertThat(map.get(5), is(file5));
		assertThat(map.get(6), is(file6));
	}

	@Test
	public void shouldOmitFinalKeyIfAnUnevenNumberOfParametersAreSupplied() {
		File file5 = new File("file5");
		Map<Integer, File> map = map(5, file5, 6);

		assertThat(map.size(), is(1));
		assertThat(map.get(5), is(file5));
		assertThat(map.containsKey(6), is(false));
	}

	@Test
	public void shouldCreateAPairedMap() {
		File file5 = new File("file5");
		File file6 = new File("file5");
		Map<Integer, File> map = mapKeys(5, 6).to(file5, file6);
		assertThat(map.size(), is(2));
		assertThat(map.get(5), is(file5));
		assertThat(map.get(6), is(file6));

	}

	@Test
	public void shouldCreateACollection() {
		Collection<String> collection = collection("a", "b", "c");
		assertThat(collection.size(), is(3));
		assertThat(collection, hasItems("a", "b", "c"));
	}

	@Test
	public void shouldFlattenArraysMaintainingOrderAsVarArsg() {
		String[] collection1 = array("a", "b", "c");
		String[] collection2 = array("e", "f", "g");
		EList<String> collection = flatten(collection1, null, new String[0], collection2);
		assertThat(collection.size(), is(6));
		assertThat(collection, hasItems("a", "b", "c", "e", "f", "g"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldFlattenCollectionsMaintainingOrderAsVarArsg() {
		Collection<String> collection1 = collection("a", "b", "c");
		Collection<String> collection2 = collection("e", "f", "g");
		EList<String> collection = flatten(collection1, null, Collections.<String> emptyList(), collection2);
		assertThat(collection.size(), is(6));
		assertThat(collection, hasItems("a", "b", "c", "e", "f", "g"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldFlattenCollectionsMaintainingOrderAsCollectionOfCollections() {
		Collection<String> collection1 = collection("a", "b", "c");
		Collection<String> collection2 = collection("e", "f", "g");
		EList<String> collection = flatten(Arrays.asList(collection1, null, Collections.<String> emptyList(), collection2));
		assertThat(collection.size(), is(6));
		assertThat(collection, hasItems("a", "b", "c", "e", "f", "g"));
	}

	@Test
	public void shouldCreateAList() {
		EList<String> elist = list("A", "B", "C", "D", "A");
		assertThat(elist, is(Arrays.asList("A", "B", "C", "D", "A")));
	}

	@Test
	public void shouldCreateAListFromACollection() {
		EList<String> elist = list(Arrays.asList("A", "B", "C", "D", "A"));
		assertThat(elist, is(Arrays.asList("A", "B", "C", "D", "A")));
	}

	@Test
	public void shouldCreateAListFromASet() {
		EList<String> elist = list(new HashSet<String>(Arrays.asList("A", "B", "C", "D", "A")));
		assertThat(elist, hasItems("A", "B", "C", "D"));
	}

	@Test
	public void shouldBeEasyToUse() {
		EList<String> elist = list("A", "B", "C", "D");
		elist.insertItems(1, "E", "F").removeItems("A").addItems("Z");
		assertThat(elist, is(Arrays.asList("E", "F", "B", "C", "D", "Z")));
	}

	@Test
	public void shouldCreateAnArray() {
		assertThat(array(1), is(new Integer[] { 1 }));
		assertThat(array("item1", "item2"), is(new String[] { "item1", "item2" }));
	}

	@Test
	public void shouldCreateAnArrayWithNoArguments() {
		String[] result = Expressive.<String>array();
		assertThat(result.length, is(0));
	}

	@Test
	public void shouldCreateAnArrayFromACollection() {
		String[] result = array(Arrays.asList("String1", "String3"));
		assertThat(result.length, is(2));
		assertThat(result[0], is("String1"));
		assertThat(result[1], is("String3"));
	}

	@Test
	public void shouldCreateKeySetFromVarArgs() {
		MapKeys<String> mapKeys = mapKeys("item1", "item2");
		assertThat(mapKeys.size(), is(2));
		Map<String, Integer> map = mapKeys.to(4, 5);
		assertThat(map.size(), is(2));
		assertThat(map.get("item1"), is(4));
		assertThat(map.get("item2"), is(5));
	}

	@Test
	public void shouldCreateKeySetFromList() {
		MapKeys<String> mapKeys = mapKeys(list("item1", "item2"));
		assertThat(mapKeys.size(), is(2));
		Map<String, Integer> map = mapKeys.to(4, 5);
		assertThat(map.size(), is(2));
		assertThat(map.get("item1"), is(4));
		assertThat(map.get("item2"), is(5));
	}

	@Test
	public void shouldCreateIterableFromIterator() {
		Iterator<Integer> iterator = list(1, 2, 3, 4).iterator();
		Iterable<Integer> iterable = iterable(iterator);
		assertThat(iterable.iterator(), is(iterator));
	}

	@Test
	public void shouldCreateIterableFromEnumeration() {
		Vector<Integer> vector = new Vector<Integer>(list(1, 2, 3, 4));
		Iterable<Integer> iterable = iterable(vector.elements());
		assertThat(iterable.iterator().next(), is(1));
		assertThat(iterable.iterator().next(), is(2));
		assertThat(iterable.iterator().next(), is(3));
		assertThat(iterable.iterator().next(), is(4));
		assertThat(iterable.iterator().hasNext(), is(false));
	}

	@Test
	public void shouldCreateASet() {
		Set<String> set = set("A", "B", null, "A", "C");
		assertThat(set.size(), is(4));
		assertThat(set, hasItems("A", "B", "C", null));
	}

	@Test
	public void shouldCreateAnEmptySet() {
		assertThat(set().size(), is(0));
		assertThat(set().isEmpty(), is(true));
	}

	@Test
	public void shouldNotSupportRemoveFromIterableFromEnumeration() {
		thrown.expect(UnsupportedOperationException.class);
		Vector<Integer> vector = new Vector<Integer>(list(1, 2, 3, 4));
		Iterable<Integer> iterable = iterable(vector.elements());
		iterable.iterator().remove();
	}

	@Test
	public void shouldExcerciseTheConstructorSoIDontKeepCheckingCoverageReports() {
		Expressive e = new Expressive();
		assertThat(e, is(notNullValue()));
	}

	/*
	 * @Test public void exampleUsage(){ List<String> values =
	 * join(collection).with(values);
	 * using(comparator).reverse().sort(concatenationOf
	 * (collection).and(values).and(map).keys()); map(values).with(otherValues);
	 * map(values).to(otherValues); map(interleave(keys, values)); }
	 * 
	 * public void thisIsSomeThoughtsOnFluentDatastructureCreation() {
	 * from(keys).and(values).createMap(); from(keys).and(values).createPairs();
	 * from(keys).and(values).and(values).createList();
	 * from(keys).and(values).and(values).createSet(); }
	 * 
	 * public void thisIsASortOfInMemoryDatabaseWIthPairsOrTripletsORWhatever(){
	 * from(keys).and(values).createLookup(); map(keys).and(values).and(values);
	 * map(keys).as("Key").and(values).as("Values"); magicMap.where("Key", is) }
	 */
}
