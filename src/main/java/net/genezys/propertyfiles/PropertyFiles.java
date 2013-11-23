package net.genezys.propertyfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFiles
{
	public static final String JAVA_PROPERTIES_FILE = "java.properties.file";
	public static final String JAVA_PROPERTIES_RESOURCE = "java.properties.resource";

	public static void loadSystemProperties() throws FileNotFoundException, IOException
	{
		Properties properties = includeProperties(System.getProperties());
		System.setProperties(properties);
	}

	/**
	 * Try to include {@link Properties} into another {@link Properties}.
	 * 
	 * The included {@link Properties} can either be a file designated by 
	 * the property <code>{@value #JAVA_PROPERTIES_FILE}</code> or a resource 
	 * designated by the property <code>{@value #JAVA_PROPERTIES_RESOURCE}</code>.
	 * 
	 * @param properties source properties to apply after the included ones
	 * @return a {@link Properties} object with included values
	 * @throws IOException if loading fails
	 */
	public static Properties includeProperties(Properties properties) throws IOException
	{
		Properties propertiesToInclude = tryLoadIncludedPropertiesFile(properties);

		if ( propertiesToInclude != null )
		{
			propertiesToInclude = includeProperties(propertiesToInclude);
			propertiesToInclude.putAll(properties);
			return propertiesToInclude;
		}

		return properties;
	}
	
	/**
	 * Try to load a {@link Properties} object from existing properties.
	 * 
	 * The loaded {@link Properties} can either be a file designated by 
	 * the property <code>{@value #JAVA_PROPERTIES_FILE}</code> in the 
	 * source object, or a resource designated by the property 
	 * <code>{@value #JAVA_PROPERTIES_RESOURCE}</code> in the source object.
	 * 
	 * @param properties source properties to read properties from
	 * @return a {@link Properties} object loaded from the file or the resource. 
	 * @throws IOException if loading fails
	 */
	public static Properties tryLoadIncludedPropertiesFile(Properties properties) throws IOException
	{
		String includeFilePath = properties.getProperty(JAVA_PROPERTIES_FILE);
		if ( includeFilePath != null )
		{
			File includeFile = new File(includeFilePath);
			return loadPropertiesFromFile(includeFile);
		}

		String includeResourcePath = properties.getProperty(JAVA_PROPERTIES_RESOURCE);
		if ( includeResourcePath != null )
		{
			return loadPropertiesFromResource(includeResourcePath);
		}

		return null;
	}

	/**
	 * Load {@link Properties} from a resource. 
	 * 
	 * @param resourcePath path of the resource to load
	 * @return a {@link Properties} object loaded from the resource
	 * @throws IOException if loading fails
	 */
	public static Properties loadPropertiesFromResource(String resourcePath) throws IOException
	{
		InputStream stream = ClassLoader.getSystemResourceAsStream(resourcePath);
		if( stream == null )
		{
			throw new IllegalArgumentException(String.format("Resource not found '%s'", resourcePath));
		}

		return loadPropertiesFromStreamAndClose(stream);
	}

	/**
	 * Load {@link Properties} from a {@link File}. 
	 * 
	 * @param file file to load
	 * @return a {@link Properties} object loaded from the file
	 * @throws IOException if file is not found or loading fails
	 */
	public static Properties loadPropertiesFromFile(File file) throws IOException
	{
		FileInputStream stream = new FileInputStream(file);
		return loadPropertiesFromStreamAndClose(stream);
	}

	/**
	 * Convenience method to read properties from a stream and close it.
	 * 
	 * @param stream stream to read properties from. It will be closed after this method.
	 * @return a {@link Properties} object loaded from the stream
	 * @throws IOException from {@link Properties#load(InputStream)}.
	 */
	public static Properties loadPropertiesFromStreamAndClose(InputStream stream) throws IOException
	{
		try
		{
			Properties properties = new Properties();
			properties.load(stream);
			return properties;
		}
		finally
		{
			stream.close();
		}
	}
}
