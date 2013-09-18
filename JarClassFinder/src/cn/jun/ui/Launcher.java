package cn.jun.ui;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
public class Launcher {
	public static void main(String[] args) {
		String osName = System.getProperty("os.name");
		if (osName != null && !osName.equals("")) {
			if (osName.toLowerCase().contains("windows")) {
				try {
					UIManager.setLookAndFeel(new WindowsLookAndFeel());
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
			}
		}

		MainWindow mainWindow = new MainWindow();
		mainWindow.setTitle("Jar Class Finder");

		URL url = Launcher.class.getClassLoader().getResource("cn/jun/image/JarClassFinderLogo.png");
		ImageIcon imageIcon = new ImageIcon(url);
		Image image = imageIcon.getImage();
		mainWindow.setIconImage(image);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);
	}
}
