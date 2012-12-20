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

public class EnumFromStringTransformerTest {

	@Test
	public void shouldConvertEnumValToStringUsingName() {
		EnumFromStringTransformer<TestEnum> transformer = new EnumFromStringTransformer<TestEnum>(TestEnum.class);
		assertThat(transformer.from("TestVal1"), is(TestEnum.TestVal1));
		assertThat(transformer.from("TestVal2"), is(TestEnum.TestVal2));
		assertThat(transformer.from(null), is(nullValue()));
		assertThat(transformer.from("testVal2"), is(nullValue()));
		assertThat(transformer.from("junk"), is(nullValue()));
	}

	public static enum TestEnum {
		TestVal1,
		TestVal2;
	}
}
