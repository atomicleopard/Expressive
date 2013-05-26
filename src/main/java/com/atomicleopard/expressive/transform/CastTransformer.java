package com.atomicleopard.expressive.transform;

import com.atomicleopard.expressive.Cast;
import com.atomicleopard.expressive.ETransformer;

/**
 * This {@link ETransformer} will cast from the type given in the constructor to the desired type.
 * If no cast can be made (i.e. a regular cast would throw a {@link NullPointerException} or the value to be transformed is null,
 * null is returned.
 * 
 * @param <From>
 * @param <To>
 */
public class CastTransformer<From, To> implements ETransformer<From, To> {
	private Class<To> type;

	public CastTransformer(Class<To> type) {
		this.type = type;
	}

	@Override
	public To from(From from) {
		return Cast.as(from, type);
	}

	@Override
	public String toString() {
		return String.format("%s to %s", this.getClass().getSimpleName(), type.getSimpleName());
	}
}
