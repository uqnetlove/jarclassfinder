package cn.jun.util;

/**
 * Jar Class Finder
 * 
 * @author Jun.Xiao
 * @since jdk1.6
 * 
 * Copyright 2012 Jun Xiao The program is distributed under the terms of the GNU
 * General Public License
 */
public class SearchValidation {
	public String validate(SearchCondition searchCondition) {
		if (searchCondition.getWantToSearch().isEmpty()) {
			return "Please input the name that you want to search";
		}
		if (searchCondition.getSearchLocation().isEmpty()) {
			return "Please input the location that you want to search";
		}
		if(!FileSearchUtils.isPathValid(searchCondition.getSearchLocation())){
			return "Please input the valid search location";
		}
		if (searchCondition.getExactlyOrFuzzySearch().equals(
				Constant.EXACTLY_SEARCH)) {
			if (searchCondition.getWantToSearch().indexOf("*") != -1) {
				return "Your input contain *, do you mean fuzzy search?";
			}
			if (searchCondition.getSearchType().equals(Constant.JAR_TYPE)) {
				if (!searchCondition.getWantToSearch().endsWith(".jar")) {
					return "Please input the valid jar name \r\n like: util.jar";
				}
			}
			if (searchCondition.getSearchType().equals(Constant.CLASS_TYPE)) {
				if (searchCondition.getWantToSearch().endsWith(".jar")
						|| searchCondition.getWantToSearch().contains(".")) {
					return "Please input the valid class name \r\n like: String";
				}
			}
			if (searchCondition.getSearchType().equals(
					Constant.PACKAGE_CLASS_TYPE)) {
				if (!searchCondition.getWantToSearch().contains(".")
						&& !searchCondition.getWantToSearch().contains("/")
						&& !searchCondition.getWantToSearch().contains("\\")) {
					return "Please input the valid package.class name \r\n like: java.lang.String java/lang/String java\\lang\\String";
				}
			}
		} else {
			if (!searchCondition.getWantToSearch().contains("*")) {
				if (searchCondition.getSearchType().equals(Constant.JAR_TYPE)) {
					return "Please input the valid jar name \r\n like: *.jar";
				}
				if (searchCondition.getSearchType().equals(Constant.CLASS_TYPE)) {
					return "Please input the valid class name \r\n like: Str*ing";
				}
				if (searchCondition.getSearchType().equals(
						Constant.PACKAGE_CLASS_TYPE)) {
					return "Please input the valid package.class name \r\n like: java.*.String";
				}
			}
		}
		return "";
	}
}
