package com.vsp.enterprise.test;

import com.vsp.enterprise.test.helper.*;
import java.io.*;
import java.util.logging.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.*;

@Mojo(name = "pojo_create", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class PojoCreateMojo extends AbstractMojo {

	@Parameter(defaultValue = "false", property = "overwriteExistJavaTest")
	private boolean overwriteExistingJavaTest;

	@Parameter(property = "inputFile")
	private String inputFile;

	@Parameter(defaultValue = "./target/java", property = "javaTestDir")
	private String javaTestDirectory;

	@Parameter(defaultValue = "${basedir}/src/main/resources/UnitTestTemplate.txt", property = "templateFile")
	private String templateRuleFile;

	public void execute() {
		if ((inputFile == null)) {
			System.out.println("inputFile param is mandatory!!!");
			return;
		}

		if (inputFile != null) {
			createPojoFile(inputFile);
		}
	}

	private void createPojoFile(String origRuleFileName) {
		FileHelper fileHelper = new FileHelper();

		createPojoFile(fileHelper, origRuleFileName);
	}

	/**
	 * @param fileHelper A helper object that reads & write files.
	 * @param origRuleFileName "./some/dir/myRule.drl"
	 * @return The freshly generated unit test file name, in unix dash format
	 * since windows can read the forward slash also. ie
	 * "./src/test/java/myOrigRuleTest.java" An empty string for failure.
	 */
	private String createPojoFile(FileHelper fileHelper, String origRuleFileName) {
		String qualifiedJavaFileName = "";
		try {
			String pojoStr = fileHelper.readJavaFile(origRuleFileName);
			FileNameManipulator manipulator = new FileNameManipulator(origRuleFileName);
			String qualifiedJavaDir = javaTestDirectory + File.separator + fileHelper.getJavaPackageAsPath();
			DirectoryUtil directoryUtil = new DirectoryUtil(qualifiedJavaDir);

			directoryUtil.mkdirs();

			qualifiedJavaFileName = manipulator.createJavaTestFileNameString(javaTestDirectory, fileHelper.getJavaPackage());

			File javaTestFile = new File(qualifiedJavaFileName);
			if (overwriteExistingJavaTest || !javaTestFile.exists()) {
				String javaFileName = FileNameManipulator.splitFileName(qualifiedJavaFileName)[1];
				System.out.println(pojoStr);
				fileHelper.writeFile(qualifiedJavaFileName, pojoStr);
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

	public String getJavaTestDirectory() {
		return javaTestDirectory;
	}

	public void setJavaTestDirectory(String javaTestDirectory) {
		this.javaTestDirectory = javaTestDirectory;
	}

}