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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

@SuppressWarnings("unchecked")
public class InvocationTarget<T> {

	private LinkedHashMap<Method, Object[]> invocationStack;

	public InvocationTarget(LinkedHashMap<Method, Object[]> invocationStack) {
		this.invocationStack = invocationStack;
	}

	public <S> S on(T target) {
		Object current = target;
		try {
			for (Entry<Method, Object[]> entry : invocationStack.entrySet()) {
				Method method = entry.getKey();
				current = method.invoke(current, entry.getValue());
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Unable to perform action on target: " + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Unable to perform action on target: " + e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Unable to perform action on target: " + e.getMessage(), e);
		}
		return (S) current;
	}

	public <S> List<S> on(T... targets) {
		return on(Arrays.asList(targets));
	}

	public <S> Collection<S> on(Collection<T> targets) {
		List<S> results = new ArrayList<S>(targets.size());
		for (T t : targets) {
			results.add((S) on(t));
		}
		return results;
	}

	public <S> List<S> on(List<T> targets) {
		List<S> results = new ArrayList<S>(targets.size());
		for (T t : targets) {
			results.add((S) on(t));
		}
		return results;
	}
}
