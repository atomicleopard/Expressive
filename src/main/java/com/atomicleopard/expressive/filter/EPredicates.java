package com.atomicleopard.expressive.filter;

public class EPredicates {
	public static <T> EPredicate<T> all() {
		return new ConstantPredicate<T>(true);
	}

	public static <T> EPredicate<T> none() {
		return new ConstantPredicate<T>(false);
	}

	public static <T> EPredicate<T> is(T object) {
		return new EqualsPredicate<T>(object);
	}

	public static <T> EPredicate<T> notNull() {
		return new NotNullPredicate<T>();
	}

	public static <T> EPredicate<T> not(EPredicate<T> predicate) {
		return new InversePredicate<T>(predicate);
	}

	public static <T> AndPredicate<T> chain(EPredicate<T> predicate) {
		return new AndPredicate<T>(predicate);
	}
}
