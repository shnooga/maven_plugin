package com.vsp.enterprise.test;

import com.vsp.enterprise.test.helper.*;
import java.io.*;
import java.util.logging.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.*;

@Mojo(name = "unit_test_create", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class UnitTestCreateMojo extends AbstractMojo {

	@Parameter(defaultValue = "false", property = "overwriteExistJavaTest")
	private boolean overwriteExistingJavaTest;

	@Parameter(property = "inputFile")
	private String inputFile;

	@Parameter(defaultValue = "./target/rules", property = "resourceDir")
	private String fauxRuleDirectory;

	@Parameter(defaultValue = "./target/java", property = "javaTestDir")
	private String javaTestDirectory;

	@Parameter(defaultValue = "${basedir}/src/main/resources/UnitTestTemplate.txt", property = "templateFile")
	private String templateRuleFile;

	/**
	 * This generates 2 files: A slightly modified rule file from the original
	 * rule file. A java unit test file that will test the above modified rule
	 * file. NOTE: The generated modified rule file contains the same exact rule
	 * algorithm. Syntax unrelated to unit testing has been removed. ie
	 * "ruleflow-group"
	 */
	public void execute() {
		if (inputFile == null) {
			System.out.println("Missing inputFile param!!!");
			return;
		}
		FileHelper fileHelper = new FileHelper();
		createFauxRuleFile(fileHelper, inputFile);
		createUnitTestFile(fileHelper, inputFile);
	}

	/**
	 * @param fileHelper A helper object that reads & write files.
	 * @param origRuleFileName "./some/dir/myRule.drl"
	 * @return The freshly generated unit test file name, in unix dash format
	 * since windows can read the forward slash also. ie
	 * "./src/test/java/myOrigRuleTest.java" An empty string for failure.
	 */
	private String createUnitTestFile(FileHelper fileHelper, String origRuleFileName) {
		String qualifiedJavaFileName = "";
		try {
			FileNameManipulator manipulator = new FileNameManipulator(origRuleFileName);
			String qualifiedJavaDir = javaTestDirectory + File.separator + fileHelper.getJavaPackageAsPath();
			DirectoryUtil directoryUtil = new DirectoryUtil(qualifiedJavaDir);

			directoryUtil.mkdirs();

			qualifiedJavaFileName = manipulator.createJavaTestFileNameString(javaTestDirectory, fileHelper.getJavaPackage());

			File javaTestFile = new File(qualifiedJavaFileName);
			if (overwriteExistingJavaTest || !javaTestFile.exists()) {
				String javaFileName = FileNameManipulator.splitFileName(qualifiedJavaFileName)[1];
				String qualifiedFauxRuleFileName = manipulator.createRuleFauxFileNameString(fauxRuleDirectory, fileHelper.getJavaPackage());

				fileHelper.writeFile(qualifiedJavaFileName, fileHelper.readJavaTemplateFile(templateRuleFile, javaFileName, qualifiedFauxRuleFileName));
			} else {
				System.out.println("" + qualifiedJavaFileName + " already exists!! To overwrite use -DoverwriteExistJavaTest=true");
			}
		} catch (Exception ex) {
			Logger.getLogger(UnitTestCreateMojo.class.getName()).log(Level.SEVERE, null, ex);
		}
		return FileNameManipulator.replaceWithUnixSeparator(qualifiedJavaFileName);
	}

	/**
	 * @param fileHelper A helper object that reads & write files.
	 * @param origRuleFileName "./some/dir/myRule.drl"
	 * @return The freshly generated rule file name, in unix dash format since
	 * windows can read the forward slash also. ie "./target/myRuleFaux.drl" An
	 * empty string for failure.
	 */
	private String createFauxRuleFile(FileHelper fileHelper, String origRuleFileName) {
		String qualifiedFauxRuleFileName = "";

		try {
			FileNameManipulator manipulator = new FileNameManipulator(origRuleFileName);
			String ruleText = fileHelper.readDroolFile(origRuleFileName);

			manipulator = new FileNameManipulator(fauxRuleDirectory + File.separator + fileHelper.getJavaPackageAsPath() + File.separator + manipulator.extractFileNameAndExtension());

			DirectoryUtil directoryUtil = new DirectoryUtil(manipulator.extractParentFile());

			directoryUtil.mkdirs();
			qualifiedFauxRuleFileName = manipulator.createRuleFauxFileNameString(fauxRuleDirectory, fileHelper.getJavaPackage());
			fileHelper.writeFile(qualifiedFauxRuleFileName, ruleText);

		} catch (Exception ex) {
			Logger.getLogger(UnitTestCreateMojo.class.getName()).log(Level.SEVERE, null, ex);
		}
		return FileNameManipulator.replaceWithUnixSeparator(qualifiedFauxRuleFileName);
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

	public String getFauxRuleDirectory() {
		return fauxRuleDirectory;
	}

	public void setFauxRuleDirectory(String fauxRuleDirectory) {
		this.fauxRuleDirectory = fauxRuleDirectory;
	}

	public String getJavaTestDirectory() {
		return javaTestDirectory;
	}

	public void setJavaTestDirectory(String javaTestDirectory) {
		this.javaTestDirectory = javaTestDirectory;
	}

	public String getTemplateRuleFile() {
		return templateRuleFile;
	}

	public void setTemplateRuleFile(String templateRuleFile) {
		this.templateRuleFile = templateRuleFile;
	}

}
