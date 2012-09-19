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

/**
 * <p>
 * {@link EBidiTransformer} allows bidirectional transformation using the
 * {@link ETransformer} pattern.
 * </p>
 * 
 * @param <A>
 * @param <B>
 * 
 * @see ETransformer
 * @see CollectionTransformer
 * @see IteratorTransformer
 */
public interface EBidiTransformer<A, B> extends ETransformer<A, B> {
	/**
	 * <p>
	 * Defines a reverse transformation, that is transforms objects in the other
	 * direction than {@link #to(Object)}.
	 * </p>
	 * 
	 * @param from
	 * @return
	 */
	public A from(B from);
}
