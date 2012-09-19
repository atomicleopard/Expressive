package com.atomicleopard.expressive.filter;

/**
 * Passes any input which is not null.
 * 
 * @param <T>
 */
public class NotNullPredicate<T> implements EPredicate<T> {
	@Override
	public boolean pass(T input) {
		return input != null;
	}
}
