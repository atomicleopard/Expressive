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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.junit.Ignore;
import org.junit.Test;

public class SelectorTest {

	@Test
	public void shouldNotCauseNullPointerExceptionOnChainedCalls() {
		Customer customerSelector = select(Customer.class);
		Address address = customerSelector.getHomeAddress();
		assertThat(address, is(notNullValue()));

		Suburb secondCall = address.getSuburb();
		assertThat(secondCall, is(notNullValue()));

		String anotherName = address.getStreetName();
		assertThat(anotherName, is(nullValue()));
	}

	@Test
	public void shouldRecordAnInvokeOnDifferentInstance() {
		Customer customer = new Customer();
		customer.setName("Expected");

		Customer selector = select(Customer.class);

		String result = using(selector).invoke(selector.getName()).on(customer);

		assertThat(result, is("Expected"));
	}

	@Test
	public void shouldRecordAndInvokeAcrossAnArray() {
		Customer customer1 = new Customer();
		customer1.setName("Expected");
		Customer customer2 = new Customer();
		customer2.setName("Expected2");

		Customer selector = select(Customer.class);

		List<String> result = using(selector).invoke(selector.getName()).on(customer1, customer2);

		assertThat(result, hasItems("Expected", "Expected2"));
		assertThat(result.get(0), is("Expected"));
	}

	@Test
	public void shouldInvokeSettersAcrossList() {
		Customer customer1 = new Customer();
		customer1.setName("Not Expected");
		Customer customer2 = new Customer();
		customer2.setName("Not Expected2");

		Customer selector = select(Customer.class);

		Selector<Customer> selection = using(selector);
		selector.setName("Name");
		selection.invokeOn(Arrays.asList(customer1, customer2));

		assertThat(customer1.getName(), is("Name"));
		assertThat(customer2.getName(), is("Name"));
	}

	@Test
	public void shouldBehaveNormallyWhenARealInstanceIsSelected() {
		Customer customer = new Customer();
		customer.setName("Expected");
		Address homeAddress = new Address();
		Suburb suburb = new Suburb();
		suburb.setName("Test suburb");
		homeAddress.setSuburb(suburb);
		customer.setHomeAddress(homeAddress);

		homeAddress.setStreetName("Real AnotherName");

		Customer selectedInstance = select(customer);
		assertThat(selectedInstance.getName(), is("Expected"));
		assertThat(selectedInstance.getHomeAddress().getStreetName(), is("Real AnotherName"));
		assertThat(selectedInstance.getHomeAddress().getSuburb().getName(), is("Test suburb"));

		assertThat(selectedInstance, is(not(sameInstance(customer))));
		assertThat(selectedInstance.getHomeAddress(), is(not(sameInstance(homeAddress))));

	}

	@Test
	public void shouldProxyAllProxiableInstancesWehnSelectingARealInstance() {
		Customer realInstance = new Customer();
		realInstance.setName("Expected");
		Address child1 = new Address();
		realInstance.setHomeAddress(child1);
		child1.setStreetName("Real AnotherName");

		realInstance = select(realInstance);
		Selector<Customer> with = using(realInstance);
		InvocationTarget<Customer> invoke = with.invoke(realInstance.getName());

		SelectorMethodInterceptor selectorMethodInterceptor = with.getSelectorMethodInterceptor();
		assertThat(selectorMethodInterceptor, is(ProxyingStackMethodInterceptor.class));
		ProxyingStackMethodInterceptor interceptor = (ProxyingStackMethodInterceptor) selectorMethodInterceptor;
		LinkedHashMap<Method, Object[]> invocationStack = interceptor.getInvocationStack();

		Iterator<Method> keyIterator = invocationStack.keySet().iterator();
		assertThat(keyIterator.next().getName(), is("getName"));

		String results = invoke.on(realInstance);
		assertThat(results, is("Expected"));
	}

	@Test
	public void shouldProxyRealInstances() {
		Customer realInstance = new Customer();
		realInstance.setName("Expected");
		realInstance.setHomeAddress(new Address());

		Customer realInstance2 = new Customer();
		realInstance2.setName("Expected2");
		realInstance2.setHomeAddress(new Address());

		Customer selector = select(realInstance);

		Selector<Customer> using = using(selector);
		selector.getHomeAddress().setStreetName("AnotherName");
		using.invokeOn(realInstance2);

		assertThat(realInstance.getHomeAddress().getStreetName(), is("AnotherName"));
		assertThat(realInstance2.getHomeAddress().getStreetName(), is("AnotherName"));
	}

	@Test
	public void shouldProxyRealInstanceEvenWithNulls() {
		Customer realInstance = new Customer();
		realInstance.setName("Expected");

		Customer realInstance2 = new Customer();
		realInstance2.setName("Expected2");
		realInstance2.setHomeAddress(new Address());

		Customer selector = select(realInstance);

		Selector<Customer> using = using(selector);
		selector.getHomeAddress().setStreetName("AnotherName");
		using.invokeOn(realInstance2);

		assertThat(realInstance.getHomeAddress(), is(nullValue()));
		assertThat(realInstance2.getHomeAddress().getStreetName(), is("AnotherName"));
	}

	@Test
	public void shouldProxyBasicTypeBoolean() {
		PojoWithAllBasicTypes pojo = new PojoWithAllBasicTypes();

		PojoWithAllBasicTypes selector = select(PojoWithAllBasicTypes.class);
		Selector<PojoWithAllBasicTypes> using = using(selector);
		selector.setBboolean(true);
		using.invokeOn(pojo);
		assertThat(pojo.isBboolean(), is(true));
		assertThat(selector.isBboolean(), is(false));
	}

	@Test
	public void shouldProxyBasicTypeByte() {
		PojoWithAllBasicTypes pojo = new PojoWithAllBasicTypes();

		PojoWithAllBasicTypes selector = select(PojoWithAllBasicTypes.class);
		Selector<PojoWithAllBasicTypes> using = using(selector);
		selector.setBbyte((byte) 1);
		using.invokeOn(pojo);
		assertThat(pojo.getBbyte(), is((byte) 1));

		assertThat(selector.getBbyte(), is((byte) 0));
	}

	@Test
	public void shouldProxyBasicTypeChar() {
		PojoWithAllBasicTypes pojo = new PojoWithAllBasicTypes();

		PojoWithAllBasicTypes selector = select(PojoWithAllBasicTypes.class);
		Selector<PojoWithAllBasicTypes> using = using(selector);
		selector.setCchar('g');
		using.invokeOn(pojo);
		assertThat(pojo.getCchar(), is('g'));

		assertThat(selector.getCchar(), is((char) 0));
	}

	@Test
	public void shouldProxyBasicTypeDouble() {
		PojoWithAllBasicTypes pojo = new PojoWithAllBasicTypes();

		PojoWithAllBasicTypes selector = select(PojoWithAllBasicTypes.class);
		Selector<PojoWithAllBasicTypes> using = using(selector);
		selector.setDdouble(11.11);
		using.invokeOn(pojo);
		assertThat(pojo.getDdouble(), is(11.11));

		assertThat(selector.getDdouble(), is(0.0));
	}

	@Test
	public void shouldProxyBasicTypeFloat() {
		PojoWithAllBasicTypes pojo = new PojoWithAllBasicTypes();

		PojoWithAllBasicTypes selector = select(PojoWithAllBasicTypes.class);
		Selector<PojoWithAllBasicTypes> using = using(selector);
		selector.setFfloat(33.33f);
		using.invokeOn(pojo);
		assertThat(pojo.getFfloat(), is(33.33f));

		assertThat(selector.getFfloat(), is(0f));
	}

	@Test
	public void shouldProxyBasicTypeInt() {
		PojoWithAllBasicTypes pojo = new PojoWithAllBasicTypes();

		PojoWithAllBasicTypes selector = select(PojoWithAllBasicTypes.class);
		Selector<PojoWithAllBasicTypes> using = using(selector);
		selector.setIint(12);
		using.invokeOn(pojo);
		assertThat(pojo.getIint(), is(12));

		assertThat(selector.getIint(), is(0));
	}

	@Test
	public void shouldProxyBasicTypeLong() {
		PojoWithAllBasicTypes pojo = new PojoWithAllBasicTypes();

		PojoWithAllBasicTypes selector = select(PojoWithAllBasicTypes.class);
		Selector<PojoWithAllBasicTypes> using = using(selector);
		selector.setLlong(1234l);
		using.invokeOn(pojo);
		assertThat(pojo.getLlong(), is(1234l));

		assertThat(selector.getLlong(), is(0l));
	}

	@Test
	public void shouldProxyBasicTypeShort() {
		PojoWithAllBasicTypes pojo = new PojoWithAllBasicTypes();

		PojoWithAllBasicTypes selector = select(PojoWithAllBasicTypes.class);
		Selector<PojoWithAllBasicTypes> using = using(selector);
		selector.setSshort((short) 11);
		using.invokeOn(pojo);
		assertThat(pojo.getSshort(), is((short) 11));

		assertThat(selector.getSshort(), is((short) 0));
	}

	@Test
	public void shouldBeAbleToSelectMoreThanOnce() {
		Customer realInstance = new Customer();
		realInstance.setName("Expected");

		realInstance = select(select(realInstance));

		assertThat(realInstance.getName(), is("Expected"));
	}

	@Test
	public void shouldNotNullPointerOnRealInstance() {
		Customer realInstance = new Customer();

		String actual = select(realInstance).getHomeAddress().getStreetName();
		assertThat(actual, is(nullValue()));
	}

	@Test
	public void shouldDetectEnhancedClass() {
		assertThat(isEnhanced(""), is(false));

		Customer realInstance = new Customer();
		assertThat(isEnhanced(realInstance), is(false));

		assertThat(isEnhanced(select(realInstance)), is(true));
		assertThat(isEnhanced(select(Customer.class)), is(true));

		Object cgLib = Enhancer.create(Customer.class, new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				return proxy.invokeSuper(obj, args);
			}
		});
		assertThat(isEnhanced(cgLib), is(false));
	}

	@Ignore
	@Test
	public void complexRecordAndPlayback() {
		Customer select = select(Customer.class);
		Customer dave = ExampleUsagesTest.createObjectChainWithNestedName("Dave");
		Customer bob = ExampleUsagesTest.createObjectChainWithNestedName("Bob");

		Address sharedChild = new Address();
		Suburb sharedGrandChild = new Suburb();

		Selector<Customer> using = using(select);
		select.setPostalAddress(sharedChild);
		select.setHomeAddress(sharedChild);
		select.getPostalAddress().setSuburb(sharedGrandChild);
		using.invokeOn(dave, bob);

		assertThat(dave.getPostalAddress(), sameInstance(sharedChild));
		assertThat(dave.getHomeAddress(), sameInstance(sharedChild));
		assertThat(dave.getPostalAddress().getSuburb(), sameInstance(sharedGrandChild));
		assertThat(bob.getPostalAddress(), sameInstance(sharedChild));
		assertThat(bob.getHomeAddress(), sameInstance(sharedChild));
		assertThat(bob.getPostalAddress().getSuburb(), sameInstance(sharedGrandChild));
	}

}
