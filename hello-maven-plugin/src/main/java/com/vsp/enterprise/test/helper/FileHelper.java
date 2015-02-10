package com.vsp.enterprise.test.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
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
				if (line.matches(".*package .*")) 
					javaPackage = stripSemiColon(line);
				if (line.matches(".*import .*")) 
					importPackages.add(line);
				if (!containsFlaggedSyntax(line)) 
					sb.append(line).append("\n");
			}

			br.close();
			fr.close();
		} catch (Exception ex) {
			Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
		return sb.toString();
	}

	public String translateToPojo(String ruleFileName) {
		StringBuilder sb = new StringBuilder();

		try {
			FileReader fr = new FileReader(new File(ruleFileName));
			BufferedReader br = new BufferedReader(fr);
			String line;
			List<String> pojoLines = new ArrayList<String>();
			PojoBuilder builder = new PojoBuilder();

			sb.append("// This POJO was translated from a java class .\n");
			while ((line = br.readLine()) != null) {
				if (javaPackage.isEmpty() && line.matches(".*package .*")) {
					javaPackage = stripSemiColon(line);
					sb.append(line).append("\n\n");
				}
				if (line.matches(".*import .*")) {
					importPackages.add(line);
					sb.append(line).append("\n");
				}

				if (line.matches(".*class .*")) 
					sb.append("\n").append(line).append("\n");

				if (builder.isSetter(line))
					pojoLines.add(line);				
			}
			for(String l: pojoLines){
							sb.append(builder.buildProperty(l)).append("\n");
			}

			sb.append("\n");
			for(String l: pojoLines){
							sb.append(builder.buildGetterMethod(l)).append("\n");
							sb.append(builder.buildSetterMethod(l)).append("\n");
			}
			sb.append("}");
			br.close();
			fr.close();
		} catch (Exception ex) {
			Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
		return sb.toString();
	}

	/**
	 * Trim whitespaces then remove semicolon from the end of the text
	 * @param text
	 * @return
	 *   "  Blah blah;  " -> "Blah blah"
	 *   "   Yik yak    " -> "Yik yak"
	 */
	public String stripSemiColon(String text) {
		text = text.trim();
		int lastCharIndex = text.length() -1;
		return (text.charAt(lastCharIndex) == ';') ? text.substring(0, lastCharIndex) : text;
	}

	/**
	 * Read the java template file then create a modified version of it.
	 * @param templateFileName
	 *   Fully qualified name of the template file. 
	 *   ie "c:/src/main/resources/rulestemplate.txt"
	 * @param className
	 *   The java class name of this java unit test file. ie "MyRuleTest"
	 * @param ruleFileName
	 *   Fully qualified name of a rule file. ie "c:/myproj/target/myruleFaux.drl" 
	 * @return 
	 *   A string comprised of the given rule file
	 */
	public String readJavaTemplateFile(String templateFileName, String className, String ruleFileName) {
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(javaPackage).append(";\n\n");
			for (String s : importPackages) 
				sb.append(s).append("\n");
			FileReader fr = new FileReader(new File(templateFileName));
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				if (line.matches(".*public class RuleTestTemplate.*"))
					sb.append("public class ").append(className).append(" extends RuleHarness {\n");
				else if (line.matches(".*String getRuleFileName.*"))
					sb.append("\tpublic String getRuleFileName() { return \"").append(ruleFileName).append("\"; }\n");
				else
					sb.append(line).append("\n");
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

	/**
	 * My quick & dirty way to generate a pojo from an existing EJB file.
	 * @param fileName
	 * @return 
	 */
	public static void main(String[] args) {
		FileHelper instance = new FileHelper();

		System.out.println(instance.translateToPojo("./Claim.java"));
		if (args.length > 0) {
			System.out.println(instance.translateToPojo(args[0]));
		}
	}
}
