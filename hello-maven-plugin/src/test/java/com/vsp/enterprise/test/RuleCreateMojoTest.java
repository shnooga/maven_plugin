package com.vsp.enterprise.test;

import java.io.File;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author hueyng
 */
public class RuleCreateMojoTest {
	/**
	 * Test of execute method, of class RuleCreateMojo.
	 */
	@Test
	public void testExecute() {
		RuleCreateMojo instance = new RuleCreateMojo();

		instance.setOverwriteExistingJavaTest(true);
		instance.setInputFile("./myrule.drl");
//		instance.setInputFile("c:/trash/CLM02049.drl");
//		instance.setInputFile("c:/trash/CLM02050.drl");
		instance.setFauxRuleDirectory("./target/rules");
		instance.setJavaTestDirectory("./target/java");
		instance.setTemplateRuleFile("./src/main/resources/UnitTestTemplate.txt");

		instance.execute();
		
		File fauxRuleFile = new File("./target/rules/myruleFaux.drl");
		assertThat(fauxRuleFile.exists(), is(true));

		File javaTestFile = new File("./target/java/com/entitlement/ProductEdit/Service/myruleTest.java");
		assertThat(javaTestFile.exists(), is(true));
	}

}
