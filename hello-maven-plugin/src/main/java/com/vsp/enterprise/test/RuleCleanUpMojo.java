package com.vsp.enterprise.test;

import java.io.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;

@Mojo(name = "clean", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class RuleCleanUpMojo extends AbstractMojo {
	public static final String RULE_NAME_MARKER = "Faux";

	@Parameter(property = "inputFile", required = true)
	private String inputFile;

	@Parameter(defaultValue = "${basedir}/src/main/resources", property = "resourceDir")
	private String resourceDirectory;

	public void execute() throws MojoExecutionException {
		FileNameManipulator nameManipulator = new FileNameManipulator(inputFile);
		
		nameManipulator = new FileNameManipulator(resourceDirectory + File.separator + nameManipulator.extractFileName());
		String fullyQualifiedFileName = nameManipulator.postPendTextToFileName(RULE_NAME_MARKER);
		File fauxDroolFile = new File(fullyQualifiedFileName);

		if (fauxDroolFile.exists()) {
			System.out.println("Deleting " + fullyQualifiedFileName);
			fauxDroolFile.delete();
		}
	}
}
