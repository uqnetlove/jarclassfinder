package cn.jun.util;

/**
 * Jar Class Finder
 * 
 * @author Jun.Xiao
 * @since jdk1.6
 * 
 * Copyright 2012 Jun Xiao 
 * The program is distributed under the terms of the GNU General Public License
 */
public class SearchFactory {
	private static SearchUtils searchUtils;

	private SearchFactory() {

	}

	public static SearchUtils getInstance(String exactlyOrFuzzySesarch) {
		if (exactlyOrFuzzySesarch.equals(Constant.EXACTLY_SEARCH)) {
			if (searchUtils == null
					|| searchUtils.getExactlyOrFuzzySearch().equals(
							Constant.FUZZY_SEARCH)) {
				searchUtils = new FileSearchUtils();
			}
		}
		if (exactlyOrFuzzySesarch.equals(Constant.FUZZY_SEARCH)) {
			if (searchUtils == null
					|| searchUtils.getExactlyOrFuzzySearch().equals(
							Constant.EXACTLY_SEARCH)) {
				searchUtils = new FileFuzzySearchUtils();
			}
		}
		return searchUtils;
	}

	public static SearchUtils getMultiInstance(String exactlyOrFuzzySesarch) {
		if (exactlyOrFuzzySesarch.equals(Constant.EXACTLY_SEARCH)) {
			searchUtils = new FileSearchUtils();
		}
		if (exactlyOrFuzzySesarch.equals(Constant.FUZZY_SEARCH)) {
			searchUtils = new FileFuzzySearchUtils();
		}
		return searchUtils;
	}
}
