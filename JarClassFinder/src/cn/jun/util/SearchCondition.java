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
public class SearchCondition implements Cloneable{
	private String wantToSearch;

	private String searchLocation;

	private String searchType;

	private String exactlyOrFuzzySearch;

	public String getExactlyOrFuzzySearch() {
		return exactlyOrFuzzySearch;
	}

	public void setExactlyOrFuzzySearch(String exactlyOrFuzzySearch) {
		this.exactlyOrFuzzySearch = exactlyOrFuzzySearch;
	}

	public String getSearchLocation() {
		return searchLocation;
	}

	public void setSearchLocation(String searchLocation) {
		this.searchLocation = searchLocation;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getWantToSearch() {
		return wantToSearch;
	}

	public void setWantToSearch(String wantToSearch) {
		this.wantToSearch = wantToSearch;
	}

	public boolean isReady() {
		if (wantToSearch != null && searchLocation != null
				&& searchType != null) {
			return true;
		}
		return false;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
