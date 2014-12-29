package com.vsp.enterprise.test;

import com.vsp.enterprise.test.helper.DirectoryUtil;
import java.io.File;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hueyng
 */
public class UnitTestCreateMojoTest {

	private static final String FAUX_RULE_DIR = "./target/rules";
	private static final String JAVA_TEST_DIR = "./target/java";
	private static final String JAVA_TEMPLATE_FILE = "./src/main/resources/UnitTestTemplate.txt";

	private UnitTestCreateMojo instance = null;

	@Before
	public void setUp() throws Exception {
		instance = new UnitTestCreateMojo();

		instance.setOverwriteExistingJavaTest(true);
		instance.setFauxRuleDirectory(FAUX_RULE_DIR);
		instance.setJavaTestDirectory(JAVA_TEST_DIR);
		instance.setTemplateRuleFile(JAVA_TEMPLATE_FILE);
	}

	@After
	public void tearDown() throws Exception {
		DirectoryUtil directoryUtil;

		directoryUtil = new DirectoryUtil(FAUX_RULE_DIR);
		directoryUtil.delDir();
		directoryUtil = new DirectoryUtil(JAVA_TEST_DIR);
		directoryUtil.delDir();
		instance = null;
	}

	/**
	 * Given a drl filename; create a corresponding UnitTest class and Faux Rule
	 * file
	 */
	@Test
	public void testJavaUnitAndFauxRuleCreate() {
		instance.setInputFile("./myrule.drl");
		instance.execute();

		File fauxRuleFile = new File("./target/rules/com/entitlement/ProductEdit/Service/myruleFaux.drl");
		assertThat(fauxRuleFile.exists(), is(true));

		File javaTestFile = new File("./target/java/com/entitlement/ProductEdit/Service/myruleTest.java");
		assertThat(javaTestFile.exists(), is(true));
	}

	@Test
	public void testOverWriteExistingJavaTestFlag_false() {
		File javaTestFile;
		long timeStamp;

		// 1. First, create myRuleTest.java
		testJavaUnitAndFauxRuleCreate();
		javaTestFile = new File("./target/java/com/entitlement/ProductEdit/Service/myruleTest.java");
		timeStamp = javaTestFile.lastModified();

		// 2. Attempt to create myRuleTest.java again with overWriteFlag = false
		instance.setOverwriteExistingJavaTest(false);
		instance.setInputFile("./myrule.drl");
		instance.execute();

		javaTestFile = new File("./target/java/com/entitlement/ProductEdit/Service/myruleTest.java");
		assertThat(javaTestFile.lastModified(), is(timeStamp));
	}

	@Test
	public void testOverWriteExistingJavaTestFlag_true() {
		File javaTestFile;
		long timeStamp;

		// 1. First, create myRuleTest.java
		testJavaUnitAndFauxRuleCreate();
		javaTestFile = new File("./target/java/com/entitlement/ProductEdit/Service/myruleTest.java");
		timeStamp = javaTestFile.lastModified();

		// 2. Attempt to create myRuleTest.java again with overWriteFlag = true
		instance.setOverwriteExistingJavaTest(true);
		instance.setInputFile("./myrule.drl");
		instance.execute();

		javaTestFile = new File("./target/java/com/entitlement/ProductEdit/Service/myruleTest.java");
		assertThat(javaTestFile.lastModified(), is(not(timeStamp)));
	}

	/**
	 * Given a drl directory; recursively walk it and create corresponding
	 * UnitTest classes and Faux Rule files
	 */
	@Test
	public void testJavaUnitRecursiveCreate() {
		instance.setInputDir("./src/main/resources");
		instance.execute();

		File unitTestFile;
		unitTestFile = new File("./target/java/com/vsp/one/ruleATest.java");
		assertThat(unitTestFile.exists(), is(true));

		unitTestFile = new File("./target/java/com/vsp/one/two/ruleBTest.java");
		assertThat(unitTestFile.exists(), is(true));
	}

	/**
	 * When given no inputFile or inputDir params, no UnitTest class and FauxRule
	 * files will be created
	 */
	@Test
	public void testNoInputParams() {
		instance.execute();

		File generatedFile;
		generatedFile = new File(FAUX_RULE_DIR);
		assertThat(generatedFile.exists(), is(false));

		generatedFile = new File(JAVA_TEST_DIR);
		assertThat(generatedFile.exists(), is(false));
	}
}
