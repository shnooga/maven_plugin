package com.vsp.enterprise.test.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.maven.plugin.MojoExecutionException;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FileHelperTest {

	private String ruleFileName;
	private String javaTemplateFileName;

	public FileHelperTest() {
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
		FileHelper instance = new FileHelper();

		instance.readDroolFile(ruleFileName);
		assertThat(instance.getJavaPackage(), is("rules.entitlement.ProductEdit.Service"));
	}

	@Test
	public void testGetJavaPackageAsPath() {
		FileHelper instance = new FileHelper();
		String expectedPath = "rules" + File.separator + "entitlement" + File.separator + "ProductEdit" + File.separator + "Service";

		instance.readDroolFile(ruleFileName);
		assertThat(instance.getJavaPackageAsPath(), is(expectedPath));
	}

	/**
	 * Test of readDroolFile method, of class FileHelper.
	 */
	@Test
	public void testReadDroolFile() {
		FileHelper instance = new FileHelper();
		String text = instance.readDroolFile(ruleFileName);

		assertThat(matches(".*package rules.entitlement.ProductEdit.Service.*", text), is(true));
	}

	/**
	 * Test of readJavaTemplateFile method, of class FileHelper.
	 */
	@Test
	public void testReadJavaTemplateFile() {
		FileHelper instance = new FileHelper();
		// Must read the drool file to extract the package & import string	
		instance.readDroolFile(ruleFileName);
		
		// This will tack on the package string extracted from the drool file
		String text = instance.readJavaTemplateFile(javaTemplateFileName, "MyUnitTest", "somerule.drl");
		// Look for the package string
		assertThat(matches(".*package rules.entitlement.ProductEdit.Service.*", text), is(true));
		// Look for the import string
		assertThat(matches(".*import com.vsp.arch.data.types.VSPDate;.*", text), is(true));
	}

	private boolean matches(String regex, String text) {
		Pattern p = Pattern.compile(regex, Pattern.DOTALL);
		return p.matcher(text).matches();
	}

	@Test
	public void testWriteFile() {
		final String FILE_NAME = "./target/testfile.txt";
		final String TEXT = "check baby check";

		try {
			FileHelper instance = new FileHelper();
			instance.writeFile(FILE_NAME, TEXT);
			File f = new File(FILE_NAME);

			assertThat(f.exists(), is(true));
		} catch (MojoExecutionException ex) {
			fail("Unable to write file" + FILE_NAME);
		} 

		FileReader fr = null;
		try {
			fr = new FileReader(new File(FILE_NAME));
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();

			br.close();
			fr.close();
			assertThat(matches(TEXT, line), is(true));
		} catch (IOException ex) {
			fail("Unable to read file" + FILE_NAME);
		} finally {
			try {
				fr.close();
			} catch (IOException ex) {
				fail("Unable to close file" + FILE_NAME);
			}
		}
	}
}
