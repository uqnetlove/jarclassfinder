package cn.jun.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.jun.util.CheckForUpdate;
import cn.jun.util.Constant;
import cn.jun.util.DisplayThread;
import cn.jun.util.ProgressThread;
import cn.jun.util.SearchCondition;
import cn.jun.util.SearchThread;
import cn.jun.util.SearchUtils;
import cn.jun.util.SearchValidation;

/**
 * Jar Class Finder
 * 
 * @author Jun.Xiao
 * @since jdk1.6
 * 
 *        Copyright 2012 Jun Xiao The program is distributed under the terms of
 *        the GNU General Public License
 */
public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenuBar menuBar;

	private JMenu fileMenu;

	private JMenuItem exitItem;

	// private JMenu editMenu;

	private JMenu helpMenu;

	private JMenuItem visitMySite;

	private JMenuItem checkForUpdateItem;

	private JMenuItem aboutItem;

	private JMenuItem licenseItem;

	private JTextArea editorPane;

	private JScrollPane middScrollPanel;

	private JLabel wantToSearch;

	private JTextField wantToSearchContent;

	private ButtonGroup buttonGroup;

	private JRadioButton exactlySearch;

	private JRadioButton fuzzySearch;

	private JComboBox searchType;

	private JLabel searchSource;

	private JTextField SearchSourceContent;

	private JFileChooser fileChooser;

	private JButton chooser;

	private JPanel topInputPanel;

	private JButton search;

	private JButton suspend;

	private JButton resume;

	private JButton stop;

	private JPanel topSearchPanel;

	private JPanel topPanel;

	private JLabel progressLabel;

	private JPanel buttomPanel;

	public MainWindow() {
		initCompoments();
		initComLayouts();
		initMainWindow();
	}

	private void initJMenuBar() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);

		// editMenu = new JMenu("Edit");
		// menuBar.add(editMenu);

		helpMenu = new JMenu("Help");
		licenseItem = new JMenuItem("License");
		helpMenu.add(licenseItem);
		checkForUpdateItem = new JMenuItem("Check For Update");
		helpMenu.add(checkForUpdateItem);
		visitMySite = new JMenuItem("Visit my site");
		helpMenu.add(visitMySite);
		menuBar.add(helpMenu);
		aboutItem = new JMenuItem("About");
		helpMenu.add(aboutItem);
	}

	private void initMiddScrollPanel() {
		middScrollPanel = new JScrollPane();
		editorPane = new JTextArea();

		CurrentLineHighlighter.install(editorPane);

		middScrollPanel.setViewportView(editorPane);
	}

	private void initCompoments() {
		initJMenuBar();
		initTopPanel();
		initMiddScrollPanel();
		initButtonPanel();
	}

	private void initButtonPanel() {
		buttomPanel = new JPanel();
		progressLabel = new JLabel("No progress to display at this time.");
		buttomPanel.add(progressLabel);
	}

	private void initTopPanel() {
		topPanel = new JPanel(new BorderLayout());
		topInputPanel = new JPanel(new GridLayout(2, 3));
		topSearchPanel = new JPanel(new GridLayout(1, 4));

		wantToSearch = new JLabel("Please input the search target");
		wantToSearchContent = new JTextField();

		buttonGroup = new ButtonGroup();
		exactlySearch = new JRadioButton(Constant.EXACTLY_SEARCH);
		exactlySearch.setSelected(true);
		fuzzySearch = new JRadioButton(Constant.FUZZY_SEARCH);
		buttonGroup.add(exactlySearch);
		buttonGroup.add(fuzzySearch);

		searchType = new JComboBox(new Object[] { Constant.JAR_TYPE,
				Constant.CLASS_TYPE, Constant.PACKAGE_CLASS_TYPE });
		searchSource = new JLabel("Please select the search location");
		SearchSourceContent = new JTextField();
		chooser = new JButton("Browser...");

		topInputPanel.add(wantToSearch);
		topInputPanel.add(wantToSearchContent);
		topInputPanel.add(exactlySearch);
		topInputPanel.add(searchType);
		topInputPanel.add(searchSource);
		topInputPanel.add(SearchSourceContent);
		topInputPanel.add(fuzzySearch);
		topInputPanel.add(chooser);
		topPanel.add(topInputPanel, BorderLayout.CENTER);

		search = new JButton("Search");
		suspend = new JButton("Suspend");
		resume = new JButton("Resume");
		stop = new JButton("Stop");
		topSearchPanel.add(search);
		topSearchPanel.add(suspend);
		topSearchPanel.add(resume);
		topSearchPanel.add(stop);
		topPanel.add(topSearchPanel, BorderLayout.SOUTH);
	}

	private void initComLayouts() {

	}

	private void addActions() {

		getRootPane().setDefaultButton(search);

		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								MainWindow.this,
								"    Jar Class Finder "
										+ String.valueOf(Constant.CURRENTVERSION)
										+ " \r\n Copyright 2012 Jun Xiao \r\n   uqnetlove@gmail.com");
			}
		});

		visitMySite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (System.getProperty("os.name").toLowerCase()
							.contains("win")) {
						Runtime.getRuntime()
								.exec("explorer http://jarclassfinder.sourceforge.net");
					} else {
						Runtime.getRuntime().exec(
								"open http://jarclassfinder.sourceforge.net");
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		checkForUpdateItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final ProgressFrame frame = new ProgressFrame();
				new Thread(new Runnable() {
					public void run() {
						frame.display(MainWindow.this);
					}
				}).start();
				new Thread(new Runnable() {
					public void run() {
						CheckForUpdate cfu = new CheckForUpdate(frame);
						JOptionPane.showMessageDialog(MainWindow.this,
								cfu.alertMessage());
						frame.notDisplay();
					}
				}).start();
			}
		});

		licenseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LicenseDialog ld = new LicenseDialog();
				double x = MainWindow.this.getLocation().getX();
				double y = MainWindow.this.getLocation().getY();
				ld.setBounds((int) x + MainWindow.this.getWidth() / 2 - 267,
						(int) y + MainWindow.this.getHeight() / 2 - 165, 534,
						329);
				ld.setUndecorated(true);
				ld.setVisible(true);
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		chooser.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				fileChooser = new JFileChooser("");
				fileChooser.setDialogTitle("Open File Location");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showOpenDialog(chooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					SearchSourceContent.setText(file.getAbsolutePath());
				}
			}
		});

		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchUtils.needRun = true;
				SearchUtils.needSuspend = false;
				SearchUtils.needStop = false;

				editorPane.setText(null);

				SearchCondition searchCondition = new SearchCondition();
				searchCondition.setSearchType(searchType.getSelectedItem()
						.toString());
				searchCondition.setWantToSearch(wantToSearchContent.getText()
						.trim());
				searchCondition.setSearchLocation(SearchSourceContent.getText()
						.trim());
				if (exactlySearch.isSelected()) {
					searchCondition
							.setExactlyOrFuzzySearch(Constant.EXACTLY_SEARCH);
				} else {
					searchCondition
							.setExactlyOrFuzzySearch(Constant.FUZZY_SEARCH);
				}

				SearchValidation searchValidation = new SearchValidation();
				String cause = searchValidation.validate(searchCondition);
				if (!cause.isEmpty()) {
					JOptionPane.showMessageDialog(MainWindow.this, cause,
							"Alert", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				search.setEnabled(false);

				SearchUtils.startTime = System.currentTimeMillis();
				SearchThread searchThread = new SearchThread(searchCondition);
				searchThread.start();

				DisplayThread displayThread = new DisplayThread(searchThread,
						editorPane, progressLabel, search);
				displayThread.start();

				ProgressThread progressThread = new ProgressThread(
						searchThread, progressLabel);
				progressThread.start();
			}
		});

		suspend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchUtils.needRun = false;
				SearchUtils.needSuspend = true;
				SearchUtils.needStop = false;
			}
		});
		resume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchUtils.needRun = true;
				SearchUtils.needSuspend = false;
				SearchUtils.needStop = false;
			}
		});
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchUtils.needRun = false;
				SearchUtils.needSuspend = true;
				SearchUtils.needStop = true;
			}
		});

		editorPane.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				editorPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseClicked(MouseEvent e) {
				Rectangle rec;
				try {
					rec = editorPane.modelToView(editorPane.getCaretPosition());
					int line = rec.y / rec.height + 1;
					String s[] = editorPane.getText().split("\n");
					if (line <= s.length) {
						String a = s[line - 1];
						// System.out.println(a);

						String sType = searchType.getSelectedItem().toString();
						StringBuffer url = new StringBuffer();
						url.append("cmd /c explorer /select,");
						if (!sType.equals(Constant.JAR_TYPE)) {
							if (a.contains("under")) {
								a = a.split("under")[1].trim();
							}
						}
						// System.out.println(a);
						url.append(a);
						if (a.contains("\\") || a.contains("/")) {
							if (System.getProperty("os.name").toLowerCase()
									.contains("win")) {
								Runtime.getRuntime().exec(url.toString());
							} else {
								Runtime.getRuntime().exec(
										"open "
												+ a.substring(0,
														a.lastIndexOf("/")));
							}
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void mergeCompstoMainWindow() {
		setJMenuBar(menuBar);
		Container c = getContentPane();
		c.add(topPanel, BorderLayout.NORTH);
		c.add(middScrollPanel);
		c.add(buttomPanel, BorderLayout.SOUTH);
		addActions();
	}

	private void initMainWindow() {
		// setLocationRelativeTo(null);
		// setSize(WIDTH, HEIGHT);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scmSize = toolkit.getScreenSize();

		int w = scmSize.width * 2 / 3;
		int h = scmSize.height * 2 / 3;

		int s = scmSize.width / 2 - (w / 2);
		int e = scmSize.height / 2 - (h / 2);

		setBounds(s, e, w, h);
		setTitle("Jar Class Finder");
		mergeCompstoMainWindow();
	}

	public static void main(String[] args) {
		System.out
				.println("/Users/jun/libs/HTMLParser-2.0-SNAPSHOT/lib/htmllexer.jar"
						.substring(0,
								"/Users/jun/libs/HTMLParser-2.0-SNAPSHOT/lib/htmllexer.jar"
										.lastIndexOf("/")));
	}
}
