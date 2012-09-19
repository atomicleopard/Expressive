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
package com.atomicleopard.selector;

import static com.atomicleopard.selector.Selector.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ExampleUsagesTest {

	@Test
	public void simpleSelectorForApplyingSameMethodCallToAGroupOfObjects() {
		Customer select = Selector.select(Customer.class);

		Customer dave = createObjectChainWithNestedName("Dave");
		Customer bob = createObjectChainWithNestedName("Bob");

		List<String> resultsFromVarArgs = using(select).invoke(select.getHomeAddress().getStreetName()).on(dave, bob);
		assertThat(resultsFromVarArgs, is(Arrays.asList("Dave", "Bob")));

		List<String> resultsFromList = using(select).invoke(select.getHomeAddress().getStreetName()).on(Arrays.asList(dave, bob));
		assertThat(resultsFromList, is(Arrays.asList("Dave", "Bob")));
	}

	@Test
	public void selectingWithARealInstanceForApplyingSameMethodCallToAGroupOfObjects() {
		Customer select = createObjectChainWithNestedName("jennifer");
		select = select(select);

		assertThat(select.getHomeAddress().getStreetName(), is("jennifer"));

		Customer dave = createObjectChainWithNestedName("Dave");
		Customer bob = createObjectChainWithNestedName("Bob");

		List<String> resultsFromVarArgs = using(select).invoke(select.getHomeAddress().getStreetName()).on(dave, bob);
		assertThat(resultsFromVarArgs, is(Arrays.asList("Dave", "Bob")));

		List<String> resultsFromList = using(select).invoke(select.getHomeAddress().getStreetName()).on(Arrays.asList(dave, bob));
		assertThat(resultsFromList, is(Arrays.asList("Dave", "Bob")));
	}

	@Test
	public void simpleSelectorForInvokingMethodsThatReturnNulls() {
		Customer select = select(Customer.class);

		Customer dave = createObjectChainWithNestedName("Dave");
		Customer bob = createObjectChainWithNestedName("Bob");

		Address sharedChild = new Address();

		Selector<Customer> using = using(select);
		select.setPostalAddress(sharedChild);
		using.invokeOn(dave, bob);

		assertThat(dave.getPostalAddress(), sameInstance(sharedChild));
		assertThat(bob.getPostalAddress(), sameInstance(sharedChild));
	}

	@Test
	public void selectingWithARealInstanceForInvokingMethodsThatReturnNulls() {
		Customer jennifer = createObjectChainWithNestedName("jennifer");
		Customer select = select(jennifer);

		Customer dave = createObjectChainWithNestedName("Dave");
		Customer bob = createObjectChainWithNestedName("Bob");

		Address sharedChild = new Address();

		Selector<Customer> using = using(select);
		select.setPostalAddress(sharedChild);
		using.invokeOn(dave, bob);

		assertThat(jennifer.getPostalAddress(), sameInstance(sharedChild));
		assertThat(dave.getPostalAddress(), sameInstance(sharedChild));
		assertThat(bob.getPostalAddress(), sameInstance(sharedChild));
		// note the difference = the selectors child is enhanced, but appears to
		// be identical even though == does not evaluate to true
		// this will cause most generated equals methods to fail because the
		// getClass() methods are not the same
		assertThat(select.getPostalAddress(), not(sameInstance(sharedChild)));
	}

	@Test
	public void simpleSelectorUsageForNullSafeChaining() {
		Customer select = Selector.select(Customer.class);

		using(select).invoke(select.getHomeAddress().getStreetName()).on(select);
	}

	public static Customer createObjectChainWithNestedName(String string) {
		Customer testRootPojo = new Customer();
		Address child1 = new Address();
		testRootPojo.setHomeAddress(child1);
		child1.setStreetName(string);
		return testRootPojo;
	}
}
