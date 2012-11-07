package com.atomicleopard.expressive.predicate;

import java.util.List;

import com.atomicleopard.expressive.EList;
import com.atomicleopard.expressive.ETransformer;
import com.atomicleopard.expressive.Expressive;
import com.atomicleopard.expressive.transform.CollectionTransformer;

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
		return value == null ? input == null : value.equals(input);
	}

	@Override
	public String toString() {
		return "is " + value;
	}

	public static <T> EList<EPredicate<T>> asPredicates(T... values) {
		return EqualsPredicate.Transformer.<T> ForValues().to(values);
	}

	public static <T> EList<EPredicate<T>> asPredicates(List<T> values) {
		return EqualsPredicate.Transformer.<T> ForValues().to(values);
	}

	public static final class Transformer {
		Transformer() {

		}

		public static <T> ETransformer<T, EPredicate<T>> ForValue() {
			return new ETransformer<T, EPredicate<T>>() {
				@Override
				public EPredicate<T> to(T from) {
					return new EqualsPredicate<T>(from);
				}
			};
		}

		public static <T> CollectionTransformer<T, EPredicate<T>> ForValues() {
			return Expressive.Transformers.transformAllUsing(Transformer.<T> ForValue());
		}
	}
}
