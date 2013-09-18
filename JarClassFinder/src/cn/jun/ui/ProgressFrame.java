package cn.jun.ui;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
/**
 * Jar Class Finder
 * 
 * @author Jun.Xiao
 * @since jdk1.6
 *
 * Copyright 2012 Jun Xiao 
 * The program is distributed under the terms of the GNU General Public License
 */
public class ProgressFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int iWidth = 200;

	private int iHeight = 16;

	private boolean needDisplay = true;

	public void setNeedDisplay(boolean needDisplay) {
		this.needDisplay = needDisplay;
	}

	public boolean getNeedDisplay() {
		return this.needDisplay;
	}

	public void display(JFrame parentFrame) {
		JProgressBar aJProgressBar = new JProgressBar(0, 100);
		aJProgressBar.setIndeterminate(true);
		Container contentPane = getContentPane();
		contentPane.add(aJProgressBar);
		double x = parentFrame.getLocation().getX();
		double y = parentFrame.getLocation().getY();
		setBounds((int) x + parentFrame.getWidth() / 2 - iWidth / 2, (int) y
				+ parentFrame.getHeight() / 2 - iHeight / 2, iWidth, iHeight);
		setUndecorated(true);
		setVisible(true);
	}

	public void notDisplay() {
		dispose();
	}
}
