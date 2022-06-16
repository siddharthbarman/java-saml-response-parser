# java-saml-response-parser
Demonstrates parsing SAML response XML.

## Directory structure
+- file
   +- settings.xml (used to configure the xpaths and xml namespaces to extract attributes from the SAML response)
   +- okta.xml (sample SAML response from Okta)
+- source (the project source code)
   +- pom.xml
   +- src
+- license
+- README.md

## Building the project
Use maven or build from command line or use an IDE like IntelliJ.
```
mvn package
```

## Running the application
```
> cd target
> java -jar SamlParse-1.0-jar-with-dependencies.jar ..\..\files\settings.xml ..\..\files\okta.xml
```
