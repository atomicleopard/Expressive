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
package com.atomicleopard.expressive.transform;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ObjectToStringTransformerTest {

	@Test
	public void shouldConvertObjectToString() {
		ObjectToStringTransformer<Integer> transformer = new ObjectToStringTransformer<Integer>();
		assertThat(transformer.from(123), is("123"));
		assertThat(transformer.from(456), is("456"));
		assertThat(transformer.from(null), is(nullValue()));
	}

	@Test
	public void shouldConvertDifferentObjectToString() {

		ObjectToStringTransformer<String> transformer = new ObjectToStringTransformer<String>();
		assertThat(transformer.from("String"), is("String"));
		assertThat(transformer.from("any text at all"), is("any text at all"));
		assertThat(transformer.from(null), is(nullValue()));
	}

}
