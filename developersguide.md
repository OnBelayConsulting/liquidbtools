# Developers Guide

liquidbtools is composed of the following:
1. RegenerateChangeLogCmd.java - this is the class that handles the CLI invocation.
2. Nodes - java classes that map to specific liquibase changeset elements.
3. ChangeLogReader - reads in a file and generates a ChangeLogContainer
4. ChangeLogProcessor - processes the individual elements for the reader
5. ChangeLogContainer - a pojo that represents the parsed changelog.
6. ChangeLogWriter - consumes the ChangeLogContainer to regenerate the specic changelog files.


The RegenerateChangeLogCmd is executed when you run the jar file. This
is the class that reads in the application.properties and then
subsequently sets the attributes for the reading and writing.

If you want to modify what is written out in the changeset for an element
such as createTable then consider extending or modifying the CreateTableNode.

If you want to change what elements are written out to what separate files
then take a look at the ChangeLogWriter.

Note that the xml to java to xml mapping is handled by JAXB library.

The build.gradle file is used to create the stand-alone (boot) jar file
that can be subsequently executed.
