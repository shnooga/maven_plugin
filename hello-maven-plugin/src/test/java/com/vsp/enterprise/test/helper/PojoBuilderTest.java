package com.vsp.enterprise.test.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class PojoBuilderTest {
		PojoBuilder instance = new PojoBuilder();

	@Test
	public void testIsSetter_get_Pos() {
		assertThat(instance.isSetter("public int getAge()"), is(true));
		assertThat(instance.isSetter(" public int  getAge()"), is(true));
	}

	@Test
	public void testIsSetter_get_Neg() {
		assertThat(instance.isSetter("private int getAge()"), is(false));
		assertThat(instance.isSetter(" private int   getAge()"), is(false));
	}
	
	@Test
	public void testExtractPropertyName_Pos() {
		String[] pair = instance.extractPropertyName("public int getAge()");

		assertThat(pair[0], is("int"));
		assertThat(pair[1], is("Age"));
	}

	@Test
	public void testBuildGetterMethod1() {
		String method = instance.buildGetterMethod("public int getAge() { ");
		assertThat(method, is("public int getAge() { return age; }"));
	}

	@Test
	public void testBuildGetterMethod2() {
		String method = instance.buildGetterMethod("public int getAge()");
		assertThat(method, is("public int getAge() { return age; }"));
	}

	@Test
	public void testBuildGetterMethod3() {
		String method = instance.buildGetterMethod("public int getAge(");
		assertThat(method, is("public int getAge() { return age; }"));
	}

	@Test
	public void testBuildSetterMethod() {
		String method = instance.buildSetterMethod("public int getAge(");
		assertThat(method, is("\tpublic void setAge(int age) { this.age = age; }"));
	}

	@Test
	public void testBuildProperty_1() {
		String method = instance.buildProperty("public int getAge(");
		assertThat(method, is("\tprivate int age;"));
	}

	@Test
	public void testBuildProperty_2() {
		String method = instance.buildProperty("public String[] getNames(");
		assertThat(method, is("\tprivate String[] names;"));
	}

}
