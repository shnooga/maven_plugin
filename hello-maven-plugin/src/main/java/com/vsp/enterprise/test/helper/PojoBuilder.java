package com.vsp.enterprise.test.helper;

import java.util.StringTokenizer;
import static com.vsp.enterprise.test.helper.SetterKeyWord.*;

public class PojoBuilder {

	public boolean isSetter(String line) {
		if (!line.matches(".*public.*"))
			return false;

		for (SetterKeyWord keyWord : SetterKeyWord.values()) {
			if (line.contains(keyWord.methodPrefix()))
				return true;
		}
		return false;
	}

	/**
	 * @param line "public String getName() {"
	 * @return [0] -> type [1]-> property name. ie {"String", "name"}
	 */
	public String[] extractPropertyName(String line) {
		StringTokenizer tokenizer = new StringTokenizer(line, " ");

		tokenizer.nextToken();

		String type		= tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
		String method	= tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
		int beginIndex	= getSetterKeywordLength(method);
		int endIndex 	= (method.indexOf("(") == -1) 
						? method.length() - 1
						: method.indexOf("(");

		String propertyName = method.substring(beginIndex, endIndex);
		return new String[] { type, propertyName };
	}

	private int getSetterKeywordLength(String methodName) {
		 for(SetterKeyWord keyWord : SetterKeyWord.values()) 
			 if (methodName.contains(keyWord.text())) 
				 return keyWord.length();
		return 0;
	}

	private String lowerCaseFirstChar(String name) {
		StringBuilder sb = new StringBuilder(name.substring(0, 1).toLowerCase());
		sb.append(name.substring(1, name.length()));
		return sb.toString();

	}

	public String buildGetterMethod(String line) {
		line = swapAddForGetKeyword(line);

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

	private boolean isAddMethod(String line) {
		return (isSetter(line) && line.contains(ADD.methodPrefix()));
	}

	private String swapAddForGetKeyword(String line) {
		if(!isAddMethod(line))
			return line;

		int index = line.indexOf(ADD.text());
		StringBuilder sb = new StringBuilder(line.substring(0, index));

		sb.append(GET.text()).append(line.substring(index + 3));
		return sb.toString();
	}

	public String buildSetterMethod(String line) {
		boolean isAddMethod	= isAddMethod(line);
		StringBuilder sb 	= new StringBuilder("\t");
		String[] pair		= extractPropertyName(line);
		String propName		= lowerCaseFirstChar(pair[1]);

		sb.append(isAddMethod ? line : "public void set");
		if(!isAddMethod)
			sb.append(pair[1]).append("(").append(pair[0]).append(" ").append(propName).append(")");
		sb.append(" { this.").append(propName).append(" = ").append(propName).append("; }");
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
