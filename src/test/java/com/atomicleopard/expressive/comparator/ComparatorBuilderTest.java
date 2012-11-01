package com.atomicleopard.expressive.comparator;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.Comparator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class ComparatorBuilderTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldBuildAComparatorWeightingComparatorsBasedOnBuildOrder() {
		ComparatorBuilder<TestCompare> originalBuilder = new ComparatorBuilder<TestCompare>(TestCompare.class);
		ComparatorBuilder<TestCompare> builder = originalBuilder.on("str1").naturally();
		builder = builder.on("str2").naturally();
		builder = builder.on("int1").naturally();
		builder = builder.on("dbl1").naturally();
		assertThat(builder, sameInstance(originalBuilder));

		assertThat(builder.compare(tc("a", "b", 1, 2d), tc("a", "b", 1, 2d)), is(0));

		assertThat(builder.compare(tc("a", "b", 1, 3d), tc("a", "b", 1, 2d)), is(1));
		assertThat(builder.compare(tc("a", "b", 1, 2d), tc("a", "b", 1, 3d)), is(-1));

		assertThat(builder.compare(tc("a", "b", 2, 2d), tc("a", "b", 1, 2d)), is(1));
		assertThat(builder.compare(tc("a", "b", 1, 2d), tc("a", "b", 2, 2d)), is(-1));

		assertThat(builder.compare(tc("a", "c", 1, 2d), tc("a", "b", 2, 2d)), is(1));
		assertThat(builder.compare(tc("a", "b", 1, 2d), tc("a", "c", 2, 2d)), is(-1));

		assertThat(builder.compare(tc("b", "b", 1, 2d), tc("a", "b", 1, 2d)), is(1));
		assertThat(builder.compare(tc("a", "b", 1, 2d), tc("b", "b", 1, 2d)), is(-1));

		assertThat(builder.compare(tc("b", "b", 1, 2d), tc("a", "b", 1, 10000000d)), is(1));
		assertThat(builder.compare(tc("a", "b", 1, 10000000d), tc("b", "b", 1, 3d)), is(-1));

		assertThat(builder.compare(tc("b", "b", 1, 2d), tc("a", "bbbbb", 100000, 200000d)), is(1));
		assertThat(builder.compare(tc("a", "c", 1, 2d), tc("a", "bbbbb", 100000, 200000d)), is(1));
	}

	@Test
	public void shouldBuildAComparatorUsingSpecifiedComparatorsOnProperties() {
		ComparatorBuilder<TestCompare> builder = new ComparatorBuilder<TestCompare>(TestCompare.class);
		builder.<String> on("str1").using(new NoopComparator<String>());
		builder.<String> on("str2").using(new CaseInsensitiveComparator());
		builder.<Integer> on("int1").using(new ComparableComparator<Integer>());
		builder.<Double> on("dbl1").using(new ConstantComparator<Double>(100));

		assertThat(builder.compare(tc("a", "a", 0, 0d), tc("a", "a", 0, 0d)), is(100));
		// constant comparator on dbl1 means that the value is irrelevant
		assertThat(builder.compare(tc("a", "a", 0, 1d), tc("a", "a", 0, 0d)), is(100));
		assertThat(builder.compare(tc("a", "a", 0, 1d), tc("a", "a", 0, 0d)), is(100));
		assertThat(builder.compare(tc("a", "a", 0, 0d), tc("a", "a", 0, 1d)), is(100));
		// this will compare on the natural value of in1
		assertThat(builder.compare(tc("a", "a", 1, 0d), tc("a", "a", 0, 0d)), is(1));
		assertThat(builder.compare(tc("a", "a", 0, 0d), tc("a", "a", 1, 0d)), is(-1));
		assertThat(builder.compare(tc("a", "a", 1, 0d), tc("a", "a", 1, 0d)), is(100));

		// this will compare on str2 in a case insensitive fashion
		assertThat(builder.compare(tc("a", "a", 0, 0d), tc("a", "a", 0, 0d)), is(100));
		assertThat(builder.compare(tc("a", "A", 0, 0d), tc("a", "a", 0, 0d)), is(100));
		assertThat(builder.compare(tc("a", "a", 0, 0d), tc("a", "A", 0, 0d)), is(100));
		assertThat(builder.compare(tc("a", "A", 0, 0d), tc("a", "A", 0, 0d)), is(100));

		// this will compare on str1, which is always noop, so it will fallback to later comparators
		assertThat(builder.compare(tc("AAAA", "a", 0, 0d), tc("a", "a", 0, 0d)), is(100));
		assertThat(builder.compare(tc("AAAA", "b", 0, 0d), tc("a", "a", 0, 0d)), is(1));
		assertThat(builder.compare(tc("AAAA", "a", 1, 0d), tc("a", "A", 0, 0d)), is(1));
		assertThat(builder.compare(tc("AAAA", "A", 0, 0d), tc("a", "A", 0, 0d)), is(100));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldThrowARuntimeExceptionIfAnyComparatorFails() {
		thrown.expect(RuntimeException.class);

		Comparator<Double> throwComparator = mock(Comparator.class);
		when(throwComparator.compare(Mockito.any(Double.class), Mockito.any(Double.class))).thenThrow(new ArrayIndexOutOfBoundsException());

		ComparatorBuilder<TestCompare> builder = new ComparatorBuilder<TestCompare>(TestCompare.class);
		builder.<String> on("str1").using(new NoopComparator<String>());
		builder.<String> on("str2").using(new CaseInsensitiveComparator());
		builder.<Integer> on("int1").using(new ComparableComparator<Integer>());
		builder.<Double> on("dbl1").using(throwComparator);

		builder.compare(tc("a", "a", 0, 0d), tc("a", "a", 0, 0d));
	}

	@Test
	public void shouldThrowExceptionWhenAttemptingToCreateComparisonOnANonPropertyValue() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Unable to compare com.atomicleopard.expressive.comparator.ComparatorBuilderTest$TestCompare on the property doesntExist, there is no accessible bean property with that name");
		ComparatorBuilder<TestCompare> builder = new ComparatorBuilder<TestCompare>(TestCompare.class);
		builder.<String> on("doesntExist").using(new NoopComparator<String>());
	}

	@Test
	public void shouldBuildAComparatorWithoutCachingProperties() {
		ComparatorBuilder<TestCompare> builder = new ComparatorBuilder<TestCompare>(TestCompare.class, true);
		builder.<String> on("str1").using(new NoopComparator<String>());
		builder.<String> on("str2").using(new CaseInsensitiveComparator());

		assertThat(builder.compare(tc("a", "a", 0, 0d), tc("a", "a", 0, 0d)), is(0));
		assertThat(builder.compare(tc("a", "a", 0, 0d), tc("a", "A", 0, 0d)), is(0));
		assertThat(builder.compare(tc("a", "a", 0, 0d), tc("a", "B", 0, 0d)), is(-1));
	}

	private TestCompare tc(String str1, String str2, Integer int1, Double dbl1) {
		return new TestCompare(str1, str2, int1, dbl1);
	}

	@SuppressWarnings("unused")
	private static class TestCompare {
		private String str1;
		private String str2;
		private Integer int1;
		private Double dbl1;

		public TestCompare(String str1, String str2, Integer int1, Double dbl1) {
			super();
			this.str1 = str1;
			this.str2 = str2;
			this.int1 = int1;
			this.dbl1 = dbl1;
		}

		public String getStr1() {
			return str1;
		}

		public String getStr2() {
			return str2;
		}

		public Integer getInt1() {
			return int1;
		}

		public Double getDbl1() {
			return dbl1;
		}
	}
}
