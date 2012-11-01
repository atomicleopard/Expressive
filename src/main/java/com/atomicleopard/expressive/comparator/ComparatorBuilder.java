package com.atomicleopard.expressive.comparator;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.cglib.core.ReflectUtils;

import com.atomicleopard.expressive.ETransformer;
import com.atomicleopard.expressive.transform.ETransformers;

public class ComparatorBuilder<T> implements Comparator<T> {
	private LinkedHashMap<String, Comparator<?>> propertyComparators = new LinkedHashMap<String, Comparator<?>>();
	private Class<T> type;
	private Map<String, PropertyDescriptor> getters;

	private static Map<Class<?>, Map<String, PropertyDescriptor>> PropertyDescriptorCache = new HashMap<Class<?>, Map<String, PropertyDescriptor>>();
	private static ETransformer<Collection<PropertyDescriptor>, Map<String, PropertyDescriptor>> PropertyDescriptorLookupTransformer = ETransformers.toKeyBeanLookup("name", PropertyDescriptor.class);

	public ComparatorBuilder(Class<T> type) {
		this(type, false);
	}

	public ComparatorBuilder(Class<T> type, boolean noCache) {
		this.type = type;
		this.getters = getPropertyDescriptions(type, noCache);
	}

	protected void put(String property, Comparator<?> comparator) {
		if (!this.getters.containsKey(property)) {
			throw new RuntimeException(String.format("Unable to compare %s on the property %s, there is no accessible bean property with that name", type.getName(), property));
		}
		propertyComparators.put(property, comparator);
	}

	public <S> CompareUsing<S> on(String property) {
		return new CompareUsing<S>(property);
	}

	/**
	 * We cache property descriptors for types
	 * 
	 * @param type
	 * @param noCache
	 * @return
	 */
	private static Map<String, PropertyDescriptor> getPropertyDescriptions(Class<?> type, boolean noCache) {
		Map<String, PropertyDescriptor> existing = noCache ? null : PropertyDescriptorCache.get(type);
		if (existing == null) {
			PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(type);
			existing = PropertyDescriptorLookupTransformer.to(Arrays.asList(getters));
			if (!noCache) {
				PropertyDescriptorCache.put(type, existing);
			}
		}
		return existing;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int compare(T o1, T o2) {
		try {
			for (Map.Entry<String, Comparator<?>> entry : propertyComparators.entrySet()) {
				String property = entry.getKey();
				Object l = getters.get(property).getReadMethod().invoke(o1);
				Object r = getters.get(property).getReadMethod().invoke(o2);
				Comparator comparator = entry.getValue();
				int value = comparator.compare(l, r);
				if (value != 0) {
					return value;
				}
			}
			return 0;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public class CompareUsing<S> {
		private String property;

		public CompareUsing(String property) {
			this.property = property;
		}

		public ComparatorBuilder<T> using(Comparator<S> propertyComparator) {
			put(property, propertyComparator);
			return ComparatorBuilder.this;
		}

		public <C extends Comparable<C>> ComparatorBuilder<T> naturally() {
			put(property, new ComparableComparator<C>());
			return ComparatorBuilder.this;
		}
	}
}
