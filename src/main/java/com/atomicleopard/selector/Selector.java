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

import static com.atomicleopard.expressive.Cast.*;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.cglib.core.TypeUtils;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;

/**
 * <p>
 * {@link Selector} is still 'experimental', I'm putting it out there to gather
 * feedback on it's usefulness and interface/usability. If you use it, or can't,
 * let me know!
 * </p>
 * <p>
 * Rather than comprehensive documentation, I will illustrate it's usage with a
 * few examples. <br/>
 * Given we are trying to do this:
 * 
 * <pre>
 * Customer dave = getCustomer(123);
 * Customer bob = getCustomer(456);
 * List&lt;String&gt; streetNames = new ArrayList&lt;String&gt;();
 * for (Customer customer : Arrays.asList(dave, bob)) {
 * 		Address address = customer.getHomeAddress()
 * 		streetNames.add(address == null ? null : address.getStreetName());
 * }
 * </pre>
 * 
 * Using selector, we can do the same like this;
 * 
 * <pre>
 * // creates a selector, a dummy Customer that we can define operations on
 * Customer select = Selector.select(Customer.class);
 * 
 * // here we define an operation on the selector, then execute it on real instances 
 * List&lt;String&gt; streetNames = using(select).invoke(select.getHomeAddress().getStreetName()).on(dave, bob);
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * 
 * <pre>
 * // as well as creating a synthetic selector, we can use a real instance. Methods calls
 * // will be delegated to the real instance, but it also still acts as selector. 
 * Customer customer1 = getCustomer(123);
 * Customer customer2 = getCustomer(456);
 * 
 * Customer selector = select(customer1);
 * 
 * // after this invocation, both customers will have the same street name
 * Selector&lt;Customer&gt; using = using(selector);
 * selector.getHomeAddress().setStreetName(&quot;Shared StreetName&quot;);
 * using.invokeOn(customer2);
 * </pre>
 * 
 * </p>
 * 
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class Selector<T> {
	public static <T> T select(Class<T> clazz) {
		StackMethodInterceptor stackMethodInterceptor = new StackMethodInterceptor();
		T selector = enhanceWithInterceptor(clazz, stackMethodInterceptor);
		return selector;
	}

	public static <T> T select(T instance) {
		T selector = instance;
		if (!isEnhanced(instance)) {
			selector = (T) enhanceWithInterceptor(instance.getClass(), new ProxyingStackMethodInterceptor(instance));
		}
		return selector;
	}

	public static <T> Selector<T> using(T selector) {
		if (!isEnhanced(selector)) {
			throw new RuntimeException("The specified object is not a selector.");
		}
		// CGLib applies the Factory interface to all enhanced proxies
		Factory factory = as(selector, Factory.class);
		SelectorMethodInterceptor interceptor = as(factory.getCallback(0), SelectorMethodInterceptor.class);
		interceptor.reset();
		return new Selector<T>(interceptor);
	}

	protected final static <T> T enhance(T instance, SelectorMethodInterceptor methodInterceptor) {
		return (T) enhanceWithInterceptor(instance.getClass(), methodInterceptor);
	}

	protected final static <T> T enhanceWithInterceptor(Class<T> clazz, SelectorMethodInterceptor methodInterceptor) {
		T enhancedInstance = (T) Enhancer.create(clazz, methodInterceptor);

		return enhancedInstance;
	}

	protected final static <T> boolean isEnhanced(T selector) {
		Factory factory = as(selector, Factory.class);
		if (factory != null) {
			Callback callback = factory.getCallback(0);
			SelectorMethodInterceptor selectorInterceptor = as(callback, SelectorMethodInterceptor.class);
			return selectorInterceptor != null;
		}
		return false;
	}

	public static Object proxy(Object value, SelectorMethodInterceptor interceptor) {
		Class<? extends Object> cls = value.getClass();
		if (String.class.isAssignableFrom(cls)) {
			return value;
		} else if (void.class.isAssignableFrom(cls)) {
			return null;
		} else if (boolean.class.isAssignableFrom(cls)) {
			return value;
		} else if (int.class.isAssignableFrom(cls)) {
			return value;
		} else if (long.class.isAssignableFrom(cls)) {
			return value;
		} else if (short.class.isAssignableFrom(cls)) {
			return value;
		} else if (double.class.isAssignableFrom(cls)) {
			return value;
		} else if (float.class.isAssignableFrom(cls)) {
			return value;
		} else if (char.class.isAssignableFrom(cls)) {
			return value;
		} else if (byte.class.isAssignableFrom(cls)) {
			return value;
		} else if (TypeUtils.isFinal(cls.getModifiers())) {
			return value;
		}
		return Selector.enhance(value, interceptor);
	}

	/**
	 * To chain methods on synthetic classes and implementations we need to
	 * guarantee that they won't cause NPEs.<br/>
	 * We enhance the return value of these methods, and return default values
	 * for basic types. Final types cannot be enhanced, and so will return null.
	 * This may cause NPEs.
	 * 
	 * @param returnType
	 * @return
	 * 
	 */
	public static Object nullsafe(Class<?> returnType, SelectorMethodInterceptor interceptor) {
		if (returnType == String.class) {
			return null;
		} else if (returnType == void.class) {
			return null;
		} else if (returnType == boolean.class) {
			return false;
		} else if (returnType == int.class) {
			return 0;
		} else if (returnType == short.class) {
			return 0;
		} else if (returnType == long.class) {
			return 0;
		} else if (returnType == float.class) {
			return 0.0;
		} else if (returnType == double.class) {
			return 0.0;
		} else if (returnType == char.class) {
			return (char) 0;
		} else if (returnType == byte.class) {
			return 0;
		} else if (TypeUtils.isFinal(returnType.getModifiers())) {
			return null;
		}
		return Selector.enhanceWithInterceptor(returnType, interceptor);
	}

	private SelectorMethodInterceptor selectorMethodInterceptor;

	Selector(SelectorMethodInterceptor stackMethodInterceptor) {
		this.selectorMethodInterceptor = stackMethodInterceptor;
	}

	public <S> InvocationTarget<T> invoke(S s) {
		LinkedHashMap<Method, Object[]> duplicateStack = new LinkedHashMap<Method, Object[]>(selectorMethodInterceptor.getInvocationStack());
		return new InvocationTarget<T>(duplicateStack);
	}

	public <S> S invokeOn(T target) {
		return (S) invoke(null).on(target);
	}

	public <S> List<S> invokeOn(T... targets) {
		return invoke(null).on(targets);
	}

	public <S> Collection<S> invokeOn(Collection<T> targets) {
		return invoke(null).on(targets);
	}

	protected final SelectorMethodInterceptor getSelectorMethodInterceptor() {
		return selectorMethodInterceptor;
	}
}
