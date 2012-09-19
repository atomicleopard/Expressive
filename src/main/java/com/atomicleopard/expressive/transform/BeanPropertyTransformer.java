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
package com.atomicleopard.expressive.transform;

import com.atomicleopard.expressive.ETransformer;

public class BeanPropertyTransformer<V, K> extends BaseBeanPropertyTransformer<V, K> implements ETransformer<V, K> {
	public BeanPropertyTransformer(String propertyName) {
		super(propertyName);
	}

	public BeanPropertyTransformer(Class<V> clazz, String propertyName) {
		super(clazz, propertyName);
	}

	public K to(V from) {
		return from != null ? super.<K> getPropertyValue(from, getReadMethod(from)) : null;
	}
}
