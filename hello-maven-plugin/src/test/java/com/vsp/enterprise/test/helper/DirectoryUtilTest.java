package com.vsp.enterprise.test.helper;

import java.io.File;
import java.util.List;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hueyng
 */
public class DirectoryUtilTest {
	private String singleDir;
	private String multiDir;
	private String multiDelDir;

	@Before
	public void setUp() {
		singleDir 	= OsUtils.isWindows() ? "c:/trash/delme1" : "./delme1";
		multiDir 	= OsUtils.isWindows() ? "c:/trash/delme2/again/please" : "./delme2/again/please";
		multiDelDir = OsUtils.isWindows() ? "c:/trash/delme2" : "./delme2";
	}

	@After
	public void tearDown() {
		DirectoryUtil instance;

		instance = new DirectoryUtil(singleDir);
		instance.delDir();
		instance = new DirectoryUtil(multiDelDir);
		instance.delDir();
	}

	@Test
	public void testExistenceOfBogusDir() {
		String dir = OsUtils.isWindows() ? "c:/yik/yak" : "/yik/yak";
		DirectoryUtil instance = new DirectoryUtil(dir);
		assertThat(instance.exists(), is(false));
	}

	@Test
	public void testExistenceOfValidDir() {
		String dir = OsUtils.isWindows() ? "c:/trash" : "/opt";
		DirectoryUtil instance = new DirectoryUtil(dir);

		assertThat(instance.exists(), is(true));
	}

	@Test
	public void testMkdir() {
		DirectoryUtil instance = new DirectoryUtil(singleDir);
		assertThat(instance.mkdir(), is(true));
	}

	@Test
	public void testMkdirs() {
		DirectoryUtil instance = new DirectoryUtil(multiDir);
		assertThat(instance.mkdirs(), is(true));
	}

	@Test
	public void testDelDir() {
		String dir = OsUtils.isWindows() ? "c:/trash/delme3/again/please" : "./delme3/again/please";
		String delDir = OsUtils.isWindows() ? "c:/trash/delme3" : "./delme3";
		DirectoryUtil instance;

		instance = new DirectoryUtil(dir);
		instance.mkdirs();

		instance = new DirectoryUtil(delDir);
		instance.delDir();
		File f = new File(delDir);
		assertThat(f.exists(), is(false));
	}

	@Test
	public void testRuleFilesSearch() {
		DirectoryUtil instance = new DirectoryUtil("./src/main/resources");
		List<File> filesFound = instance.ruleFilesSearch();
		assertThat(filesFound.size(), is(2));
		for(File file : filesFound) {
			assertThat(file.getName(), isOneOf("ruleA.drl", "ruleB.drl"));
		}
	}

	@Test
	public void testFilesSearch() {
		DirectoryUtil instance = new DirectoryUtil("./src/main/java");
		List<File> filesFound = instance.filesSearch("Rule.+\\.java");
		assertThat(filesFound.size(), is(1));
		for(File file : filesFound) {
			assertThat(file.getName(), isOneOf("RulesCreateMojo.java", "RuleTestConstants.java"));
		}
	}

}
