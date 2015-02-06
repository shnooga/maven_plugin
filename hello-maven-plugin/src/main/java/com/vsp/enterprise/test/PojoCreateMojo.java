package com.vsp.enterprise.test;

import com.vsp.enterprise.test.helper.*;
import java.io.*;
import java.util.List;
import java.util.logging.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.*;

@Mojo(name = "pojo_create", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class PojoCreateMojo extends AbstractMojo {

	@Parameter(defaultValue = "false", property = "overwriteExistJavaTest")
	private boolean overwriteExistingJavaTest;

	@Parameter(property = "inputFile")
	private String inputFile;

	@Parameter(property = "inputDir")
	private String inputDir;

	@Parameter(defaultValue = "./target/java", property = "javaTestDir")
	private String javaTestDirectory;

	public void execute() {
		if ((inputFile == null) && (inputDir == null)) {
			System.out.println("inputFile or inputDir param are mandatory!!!");
			return;
		}

		if (inputFile != null) {
			createPojoFile(inputFile);
		}
		if (inputDir != null) {
			createPojoFiles(inputDir);
		}
	}

	private void createPojoFiles(String ejbFileDir) {
		try {

			DirectoryUtil directoryUtil = new DirectoryUtil(ejbFileDir);
			List<File> files = directoryUtil.javaFilesSearch();

			for (File ejbFile : files) {
				System.out.println(ejbFile.getName() + " : " + ejbFile.getCanonicalPath());
				createPojoFile(ejbFile.getCanonicalPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param fileHelper A helper object that reads & write files.
	 * @param origJavaFileName "./some/dir/OrigClass.java"
	 * @return The freshly generated Pojo file name, in unix dash format
	 * since windows can read the forward slash also. ie
	 * "./src/test/java/OrigClass.java" An empty string for failure.
	 */
	private String createPojoFile(String origJavaFileName) {
		FileHelper fileHelper = new FileHelper();
		String qualifiedJavaFileName = "";
		try {
			String pojoStr = fileHelper.translateToPojo(origJavaFileName);
			FileNameManipulator manipulator = new FileNameManipulator(origJavaFileName);
			String qualifiedJavaDir = javaTestDirectory + File.separator + fileHelper.getJavaPackageAsPath();
			DirectoryUtil directoryUtil = new DirectoryUtil(qualifiedJavaDir);

			directoryUtil.mkdirs();

			qualifiedJavaFileName = manipulator.createPojoFileNameString(javaTestDirectory, fileHelper.getJavaPackage());

			File javaTestFile = new File(qualifiedJavaFileName);
			if (overwriteExistingJavaTest || !javaTestFile.exists()) {
				fileHelper.writeFile(qualifiedJavaFileName, pojoStr);
				System.out.println(pojoStr);
			} else {
				System.out.println("" + qualifiedJavaFileName + " already exists!! To overwrite use -DoverwriteExistJavaTest=true");
			}
		} catch (Exception ex) {
			Logger.getLogger(PojoCreateMojo.class.getName()).log(Level.SEVERE, null, ex);
		}
		return FileNameManipulator.replaceWithUnixSeparator(qualifiedJavaFileName);
	}

	public boolean isOverwriteExistingJavaTest() {
		return overwriteExistingJavaTest;
	}

	public void setOverwriteExistingJavaTest(boolean overwriteExistingJavaTest) {
		this.overwriteExistingJavaTest = overwriteExistingJavaTest;
	}

	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String getInputDir() {
		return inputDir;
	}

	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}
	public String getJavaTestDirectory() {
		return javaTestDirectory;
	}

	public void setJavaTestDirectory(String javaTestDirectory) {
		this.javaTestDirectory = javaTestDirectory;
	}

}
