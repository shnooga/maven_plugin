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

	@Before
	public void setUp() {
		singleDir = OsUtils.isWindows() ? "c:/trash/delme1" : "/opt/delme1";
		multiDir = OsUtils.isWindows() ? "c:/trash/delme2/again/please" : "/opt/delme2/again/please";
	}

	@After
	public void tearDown() {
		DirectoryCreator instance;

		instance = new DirectoryCreator(singleDir);
		instance.delDir();
		instance = new DirectoryCreator(multiDir);
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
	}

	@Test
	public void testMkdirs() {
		DirectoryCreator instance = new DirectoryCreator(multiDir);
		assertThat(instance.mkdirs(), is(true));
	}

	@Test
	public void testDelDir() {
		String dir = OsUtils.isWindows() ? "c:/trash/junk/again/please" : "/opt/junk/again/please";
		DirectoryCreator instance;

		instance = new DirectoryCreator(dir);
		instance.mkdirs();

		instance = new DirectoryCreator(dir);
		instance.delDir();
		File f = new File(dir);
		assertThat(f.exists(), is(false));
	}

}
