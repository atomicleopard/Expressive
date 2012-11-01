package com.atomicleopard.expressive.filter;

import static com.atomicleopard.expressive.Expressive.list;

import java.util.Collection;

import com.atomicleopard.expressive.EList;
import com.atomicleopard.expressive.collection.Pair;

public class EFilters {
	public static <T> EFilter<T> retain(EPredicate<T> predicate) {
		return new EFilterImpl<T>(predicate);
	}

	public static <T> EFilter<T> remove(EPredicate<T> predicate) {
		return retain(EPredicates.not(predicate));
	}

	public static <T> EList<T> filter(Collection<T> items, EPredicate<T> predicate) {
		return retain(predicate).filter(items);
	}

	public static <T> Pair<EList<T>, EList<T>> split(Collection<T> items, EFilter<T> filter) {
		EList<T> first = filter.filter(items);
		EList<T> second = list(items);
		second.removeAll(first);
		return new Pair<EList<T>, EList<T>>(first, second);
	}

	public static <T> Pair<EList<T>, EList<T>> split(Collection<T> items, EPredicate<T> predicate) {
		EFilter<T> filter = retain(predicate);
		return split(items, filter);
	}
}
