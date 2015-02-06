package com.vsp.enterprise.test.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class DirectoryUtil {

	String dir;
	static public final String DRL_REGEX = ".+\\.drl";
	static public final String JAVA_REGEX = ".+\\.java";

	public DirectoryUtil(String dir) {
		this.dir = dir;
	}

	public boolean exists() {
		File file = new File(dir);
		return file.exists();
	}

	public boolean mkdir() {
		File file = new File(dir);

		if (!file.exists()) {
			return file.mkdir();
		}
		return false;
	}

	public boolean mkdirs() {
		File file = new File(dir);

		if (!file.exists()) {
			return file.mkdirs();
		}
		return false;
	}

	public static boolean delDir(String dir) {
		boolean retVal = true;
		try {
			File file = new File(dir);
			FileUtils.deleteDirectory(file);
		} catch (IOException ex) {
			Logger.getLogger(DirectoryUtil.class.getName()).log(Level.SEVERE, null, ex);
			retVal = false;
		}
		return retVal;
	}

	/**
	 * @param regEx
	 * @return
	 */
	public List<File> filesSearch(String regEx) {
		List<File> filesFound = new ArrayList<File>();

		findFile(regEx, new File(dir), filesFound);
		return filesFound;
	}

	public List<File> ruleFilesSearch() {
		return filesSearch(DRL_REGEX);
	}

	public List<File> javaFilesSearch() {
		return filesSearch(JAVA_REGEX);
	}

	private void findFile(String regEx, File parentFile, List<File> filesFound) {
		File[] list = parentFile.listFiles();
		if (list == null) {
			System.out.println(parentFile.getName() + " is an invalid directory!");
			return;
		}
		for (File file : list) {
			if (file.isDirectory()) {
				findFile(regEx, file, filesFound);
			} else if (file.getName().matches(regEx)) {
				filesFound.add(file);
			}
		}
	}
}
