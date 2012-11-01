package com.atomicleopard.expressive.filter;

import static com.atomicleopard.expressive.Expressive.list;

import java.util.List;

public class AndPredicate<T> implements EPredicate<T> {
	protected List<EPredicate<T>> predicates;

	@SuppressWarnings("unchecked")
	public AndPredicate(EPredicate<T> predicate) {
		this.predicates = list(predicate);
	}

	public AndPredicate(List<EPredicate<T>> predicates) {
		this.predicates = predicates;
	}

	@Override
	public boolean pass(T input) {
		for (EPredicate<T> predicate : predicates) {
			if (!predicate.pass(input)) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public AndPredicate<T> and(EPredicate<T> predicate) {
		return new AndPredicate<T>(list(predicates).addItems(predicate));
	}
}
