package com.vsp.enterprise.test;

import java.io.File;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author hueyng
 */
public class RulesCreateMojoTest {

	/**
	 * Test of execute method, of class RulesCreateMojo.
	 */
	@Test
	public void testExecute() {
		RulesCreateMojo instance = new RulesCreateMojo();

		instance.setInputFile("./src/main/resources");
		instance.setFauxRuleDirectory("./target/rules");

		instance.execute();

		File fauxRuleFile;
		fauxRuleFile = new File("./target/rules/com/vsp/one/ruleAFaux.drl");
		assertThat(fauxRuleFile.exists(), is(true));

		fauxRuleFile = new File("./target/rules/com/vsp/one/two/ruleBFaux.drl");
		assertThat(fauxRuleFile.exists(), is(true));
	}

}
