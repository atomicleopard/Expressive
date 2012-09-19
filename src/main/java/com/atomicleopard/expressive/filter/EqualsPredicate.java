package com.atomicleopard.expressive.filter;

/**
 * An {@link EPredicate} which will pass any input which is considered equal to
 * the specified object, using the {@link Object#equals(Object)} implementation.
 * 
 * @param <T>
 */
public class EqualsPredicate<T> implements EPredicate<T> {
	private T value;

	public EqualsPredicate(T value) {
		this.value = value;
	}

	@Override
	public boolean pass(T input) {
		return value.equals(input);
	}
}
