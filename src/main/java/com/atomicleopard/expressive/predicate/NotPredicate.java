package com.atomicleopard.expressive.predicate;

public class NotPredicate<T> implements EPredicate<T> {
	private EPredicate<T> predicate;

	public NotPredicate(EPredicate<T> predicate) {
		if (predicate == null) {
			throw new NullPointerException(String.format("Null %s passed to the %s constructor", EPredicate.class.getSimpleName(), NotPredicate.class.getSimpleName()));
		}
		this.predicate = predicate;
	}

	@Override
	public boolean pass(T input) {
		return !predicate.pass(input);
	}

	@Override
	public String toString() {
		return "not " + predicate;
	}
}
