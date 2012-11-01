package com.atomicleopard.expressive.comparator;

/**
 * The {@link NoopComparator} always returns 0.
 * It is useful mostly in testing scenarios.
 */
public class NoopComparator<T> extends ConstantComparator<T> {
	public NoopComparator() {
		super(0);
	}
}
