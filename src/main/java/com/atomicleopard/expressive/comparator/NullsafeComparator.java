package com.atomicleopard.expressive.comparator;

import java.util.Comparator;

/**
 * A comparator which allows one or both arguments to be null.
 * Whether a single null argument comes before or after a non-null (i.e. -1 or 1) is controlled at construction time.
 * 
 * @param <T>
 */
public class NullsafeComparator<T> extends DelegateComparator<T> {
	private int nullLast = 1;

	public NullsafeComparator(Comparator<T> delegate, boolean nullLast) {
		super(delegate);
		this.nullLast = nullLast ? -1 : 1;
	}

	@Override
	public int compare(T arg0, T arg1) {
		return arg0 != null && arg1 != null ? super.compare(arg0, arg1) : nullComparison(arg0, arg1);
	}

	private int nullComparison(T arg0, T arg1) {
		if (arg0 == arg1) {
			return 0;
		}
		if (arg0 == null) {
			return nullLast;
		} else {
			return -1 * nullLast;
		}
	}
}
