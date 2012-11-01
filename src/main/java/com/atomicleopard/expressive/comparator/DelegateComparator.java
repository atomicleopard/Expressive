package com.atomicleopard.expressive.comparator;

import java.util.Comparator;

public abstract class DelegateComparator<T> implements Comparator<T> {
	private Comparator<T> delegate;

	public DelegateComparator(Comparator<T> delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public int compare(T o1, T o2) {
		return delegate.compare(o1, o2);
	}
}
