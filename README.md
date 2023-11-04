# liquidbtools
Tools to process, filter and separate liquibase changelog changesets into
separate files for maintainability.

This tool is complementary to the open source (or pro edition) of the fabulous liquibase application
download from https://www.liquibase.org/DOWNLOAD

## Introduction
Lets say you have an existing database you want to reverse engineer with
the liquibase tool. The liquibase tool will extract database objects and
will generate changesets with createTable, createSequence, createForeignKeyConstraint, etc.
elements in one large file.

One large file might be fine for smaller projects but most developers would prefer
separate files for each changeset category: 
1. tables, 
2. codes, 
3. indices, 
4. foreign key constraints,
5. unique constraints,
6. inserts to populate tables.

liquidbtools will separate out elements from a single file (with a few assumptions) 
into the categories above.(The main assumption is that code tables end with _CODE).
This behaviour can be easily changed by modifying the code.

liquidbtools also supports filtering out unwanted tables, sequences and columns
that are not needed or wanted in the changelog. This behaviour is controlled by
the settings in the application.properties file. Note that if you exclude a table
then all foreign key constraints, unique constraints and indices referencing that 
table are excluded as well.

## Usage

The steps from start to completion are as follows:
1. Download the liquibase application (jar) from liquibase. https://www.liquibase.org/DOWNLOAD
2. Download the liquidbtools jar from github
3. Optional: extract the scripts folder from the liquidbtools jar
4. run the liquibase command to extract the single changelog file from the database.
5. run the liquidbtools script with the name of the single changelog file.

### Download liquidbtools jar
Click on liquidbtools Under Packages and download the jar. 

### Extract scripts to use as a starting point
Use a zip tool to unzip the liquidbtools jar.
The scripts are located in BOOT-INF/classes/scripts.
The scripts folder contains bat files and sample liquibase and 
application property files.

### Create a liquibase properties file
First create a liquibase.properties file (or use the sample from the scripts) 
that points to the database you want to extract changelogs from. 

### Run the liquibase application to extract the changelog
The liquibaseExtractChangeLog.bat is a sample batch file that illustrates
what parameters to pass into liquibase.

Note that the liquibase script batch files will extract the DDL separate from the 
DML (Inserts) but it is possible to extract everything in a single file.

The command is <code> liquibase generate-changelog --changelog-file=everest_extract.xml </code>
everest_extract.xml is the name of the changelog to be produced.

liquibase --changelog command supports many features to exclude tables and other
elements as well so its worth exploring what this tool can do for you in combination
with liquidbtools.

### Create or modify the application.properties file used by liquidbtools
The scripts folder contains an example application.properties file that you can use.
The only required entries are:
- spring.main.web-application-type=none
- spring.main.allow-bean-definition-overriding=true
- application.name=[your application name]
- liquidbtools.db.tablespace.data.name=[Name of tablespace for data]
- liquidbtools.db.tablespace.index.name=[Name of tablespace for indices] 
- liquidbtools.logicalfile.prefix=[logical file prefix for databaseChangeLog entry]

The application name is used as the author name and the applicaition name and logicalfile prefix are used in generating the changelog
files.

The tablespace names are inserted into the appropriate element. A common practice is 
to use a variable instead of a hard-coded name such as ${tblspace_data} but I haven't found a way to escape it in the 
application.properties so you would need to set it in the environment as ${tblspace_data} and feed it into the parameter. 

Other options are (commented out) in the sample application.properties are:
- liquidbtools.ignore.tables=[a list of table names separated by commas]
- liquidbtools.ignore.tables.partialmatch=[a list of names separated commas. See Note]
- liquidbtools.ignore.sequences.partialmatch=[a list of names separated commas. See Note.]
- liquidbtools.ignore.column.names.bytable=[table.columnNames separated by commas]
- liquidbtools.ignore.column.names=[column names separated by commas]

All the options above will result in any matches from being excluded in the final changelog.

Partial matches: a partial match is if the target table or sequence name contains the name
provided. 

#### Examples

#### Exclude the UDF_PROPERTY table
<code>liquidbtools.ignore.tables=UDF_PROPERTY </code>

#### Exclude all tables that contain UDF or ORG in the name
<code>liquidbtools.ignore.tables.partialmatch=UDF,ORG </code>

#### Exclude all sequences that contain UDF or ORG in the name
<code>liquidbtools.ignore.sequences.partialmatch=UDF,ORG </code>

#### Exclude all MODIFIED_BY columns in tables, constraints, indices.
<code>liquidbtools.ignore.columns=MODIFIED_BY</code>

#### Exclude MODIFIED_BY column only in the UDF_PROPERTY table ( and if referenced in constraints, indices).
<code>liquidbtools.ignore.columns.bytable=UDF_PROPERTY.MODIFIED_BY</code>

Note the ignore options above are also used when regenerating the inserts so if a 
column is ignored then it will be excluded from the table definition and it will be 
excluded from the insert.

### Gotchas and Limitations
The liquidbtools insert regeneration support was developed to handle code tables. If you use this 
function for tables other than codes then you may need to order inserts to make it work.

This tool assumes that code tables end in <b> _CODE </b>. This can be easily changed by
modifying the code.

