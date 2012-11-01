package com.atomicleopard.expressive.comparator;

import java.util.Arrays;
import java.util.Comparator;

public class EComparators {

	public static <T> ComparatorBuilder<T> compare(Class<T> type) {
		return new ComparatorBuilder<T>(type);
	}

	public static <T> Comparator<T> nullSafe(Comparator<T> delegate) {
		return nullSafe(delegate, true);
	}

	public static <T> Comparator<T> nullSafe(Comparator<T> delegate, boolean nullLast) {
		return new NullsafeComparator<T>(delegate, nullLast);
	}

	public static Comparator<String> caseInsensitive() {
		return new CaseInsensitiveComparator();
	}

	public static <T extends Comparable<T>> Comparator<T> as(Class<T> comparable) {
		return new ComparableComparator<T>();
	}

	public static <T> Comparator<T> all(Comparator<T>... comparators) {
		return new CompositeComparator<T>(Arrays.asList(comparators));
	}

	public static <T> Comparator<T> all(Iterable<Comparator<T>> comparators) {
		return new CompositeComparator<T>(comparators);
	}
}
