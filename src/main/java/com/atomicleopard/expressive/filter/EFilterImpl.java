package com.atomicleopard.expressive.filter;

import com.atomicleopard.expressive.EList;
import com.atomicleopard.expressive.EListImpl;

public class EFilterImpl<T> implements EFilter<T> {
	private EPredicate<T> predicate;

	public EFilterImpl(EPredicate<T> predicate) {
		this.predicate = predicate;
	}

	@Override
	public EList<T> filter(Iterable<T> iterable) {
		EList<T> result = new EListImpl<T>();
		for (T t : iterable) {
			if (predicate.pass(t)) {
				result.add(t);
			}
		}
		return result;
	}
}
