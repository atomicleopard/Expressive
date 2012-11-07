package com.atomicleopard.expressive.predicate;

import static com.atomicleopard.expressive.Expressive.list;

import java.util.Arrays;
import java.util.List;

/**
 * An {@link EPredicate} which will pass any input which is considered equal to
 * one of the specified objects, using the {@link Object#equals(Object)} implementation.
 * 
 * @param <T>
 */
public class AnyOfPredicate<T> implements EPredicate<T> {
	protected List<EPredicate<T>> predicates;

	public AnyOfPredicate(EPredicate<T>... predicates) {
		this(Arrays.asList(predicates));
	}

	public AnyOfPredicate(List<EPredicate<T>> predicates) {
		this.predicates = predicates;
	}

	@Override
	public boolean pass(T input) {
		for (EPredicate<T> predicate : predicates) {
			if (predicate.pass(input)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "any of " + predicates;
	}

	public AnyOfPredicate<T> or(EPredicate<T>... predicates) {
		return new AnyOfPredicate<T>(list(this.predicates).addItems(predicates));
	}

	public AnyOfPredicate<T> or(List<T> values) {
		return new AnyOfPredicate<T>(list(this.predicates).addItems(EqualsPredicate.<T> asPredicates(values)));
	}

	public AnyOfPredicate<T> or(T... values) {
		return new AnyOfPredicate<T>(list(this.predicates).addItems(EqualsPredicate.<T> asPredicates(values)));
	}
}
