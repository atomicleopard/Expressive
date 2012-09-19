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

import static com.atomicleopard.expressive.Expressive.mapKeys;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

public class MappingTransformerTest {
	@Test
	public void shouldTransformUsingLookup() {
		Map<String, Integer> lookup = mapKeys("one", "two", "three").to(1, 2, 3);
		MappingTransformer<String, Integer> transformer = new MappingTransformer<String, Integer>(lookup);
		assertThat(transformer.to("one"), is(1));
		assertThat(transformer.to("two"), is(2));
		assertThat(transformer.to("three"), is(3));
		assertThat(transformer.to("four"), is(nullValue()));
		assertThat(transformer.to(null), is(nullValue()));
	}

}
