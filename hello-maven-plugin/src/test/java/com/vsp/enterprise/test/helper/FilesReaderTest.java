package com.vsp.enterprise.test.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FilesReaderTest {

	private String ruleFileName;
	private String javaTemplateFileName;

	public FilesReaderTest() {
	}

	@Before
	public void setUp() {
		ruleFileName = "./myrule.drl";
		javaTemplateFileName = "./src/main/resources/ruletesttemplate.txt";
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testGetJavaPackage() {
		FilesReader instance = new FilesReader();

		instance.readDroolFile(ruleFileName);
		assertThat(instance.getJavaPackage(), is("rules.entitlement.ProductEdit.Service"));
	}

	@Test
	public void testGetJavaPackageAsPath() {
		FilesReader instance = new FilesReader();

		instance.readDroolFile(ruleFileName);
		assertThat(instance.getJavaPackageAsPath(), is("rules/entitlement/ProductEdit/Service"));
	}

	/**
	 * Test of readDroolFile method, of class FilesReader.
	 */
	@Test
	public void testReadDroolFile() {
		FilesReader instance = new FilesReader();
		String result = instance.readDroolFile(ruleFileName);

		Pattern p = Pattern.compile(".*package rules.entitlement.ProductEdit.Service.*", Pattern.DOTALL);
		Matcher m = p.matcher(result);

		assertThat(m.matches(), is(true));
	}

	/**
	 * Test of readJavaTemplateFile method, of class FilesReader.
	 */
	@Test
	public void testReadJavaTemplateFile() {
		FilesReader instance = new FilesReader();
		String result;
		// Must read the drool file to extract the package & import string	
		instance.readDroolFile(ruleFileName);
		// This will tack on the package string extracted from the drool file
		result = instance.readJavaTemplateFile(javaTemplateFileName, "MyUnitTest", "somerule.drl");

		Pattern p;
		Matcher m;

		p = Pattern.compile(".*package rules.entitlement.ProductEdit.Service.*", Pattern.DOTALL);
		m = p.matcher(result);
		// Look for the package string
		assertThat(m.matches(), is(true));

		p = Pattern.compile(".*import com.vsp.arch.data.types.VSPDate;.*", Pattern.DOTALL);
		m = p.matcher(result);
		// Look for the import string
		assertThat(m.matches(), is(true));
	}

}
