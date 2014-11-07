package sample.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;

/**
 * Goal which touches a timestamp file.
 *
 * @deprecated Don't use!
 */
@Mojo(name = "touch", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MyMojo extends AbstractMojo {
	private String[] flaggedSyntax = {"import .*", "ruleflow-group .*"};

	@Parameter(defaultValue = "${basedir}/myrule.drl", property = "inputFile", required = true)
	private String inputFile;
	@Parameter(defaultValue = "${basedir}/src/main/resources", property = "outputDir", required = true)
	private File outputDirectory;

	public void execute() throws MojoExecutionException {
		File f = outputDirectory;

		if (!f.exists()) {
			f.mkdirs();
		}

		File touch = new File(f, "touch.txt");

		FileWriter w = null;
		try {
			w = new FileWriter(touch);
			w.write("touch.txt");
		} catch (IOException e) {
			throw new MojoExecutionException("Error creating file " + touch, e);
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}

		String inputTxt = readFile(inputFile);
		System.out.println(inputTxt);
	}


	private boolean containsFlaggedSyntax(String text) {
//		return getFlaggedSyntax().contains(text);
		for (String syntax: flaggedSyntax) 
			if(text.matches(syntax))
				return true;
		return false;
	}

	private String readFile(String fileName) {
		StringBuilder sb = new StringBuilder();

		try {
			FileReader fr = new FileReader(new File(fileName));
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				if(!containsFlaggedSyntax(line))
					sb.append(line).append("\n");
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(MyMojo.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(MyMojo.class.getName()).log(Level.SEVERE, null, ex);
		}
		return sb.toString();
	}
}
