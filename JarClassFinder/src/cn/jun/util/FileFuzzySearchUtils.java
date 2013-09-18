package cn.jun.util;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Jar Class Finder
 * 
 * @author Jun.Xiao
 * @since jdk1.6
 * 
 * Copyright 2012 Jun Xiao 
 * The program is distributed under the terms of the GNU General Public License
 */
public class FileFuzzySearchUtils extends SearchUtils {
	private Pattern p;

	private void initPattern(String pattern) {
		if (searchCondition.getSearchType().equals(Constant.JAR_TYPE)) {
			pattern = pattern.replace("*", ".*").replace(".jar", "");
			p = Pattern.compile(pattern);
		} else if (searchCondition.getSearchType().equals(Constant.CLASS_TYPE)) {
			pattern = pattern.replace("*", ".*");
			p = Pattern.compile(pattern);
		} else {
			searchCondition.setWantToSearch(pattern.replace(".", "/"));
			pattern = searchCondition.getWantToSearch().replace("*", ".*");
			p = Pattern.compile(pattern);
		}
	}

	public void look() {
		initPattern(searchCondition.getWantToSearch());

		if (searchCondition.getSearchType().equals(Constant.JAR_TYPE)) {
			lookJarName(searchCondition.getSearchLocation());
		} else {
			lookPackageClassNameOrClassName(searchCondition.getSearchLocation());
		}
	}

	private void lookJarName(String searchLocation) {
		while (needSuspend) {
			if (needStop) {
				return;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		progressText = searchLocation;

		File file = new File(searchLocation);
		if (file == null) {
			return;
		} else {
			if (file.isFile()) {
				// System.out.println(file.getPath());
				String fileName = file.getName();
				Matcher m = p.matcher(fileName.split(".jar")[0]);
				boolean b = m.matches();
				if (b) {
					JarBean bean = new JarBean();
					bean.setLocation(file.getAbsolutePath());
					bean.setJarName(file.getName());
					allFoundJars.add(bean);
				}

			} else if (file.isDirectory()) {
				File[] all = file.listFiles(new JarFileFilter());
				if (all != null) {
					for (File f : all) {
						if (f.isFile()) {
							// System.out.println(f.getPath());
							String fileName = f.getName();
							Matcher m = p.matcher(fileName.split(".jar")[0]);
							boolean b = m.matches();
							if (b) {
								JarBean bean = new JarBean();
								bean.setLocation(f.getParent());
								bean.setJarName(f.getName());
								allFoundJars.add(bean);
							}
						} else {
							lookJarName(f.getAbsolutePath());
						}
					}
				}
			}
		}
	}

	private void lookPackageClassNameOrClassName(String searchLocation) {
		
		while (needSuspend) {
			if (needStop) {
				return;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		progressText = searchLocation;

		File file = new File(searchLocation);
		if (file == null) {
			return;
		} else {
			if (file.isFile()) {
				// System.out.println(file.getPath());
				try {
					JarFile jarFile = new JarFile(file);
					Enumeration e = jarFile.entries();

					if (searchCondition.getSearchType().equals(
							Constant.PACKAGE_CLASS_TYPE)) {

						while (e.hasMoreElements()) {
							Object element = e.nextElement();

							Matcher m = p.matcher(element.toString().split(
									".class")[0]);
							boolean b = m.matches();

							if (b) {
								JarBean bean = new JarBean();
								bean.setLocation(file.getParent());
								bean.setJarName(file.getName());
								bean.setPackageName(element.toString());
								allFoundJars.add(bean);
							}
						}
					} else {

						while (e.hasMoreElements()) {
							Object element = e.nextElement();
							String[] split = element.toString().split("/");

							Matcher m = p.matcher(split[split.length - 1]
									.split("\\.")[0]);
							boolean b = m.matches();

							if (b) {
								JarBean bean = new JarBean();
								bean.setLocation(file.getParent());
								bean.setJarName(file.getName());
								bean.setClassName(element.toString());
								allFoundJars.add(bean);
							}
						}
					}
				} catch (IOException ioe) {
					// System.out.println("error " + ioe.getMessage()
					// + " when read " + file.getPath());
				}
			} else if (file.isDirectory()) {
				File[] all = file.listFiles(new JarFileFilter());
				if (all != null) {
					for (File f : all) {
						if (f.isFile()) {
							// System.out.println(f.getPath());
							try {
								JarFile jarFile = new JarFile(f);
								Enumeration e = jarFile.entries();

								if (searchCondition.getSearchType().equals(
										Constant.PACKAGE_CLASS_TYPE)) {

									while (e.hasMoreElements()) {
										Object element = e.nextElement();

										Matcher m = p.matcher(element
												.toString().split(".class")[0]);
										boolean b = m.matches();

										if (b) {
											JarBean bean = new JarBean();
											bean.setLocation(f.getParent());
											bean.setJarName(f.getName());
											bean.setPackageName(element
													.toString());
											allFoundJars.add(bean);
										}
									}
								} else {

									while (e.hasMoreElements()) {
										Object element = e.nextElement();
										String[] split = element.toString()
												.split("/");

										Matcher m = p
												.matcher(split[split.length - 1]
														.split("\\.")[0]);
										boolean b = m.matches();

										if (b) {
											JarBean bean = new JarBean();
											bean.setLocation(f.getParent());
											bean.setJarName(f.getName());
											bean.setClassName(element
													.toString());
											allFoundJars.add(bean);
										}
									}
								}
							} catch (IOException ioe) {
								// System.out.println("error " +
								// ioe.getMessage()
								// + " when read " + file.getPath());
							}
						} else {
							lookPackageClassNameOrClassName(f.getAbsolutePath());
						}
					}
				}
			}
		}
	}

	public String getExactlyOrFuzzySearch() {
		return searchCondition.getExactlyOrFuzzySearch();
	}

	public static void main(String[] args) {
		// String pp = "comm*";
		// pp = pp.replace("*", ".*");
		// System.out.println(pp);
		// Pattern p = Pattern.compile(pp);
		// Matcher m = p.matcher("commonj-1.0.0.jar".split(".jar")[0]);
		// boolean b = m.matches();
		// System.out.println(b);
		Pattern p = Pattern.compile("commonj/.*/Timer");
		Matcher m = p.matcher("commonj/timers/Timer");
		System.out.println(m.matches());

	}

	public static void main1(String[] args) {
		// SearchCondition searchCondition = new SearchCondition();
		// searchCondition.setSearchLocation("d:/libs");
		// FileSearchUtils fsu = new FileSearchUtils(searchCondition);
		// fsu.lookJarName(searchCondition.getSearchLocation());

		// if package.class name
		String input = "commonj.timers.CancelTimerListener";
		input = input.trim().replace(".", "/") + ".class";
		System.out.println(input);
		// if class name
		// String input = "CancelTimerListener";
		// input = input.trim() + ".class";
		// System.out.println(input);
		try {
			JarFile jarFile = new JarFile("d:/temp/commonj-1.0.0.jar");
			Enumeration e = jarFile.entries();
			while (e.hasMoreElements()) {
				// if(e.nextElement().toString().equals(input)){
				// System.out.println("found " + e.nextElement());
				// }

				if (e.nextElement().toString().contains(input)) {
					System.out.println("found " + e.nextElement());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}