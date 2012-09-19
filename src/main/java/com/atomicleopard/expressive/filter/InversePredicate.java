package com.atomicleopard.expressive.filter;

public class InversePredicate<T> implements EPredicate<T> {
	private EPredicate<T> predicate;

	public InversePredicate(EPredicate<T> predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean pass(T input) {
		return !predicate.pass(input);
	}
}
