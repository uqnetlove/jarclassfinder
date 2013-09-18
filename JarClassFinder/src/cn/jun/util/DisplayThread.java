package cn.jun.util;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * Jar Class Finder
 * 
 * @author Jun.Xiao
 * @since jdk1.6
 * 
 *        Copyright 2012 Jun Xiao The program is distributed under the terms of
 *        the GNU General Public License
 */
public class DisplayThread extends Thread {
	private Thread searchThread;

	private JTextArea editorPane;

	private JLabel progressLabel;

	private JButton search;

	private volatile int count;

	public DisplayThread(Thread searchThread, JTextArea editorPane,
			JLabel progressLabel, JButton search) {
		this.searchThread = searchThread;
		this.editorPane = editorPane;
		this.progressLabel = progressLabel;
		this.search = search;
	}

	public void addCount() {
		count++;
	}

	public int getCount() {
		return count;
	}

	public void run() {
		while (searchThread.isAlive()
				|| !FileSearchUtils.allFoundJars.isEmpty()) {
			if (SearchUtils.allFoundJars.isEmpty()) {
				continue;
			}

			if (!SearchUtils.anyMatchedRecord) {
				SearchUtils.anyMatchedRecord = true;
			}

			JarBean bean = FileSearchUtils.allFoundJars.remove(0);
			addCount();

			if (bean.getPackageName() != null
					&& !bean.getPackageName().isEmpty()) {
				editorPane.append(bean.getPackageName() + " under "
						+ bean.getLocation() + File.separator
						+ bean.getJarName() + "\r\n");
			} else if (bean.getClassName() != null
					&& !bean.getClassName().isEmpty()) {
				editorPane.append(bean.getClassName() + " under "
						+ bean.getLocation() + File.separator
						+ bean.getJarName() + "\r\n");
			} else {
				editorPane.append(bean.getLocation() + File.separator
						+ bean.getJarName() + "\r\n");
			}
		}
		if (!SearchUtils.anyMatchedRecord) {
			editorPane.append("search completely...\r\n");
			editorPane.append("no matched item found...\r\n");
		}

		FileSearchUtils.endTime = System.currentTimeMillis();
		// editorPane
		// .append("total spent "
		// + ((FileSearchUtils.endTime - FileSearchUtils.startTime) / 1000)
		// + " seconds");

		progressLabel
				.setText("search completely "
						+ count
						+ " items found"
						+ " total spent "
						+ ((FileSearchUtils.endTime - FileSearchUtils.startTime) / 1000)
						+ " seconds");
		search.setEnabled(true);
	}
}
