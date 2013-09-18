package cn.jun.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import cn.jun.ui.ProgressFrame;

/**
 * Jar Class Finder
 * 
 * @author Jun.Xiao
 * @since jdk1.6
 * 
 * Copyright 2012 Jun Xiao 
 * The program is distributed under the terms of the GNU General Public License
 */
public class CheckForUpdate {
	private float lastestVersion;

	private String serverURL = "http://sourceforge.net/projects/jarclassfinder";

	private String checkMark = "small title";

	private ProgressFrame progressFrame;

	public CheckForUpdate(ProgressFrame progressFrame) {
		this.progressFrame = progressFrame;
	}

	public String alertMessage() {
		try {
			if (isUpToDate()) {
				progressFrame.setNeedDisplay(false);
				return "the version is up to date";
			} else {
				progressFrame.setNeedDisplay(false);
				return "you can update to " + lastestVersion;
			}
		} catch (Exception e) {
			progressFrame.setNeedDisplay(false);
			return "-_-!, network error, please try again later.";
		}
	}

	private boolean isUpToDate() throws IOException {
		if (getServerVersion() > getCurrentVersion()) {
			return false;
		}
		return true;
	}

	private float getServerVersion() throws IOException {
		// 1, connect to server page
		String temp = getContent(serverURL);
		temp = removeAllWordFromString(temp);
		temp = removeDotFromString(temp);
		// 2, get the lastest server version number
		this.lastestVersion = Float.valueOf(temp);
		return lastestVersion;
	}

	private String getContent(String strUrl) throws IOException {
		URL url = new URL(strUrl);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		String s = "";
		StringBuffer sb = new StringBuffer("");
		while ((s = br.readLine()) != null) {
			if (s.indexOf(checkMark) != -1)
				sb.append(s.trim().split("jar")[0]);
		}
		br.close();
		return sb.toString();
	}

	public float getCurrentVersion() {
		return Constant.CURRENTVERSION;
	}

	/*
	 * change <small title="/JarClassFinder1.0.3.jar"> to 1.0.3.
	 */
	private String removeAllWordFromString(String str) {
		char[] chs = str.toCharArray();
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < chs.length; i++) {
			if (chs[i] == '.' || (chs[i] >= '0' && chs[i] <= '9')) {
				s.append(chs[i]);
			}
		}
		return s.toString();
	}

	/*
	 * change 1.0.3. to 1.03
	 */
	private String removeDotFromString(String s) {
		boolean firstDot = true;
		char[] chs = s.toCharArray();
		StringBuffer ss = new StringBuffer();
		for (int i = 0; i < chs.length; i++) {
			if (chs[i] == '.') {
				if (firstDot) {
					firstDot = false;
					ss.append(chs[i]);
					continue;
				}
			} else {
				ss.append(chs[i]);
			}
		}
		return ss.toString();
	}
}
