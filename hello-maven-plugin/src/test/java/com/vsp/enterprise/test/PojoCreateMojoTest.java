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
public class PojoCreateMojoTest {

	private static final String JAVA_TEST_DIR = "./target/java";

	private PojoCreateMojo instance = null;

	@Before
	public void setUp() throws Exception {
		instance = new PojoCreateMojo();
		instance.setOverwriteExistingJavaTest(true);
		instance.setJavaTestDirectory(JAVA_TEST_DIR);
	}

	@After
	public void tearDown() throws Exception {
		DirectoryUtil directoryUtil = new DirectoryUtil(JAVA_TEST_DIR);

		directoryUtil.delDir();
		instance = null;
	}

	/**
	 * Given a java filename; create a corresponding POJO class
	 */
	@Test
	public void testPojoCreate() {
		instance.setInputFile("./Claim.java");
		instance.execute();


		File pojoFile = new File("./target/java/com/vsp/enterprise/busobj/entity/claim/Claim.java");
		assertThat(pojoFile.exists(), is(true));
	}

	@Test
	public void testOverWriteExistingPojo_Flag_false() {
		File pojoFile;
		long timeStamp;

		// 1. First, create POJO
		testPojoCreate();
		pojoFile = new File("./target/java/com/vsp/enterprise/busobj/entity/claim/Claim.java");
		timeStamp = pojoFile.lastModified();

		// 2. Attempt to create POJO again with overWriteFlag = false
		instance.setOverwriteExistingJavaTest(false);
		instance.setInputFile("./Claim.java");
		instance.execute();

		pojoFile = new File("./target/java/com/vsp/enterprise/busobj/entity/claim/Claim.java");
		assertThat(pojoFile.lastModified(), is(timeStamp));
	}

	@Test
	public void testOverWriteExistingPojo_Flag_true() throws InterruptedException {
		File pojoFile;
		long timeStamp;

		// 1. First, create POJO
		testPojoCreate();
		pojoFile = new File("./target/java/com/vsp/enterprise/busobj/entity/claim/Claim.java");
		timeStamp = pojoFile.lastModified();
		Thread.currentThread().sleep(100);

		// 2. Attempt to create POJO again with overWriteFlag = true
		instance.setOverwriteExistingJavaTest(true);
		instance.setInputFile("./Claim.java");
		instance.execute();

		pojoFile = new File("./target/java/com/vsp/enterprise/busobj/entity/claim/Claim.java");
		assertThat(pojoFile.lastModified(), is(not(timeStamp)));
	}

}
