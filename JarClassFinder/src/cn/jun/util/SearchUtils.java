package cn.jun.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

/**
 * Jar Class Finder
 * 
 * @author Jun.Xiao
 * @since jdk1.6
 * 
 * Copyright 2012 Jun Xiao The program is distributed under the terms of the GNU
 * General Public License
 */
public abstract class SearchUtils {

	public static double startTime;

	public static double endTime;

	public static String progressText = "";

	public static boolean needRun;

	public static boolean needSuspend;

	public static boolean needStop;

	public static boolean anyMatchedRecord;

	SearchCondition searchCondition;

	public static Vector<JarBean> allFoundJars = new Vector<JarBean>();

	public abstract String getExactlyOrFuzzySearch();

	public abstract void look();

	public Vector<JarBean> getAllFoundJars() {
		return allFoundJars;
	}

	public static void clearLastRecords() {
		if (!allFoundJars.isEmpty()) {
			allFoundJars.clear();
		}
	}

	// public static void totalFiles(String searchLocation) {
	// File file = new File(searchLocation);
	// if (file == null) {
	// return;
	// } else {
	// if (file.isFile()) {
	// count++;
	// } else if (file.isDirectory()) {
	// File[] all = file.listFiles();
	// if (all != null) {
	// for (File f : all) {
	// if (f.isFile()) {
	// count++;
	// } else {
	// totalFiles(f.getAbsolutePath());
	// }
	// }
	// }
	// }
	// }
	// }

	public SearchCondition getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(SearchCondition searchCondition) {
		this.searchCondition = searchCondition;
	}
}

class JarFileFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
		File temp = new File(dir.getPath() + File.separator + name);
		if (temp.isDirectory()) {
			return true;
		} else if (temp.isFile()) {
			if (name.toLowerCase().endsWith(".jar")) {
				return true;
			}
		}
		return false;
	}
}
