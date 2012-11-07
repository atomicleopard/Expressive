package com.atomicleopard.expressive.predicate;

/**
 * <p>
 * {@link EPredicate} defines a consistent pattern for conditional processing.
 * </p>
 * 
 * @param <T>
 */
public interface EPredicate<T> {
	public boolean pass(T input);
}
