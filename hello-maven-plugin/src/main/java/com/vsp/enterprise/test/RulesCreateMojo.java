package com.vsp.enterprise.test;

import com.vsp.enterprise.test.helper.*;
import java.io.*;
import java.util.List;
import java.util.logging.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.*;

@Mojo(name = "rules_create", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class RulesCreateMojo extends AbstractMojo {

	@Parameter(property = "inputDir")
	private String inputDir;

	@Parameter(defaultValue = "./target/rules", property = "resourceDir")
	private String fauxRuleDirectory;

	/**
	 * This generates a directory tree full of rule files and save them under
	 * the configurable resourceDir:
	 *
	 * NOTE: The generated rule files are slightly modified from the original
	 * rule file. The generated rule files contains the same exact algorithm.
	 * Only Syntax unrelated to unit testing has been removed. ie
	 * "ruleflow-group"
	 */
	public void execute() {
		try {

			if (inputDir == null) {
				System.out.println("Missing inputDir param. Rule Directory is mandatory!!!");
				return;
			}
			DirectoryUtil directoryUtil = new DirectoryUtil(inputDir);
			List<File> files = directoryUtil.ruleFilesSearch();
			FileHelper fileHelper = new FileHelper();

			for (File ruleFile : files) {
				System.out.println(ruleFile.getName() + " : " + ruleFile.getCanonicalPath());
				String fauxRuleFileName = createFauxRuleFile(fileHelper, ruleFile.getCanonicalPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public String getInputDir() {
		return inputDir;
	}

	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}

	public String getFauxRuleDirectory() {
		return fauxRuleDirectory;
	}

	public void setFauxRuleDirectory(String fauxRuleDirectory) {
		this.fauxRuleDirectory = fauxRuleDirectory;
	}
}
