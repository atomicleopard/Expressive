package com.atomicleopard.expressive.predicate;

import static com.atomicleopard.expressive.Expressive.list;

import java.util.List;

public class AllOfPredicate<T> implements EPredicate<T> {
	protected List<EPredicate<T>> predicates;

	public AllOfPredicate(EPredicate<T>... predicates) {
		this.predicates = list(predicates);
	}

	public AllOfPredicate(List<EPredicate<T>> predicates) {
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

	@Override
	public String toString() {
		return "all of " + predicates;
	}

	public AllOfPredicate<T> and(EPredicate<T>... predicates) {
		return new AllOfPredicate<T>(list(this.predicates).addItems(predicates));
	}

	public AllOfPredicate<T> and(List<T> values) {
		return new AllOfPredicate<T>(list(this.predicates).addItems(EqualsPredicate.asPredicates(values)));
	}

	public AllOfPredicate<T> and(T... values) {
		return new AllOfPredicate<T>(list(this.predicates).addItems(EqualsPredicate.asPredicates(values)));
	}
}
