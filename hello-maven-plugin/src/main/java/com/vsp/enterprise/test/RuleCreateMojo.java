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

	@Parameter(defaultValue = "${basedir}/target", property = "resourceDir")
	private String resourceDirectory;

	@Parameter(defaultValue = "${basedir}/src/test/java", property = "javaTestDir")
	private String javaTestDirectory;

	@Parameter(defaultValue = "${basedir}/src/main/resources/ruletesttemplate.txt", property = "templateFile")
	private String templateRuleFile;

	/**
	 * This generates 2 files A slightly modified rule file from the original
	 * rule file A java unit test file that will test the above modified rule
	 * file NOTE: The generated modified rule file contains the same exact rule
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
	 * "./src/main/resources/myOrigRule.drl"
	 */
	private FileNameManipulator createRuleFileNameManipulator() {
		FileNameManipulator manipulator = new FileNameManipulator(inputFile);
		return new FileNameManipulator(resourceDirectory + File.separator + manipulator.extractFileName());
	}

	/**
	 * @param fileHelper A helper object that reads & write files.
	 * @param qualifiedFauxRuleFileName
	 * "./src/main/resources/myOrigRuleFaux.drl"
	 * @return The freshly generated qualified unit test file name, ie
	 * "./src/test/java/myOrigRuleTest.java" An empty string for failure.
	 */
	private String createUnitTestFile(FileHelper fileHelper, String qualifiedFauxRuleFileName) {
		String qualifiedJavaFileName = "";
		try {
			String qualifiedJavaDir = javaTestDirectory + File.separator + fileHelper.getJavaPackageAsPath();
			DirectoryCreator directoryUtil = new DirectoryCreator(qualifiedJavaDir);

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
		return qualifiedJavaFileName;
	}

	/**
	 * @param fileHelper A helper object that reads & write files.
	 * @param origRuleFileName "./some/dir/myOrigRule.drl"
	 * @return The freshly generated rule file name, ie
	 * "./src/main/resources/myOrigRuleFaux.drl" An empty string for failure.
	 */
	private String createFauxRuleFile(FileHelper fileHelper, String origRuleFileName) {
		String qualifiedFauxRuleFileName = "";

		try {
			qualifiedFauxRuleFileName = createRuleFileNameManipulator().postPendTextToFileName(RULE_NAME_MARKER);
			fileHelper.writeFile(qualifiedFauxRuleFileName, fileHelper.readDroolFile(origRuleFileName));

		} catch (Exception ex) {
			Logger.getLogger(RuleCreateMojo.class.getName()).log(Level.SEVERE, null, ex);
		}
		return qualifiedFauxRuleFileName;
	}
}
