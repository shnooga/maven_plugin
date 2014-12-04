package com.vsp.enterprise.test.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class FilesReader {

	private String javaPackage = "";
	private final List<String> importPackages = new ArrayList<String>();
	private static final String[] flaggedDroolSyntax = {".*ruleflow-group .*"};

	/**
	 * @return The package structure only .ie "com.vsp.enterprise" 
	 */
	public String getJavaPackage() {
		String[] s = javaPackage.split(" ");
		return (s.length > 0) ? s[s.length-1] : "";
	}

	private boolean containsFlaggedSyntax(String text) {
		for (String syntax : flaggedDroolSyntax) {
			if (text.matches(syntax)) {
				return true;
			}
		}
		return false;
	}

	public String readDroolFile(String fileName) {
		StringBuilder sb = new StringBuilder();

		try {
			FileReader fr = new FileReader(new File(fileName));
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
//				if(line.matches("package .*"))
//					javaTestDirectory = createJavaTestDirString(line)
			}

			br.close();
			fr.close();
		} catch (Exception ex) {
			Logger.getLogger(FilesReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		return sb.toString();
	}

	/**
	 *
	 * @param fileName
	 * @param className
	 * @param ruleName
	 * @return
	 */
	public String readJavaTemplateFile(String fileName, String className, String ruleName) {
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(javaPackage).append("\n\n");
			for (String s : importPackages) {
				sb.append(s).append("\n");
			}

			FileReader fr = new FileReader(new File(fileName));
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				if (line.matches(".*public class RuleTestTemplate.*")) {
					sb.append("public class ").append(className).append(" extends RuleHarness {\n");
				} else if (line.matches(".*String getRuleFileName.*")) //					sb.append("public String getRuleFileName() { return \"").append(resourceDirectory).append(File.separator).append(ruleName).append("\"; }\n");
				{
					sb.append("\tpublic String getRuleFileName() { return \"").append(ruleName).append("\"; }\n");
				} else {
					sb.append(line).append("\n");
				}
			}
			br.close();
			fr.close();
		} catch (Exception ex) {
			Logger.getLogger(FilesReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		return sb.toString();
	}
}
