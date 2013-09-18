package cn.jun.util;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarFile;

/**
 * Jar Class Finder
 * 
 * @author Jun.Xiao
 * @since jdk1.6
 * 
 * Copyright 2012 Jun Xiao 
 * The program is distributed under the terms of the GNU General Public License
 */
public class FileSearchUtils extends SearchUtils {

	public void look() {
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
				if (file.getName().equals(searchCondition.getWantToSearch())) {
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
							if (f.getName().equals(
									searchCondition.getWantToSearch())) {
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
						String wantToSearch = searchCondition.getWantToSearch()
								.replace(".", "/")
								+ ".class";
						while (e.hasMoreElements()) {
							Object element = e.nextElement();
							if (element.toString().equals(wantToSearch)) {
								JarBean bean = new JarBean();
								bean.setLocation(file.getParent());
								bean.setJarName(file.getName());
								bean.setPackageName(element.toString());
								allFoundJars.add(bean);
							}
						}
					} else {
						String wantToSearch = searchCondition.getWantToSearch()
								+ ".class";
						while (e.hasMoreElements()) {
							Object element = e.nextElement();
							String[] split = element.toString().split("/");
							if (split[split.length - 1].equals(wantToSearch)) {
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
									String wantToSearch = searchCondition
											.getWantToSearch()
											.replace(".", "/")
											+ ".class";
									while (e.hasMoreElements()) {
										Object element = e.nextElement();
										if (element.toString().equals(
												wantToSearch)) {
											JarBean bean = new JarBean();
											bean.setLocation(f.getParent());
											bean.setJarName(f.getName());
											bean.setPackageName(element
													.toString());
											allFoundJars.add(bean);
										}
									}
								} else {
									String wantToSearch = searchCondition
											.getWantToSearch()
											+ ".class";
									while (e.hasMoreElements()) {
										Object element = e.nextElement();
										String[] split = element.toString()
												.split("/");
										if (split[split.length - 1]
												.equals(wantToSearch)) {
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

	public String getExactlyOrFuzzySearch() {
		return searchCondition.getExactlyOrFuzzySearch();
	}
	
	public static boolean isPathValid(String path){
		try {
			File f = new File(path);
			return f.exists();
		} catch (Exception e) {
			return false;
		}
	}
}
