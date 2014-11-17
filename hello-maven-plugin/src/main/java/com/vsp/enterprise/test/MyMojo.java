package com.vsp.enterprise.test;

import java.io.*;
import java.util.logging.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;

@Mojo(name = "touch", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MyMojo extends AbstractMojo {
	private String[] flaggedDroolSyntax = {"import .*", "ruleflow-group .*"};
	private String[] flaggedJavaSyntax  = {".*public class RuleTestTemplate.*", ".*String getRuleFileName.*"};

	@Parameter(defaultValue = "${basedir}/myrule.drl", property = "inputFile", required = true)
	private String inputFile;

	@Parameter(defaultValue = "${basedir}/src/main/resources", property = "outputDir", required = true)
	private File outputDirectory;

	@Parameter(defaultValue = "${basedir}/src/main/resources/ruletesttemplate.txt", property = "templateFile", required = true)
	private String templateRuleFile;

	public void execute() throws MojoExecutionException {
		File f = outputDirectory;

		if (!f.exists()) 
			f.mkdirs();

		File touch = new File(f, "touch.txt");

		FileWriter w = null;
		try {
			w = new FileWriter(touch);
			w.write("touch.txt");
		} catch (IOException e) {
			throw new MojoExecutionException("Error creating file " + touch, e);
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}

		String inputTxt;

		inputTxt = readDroolFile(inputFile);
		System.out.println(inputTxt);

		inputTxt = readJavaTemplateFile(templateRuleFile);
		System.out.println(inputTxt);
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
