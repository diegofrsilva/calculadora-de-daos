package listeners;

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import antlr.ParserRunner;

public class ClassInfoTest {

	private ClassInfo classInfo;
	@Before
	public void setUp() {
		classInfo = new ClassInfo();
	}
	
	@Test
	public void shouldIdentifyIfItIsAEnum() {
		String dao = 
				  "enum PaymentInfo {"
				+ "A,B,C;"
				+ "}";
		
		new ParserRunner(classInfo).run(new ByteArrayInputStream(dao.getBytes()));
		
		Assert.assertTrue(classInfo.isEnum());
		Assert.assertEquals("PaymentInfo",classInfo.getName());
	}
	
	@Test
	public void shouldIdentifyIfClassHasNoInterfaceImplementations() {
		String dao = 
				  "class Payment {"
				+ "public void x() {}"
				+ "}";
		
		new ParserRunner(classInfo).run(new ByteArrayInputStream(dao.getBytes()));
		
		Assert.assertFalse(classInfo.isEnum());
		Assert.assertFalse(classInfo.implementsInterface());
		Assert.assertEquals("Payment", classInfo.getName());
	}
	
	@Test
	public void shouldIdentifyInterfacesImplemented() {
		String dao = 
				"class Payment implements A {"
						+ "public void x() {}"
						+ "}";
		
		new ParserRunner(classInfo).run(new ByteArrayInputStream(dao.getBytes()));
		
		Assert.assertTrue(classInfo.implementsInterface());
		Assert.assertEquals("Payment", classInfo.getName());
		Assert.assertTrue(classInfo.interfaces().contains("A"));
	}
	@Test
	public void shouldIdentifyMultipleInterfacesImplemented() {
		String dao = 
				"class Payment implements A,B,C {"
						+ "public void x() {}"
						+ "}";
		
		new ParserRunner(classInfo).run(new ByteArrayInputStream(dao.getBytes()));
		
		Assert.assertTrue(classInfo.implementsInterface());
		Assert.assertTrue(classInfo.interfaces().contains("A"));
		Assert.assertTrue(classInfo.interfaces().contains("B"));
		Assert.assertTrue(classInfo.interfaces().contains("C"));
	}
}
