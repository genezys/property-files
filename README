property-files
==============

Utility class to define system properties from files with support for included files


Usage
=====

1. Call `PropertyFiles.loadSystemProperties()` at the start of your application
2. Define either the system property `java.properties.file` or `java.properties.resource` when running the application (i.e. `java -Djava.properties.file=prod.properties ...`)

The `.properties` file will be loaded and all its properties will be added as system properties.

If the file defines either `java.properties.file` or `java.properties.resource` again, the new file will be included before and its properties overridden by the current file. This way you can centralize common properties in one file included by the other specific configurations.

Your application can retrieve configuration values using `System.getProperty` or an other similar tool working with standard system properties.


Example
=======

Common properties are used to share configuration values, define frequently used default values and define external configuration.

`common.properties` delivered with application

	# Default database configuration
	database.jdbc.url = jdbc:mysql://localhost/app
	database.jdbc.user = app_user
	database.jdbc.password =

	# Configure logging
	logger.com.company.application = INFO

	# Enable debug in Netty
	org.jboss.netty.debug =

Developers can have their own configurations to ease debugging. Developers will run the application with `-Djava.properties.resource=dev.properties`

`dev.properties` delivered with application

	# Include common properties
	java.properties.resource = common.properties

	# Overrides some parameters
	logger.com.company.application = DEBUG

Production can override some settings to comply with the deployment target

`prod.properties` delivered with application

	# Include common properties
	java.properties.resource = common.properties

	# Overrides some parameters
	database.jdbc.user = app_user_prod

An application deployer can avoid storing sensible information by having its own configuration in a file instead of embedded in the application. Deployer will run the application with `-Djava.properties.file=dev.properties`

`prod_secure.properties` only managed by application deployer

	# Include common properties
	java.properties.resource = prod.properties

	# Overrides some parameters
	database.jdbc.password = Secur3!P@ssw0rd
