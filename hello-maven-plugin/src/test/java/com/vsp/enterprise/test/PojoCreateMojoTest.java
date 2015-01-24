package com.vsp.enterprise.test;

import com.vsp.enterprise.test.helper.DirectoryUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

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

		File pojoFile = new File(
				"./target/java/com/vsp/enterprise/busobj/entity/claim/Claim.java");
		assertThat(pojoFile.exists(), is(true));
	}

	@Test
	public void testOverWriteExistingPojo_Flag_false() {
		File pojoFile;
		long timeStamp;

		// 1. First, create POJO
		testPojoCreate();
		pojoFile = new File(
				"./target/java/com/vsp/enterprise/busobj/entity/claim/Claim.java");
		timeStamp = pojoFile.lastModified();

		// 2. Attempt to create POJO again with overWriteFlag = false
		instance.setOverwriteExistingJavaTest(false);
		instance.setInputFile("./Claim.java");
		instance.execute();

		pojoFile = new File(
				"./target/java/com/vsp/enterprise/busobj/entity/claim/Claim.java");
		assertThat(pojoFile.lastModified(), is(timeStamp));
	}

	// This test gives erroneous false sporadically =(
//	@Test
	public void testOverWriteExistingPojo_Flag_true()
			throws InterruptedException {
		File pojoFile;
		long timeStamp;

		// 1. First, create POJO
		testPojoCreate();
		pojoFile = new File(
				"./target/java/com/vsp/enterprise/busobj/entity/claim/Claim.java");
		timeStamp = pojoFile.lastModified();
		dumpFileAttributes("testOverWriteExistingPojo_Flag_true", "./target/java/com/vsp/enterprise/busobj/entity/claim/Claim.java");
		Thread.currentThread().sleep(3000);

		// 2. Attempt to create POJO again with overWriteFlag = true
		instance.setOverwriteExistingJavaTest(true);
		instance.setInputFile("./Claim.java");
		instance.execute();

		pojoFile = new File(
				"./target/java/com/vsp/enterprise/busobj/entity/claim/Claim.java");
		dumpFileAttributes("testOverWriteExistingPojo_Flag_true", "./target/java/com/vsp/enterprise/busobj/entity/claim/Claim.java");
		assertThat(pojoFile.lastModified(), is(not(timeStamp)));
	}

	private void dumpFileAttributes(String title, String filePath) {
		try {
			Path path = Paths.get(filePath);
			BasicFileAttributes attr;
			attr = Files.readAttributes(path, BasicFileAttributes.class);
			System.out.println("\n ~~~~~~~~~~~~ " + title +"~~~~~~~~~~~~");

			System.out.println("creationTime: " + attr.creationTime());
			System.out.println("lastAccessTime: " + attr.lastAccessTime());
			System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

//			System.out.println("isDirectory: " + attr.isDirectory());
//			System.out.println("isOther: " + attr.isOther());
//			System.out.println("isRegularFile: " + attr.isRegularFile());
//			System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
//			System.out.println("size: " + attr.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
