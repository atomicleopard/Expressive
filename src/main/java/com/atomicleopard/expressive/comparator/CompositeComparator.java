package com.atomicleopard.expressive.comparator;

import java.util.Comparator;

import com.atomicleopard.expressive.EList;
import com.atomicleopard.expressive.Expressive;

public class CompositeComparator<T> implements Comparator<T> {
	private EList<Comparator<T>> comparators;

	public CompositeComparator(Iterable<Comparator<T>> comparators) {
		this.comparators = Expressive.list(comparators);
	}

	@Override
	public int compare(T o1, T o2) {
		for (Comparator<T> comparator : comparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}
