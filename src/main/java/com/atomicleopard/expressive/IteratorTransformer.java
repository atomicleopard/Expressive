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

import java.util.Iterator;

/**
 * <p>
 * {@link IteratorTransformer} can be used to effectively iterate over an
 * {@link Iterator} and convert the items to another type. It acts as an adapter
 * between the supplied iterator and the desired iterator.
 * </p>
 * <p>
 * {@link IteratorTransformer} supports {@link #remove()} if the supplied
 * iterator supports it, and will remove items from the underlying iterator.
 * </p>
 * 
 * @param <In>
 * @param <Out>
 */
public class IteratorTransformer<In, Out> implements Iterator<Out> {

	private ETransformer<In, Out> transformer;
	private Iterator<In> iterator;

	public IteratorTransformer(ETransformer<In, Out> transformer, Iterator<In> iterator) {
		this.iterator = iterator;
		this.transformer = transformer;
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public Out next() {
		return transformer.to(iterator.next());
	}

	public void remove() {
		iterator.remove();
	}

}
