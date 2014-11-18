package com.vsp.enterprise.test;

import java.io.*;
import java.util.logging.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;

@Mojo(name = "touch", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MyMojo extends AbstractMojo {
	public static final String RULE_NAME_MARKER = "Faux";
	public static final String JAVA_NAME_MARKER = "Test";
	private static final String[] flaggedDroolSyntax = {"import .*", ".*ruleflow-group .*"};

	@Parameter(defaultValue = "${basedir}/myrule.drl", property = "inputFile", required = true)
	private String inputFile;

	@Parameter(defaultValue = "${basedir}/src/main/resources", property = "resourceDir", required = true)
	private String resourceDirectory;

	@Parameter(defaultValue = "${basedir}/src/test/java/com/vsp/enterprise/test", property = "javaTestDir", required = true)
	private String javaTestDirectory;

	@Parameter(defaultValue = "${basedir}/src/main/resources/ruletesttemplate.txt", property = "templateFile", required = true)
	private String templateRuleFile;

	public void execute() throws MojoExecutionException {
		FileNameManipulator nameManipulator = new FileNameManipulator(inputFile);
		
		nameManipulator = new FileNameManipulator(resourceDirectory + File.separator + nameManipulator.extractFileName());
		String fullyQualifiedFileName = nameManipulator.postPendTextToFileName(RULE_NAME_MARKER);
		String ruleFileName = FileNameManipulator.extractFileName(fullyQualifiedFileName);

		writeFile(fullyQualifiedFileName, readDroolFile(inputFile));

		String[] fileNamePaths = nameManipulator.splitFileName();

		nameManipulator = new FileNameManipulator(javaTestDirectory + File.separator + fileNamePaths[1] + ".java");
		fullyQualifiedFileName = nameManipulator.postPendTextToFileName(JAVA_NAME_MARKER);
		String javaFileName = FileNameManipulator.splitFileName(fullyQualifiedFileName)[1];
		writeFile(fullyQualifiedFileName, readJavaTemplateFile(templateRuleFile, javaFileName, ruleFileName));
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
					// ignore
				}
			}
		}
	}

	private boolean containsFlaggedSyntax(String text) {
		for (String syntax: flaggedDroolSyntax) 
			if(text.matches(syntax))
				return true;
		return false;
	}

	private String readDroolFile(String fileName) {
		StringBuilder sb = new StringBuilder();

		try {
			FileReader fr = new FileReader(new File(fileName));
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				System.out.println(line);
				if(!containsFlaggedSyntax(line))
					sb.append(line).append("\n");
			}

			br.close();
			fr.close();
		} catch (Exception ex) {
			Logger.getLogger(MyMojo.class.getName()).log(Level.SEVERE, null, ex);
		}
		return sb.toString();
	}

	private String readJavaTemplateFile(String fileName, String className, String ruleName) {
		StringBuilder sb = new StringBuilder();

		try {
			FileReader fr = new FileReader(new File(fileName));
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				if(line.matches(".*public class RuleTestTemplate.*"))
					sb.append("public class ").append(className).append(" extends RuleHarness {\n");
				else if(line.matches(".*String getRuleFileName.*"))
					sb.append("String getRuleFileName() { return \"").append(ruleName).append("\"; }\n");
				else
					sb.append(line).append("\n");
			}
			br.close();
			fr.close();
		} catch (Exception ex) {
			Logger.getLogger(MyMojo.class.getName()).log(Level.SEVERE, null, ex);
		}
		return sb.toString();
	}
}
