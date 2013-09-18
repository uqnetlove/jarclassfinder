package cn.jun.util;

import javax.swing.JLabel;

/**
 * Jar Class Finder
 * 
 * @author Jun.Xiao
 * @since jdk1.6
 * 
 * Copyright 2012 Jun Xiao 
 * The program is distributed under the terms of the GNU General Public License
 */
public class ProgressThread extends Thread {
	private Thread searchThread;

	private JLabel progressLabel;

	public ProgressThread(Thread searchThread, JLabel progressLabel) {
		this.searchThread = searchThread;
		this.progressLabel = progressLabel;
	}

	public void run() {
		while (searchThread.isAlive()) {
			progressLabel.setText(FileSearchUtils.progressText);
		}
	}
}
