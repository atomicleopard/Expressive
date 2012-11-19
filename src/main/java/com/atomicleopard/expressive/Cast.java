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

import java.util.Arrays;

/**
 * Utility class which provides casting operations.
 * 
 * <p>
 * {@link #as(Object, Class)} performs a cast operation, returning null if a
 * cast is not valid. <br/>
 * This allows illegal cast syntax to be more elegant and brief - a null check
 * as opposed to a try/catch.<br/>
 * </p>
 * <p>
 * {@link #is(Object, Class)} and its variants allow for quick type checking.
 * Usually {@link #as(Object, Class)} allows for more succinct code, but there
 * are scenarios where type, or multiple type checking are useful.
 * </p>
 * 
 */
@SuppressWarnings("unchecked")
public final class Cast {
	Cast() {
	}

	/**
	 * <p>
	 * Performs a cast operation of the parameter t to the type defined by the
	 * class s, or null if no legal cast can be made.
	 * </p>
	 * <p>
	 * This allows illegal cast syntax to be more elegant and brief - a null
	 * check as opposed to a try/catch.
	 * </p>
	 * <p>
	 * Returns null if t is null, in this case no type can be inferred.
	 * </p>
	 * 
	 * @param <S>
	 * @param <T>
	 * @param t
	 *            object to cast
	 * @param s
	 *            Class to cast the parameter t to
	 * @return t cast to the type of S, or null if this is not a legal cast.
	 */
	public static <S, T> S as(T t, Class<S> s) {
		return is(t, s) ? (S) t : null;
	}

	/**
	 * @param t
	 *            instance who's type is to be tested
	 * @param s
	 *            a type as a {@link Class}
	 * @return true if the given t is of type s, that is a legal cast from T to
	 *         S can occur.
	 */
	public static <S, T> boolean is(T t, Class<S> s) {
		return t != null && s.isAssignableFrom(t.getClass());
	}

	/**
	 * @param t
	 *            instance who's type is to be tested
	 * @param s
	 *            one or more {@link Class}es
	 * @return true if t is of any of the types in the varargs s, that is a
	 *         legal cast can be made from T to at least one of the types
	 *         contained in s
	 */
	public static <T> boolean is(T t, Class<?>... s) {
		return is(t, Arrays.asList(s));
	}

	/**
	 * @param t
	 *            instance who's type is to be tested
	 * @param s
	 *            one or more {@link Class}es
	 * @return true if t is of any of the types in the varargs s, that is a
	 *         legal cast can be made from T to at least one of the types
	 *         contained in s
	 */
	public static <T> boolean is(T t, Iterable<Class<?>> s) {
		if (t != null) {
			for (Class<?> class1 : s) {
				if (class1.isAssignableFrom(t.getClass())) {
					return true;
				}
			}
		}
		return false;
	}
}
