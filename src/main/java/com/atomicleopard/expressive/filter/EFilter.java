package com.atomicleopard.expressive.filter;

import com.atomicleopard.expressive.EList;

public interface EFilter<T> {
	public EList<T> filter(Iterable<T> iterable);
}
