/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsp.enterprise.test.helper;

import java.util.StringTokenizer;

/**
 *
 * @author Papa
 */
public class PojoBuilder {

				private String[] setterKeyWords = new String[]{" get", " is", " has"};

				public boolean isSetter(String line) {

								boolean retVal = line.matches(".*public.*");

								if (retVal) {
												for (String s : setterKeyWords) {
																if (line.contains(s)) {
																				break;
																}
																retVal = false;
												}
								}
								return retVal;
				}

				/**
				 *
				 * @param line
				 * @return [0] -> type [1]-> property name. ie "String", "name"
				 */
				public String[] extractPropertyName(String line) {
								StringTokenizer tokenizer = new StringTokenizer(line, " ");

								tokenizer.nextToken();
								String type = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
								String method = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
								int beginIndex = getSetterKeywordLength(method);
								int endIndex = (method.indexOf("(") == -1) ? method.length() - 1 : method.indexOf("(");

								String propertyName = method.substring(beginIndex, endIndex);
								return new String[]{type, propertyName};
				}

				private int getSetterKeywordLength(String methodName) {
								if (methodName.contains("get")) {
												return 3;
								}
								if (methodName.contains("has")) {
												return 3;
								}
								if (methodName.contains("is")) {
												return 2;
								}
								return 0;
				}

				private String lowerCaseFirstChar(String name) {
								StringBuilder sb = new StringBuilder(name.substring(0, 1).toLowerCase());
								sb.append(name.substring(1, name.length()));
								return sb.toString();

				}

				public String buildGetterMethod(String line) {
								StringBuilder sb = new StringBuilder();
								String[] pair = extractPropertyName(line);
								int index = line.indexOf("{");

								if (index != -1) {
												return sb.append(line.substring(0, index + 1)).append(" return ").append(lowerCaseFirstChar(pair[1])).append("; }").toString();
								} else if (line.indexOf(")") != -1) {

												return sb.append(line).append(" {").append(" return ").append(lowerCaseFirstChar(pair[1])).append("; }").toString();
								}
								return sb.append(line).append(") {").append(" return ").append(lowerCaseFirstChar(pair[1])).append("; }").toString();

				}

				public String buildSetterMethod(String line) {
								StringBuilder sb = new StringBuilder("\tpublic void set");
								String[] pair = extractPropertyName(line);
								String propName = lowerCaseFirstChar(pair[1]);

								sb.append(pair[1]).append("(").append(pair[0]).append(" ").append(propName).append(") { this.").append(propName).append(" = ").append(propName).append("; }");
								return sb.toString();

				}

				public String buildProperty(String line) {
								String[] pair = extractPropertyName(line);
								String propName = lowerCaseFirstChar(pair[1]);
								StringBuilder sb = new StringBuilder("\tprivate ");

								sb.append(pair[0]).append(" ").append(propName).append(";");
								return sb.toString();
				}

}
