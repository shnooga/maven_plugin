package com.vsp.enterprise.test;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author oogie
 */
public class FileNameManipulatorTest {
	private FileNameManipulator manipulator;
	
	/**
	 * Test of postPendTextToFileName method, of class FileNameManipulator.
	 */
	@Test
	public void testPostPendTextToFileName_Windows() {
		manipulator = new FileNameManipulator("c:\\mydir\\is\\long\\hello.drl");
		assertThat(manipulator.postPendTextToFileName("bogus"), is("c:\\mydir\\is\\long\\hellobogus.drl"));
	}

	@Test
	public void testPostPendTextToFileName_Unix() {
		manipulator = new FileNameManipulator("/mydir/is/long/hello.drl");
		assertThat(manipulator.postPendTextToFileName("bogus"), is("\\mydir\\is\\long\\hellobogus.drl"));
	}

	/**
	 * Test of extractFileName method, of class FileNameManipulator.
	 */
	@Test
	public void testExtractFileName() {
		manipulator = new FileNameManipulator("c:\\mydir\\hello.drl");
		assertThat(manipulator.extractFileName(), is("hello.drl"));
	}
}
