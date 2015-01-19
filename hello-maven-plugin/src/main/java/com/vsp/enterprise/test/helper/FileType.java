package com.vsp.enterprise.test.helper;

/**
 *
 * @author hueyng
 */
public enum FileType {

	JAVA("Java", ".java", "Test"), 
	POJO("Pojo", ".java", ""), 
	RULE("Rule", ".drl", "Faux");

	private final String description;
	private final String extension;
	private final String marker;

	FileType(String description, String extension, String marker) {
		this.description = description;
		this.extension = extension;
		this.marker = marker;
	}

	public String description() {
		return description;
	}

	public String extension() {
		return extension;
	}

	public String marker() {
		return marker;
	}
}
