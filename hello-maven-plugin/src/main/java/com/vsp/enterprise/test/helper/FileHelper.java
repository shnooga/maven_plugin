package com.vsp.enterprise.test.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import org.apache.maven.plugin.MojoExecutionException;

public class FileHelper {

	private String javaPackage = "";
	private final List<String> importPackages = new ArrayList<String>();
	private static final String[] flaggedDroolSyntax = {".*ruleflow-group .*"};

	/**
	 * @return The package structure only ie "com.vsp.enterprise" 
	 */
	public String getJavaPackage() {
		String[] s = javaPackage.split(" ");
		return (s.length > 0) ? s[s.length-1] : "";
	}

	/**
	 * 
	 * @return The package structure as a path 
	 * ie. "com.vsp.enterprise" -> "com/vsp/enterprise" 
	 */
	public String getJavaPackageAsPath() {
		return getJavaPackage().replace(".", File.separator);
	}

	private boolean containsFlaggedSyntax(String text) {
		for (String syntax : flaggedDroolSyntax) {
			if (text.matches(syntax)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Read a give rule file name then create a modified version of it.
	 * 
	 * @param ruleFileName
	 *   Fully qualified name of the rule file. ie "c:/mydir/somerule.drl"
	 * @return 
	 *   A string comprised of the given rule file
	 */
	public String readDroolFile(String ruleFileName) {
		StringBuilder sb = new StringBuilder();

		try {
			FileReader fr = new FileReader(new File(ruleFileName));
			BufferedReader br = new BufferedReader(fr);
			String line;

			sb.append("// Do not modify this file; modify the original file instead.\n");
			sb.append("// This file is autogenerated every time Maven test is called.\n");
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				if (line.matches(".*package .*")) {
					javaPackage = line;
				}
				if (line.matches(".*import .*")) {
					importPackages.add(line);
				}

				if (!containsFlaggedSyntax(line)) {
					sb.append(line).append("\n");
				}
			}

			br.close();
			fr.close();
		} catch (Exception ex) {
			Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
		return sb.toString();
	}

	/**
	 * Read the java template file then create a modified version of it.
	 * @param templateFileName
	 *   Fully qualified name of the template file. 
	 *   ie "c:/src/main/resources/rulestemplate.txt"
	 * @param className
	 *   The java class name of this java unit test file. ie "MyRuleTest"
	 * @param ruleName
	 *   Fully qualified name of a rule file. ie "c:/myproj/target/myruleFaux.drl" 
	 * @return 
	 *   A string comprised of the given rule file
	 */
	public String readJavaTemplateFile(String templateFileName, String className, String ruleName) {
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(javaPackage).append("\n\n");
			for (String s : importPackages) {
				sb.append(s).append("\n");
			} 
			FileReader fr = new FileReader(new File(templateFileName));
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				if (line.matches(".*public class RuleTestTemplate.*")) {
					sb.append("public class ").append(className).append(" extends RuleHarness {\n");
				} else if (line.matches(".*String getRuleFileName.*")) {
//					sb.append("public String getRuleFileName() { return \"").append(resourceDirectory).append(File.separator).append(ruleName).append("\"; }\n");
					sb.append("\tpublic String getRuleFileName() { return \"").append(ruleName).append("\"; }\n");
				} else {
					sb.append(line).append("\n");
				}
			}
			br.close();
			fr.close();
		} catch (Exception ex) {
			Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
		return sb.toString();
	}

	/**
	 * Create a file with the text data.
	 * @param fileName
	 *   Fully qualified file name. 
	 * @param text
	 *   Data to write in the file.
	 * @throws MojoExecutionException 
	 */
	public void writeFile(String fileName, String text) throws MojoExecutionException {
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