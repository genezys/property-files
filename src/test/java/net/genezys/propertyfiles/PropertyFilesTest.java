package net.genezys.propertyfiles;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class PropertyFilesTest
{
	private static Properties ms_defaultSystemProperties;

	private static Properties copySystemProperties()
	{
		return copyProperties(System.getProperties());
	}

	private static Properties copyProperties(Properties properties)
	{
		Properties properties2 = new Properties();
		properties2.putAll(properties);
		return properties2;
	}

	@BeforeClass
	public static void saveDefaultSystemProperties()
	{
		ms_defaultSystemProperties = copySystemProperties();
	}
	
	@Before
	public void setDefaultSystemProperties()
	{
		System.setProperties(copyProperties(ms_defaultSystemProperties));
	}
	
	@Test
	public void testLoadSystemPropertiesWithoutProperty() throws Exception
	{
		Properties propertiesBefore = copySystemProperties();
		PropertyFiles.loadSystemProperties();
		Properties propertiesAfter = copySystemProperties();
		
		Assert.assertEquals("System properties did not change", propertiesBefore, propertiesAfter);
	}

	@Test
	public void testLoadSystemPropertiesWithSimpleFile() throws Exception
	{
		System.getProperties().setProperty("java.properties.file", "src/test/resources/simple.properties");
		Properties propertiesBefore = copySystemProperties();
		PropertyFiles.loadSystemProperties();
		Properties propertiesAfter = copySystemProperties();
		
		Assert.assertFalse("System properties did change", propertiesBefore.equals(propertiesAfter));
		Assert.assertNull(propertiesBefore.get("plop"));
		Assert.assertEquals("onk", propertiesAfter.get("plop"));
		Assert.assertEquals("src/test/resources/simple.properties", propertiesAfter.get("java.properties.file"));
	}

	@Test
	public void testLoadSystemPropertiesWithSimpleResource() throws Exception
	{
		System.getProperties().put("java.properties.resource", "simple.properties");
		Properties propertiesBefore = copySystemProperties();
		PropertyFiles.loadSystemProperties();
		Properties propertiesAfter = copySystemProperties();
		
		Assert.assertFalse("System properties did change", propertiesBefore.equals(propertiesAfter));
		Assert.assertNull(propertiesBefore.get("plop"));
		Assert.assertEquals("onk", propertiesAfter.get("plop"));
		Assert.assertEquals("simple.properties", propertiesAfter.get("java.properties.resource"));
	}

	@Test
	public void testLoadSystemPropertiesWithInclude() throws Exception
	{
		System.getProperties().put("java.properties.resource", "include.properties");
		Properties propertiesBefore = copySystemProperties();
		PropertyFiles.loadSystemProperties();
		Properties propertiesAfter = copySystemProperties();
		
		Assert.assertFalse("System properties did change", propertiesBefore.equals(propertiesAfter));
		Assert.assertNull(propertiesBefore.get("plop"));
		Assert.assertEquals("gloubi", propertiesAfter.get("plop"));
		Assert.assertEquals("include.properties", propertiesAfter.get("java.properties.resource"));
	}
	
	@Test
	public void testLoadSystemPropertiesWithInclude2() throws Exception
	{
		System.getProperties().put("java.properties.resource", "include2.properties");
		Properties propertiesBefore = copySystemProperties();
		PropertyFiles.loadSystemProperties();
		Properties propertiesAfter = copySystemProperties();
		
		Assert.assertFalse("System properties did change", propertiesBefore.equals(propertiesAfter));
		Assert.assertNull(propertiesBefore.get("plop"));
		Assert.assertEquals("grumpf", propertiesAfter.get("plop"));
		Assert.assertEquals("include2.properties", propertiesAfter.get("java.properties.resource"));
	}
}
