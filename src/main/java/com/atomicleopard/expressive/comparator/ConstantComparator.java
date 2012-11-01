package com.atomicleopard.expressive.comparator;

import java.util.Comparator;

public class ConstantComparator<T> implements Comparator<T> {
	private int result;

	public ConstantComparator(int result) {
		this.result = result;
	}

	@Override
	public int compare(T o1, T o2) {
		return result;
	}
}
