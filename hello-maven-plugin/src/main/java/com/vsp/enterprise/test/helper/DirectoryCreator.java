package com.vsp.enterprise.test.helper;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class DirectoryCreator {
	String dir;

	public DirectoryCreator(String dir) {
		this.dir = dir;
	}

	public static void main(String[] args) {
		String singleDir = OsUtils.isWindows() ? "c:/trash/delme1" : "./delme1";
		File file = new File(singleDir);

		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

		String multiDir = OsUtils.isWindows() ? "c:/trash/delme2/again/please" : "./delme2/again/please";
//		String multiDir = "/Users/oogie/projects/java/maven_plugin/hello-maven-plugin/src/test/java/rules/entitlement/ProductEdit/Service";
		File files = new File(multiDir);

		if (!files.exists()) {
			if (files.mkdirs()) {
				System.out.println("Multiple directories are created!");
			} else {
				System.out.println("Failed to create multiple directories!");
			}
		}

	}

	public boolean exists() {
		File file = new File(dir);
		return file.exists();
	}

	public boolean mkdir() {
		File file = new File(dir);

		if (!file.exists()) 
			return file.mkdir();
		return false;
	}

	public boolean mkdirs() {
		File file = new File(dir);

		if (!file.exists()) 
			return file.mkdirs();
		return false;
	}

	public boolean delDir() {
		boolean retVal = true;
		try {
			File file = new File(dir);
			FileUtils.deleteDirectory(file);
		} catch (IOException ex) {
			Logger.getLogger(DirectoryCreator.class.getName()).log(Level.SEVERE, null, ex);
			retVal = false;
		}
		return retVal;
	}
}
