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
	public void testPostPendTextToFileName() {
		String fileName = OsUtils.isWindows() 
						? "c:\\mydir\\is\\long\\hello.drl" 
						: "/mydir/is/long/hello.drl";
		String expectFileName = OsUtils.isWindows() 
						? "c:\\mydir\\is\\long\\hellobogus.drl"
						: "/mydir/is/long/hellobogus.drl";
		manipulator = new FileNameManipulator(fileName);
		assertThat(manipulator.postPendTextToFileName("bogus"), is(expectFileName));
	}

	/**
	 * Test of extractFileName method, of class FileNameManipulator.
	 */
	@Test
	public void testExtractFileName() {
		String fileName = OsUtils.isWindows() 
						? "c:\\mydir\\is\\long\\hello.drl" 
						: "/mydir/is/long/hello.drl";
		manipulator = new FileNameManipulator(fileName);
		assertThat(manipulator.extractFileName(), is("hello.drl"));
	}
}
