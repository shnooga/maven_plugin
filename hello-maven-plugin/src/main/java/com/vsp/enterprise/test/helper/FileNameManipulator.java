package com.vsp.enterprise.test.helper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author oogie
 */
public class FileNameManipulator {

	private String qualifiedFileName = "";

	public FileNameManipulator(String fullyQualifiedFileName) {
		qualifiedFileName = replaceWithUnixSeparator(fullyQualifiedFileName);
	}

//	private String baseDir = "";
//	private String javaPackage = "";
//	private String fileNameWithExtension = "";
//	public FileNameManipulator(String baseDir, String javaPackage, String fileNameWithExtension) {
//		this.baseDir = baseDir;
//		this.javaPackage = javaPackage;
//		this.fileNameWithExtension = fileNameWithExtension;
//
//		StringBuilder sb = new StringBuilder(baseDir);
//		sb.append(File.separator).append(convertJavaPackageAsPath(javaPackage)).append(File.separator).append(this.fileNameWithExtension);
//		qualifiedFileName = sb.toString();
//	}


	/**
	 * Windows can handle forward slashes; this helper method is to use the most
	 * common path denominator of forward slashes.
	 *
	 * @param qualifiedFileName
	 * @return
	 */
	public static String replaceWithUnixSeparator(String qualifiedFileName) {
		return qualifiedFileName.replace("\\", "/");
	}

	/**
	 * @param text
	 * @return
	 */
	public String postPendTextToFileName(String text) {
		return postPendTextToFileName(qualifiedFileName, text);
	}

	/**
	 * /mydir/MyFile.txt + XXX -> /mydir/MyFileSomeTextXXX.txt
	 *
	 * @param qualifiedFileName /src/test/java/com/MyJaveCode.java
	 * @param text String to append to file name. ie "Faux", "Test", etc.
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
	 * @return /mydir/MyFile.txt -> MyFile
	 */
	public String extractFileName() {
		return splitFileName()[1];
	}

	/**
	 * @return /mydir/MyFile.txt -> MyFile.txt
	 */
	public String extractFileNameAndExtension() {
		return extractFileNameAndExtension(qualifiedFileName);
	}

	/**
	 * @return /mydir/MyFile.txt -> MyFile.txt
	 */
	public static String extractFileNameAndExtension(String fullFileName) {
		return Paths.get(fullFileName).getFileName().toString();
	}

	/**
	 * @return /mydir/MyFile.txt -> /mydir
	 */
	public String extractParentFile() {
		return Paths.get(qualifiedFileName).getParent().toString();
	}

	/**
	 * "c:/mydir/foo.bar" -> ["c:/mydir", "foo", "bar"]
	 *
	 * @return A 3 element array of path, filename, file extension
	 */
	public String[] splitFileName() {
		Path path = Paths.get(qualifiedFileName);
		String fileName = path.getFileName().toString();
		int extIndex = fileName.lastIndexOf(".");
		String file = (extIndex > 0) ? fileName.substring(0, extIndex) : "";
		String fileExtension = (extIndex > 0) ? fileName.substring(extIndex + 1, fileName.length()) : "";

		return new String[]{path.getParent().toString(), file, fileExtension};
	}

	/**
	 *
	 * Converts the package name to a directory path and tack on the "Test"
	 * postFixFileNameMarker.
	 *
	 * @param baseDir "./target/rules"
	 * @param javaPackage "com.vsp.enterprise"
	 * @return "./target/rules/com/vsp/enterprise/myFileTest.java"
	 */
	public String createJavaTestFileNameString(String baseDir, String javaPackage) {
		return replaceWithUnixSeparator(createTestFileNameString(baseDir, javaPackage, FileType.JAVA));
	}

	/**
	 *
	 * Converts the package name to a directory path and tack on the "Faux"
	 * postFixFileNameMarker.
	 *
	 * @param baseDir "./src/test/java"
	 * @param javaPackage "com.vsp.enterprise"
	 * @return "./src/test/java/com/vsp/enterprise/myFileFaux.drl"
	 */
	public String createRuleFauxFileNameString(String baseDir, String javaPackage) {
		return createTestFileNameString(baseDir, javaPackage, FileType.RULE);
	}

	private String createTestFileNameString(String baseDir, String javaPackage, FileType fileType) {
		StringBuilder sb = new StringBuilder(baseDir);
		sb.append(File.separator).append(convertJavaPackageAsPath(javaPackage)).append(File.separator).append(extractFileName()).append(fileType.extension());
		return replaceWithUnixSeparator(postPendTextToFileName(sb.toString(), fileType.marker()));
	}

	/**
	 *
	 * @return The package structure as a path ie. "com.vsp.enterprise" ->
	 * "com/vsp/enterprise"
	 */
	private String convertJavaPackageAsPath(String javaPackage) {
		return javaPackage.replace(".", File.separator);
	}

	/**
	 * "c:/mydir/foo.bar" -> ["c:/mydir", "foo", "bar"]
	 *
	 * @param fullFileName
	 * @return A 3 element array of path, filename, file extension
	 */
	public static String[] splitFileName(String fullFileName) {
		Path path = Paths.get(fullFileName);
		String fileName = path.getFileName().toString();
		int extIndex = fileName.lastIndexOf(".");
		String file = (extIndex > 0) ? fileName.substring(0, extIndex) : "";
		String fileExtension = (extIndex > 0) ? fileName.substring(extIndex + 1, fileName.length()) : "";

		return new String[]{path.getParent().toString(), file, fileExtension};
	}
}
