package com.vsp.enterprise.test.helper;

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

	public FileNameManipulator(String fullyQualifiedFileName) {
		this.qualifiedFileName = fullyQualifiedFileName;
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
	 * @param text
	 * @return 
	 */
	public String postPendTextToFileName(String text) {
		return postPendTextToFileName(qualifiedFileName, text);
	}

	/**
	 * /mydir/MyFile.txt -> /mydir/MyFileSomeText.txt
	 *
	 * @param qualifiedFileName
	 * @param text
	 * @return
	 */
	public String postPendTextToFileName(String fullyQualifiedFileName, String text) {
		/*
		Path path = Paths.get(qualifiedFileName);
		path = Paths.get("C:\\home\\joe\\foo");
		path = Paths.get("/home/joe/foo");
		path = Paths.get("c:/mydir/hello.drl");
		path = Paths.get("sally\\bar");
		System.out.format("toString: %s%n", path.toString());
		System.out.format("getFileName: %s%n", path.getFileName());
		System.out.format("getName(0): %s%n", path.getName(0));
		System.out.format("getNameCount: %d%n", path.getNameCount());
		System.out.format("subpath(0,2): %s%n", path.subpath(0, 2));
		System.out.format("getParent: %s%n", path.getParent());
		System.out.format("getRoot: %s%n", path.getRoot());
		*/
		String[] filePathName = splitFileName(fullyQualifiedFileName);
		StringBuilder sb = new StringBuilder();

		sb.append(filePathName[0]).append(File.separator).append(filePathName[1]).append(text).append(".").append(filePathName[2]);
		return sb.toString();
	}

	/**
	 * @return /mydir/MyFile.txt -> MyFile.txt
	 */
	public String extractFileName() {
		return extractFileName(qualifiedFileName);
	}

	/**
	 * @return /mydir/MyFile.txt -> MyFile.txt
	 */
	public static String extractFileName(String fullFileName) {
		return Paths.get(fullFileName).getFileName().toString();
	}

	/**
	 * "c:/mydir/foo.bar" -> ["c:/mydir", "foo", "bar"]
	 *
	 * @return A 3 element array of path, filename, file extension
	 */
	public String[] splitFileName() {
		Path path = Paths.get(qualifiedFileName);
		String fileName 	= path.getFileName().toString();
		int extIndex 		= fileName.lastIndexOf(".");
		String file			= (extIndex > 0) ? fileName.substring(0, extIndex): "";
		String fileExtension= (extIndex > 0) ? fileName.substring(extIndex + 1, fileName.length()): "";

		return new String[]{path.getParent().toString(), file, fileExtension};
	}

	/**
	 * Converts the package name to a directory path and tack on the postFixFileNameMarker.
	 * "src/main/java/com/vsp/rule" + "Test" -> "src/main/java/com/vsp/rule/myRuleTest.java"
	 * 
	 * @param javaTestDir
	 * @param postFixFileNameMarker
	 * @return 
	 */
	public String createJavaTestFileNameString(String javaTestDir, String postFixFileNameMarker){
		StringBuilder sb = new StringBuilder(javaTestDir);
		String[] fileNamePaths = splitFileName();
		
		sb.append(File.separator).append(fileNamePaths[1]).append(".java");
		return postPendTextToFileName(sb.toString(), postFixFileNameMarker);
	}

	/**
	 * "c:/mydir/foo.bar" -> ["c:/mydir", "foo", "bar"]
	 * 
	 * @param fullFileName
	 * @return A 3 element array of path, filename, file extension
	 */
	public static String[] splitFileName(String fullFileName) {
		Path path = Paths.get(fullFileName);
		String fileName 	= path.getFileName().toString();
		int extIndex 		= fileName.lastIndexOf(".");
		String file			= (extIndex > 0) ? fileName.substring(0, extIndex): "";
		String fileExtension= (extIndex > 0) ? fileName.substring(extIndex + 1, fileName.length()): "";

		return new String[]{path.getParent().toString(), file, fileExtension};
	}
}
