package com.vsp.enterprise.test;

import com.vsp.enterprise.test.helper.*;
import java.io.*;
import java.util.logging.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
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

	public void execute() throws MojoExecutionException {
		if(inputFile == null)
			return;
		FileNameManipulator manipulator = new FileNameManipulator(inputFile);
		FileNameManipulator ruleFileNameManipulator = new FileNameManipulator(resourceDirectory + File.separator + manipulator.extractFileName());
		String qualifiedRuleFileName = ruleFileNameManipulator.postPendTextToFileName(RULE_NAME_MARKER);
		String ruleFileName = FileNameManipulator.extractFileName(qualifiedRuleFileName);

		FilesReader filesReader = new FilesReader();
		writeFile(qualifiedRuleFileName, filesReader.readDroolFile(inputFile));

		// Creation of java unit test file
		String qualifiedJavaDir = javaTestDirectory + File.separator + filesReader.getJavaPackageAsPath();
		String qualifiedJavaFileName = ruleFileNameManipulator.createJavaTestFileNameString(qualifiedJavaDir, JAVA_NAME_MARKER);
		DirectoryCreator directoryUtil = new DirectoryCreator(qualifiedJavaDir);

		directoryUtil.mkdirs();

		File javaTestFile = new File(qualifiedJavaFileName);
		if(overwriteExistingJavaTest || !javaTestFile.exists()){
			String javaFileName = FileNameManipulator.splitFileName(qualifiedJavaFileName)[1];
			writeFile(qualifiedJavaFileName, filesReader.readJavaTemplateFile(templateRuleFile, javaFileName, ruleFileName));
		} else {
			System.out.println("" + qualifiedJavaFileName + " already exists!! To overwrite use -DoverwriteExistJavaTest=true");
		}
	}

	private void writeFile(String fileName, String text) throws MojoExecutionException {
		FileWriter w = null;
		try {
			w = new FileWriter(new File(fileName));
			w.write(text);
		} catch (IOException e) {
			throw new MojoExecutionException("Error creating file " + fileName, e);
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
