package com.vsp.enterprise.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

/**
 *
 * @author oogie
 */
public class FileNameManipulator {

	private String qualifiedFileName;
	private Path path;

	public FileNameManipulator(String fileName) {
		this.qualifiedFileName = fileName;
		path = Paths.get(qualifiedFileName);
		replaceWithUnixSeparator();
	}

	private void replaceWithUnixSeparator() {
		if (!qualifiedFileName.contains("\\")) 
			return;

		StringBuilder sb = new StringBuilder();
		StringTokenizer tokenizer = new StringTokenizer(qualifiedFileName, "\\");
		
		while (tokenizer.hasMoreElements()) {
			if (sb.length() > 0) 
				sb.append("/");
			sb.append(tokenizer.nextToken());
		}
		qualifiedFileName = sb.toString();
	}

	/**
	 * /mydir/MyFile.txt -> /mydir/MyFileSomeText.txt
	 *
	 * @param text
	 * @return
	 */
	public String postPendTextToFileName(String text) {
//		path = Paths.get("C:\\home\\joe\\foo");
//		path = Paths.get("/home/joe/foo");
//		path = Paths.get("c:/mydir/hello.drl");
//path = Paths.get("sally\\bar");
		System.out.format("toString: %s%n", path.toString());
		System.out.format("getFileName: %s%n", path.getFileName());
		System.out.format("getName(0): %s%n", path.getName(0));
		System.out.format("getNameCount: %d%n", path.getNameCount());
		System.out.format("subpath(0,2): %s%n", path.subpath(0, 2));
		System.out.format("getParent: %s%n", path.getParent());
		System.out.format("getRoot: %s%n", path.getRoot());

		String[] filePathName = splitFileName();
		StringBuilder sb = new StringBuilder();

		sb.append(filePathName[0]).append(filePathName[1]).append(text).append(".").append(filePathName[2]);
		return sb.toString();
	}

	/**
	 * /mydir/MyFile.txt -> MyFileSomeText
	 *
	 * @param text
	 * @return
	 */
	public String extractFileName() {
		return splitFileName()[2];
	}

	/**
	 * "c:/mydir/foo.bar" -> ["c:/mydir", "foo", "bar"]
	 *
	 * @return A 3 element array of path, filename, file extension
	 */
	public String[] splitFileName() {
		int extIndex = qualifiedFileName.lastIndexOf(".");
		String fileExtension = (extIndex > 0) ? qualifiedFileName.substring(extIndex, qualifiedFileName.length()) : "";

		int pathIndex = qualifiedFileName.lastIndexOf("\\");
		String fileName = (pathIndex > 0) ? qualifiedFileName.substring(pathIndex + 1, extIndex) : "";
		String path = (pathIndex > 0) ? qualifiedFileName.substring(0, pathIndex) : "";

		return new String[]{path, fileName, fileExtension};
	}
}