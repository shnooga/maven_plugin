package com.vsp.enterprise.test;

import com.vsp.enterprise.test.helper.*;
import java.io.*;
import java.util.logging.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.*;

@Mojo(name = "create", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class RuleCreateMojo extends AbstractMojo {

	public static final String RULE_NAME_MARKER = "Faux";
	public static final String JAVA_NAME_MARKER = "Test";
	private static final String[] flaggedDroolSyntax = {".*ruleflow-group .*"};

	@Parameter(defaultValue = "false", property = "overwriteExistJavaTest")
	private boolean overwriteExistingJavaTest;

	@Parameter(property = "inputFile")
	private String inputFile;

	@Parameter(defaultValue = "./target/rules", property = "resourceDir")
	private String fauxRuleDirectory;

	@Parameter(defaultValue = "./target/java", property = "javaTestDir")
	private String javaTestDirectory;

	@Parameter(defaultValue = "${basedir}/src/main/resources/ruletesttemplate.txt", property = "templateFile")
	private String templateRuleFile;

	/**
	 * This generates 2 files:
	 *   A slightly modified rule file from the original rule file.
	 *   A java unit test file that will test the above modified rule file. 
	 * NOTE: The generated modified rule file contains the same exact rule
	 * algorithm. Syntax unrelated to unit testing has been removed. ie
	 * "ruleflow-group"
	 */
	public void execute() {
		if (inputFile == null) {
			return;
		}
		FileHelper fileHelper = new FileHelper();
		String fauxRuleFileName = createFauxRuleFile(fileHelper, inputFile);
		String javaTestFileName = createUnitTestFile(fileHelper, fauxRuleFileName);
	}

	/**
	 * @return An object that represents the qualified file name
	 * "./target/myOrigRule.drl"
	 */
	private FileNameManipulator createRuleFileNameManipulator() {
		FileNameManipulator manipulator = new FileNameManipulator(inputFile);
		return new FileNameManipulator(fauxRuleDirectory + File.separator + manipulator.extractFileName());
	}

	/**
	 * @param fileHelper 
	 *   A helper object that reads & write files.
	 * @param qualifiedFauxRuleFileName
	 *   "./src/main/resources/myOrigRuleFaux.drl"
	 * @return 
	 *   The freshly generated unit test file name, in unix dash format since windows
	 *   	can read the forward slash also.  ie "./src/test/java/myOrigRuleTest.java" 
	 *   An empty string for failure.
	 */
	private String createUnitTestFile(FileHelper fileHelper, String qualifiedFauxRuleFileName) {
		String qualifiedJavaFileName = "";
		try {
			String qualifiedJavaDir = javaTestDirectory + File.separator + fileHelper.getJavaPackageAsPath();
			DirectoryUtil directoryUtil = new DirectoryUtil(qualifiedJavaDir);

			directoryUtil.mkdirs();

			FileNameManipulator ruleFileNameManipulator = createRuleFileNameManipulator();
			qualifiedJavaFileName = ruleFileNameManipulator.createJavaTestFileNameString(qualifiedJavaDir, JAVA_NAME_MARKER);

			File javaTestFile = new File(qualifiedJavaFileName);
			if (overwriteExistingJavaTest || !javaTestFile.exists()) {
				String javaFileName = FileNameManipulator.splitFileName(qualifiedJavaFileName)[1];
				fileHelper.writeFile(qualifiedJavaFileName, fileHelper.readJavaTemplateFile(templateRuleFile, javaFileName, qualifiedFauxRuleFileName));
			} else {
				System.out.println("" + qualifiedJavaFileName + " already exists!! To overwrite use -DoverwriteExistJavaTest=true");
			}
		} catch (Exception ex) {
			Logger.getLogger(RuleCreateMojo.class.getName()).log(Level.SEVERE, null, ex);
		}
		return FileNameManipulator.replaceWithUnixSeparator(qualifiedJavaFileName);
	}

	/**
	 * @param fileHelper 
	 *   A helper object that reads & write files.
	 * @param origRuleFileName 
	 *   "./some/dir/myRule.drl"
	 * @return 
	 *   The freshly generated rule file name, in unix dash format since windows
	 *   	can read the forward slash also.  ie  "./target/myRuleFaux.drl" 
	 *   An empty string for failure.
	 */
	private String createFauxRuleFile(FileHelper fileHelper, String origRuleFileName) {
		String qualifiedFauxRuleFileName = "";

		try {
			DirectoryUtil directoryUtil = new DirectoryUtil(fauxRuleDirectory);

			directoryUtil.mkdirs();
			qualifiedFauxRuleFileName = createRuleFileNameManipulator().postPendTextToFileName(RULE_NAME_MARKER);
			fileHelper.writeFile(qualifiedFauxRuleFileName, fileHelper.readDroolFile(origRuleFileName));

		} catch (Exception ex) {
			Logger.getLogger(RuleCreateMojo.class.getName()).log(Level.SEVERE, null, ex);
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
