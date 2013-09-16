package com.atomicleopard.expressive;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * This exists to remove the dependency Expressive had on cglib, it is not recommended to use this class, it is likely to change/disappear in the future.
 */
public class ReflectUtil {
	public static PropertyDescriptor[] getBeanGetters(Class<?> type) {
		try {
			BeanInfo info = Introspector.getBeanInfo(type, Object.class);
			PropertyDescriptor[] all = info.getPropertyDescriptors();
			List<PropertyDescriptor> properties = new ArrayList<PropertyDescriptor>(all.length);
			for (int i = 0; i < all.length; i++) {
				PropertyDescriptor pd = all[i];
				if (pd.getReadMethod() != null) {
					properties.add(pd);
				}
			}
			return (PropertyDescriptor[]) properties.toArray(new PropertyDescriptor[properties.size()]);
		} catch (IntrospectionException e) {
			throw new RuntimeException("Failed to find getters for type " + type.getName(), e);
		}
	}
}
