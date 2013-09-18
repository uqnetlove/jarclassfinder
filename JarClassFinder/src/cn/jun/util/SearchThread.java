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
public class SearchThread extends Thread {
	private SearchCondition searchCondition;

	public SearchThread(SearchCondition searchCondition) {
		this.searchCondition = searchCondition;
	}

	public void run() {
		// FileSearchUtils fsu = new FileSearchUtils(searchCondition);
		SearchUtils fsu = SearchFactory.getInstance(searchCondition
				.getExactlyOrFuzzySearch());
		fsu.setSearchCondition(searchCondition);
		SearchUtils.clearLastRecords();
		fsu.look();
	}
}
