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
package com.atomicleopard.selector;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import net.sf.cglib.proxy.MethodProxy;

public class ProxyingStackMethodInterceptor implements SelectorMethodInterceptor {
	private LinkedHashMap<Method, Object[]> invocationStack;
	private Object instance;

	public ProxyingStackMethodInterceptor(Object instance) {
		this(instance, null);
	}

	public ProxyingStackMethodInterceptor(Object instance, LinkedHashMap<Method, Object[]> invocationStack) {
		this.instance = instance;
		this.invocationStack = invocationStack == null ? new LinkedHashMap<Method, Object[]>() : invocationStack;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		invocationStack.put(method, args);
		Class<?> returnType = method.getReturnType();
		Object result = method.invoke(instance, args);

		return result == null ? Selector.nullsafe(returnType, new StackMethodInterceptor(invocationStack)) : Selector.proxy(result, new ProxyingStackMethodInterceptor(result, invocationStack));
	}

	public void reset() {
		invocationStack.clear();
	}

	public LinkedHashMap<Method, Object[]> getInvocationStack() {
		return invocationStack;
	}
}
