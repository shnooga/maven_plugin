package com.vsp.enterprise.test;

import com.vsp.enterprise.test.helper.FileNameManipulator;
import com.vsp.enterprise.test.helper.OsUtils;
import java.io.File;
import static org.hamcrest.Matchers.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author oogie
 */
public class FileNameManipulatorTest {
	private FileNameManipulator manipulator;
	
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

	@Test
	public void testExtractFileName() {
		String fileName = OsUtils.isWindows() 
						? "c:\\mydir\\is\\long\\hello.drl" 
						: "/mydir/is/long/hello.drl";
		manipulator = new FileNameManipulator(fileName);
		assertThat(manipulator.extractFileNameAndExtension(), is("hello.drl"));
	}

	@Test
	public void testExtractParentFile() {
		String fileName = OsUtils.isWindows() 
						? "c:\\mydir\\is\\long\\hello.drl" 
						: "/mydir/is/long/hello.drl";
		String expectDir = OsUtils.isWindows() 
						? "c:\\mydir\\is\\long"
						: "/mydir/is/long";
		manipulator = new FileNameManipulator(fileName);
		assertThat(manipulator.extractParentFile(), is(expectDir));
	}

	@Test
	public void testExtractFileNameWithParams() {
		String fileName = OsUtils.isWindows() 
						? "c:\\mydir\\is\\long\\hello.drl" 
						: "/mydir/is/long/hello.drl";
		assertThat(FileNameManipulator.extractFileNameAndExtension(fileName), is("hello.drl"));
	}

	@Test
	public void testSplitFileName() {
		String filename = OsUtils.isWindows() 
						? "c:\\mydir\\is\\long\\hello.drl" 
						: "/mydir/is/long/hello.drl";
		manipulator = new FileNameManipulator(filename);
		String[] filepaths = manipulator.splitFileName(filename);
		
		assertThat(filepaths.length, is(3));
		assertThat(filepaths[1], is("hello"));
		assertThat(filepaths[2], is("drl"));
	}
	
	@Test
	public void testSplitFileNameWithParams() {
		String filename = OsUtils.isWindows() 
						? "c:\\mydir\\is\\long\\hello.drl" 
						: "/mydir/is/long/hello.drl";
		String[] filepaths = FileNameManipulator.splitFileName(filename);
		
		assertThat(filepaths.length, is(3));
		assertThat(filepaths[1], is("hello"));
		assertThat(filepaths[2], is("drl"));
	}
	
	@Test
	public void testCreateJavaTestFileNameString() {
		String fileName = OsUtils.isWindows()    ? "c:/mydir/is/long/hello.drl" : "/mydir/is/long/hello.drl";
		String javaTestDir = OsUtils.isWindows() ? "c:/mydir/src/test"          : "/mydir/src/test";
		StringBuilder sb = new StringBuilder(javaTestDir);

		sb.append("/com/vsp/uno/dos/").append("helloTest.java");
		manipulator = new FileNameManipulator(fileName);
		String qualifiedFileName = manipulator.createJavaTestFileNameString(javaTestDir, "com.vsp.uno.dos");
		
		assertThat(qualifiedFileName, is(sb.toString()));
	}
	
	@Test
	public void testCreateRuleFauxFileNameString1() {
		String fileName = OsUtils.isWindows() 		? "c:/mydir/is/long/hello.java" : "/mydir/is/long/hello.drl";
		String javaTestDir = OsUtils.isWindows() 	? "c:/mydir/src/test" 			: "/mydir/src/test";
		StringBuilder sb = new StringBuilder(javaTestDir);

		sb.append("/com/vsp/uno/dos/").append("helloFaux.drl");
		manipulator = new FileNameManipulator(fileName);
		String qualifiedFileName = manipulator.createRuleFauxFileNameString(javaTestDir, "com.vsp.uno.dos");
		
		assertThat(qualifiedFileName, is(sb.toString()));
	}
}
