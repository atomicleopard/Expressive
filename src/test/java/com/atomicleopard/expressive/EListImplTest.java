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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.atomicleopard.expressive.collection.Pair;
import com.atomicleopard.expressive.predicate.EPredicate;

public class EListImplTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();;

	@Test
	public void shouldConstructWithVarArgs() {
		EListImpl<String> list = new EListImpl<String>("Item1", "Item2");
		assertThat(list.size(), is(2));
		assertThat(list.get(0), is("Item1"));
		assertThat(list.get(1), is("Item2"));
	}

	@Test
	public void shouldConstructWithList() {
		EListImpl<String> list = new EListImpl<String>(Arrays.asList("Item1", "Item2"));
		assertThat(list.size(), is(2));
		assertThat(list.get(0), is("Item1"));
		assertThat(list.get(1), is("Item2"));
	}

	@Test
	public void shouldConstructWithEList() {
		EListImpl<String> list = new EListImpl<String>(new EListImpl<String>("Item1", "Item2"));
		assertThat(list.size(), is(2));
		assertThat(list.get(0), is("Item1"));
		assertThat(list.get(1), is("Item2"));
	}

	@Test
	public void shouldReturnFirstElement() {
		EListImpl<String> list = new EListImpl<String>();
		assertThat(list.first(), is(nullValue()));
		list.addItems("Test");
		assertThat(list.first(), is("Test"));

		list.addItems("Items");
		assertThat(list.first(), is("Test"));

		list.insertItems(0, "Other");
		assertThat(list.first(), is("Other"));
	}

	@Test
	public void shouldReturnLastElement() {
		EListImpl<String> list = new EListImpl<String>();
		assertThat(list.last(), is(nullValue()));
		list.addItems("Test");
		assertThat(list.last(), is("Test"));

		list.addItems("Items");
		assertThat(list.last(), is("Items"));

		list.insertItems(1, "Other");
		assertThat(list.last(), is("Items"));
	}

	@Test
	public void shouldReturnElementAtIndex() {
		EListImpl<String> list = new EListImpl<String>();
		assertThat(list.at(-1), is(nullValue()));
		assertThat(list.at(0), is(nullValue()));
		assertThat(list.at(1), is(nullValue()));

		list.addItems("Test", "Items");
		assertThat(list.at(0), is("Test"));
		assertThat(list.at(1), is("Items"));
		assertThat(list.at(-1), is(nullValue()));
		assertThat(list.at(2), is(nullValue()));

		list.insertItems(1, "Other");
		assertThat(list.at(0), is("Test"));
		assertThat(list.at(1), is("Other"));
		assertThat(list.at(2), is("Items"));
		assertThat(list.at(-1), is(nullValue()));
		assertThat(list.at(3), is(nullValue()));
	}

	@Test
	public void shouldReturnElementAtIndexWhenEntriesAreNull() {
		EListImpl<String> list = new EListImpl<String>();
		assertThat(list.at(-1), is(nullValue()));
		assertThat(list.at(0), is(nullValue()));
		assertThat(list.at(1), is(nullValue()));

		list.addItems("Test", null, "Items");
		assertThat(list.at(0), is("Test"));
		assertThat(list.at(1), is(nullValue()));
		assertThat(list.at(2), is("Items"));
	}

	@Test
	public void shouldDupliateAndRetainUnderlyingObjects() {
		Object o1 = new Object();
		Object o2 = new Object();
		EListImpl<Object> list = new EListImpl<Object>(o1, o2);
		assertThat(list.size(), is(2));
		assertThat(list.get(0), is(o1));
		assertThat(list.get(1), is(o2));

		EList<Object> duplicate = list.duplicate();
		assertThat(duplicate.size(), is(2));
		assertThat(duplicate.get(0), is(o1));
		assertThat(duplicate.get(1), is(o2));
		assertThat(duplicate, is(EListImpl.class));
		assertThat((EListImpl<Object>) duplicate, not(sameInstance(list)));
	}

	@Test
	public void shouldDupliateButNotShareUnderlyingCollection() {
		Object o1 = new Object();
		Object o2 = new Object();
		EListImpl<Object> list = new EListImpl<Object>(o1, o2);
		EList<Object> duplicate = list.duplicate();
		assertThat(duplicate, is(EListImpl.class));
		assertThat((EListImpl<Object>) duplicate, not(sameInstance(list)));
		list.addItems(new Object());

		assertThat(list.size(), is(3));
		assertThat(duplicate.size(), is(2));
	}

	@Test
	public void shouldAddItemsUsingVarArgs() {
		EListImpl<BigDecimal> list = new EListImpl<BigDecimal>();
		assertThat(list.size(), is(0));
		list.addItems(BigDecimal.valueOf(111), BigDecimal.valueOf(222));
		assertThat(list.size(), is(2));
		assertThat(list, is(Arrays.asList(BigDecimal.valueOf(111), BigDecimal.valueOf(222))));

		list.addItems(BigDecimal.valueOf(333), BigDecimal.valueOf(444));
		assertThat(list.size(), is(4));
		assertThat(list, is(Arrays.asList(BigDecimal.valueOf(111), BigDecimal.valueOf(222), BigDecimal.valueOf(333), BigDecimal.valueOf(444))));
	}

	@Test
	public void shouldAddItemsUsingCollections() {
		List<Integer> list1 = Arrays.asList(1, 2, 3);
		List<Integer> list2 = Arrays.asList(4, 5, 6);

		EListImpl<Integer> list = new EListImpl<Integer>();
		assertThat(list.size(), is(0));
		list.addItems(list1);
		assertThat(list, is(Arrays.asList(1, 2, 3)));

		list.addItems(list2);
		assertThat(list, is(Arrays.asList(1, 2, 3, 4, 5, 6)));
	}

	@Test
	public void shouldAddItemsUsingCollectionsWithoutNPEOnNullCollections() {
		List<Integer> list1 = Arrays.asList(1, 2, 3);
		List<Integer> list2 = null;
		List<Integer> list3 = Arrays.asList(7, 8, 9);

		EListImpl<Integer> list = new EListImpl<Integer>();
		assertThat(list.size(), is(0));
		list.addItems(list1);
		assertThat(list, is(Arrays.asList(1, 2, 3)));

		list.addItems(list2);
		assertThat(list, is(Arrays.asList(1, 2, 3)));

		list.addItems(list3);
		assertThat(list, is(Arrays.asList(1, 2, 3, 7, 8, 9)));
	}

	@Test
	public void shouldInsertItemsUsingVarArgs() {
		EListImpl<Integer> list = new EListImpl<Integer>(1, 5, 9);
		list.insertItems(1, 4);
		assertThat(list, is(Arrays.asList(1, 4, 5, 9)));

		list.insertItems(1, 2, 3);
		assertThat(list, is(Arrays.asList(1, 2, 3, 4, 5, 9)));

		list.insertItems(5, 6, 7, 8);
		assertThat(list, is(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
	}

	@Test
	public void shouldInsertItemsUsingCollection() {
		List<Integer> list1 = Arrays.asList(4);
		List<Integer> list2 = Arrays.asList(2, 3);
		List<Integer> list3 = Arrays.asList(6, 7);

		EListImpl<Integer> list = new EListImpl<Integer>(1, 5, 9);
		list.insertItems(1, list1);
		assertThat(list, is(Arrays.asList(1, 4, 5, 9)));

		list.insertItems(1, list2);
		assertThat(list, is(Arrays.asList(1, 2, 3, 4, 5, 9)));

		list.insertItems(5, list3);
		assertThat(list, is(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 9)));
	}

	@Test
	public void shouldInsertItemsUsingCollectionInsertingAtTheStartIfOutOfBoundsNegatively() {
		List<Integer> list1 = Arrays.asList(4);
		List<Integer> list2 = Arrays.asList(2, 3);

		EListImpl<Integer> list = new EListImpl<Integer>(1, 5, 9);
		list.insertItems(-1, list1);
		assertThat(list, is(Arrays.asList(4, 1, 5, 9)));

		list.insertItems(-10, list2);
		assertThat(list, is(Arrays.asList(2, 3, 4, 1, 5, 9)));
	}

	@Test
	public void shouldInsertItemsUsingCollectionWithoutNPEOnNullCollections() {
		List<Integer> list1 = Arrays.asList(4);
		List<Integer> list2 = null;
		List<Integer> list3 = Arrays.asList(6, 7);

		EListImpl<Integer> list = new EListImpl<Integer>(1, 5, 9);
		list.insertItems(1, list1);
		assertThat(list, is(Arrays.asList(1, 4, 5, 9)));

		list.insertItems(1, list2);
		assertThat(list, is(Arrays.asList(1, 4, 5, 9)));

		list.insertItems(3, list3);
		assertThat(list, is(Arrays.asList(1, 4, 5, 6, 7, 9)));

		list.insertItems(3, Collections.<Integer> emptyList());
		assertThat(list, is(Arrays.asList(1, 4, 5, 6, 7, 9)));
	}

	@Test
	public void shouldRemoveItemsUsingVarArgs() {
		EListImpl<String> list = new EListImpl<String>("A", "B", "C", "C", "D", "E", "C", "B", "F");
		list.removeItems("C");
		assertThat(list, is(Arrays.asList("A", "B", "D", "E", "B", "F")));

		list.removeItems("A", "B");
		assertThat(list, is(Arrays.asList("D", "E", "F")));

		list.removeItems("Z");
		assertThat(list, is(Arrays.asList("D", "E", "F")));
	}

	@Test
	public void shouldRemoveItemsUsingCollection() {
		List<String> listA = Arrays.asList("A");
		List<String> listBC = Arrays.asList("B", "C");
		List<String> listZ = Arrays.asList("Z");
		EListImpl<String> list = new EListImpl<String>("A", "B", "C", "C", "D", "E", "C", "B", "F");
		list.removeItems(listA);
		assertThat(list, is(Arrays.asList("B", "C", "C", "D", "E", "C", "B", "F")));
		list.removeItems(listBC);
		assertThat(list, is(Arrays.asList("D", "E", "F")));
		list.removeItems(listZ);
		assertThat(list, is(Arrays.asList("D", "E", "F")));
		list.removeItems(Collections.<String> emptyList());
		assertThat(list, is(Arrays.asList("D", "E", "F")));
		list.removeItems((Collection<String>) null);
		assertThat(list, is(Arrays.asList("D", "E", "F")));
	}

	@Test
	public void shouldRetainItemsUsingVarArgs() {
		EListImpl<String> list = new EListImpl<String>("A", "B", "C", "C", "D", "E", "C", "B", "F");
		list.retainItems("A", "B", "C", "D", "F");
		assertThat(list, is(Arrays.asList("A", "B", "C", "C", "D", "C", "B", "F")));

		list.retainItems("A", "B", "C");
		assertThat(list, is(Arrays.asList("A", "B", "C", "C", "C", "B")));

		list.retainItems("B", "C", "Z");
		assertThat(list, is(Arrays.asList("B", "C", "C", "C", "B")));
	}

	@Test
	public void shouldRetainItemsUsingCollection() {
		List<String> listBC = Arrays.asList("B", "C");
		List<String> listZ = Arrays.asList("Z");
		EListImpl<String> list = new EListImpl<String>("A", "B", "C", "C", "D", "E", "C", "B", "F");
		list.retainItems(listBC);
		assertThat(list, is(Arrays.asList("B", "C", "C", "C", "B")));
		list.retainItems(listZ);
		assertThat(list, is(Collections.<String> emptyList()));
	}

	@Test
	public void shouldProvideElistAsSublist() {
		EListImpl<String> list = new EListImpl<String>("A", "B", "C", "C", "D", "E", "C", "B", "F");
		EList<String> subList = list.subList(1, 4);
		assertThat(subList, is(Arrays.asList("B", "C", "C")));
	}

	@Test
	public void shouldDelegateAllListOperationsToDelegate() {
		EListImpl<String> elist = new EListImpl<String>();
		elist.delegate = spy(elist.delegate);

		List<String> list = elist;
		list.add("A");
		verify(elist.delegate).add("A");

		list.add(0, "B");
		verify(elist.delegate).add(0, "B");

		list.addAll(Arrays.asList("C"));
		verify(elist.delegate).addAll(Arrays.asList("C"));

		list.addAll(0, Arrays.asList("D"));
		verify(elist.delegate).addAll(0, Arrays.asList("D"));

		list.contains("B");
		verify(elist.delegate).contains("B");

		list.containsAll(Arrays.asList("B", "C"));
		verify(elist.delegate).containsAll(Arrays.asList("B", "C"));

		list.get(1);
		verify(elist.delegate).get(1);

		list.isEmpty();
		verify(elist.delegate).isEmpty();

		list.iterator();
		verify(elist.delegate).iterator();

		list.lastIndexOf("C");
		verify(elist.delegate).lastIndexOf("C");

		list.listIterator();
		verify(elist.delegate).listIterator();

		list.remove(1);
		verify(elist.delegate).remove(1);

		list.remove("A");
		verify(elist.delegate).remove("A");

		list.removeAll(Collections.emptyList());
		verify(elist.delegate).removeAll(Collections.emptyList());

		list.retainAll(Arrays.asList("B", "C"));
		verify(elist.delegate).retainAll(Arrays.asList("B", "C"));

		list.set(0, "Z");
		verify(elist.delegate).set(0, "Z");

		list.toArray();
		verify(elist.delegate).toArray();

		list.toArray(new String[0]);
		verify(elist.delegate).toArray(new String[0]);

		list.clear();
		verify(elist.delegate).clear();
	}

	@Test
	public void shouldDelegateAllListOperationsToDelegate2() {
		EListImpl<String> elist = new EListImpl<String>();
		elist.delegate = spy(elist.delegate);
		List<String> list = elist;

		list.indexOf("C");
		verify(elist.delegate, times(1)).indexOf("C");

		list.size();
		verify(elist.delegate, times(1)).size();

		list.listIterator(0);
		verify(elist.delegate, times(1)).listIterator(0);
	}

	@Test
	public void shouldGiveASensibleToString() {
		EListImpl<String> list = new EListImpl<String>("A", "B", "C", "C", "D", "E", "C", "B", "F");
		assertThat(list.toString(), is("[A, B, C, C, D, E, C, B, F]"));
	}

	@Test
	public void shouldHaveEqualityAndHashcodeThatWorksWithOtherLists() {
		EListImpl<String> list = new EListImpl<String>("A", "B", null, "C");
		List<String> equalList = Arrays.asList("A", "B", null, "C");
		List<String> differentLengthList = Arrays.asList("A", "B", null, "C", "C");
		List<String> notEqualList = Arrays.asList("A", null, "C");

		assertThat(list.equals(list), is(true));
		assertThat(list.equals(equalList), is(true));
		assertThat(equalList.equals(list), is(true));
		assertThat(notEqualList.equals(list), is(false));
		assertThat(list.equals(notEqualList), is(false));
		assertThat(list.equals(differentLengthList), is(false));
		assertThat(list.equals(new Object()), is(false));
		assertThat(list.equals(null), is(false));

		assertThat(list.hashCode(), is(list.hashCode()));
		assertThat(list.hashCode(), is(equalList.hashCode()));
		assertThat(list.hashCode(), is(not(notEqualList.hashCode())));
		assertThat(list.hashCode(), is(not(differentLengthList.hashCode())));
	}

	@Test
	public void shouldSortList() {
		EListImpl<String> list = new EListImpl<String>("A", "Q", "B", "z", "C");
		list.sort(String.CASE_INSENSITIVE_ORDER);
		assertThat(list, is(Expressive.list("A", "B", "C", "Q", "z")));
	}

	@Test
	public void shouldSortListButNotAlterTheOrderOfASourceElist() {
		EListImpl<String> listFirst = new EListImpl<String>("A", "Q", "B", "z", "C");
		EListImpl<String> list = new EListImpl<String>(listFirst);
		list.sort(String.CASE_INSENSITIVE_ORDER);
		assertThat(list, is(Expressive.list("A", "B", "C", "Q", "z")));
		assertThat(listFirst, is(Expressive.list("A", "Q", "B", "z", "C")));
	}

	@Test
	public void shouldSortListButNotAlterTheOrderOfASourceList() {
		List<String> listFirst = Arrays.asList("A", "Q", "B", "z", "C");
		EListImpl<String> list = new EListImpl<String>(listFirst);
		list.sort(String.CASE_INSENSITIVE_ORDER);
		assertThat(list, is(Expressive.list("A", "B", "C", "Q", "z")));
		assertThat(listFirst, is((List<String>) Expressive.list("A", "Q", "B", "z", "C")));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldCreateAnNewArrayListDelegateWhenGivenAnElistWhichIsntAnEListImp() {
		EList<String> mockElist = mock(EList.class);
		when(mockElist.toArray()).thenReturn(new String[] { "Hi", "Test", "Values" });
		EListImpl<String> list = new EListImpl<String>(mockElist);
		assertThat(list, hasItems("Hi", "Test", "Values"));
	}

	@Test
	public void shouldGetItemsAtIndexForSize() {
		EListImpl<String> list = new EListImpl<String>("A", "B", "C", "D");
		assertThat(list.getItems(0, 4), is(list("A", "B", "C", "D")));
		assertThat(list.getItems(0, 3), is(list("A", "B", "C")));
		assertThat(list.getItems(0, 1), is(list("A")));
		assertThat(list.getItems(0, 0).isEmpty(), is(true));
		assertThat(list.getItems(1, 4), is(list("B", "C", "D")));
		assertThat(list.getItems(1, 3), is(list("B", "C", "D")));
		assertThat(list.getItems(4, 4).isEmpty(), is(true));
		assertThat(list.getItems(4, 100).isEmpty(), is(true));
		assertThat(list.getItems(0, 100), is(list("A", "B", "C", "D")));

		assertThat(list.getItems(-1, 2), is(list("A")));
		assertThat(list.getItems(5, 100).isEmpty(), is(true));
	}

	@Test
	public void shouldGetItemsMatchingPredicate() {
		EListImpl<String> list = new EListImpl<String>("A", "B", "C", "D");
		assertThat(list.getItems(Expressive.Predicate.is("B")), is(list("B")));

		// should return a new list
		assertThat(list.getItems(Expressive.Predicate.is("B")), not(sameInstance((EList<String>) list)));

		// verify does not modify the list
		assertThat(list, hasItems("A", "B", "C", "D"));
	}

	@Test
	public void shouldThrowNPEWhenGetItemsMatchingPredicateWithNullPredicate() {
		thrown.expect(NullPointerException.class);
		new EListImpl<String>("A", "B", "C", "D").getItems(null);
	}

	@Test
	public void shouldGetItemsReturningEmptyCollectionWhenNoMatches() {
		EListImpl<String> list = new EListImpl<String>("A", "B", "C", "D");
		assertThat(list.getItems(Expressive.Predicate.<String> none()).isEmpty(), is(true));
	}

	@Test
	public void shouldGetItemsRetainingOrder() {
		EListImpl<String> list = new EListImpl<String>("A", "B", "C", "D");
		assertThat(list.getItems(Expressive.Predicate.<String> any()), is(list("A", "B", "C", "D")));
		Collections.shuffle(list);
		assertThat(list.getItems(Expressive.Predicate.<String> any()), is((EList<String>) list));
	}

	@Test
	public void shouldRetainItemsUsingPredicate() {
		EListImpl<String> list = new EListImpl<String>("A", "B", "C", "D");
		assertThat(list.retainItems(Expressive.Predicate.<String> any()), is(list("A", "B", "C", "D")));
		assertThat(list.retainItems(Expressive.Predicate.<String> any()), sameInstance((EList<String>) list));

		// should retain order
		Collections.shuffle(list);
		assertThat(list.retainItems(Expressive.Predicate.<String> any()), is((EList<String>) list));

		assertThat(list.retainItems(Expressive.Predicate.is("B")), is(list("B")));

		assertThat(list.retainItems(Expressive.Predicate.<String> none()).isEmpty(), is(true));

		// should modify original instance
		assertThat(list.isEmpty(), is(true));
	}

	@Test
	public void shouldThrowNPEWhenRetainItemsWithNullPredicate() {
		thrown.expect(NullPointerException.class);
		new EListImpl<String>("A", "B", "C", "D").retainItems((EPredicate<String>) null);
	}

	@Test
	public void shouldRemoveItemsUsingPredicate() {
		EListImpl<String> list = new EListImpl<String>("A", "B", "C", "D");
		assertThat(list.removeItems(Expressive.Predicate.<String> none()), is(list("A", "B", "C", "D")));
		assertThat(list.removeItems(Expressive.Predicate.<String> none()), sameInstance((EList<String>) list));

		// should retain order
		Collections.shuffle(list);
		assertThat(list.removeItems(Expressive.Predicate.<String> none()), is((EList<String>) list));

		list = new EListImpl<String>("A", "B", "C", "D");
		assertThat(list.removeItems(Expressive.Predicate.anyOf("A", "B")), is(list("C", "D")));

		assertThat(list.removeItems(Expressive.Predicate.<String> any()).isEmpty(), is(true));

		// should modify original instance
		assertThat(list.isEmpty(), is(true));
	}

	@Test
	public void shouldThrowNPEWhenRemoveItemsWithNullPredicate() {
		thrown.expect(NullPointerException.class);
		new EListImpl<String>("A", "B", "C", "D").removeItems((EPredicate<String>) null);
	}

	@Test
	public void shouldSplitEListUsingPredicate() {
		EListImpl<String> list = new EListImpl<String>("A", "B", "C", "D");
		Pair<EList<String>, EList<String>> split = list.split(Expressive.Predicate.anyOf("A", "B"));
		assertThat(split.getA(), is(list("A", "B")));
		assertThat(split.getB(), is(list("C", "D")));
		// should not modify original list
		assertThat(list, is(list("A", "B", "C", "D")));
	}

	@Test
	public void shouldThrowNPEWhenSplitWithNullPredicate() {
		thrown.expect(NullPointerException.class);
		new EListImpl<String>("A", "B", "C", "D").split(null);
	}

	@Test
	public void shouldPassTheExampleTestSnippetFromTheJavadoc() {
		EList<String> list = Expressive.list("A", "B", "C");
		EList<String> items = list.getItems(-2, 3);
		assertThat(items.size(), is(1));
		assertThat(items, is(list("A")));
	}

	@Test
	public void shouldCreateWithDelegateOfGivenInitialCapacity() {
		EListImpl<String> eListImpl = new EListImpl<String>(10);
		assertThat(eListImpl, is(notNullValue()));
	}
}
