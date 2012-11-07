package com.atomicleopard.expressive.predicate;

/**
 * Passes any input which is null.
 * 
 * @param <T>
 */
public class NullPredicate<T> extends EqualsPredicate<T> {
	public NullPredicate() {
		super(null);
	}
	
	@Override
	public String toString() {
		return "is null";
	}
}
