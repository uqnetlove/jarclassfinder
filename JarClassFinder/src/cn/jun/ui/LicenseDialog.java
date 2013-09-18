package cn.jun.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
/**
 * Jar Class Finder
 * 
 * @author Jun.Xiao
 * @since jdk1.6
 *
 * Copyright 2012 Jun Xiao 
 * The program is distributed under the terms of the GNU General Public License
 */
public class LicenseDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel buildTime;

	private JLabel appName;

	private JLabel author;

	private JLabel homePage;

	private JPanel northPanel;

	private JTextArea license;

	private JScrollPane centerPanel;

	private JButton ok;

	private JPanel southPanel;
	
	public LicenseDialog(){
		init();
	}
	
	private void init() {
		buildTime = new JLabel(
				"                                                    Build time : Dec 21 2012 - 21:30:00");
		appName = new JLabel("Jar Class Finder v1.2.3");
		author = new JLabel("Author:       Jun Xiao");
		homePage = new JLabel(
				"<html><body>HomePage: <a href=''>http://jarclassfinder.sourceforge.net</a></body></html>");
		homePage.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				homePage.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("http://jarclassfinder.sourceforge.net"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});

		northPanel = new JPanel(new GridLayout(4, 1));
		northPanel.add(buildTime);
		northPanel.add(appName);
		northPanel.add(author);
		northPanel.add(homePage);

		StringBuffer buffer = new StringBuffer();
		buffer.append("                    GNU General Public License \r\n");
		buffer.append("This program is free software; you can redistribute it and/or \r\n");
		buffer.append("modify it under the terms of the GNU General Public License as \r\n");
		buffer.append("published by the Free Software Foundation; either version 2 of \r\n");
		buffer.append("the License, or (at your option) any later version. \r\n \r\n");

		buffer.append("This program is distributed in the hope that it will be useful, \r\n");
		buffer.append("but WITHOUT ANY WARRANTY; without even the implied warranty of \r\n");
		buffer.append("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. \r\n");
		buffer.append("See the GNU General Public License for more details. \r\n \r\n");

		buffer.append("You should have received a copy of the GNU General Public License \r\n");
		buffer.append("along with this program; if not, see http://www.gnu.org/licenses");
		license = new JTextArea(buffer.toString());

		centerPanel = new JScrollPane(license);

		ok = new JButton("ok");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		southPanel = new JPanel();
		southPanel.add(ok);

		Container c = getContentPane();
		c.add(northPanel, BorderLayout.NORTH);
		c.add(centerPanel);
		c.add(southPanel, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		LicenseDialog ld = new LicenseDialog();
		ld.setUndecorated(true);
		ld.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ld.pack();
		ld.setVisible(true);
		System.out.println(ld.getComponents().length);
	}
}