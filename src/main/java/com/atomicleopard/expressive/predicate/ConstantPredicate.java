package com.atomicleopard.expressive.predicate;

/**
 * An {@link EPredicate} that always returns a specified value, true or false.
 * 
 * Useful in scenarios using the Null Object and Chain of Command patterns and
 * in testing.
 * 
 * @param <T>
 */
public class ConstantPredicate<T> implements EPredicate<T> {
	private boolean result = false;

	public ConstantPredicate(boolean result) {
		this.result = result;
	}

	@Override
	public boolean pass(T input) {
		return result;
	}

	@Override
	public String toString() {
		return result ? "always passes" : "never passes";
	}
}
