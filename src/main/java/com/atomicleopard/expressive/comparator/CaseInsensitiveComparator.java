package com.atomicleopard.expressive.comparator;

import java.util.Comparator;

public class CaseInsensitiveComparator implements Comparator<String> {
	@Override
	public int compare(String o1, String o2) {
		return o1.compareToIgnoreCase(o2);
	}
}
