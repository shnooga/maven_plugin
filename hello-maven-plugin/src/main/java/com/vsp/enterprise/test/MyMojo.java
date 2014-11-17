package com.vsp.enterprise.test;

import java.io.*;
import java.util.logging.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;

@Mojo(name = "touch", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MyMojo extends AbstractMojo {
	public static final String RULE_NAME_MARKER = "_faux";
	private String[] flaggedDroolSyntax = {"import .*", "ruleflow-group .*"};
	private String[] flaggedJavaSyntax  = {".*public class RuleTestTemplate.*", ".*String getRuleFileName.*"};

	@Parameter(defaultValue = "${basedir}/myrule.drl", property = "inputFile", required = true)
	private String inputFile;

	@Parameter(defaultValue = "${basedir}/src/main/resources", property = "outputDir", required = true)
	private String outputDirectory;

	@Parameter(defaultValue = "${basedir}/src/main/java/com/vsp/enterprise/test", property = "javaSourceDir", required = true)
	private String javaSourceDirectory;

	@Parameter(defaultValue = "${basedir}/src/main/resources/ruletesttemplate.txt", property = "templateFile", required = true)
	private String templateRuleFile;

	public void execute() throws MojoExecutionException {
		FileNameManipulator nameManipulator = new FileNameManipulator(inputFile);
		nameManipulator = new FileNameManipulator(outputDirectory + File.separator + nameManipulator.extractFileName());
		String newFileName = nameManipulator.postPendTextToFileName(RULE_NAME_MARKER);

		writeFile(newFileName, readDroolFile(inputFile));

		nameManipulator = new FileNameManipulator(javaSourceDirectory + File.separator + nameManipulator.extractFileName());
		newFileName = nameManipulator.postPendTextToFileName(RULE_NAME_MARKER);
		writeFile(newFileName, readJavaTemplateFile(templateRuleFile));

		String inputTxt;

		inputTxt = readDroolFile(inputFile);
		System.out.println(inputTxt);

		inputTxt = readJavaTemplateFile(templateRuleFile);
		System.out.println(inputTxt);
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

	private boolean containsFlaggedSyntax(String text, boolean isRule) {
		String[] flaggedSyntax = isRule ? flaggedDroolSyntax : flaggedJavaSyntax;
		for (String syntax: flaggedSyntax) 
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

			while ((line = br.readLine()) != null) 
				if(!containsFlaggedSyntax(line, true))
					sb.append(line).append("\n");

			br.close();
			fr.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(MyMojo.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(MyMojo.class.getName()).log(Level.SEVERE, null, ex);
		}
		return sb.toString();
	}

	private String readJavaTemplateFile(String fileName) {
		StringBuilder sb = new StringBuilder();

		try {
			FileReader fr = new FileReader(new File(fileName));
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) 
				if(!containsFlaggedSyntax(line, false))
					sb.append(line).append("\n");

			br.close();
			fr.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(MyMojo.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(MyMojo.class.getName()).log(Level.SEVERE, null, ex);
		}
		return sb.toString();
	}
}
