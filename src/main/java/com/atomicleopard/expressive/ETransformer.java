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

import com.atomicleopard.expressive.transform.CollectionTransformer;

/**
 * <p>
 * {@link ETransformer} defines a consistent pattern for converting an object of
 * one type to an object of another.
 * </p>
 * <p>
 * By utilising this interface, assisting classes such as
 * {@link CollectionTransformer} and {@link IteratorTransformer} can help
 * succinctly transform collections of objects.
 * </p>
 * <p>
 * In circumstances where a transformation requires another object instance
 * (such as a lookup table, service, resource etc), these can be injected into
 * the implementing class using the constructor. Ideally {@link ETransformer}s
 * should be stateless, which enables them to be threadsafe and freely reusable.
 * </p>
 * <p>
 * In the case that to directional transformations are required,
 * {@link EBidiTransformer} should be used.
 * </p>
 * 
 * @param <From>
 * @param <To>
 * 
 * @see EBidiTransformer
 * @see CollectionTransformer
 * @see IteratorTransformer
 */
public interface ETransformer<From, To> {
	/**
	 * Defines a transformation from one object to another.
	 * 
	 * @param from
	 * @return
	 */
	public To from(From from);
}
