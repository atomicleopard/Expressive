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

public class EnumToStringTransformerTest {

	@Test
	public void shouldConvertEnumValToStringUsingName() {
		EnumToStringTransformer<TestEnum> transformer = new EnumToStringTransformer<TestEnum>();
		assertThat(transformer.to(TestEnum.TestVal1), is("TestVal1"));
		assertThat(transformer.to(TestEnum.TestVal2), is("TestVal2"));
		assertThat(transformer.to(null), is(nullValue()));
	}

	public static enum TestEnum {
		TestVal1,
		TestVal2;
	}

}
