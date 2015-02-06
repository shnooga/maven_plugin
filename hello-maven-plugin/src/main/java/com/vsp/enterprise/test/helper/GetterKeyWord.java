package com.vsp.enterprise.test.helper;

public enum GetterKeyWord {
	GET(" get", "get"),
	IS(" is", "is"),
	HAS(" has", "has"),
	ADD(" add", "add");
	
	private String methodPrefix;
	private String text;
	private int length;
	
	GetterKeyWord(String methodPrefix, String text) {
		this.methodPrefix = methodPrefix;
		this.text = text;
		this.length = text.length();
	}

	public String methodPrefix() {
		return methodPrefix;
	}

	public String text() {
		return text;
	}

	public int length() {
		return length;
	}
}
