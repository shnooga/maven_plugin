package com.vsp.enterprise.test.helper;

import java.io.File;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hueyng
 */
public class DirectoryCreatorTest {
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
		DirectoryCreator instance;

		instance = new DirectoryCreator(singleDir);
		instance.delDir();
		instance = new DirectoryCreator(multiDelDir);
		instance.delDir();
	}

	@Test
	public void testExistenceOfBogusDir() {
		String dir = OsUtils.isWindows() ? "c:/yik/yak" : "/yik/yak";
		DirectoryCreator instance = new DirectoryCreator(dir);
		assertThat(instance.exists(), is(false));
	}

	@Test
	public void testExistenceOfValidDir() {
		String dir = OsUtils.isWindows() ? "c:/trash" : "/opt";
		DirectoryCreator instance = new DirectoryCreator(dir);

		assertThat(instance.exists(), is(true));
	}

	@Test
	public void testMkdir() {
		DirectoryCreator instance = new DirectoryCreator(singleDir);
		assertThat(instance.mkdir(), is(true));
		System.out.println("testMkdir");
	}

	@Test
	public void testMkdirs() {
		DirectoryCreator instance = new DirectoryCreator(multiDir);
		assertThat(instance.mkdirs(), is(true));
		System.out.println("hi");
	}

	@Test
	public void testDelDir() {
		String dir = OsUtils.isWindows() ? "c:/trash/delme3/again/please" : "./delme3/again/please";
		String delDir = OsUtils.isWindows() ? "c:/trash/delme3" : "./delme3";
		DirectoryCreator instance;

		instance = new DirectoryCreator(dir);
		instance.mkdirs();

		instance = new DirectoryCreator(delDir);
		instance.delDir();
		File f = new File(delDir);
		assertThat(f.exists(), is(false));
	}

}
