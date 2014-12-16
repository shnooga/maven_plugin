package com.vsp.enterprise.test.helper;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class DirectoryUtil {

	String dir;
	static public final String DRL_REGEX = ".+\\.drl";

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

	public boolean delDir() {
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

	public List<File> fileSearch(String dir, String regExPattern) {
		List<File> filesFound = new ArrayList<File>();
		RegExFileNameFilter filter = new RegExFileNameFilter(regExPattern);

		findFile(filter, new File(dir), filesFound);
		return filesFound;
	}

	private void findFile(RegExFileNameFilter fileFilter, File parentFile, List<File> filesFound) {
		File[] list = parentFile.listFiles();
		if (list == null) {
			System.out.println(parentFile.getName() + " is an invalid directory!");
			return;
		}
		for (File file : list) {
			if (file.isDirectory()) {
				findFile(fileFilter, file, filesFound);
			} else if (file.getName().matches(DRL_REGEX)) {
				filesFound.add(file);
				System.out.println(file.getParentFile() + " " + file.getName());
			}
		}
	}

	private class RegExFileNameFilter implements FilenameFilter {

		private String regEx;

		public RegExFileNameFilter(String regEx) {
			this.regEx = regEx;
		}

		@Override
		public boolean accept(File dir, String name) {
			File file = new File(name);
			return (!file.isDirectory() && name.matches(regEx));
		}

	}
}
