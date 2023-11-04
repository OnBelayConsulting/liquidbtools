package com.onbelay.liquidbtools.write;

import com.onbelay.liquidbtools.model.ChangeLogContainerFixture;
import com.onbelay.liquidbtools.read.ChangeLogContainer;
import com.onbelay.liquidbtools.test.DbScriptsSpringTestCase;
import jakarta.xml.bind.JAXBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ChangeLogWriterTest extends DbScriptsSpringTestCase {
    private static final Logger logger = LogManager.getLogger();


    @Test
    public void testWriteTableNode() {
        ChangeLogContainer container = ChangeLogContainerFixture.createContainerWithTableNode();

        String expected =
                """
                        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                        <ns2:databaseChangeLog xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ns2="http://www.liquibase.org/xml/ns/dbchangelog" logicalFilePath="com.onbelay/onbelay/snapshot/ddl/tables/tables.xml" xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog-ext  http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.9.xsd">
                            <ns2:changeSet author="onbelay" id="tables">
                                <ns2:createTable tableName="MY_TABLE" tablespace="${tblspace_data}">
                                    <ns2:column name="col_1" type="numeric">
                                        <ns2:constraints nullable="false" primaryKey="true" primaryKeyName="pk_1" primaryKeyTablespace="${tblspace_data_idx}"/>
                                        <ns2:ignore>false</ns2:ignore>
                                    </ns2:column>
                                    <ns2:column name="col_2" type="numeric">
                                        <ns2:constraints nullable="true" primaryKey="false"/>
                                        <ns2:ignore>false</ns2:ignore>
                                    </ns2:column>
                                    <ns2:column name="col_3" type="numeric">
                                        <ns2:constraints nullable="true" primaryKey="false"/>
                                        <ns2:ignore>false</ns2:ignore>
                                    </ns2:column>
                                </ns2:createTable>
                            </ns2:changeSet>
                        </ns2:databaseChangeLog>
                        """;

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            ChangeLogWriter writer = ChangeLogWriter
                    .newWriter(outputStream)
                    .withLogicalFilePrefix("com.onbelay");

            try {
                writer.write(container);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            String xmlGenerated = outputStream.toString();
            assertEquals(expected, xmlGenerated);
        } catch (IOException e) {
            fail("Should have thrown exception");
        }

    }

    @Test
    public void testWriteUniqueConstraintNode() {
        ChangeLogContainer container = ChangeLogContainerFixture.createContainerWithUniqueConstraintNode();

        String expected =
                """
                        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                        <ns2:databaseChangeLog xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ns2="http://www.liquibase.org/xml/ns/dbchangelog" logicalFilePath="com.onbelay/onbelay/snapshot/ddl/constraints/uniqueconstraints.xml" xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog-ext  http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.9.xsd">
                            <ns2:changeSet author="onbelay" id="uniqueconstraints">
                                <ns2:addUniqueConstraint columnNames="MY_COL,MY_COL2" constraintName="MY_FOR_UC" tableName="MY_TABLE"/>
                            </ns2:changeSet>
                        </ns2:databaseChangeLog>
                        """;

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            ChangeLogWriter writer = ChangeLogWriter
                    .newWriter(outputStream)
                    .withLogicalFilePrefix("com.onbelay");

            try {
                writer.write(container);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            String xmlGenerated = outputStream.toString();
            assertEquals(expected, xmlGenerated);
        } catch (IOException e) {
            fail("Should have thrown exception");
        }
    }

    @Test
    public void testWriteForeignKeyConstraintNode() {
        ChangeLogContainer container = ChangeLogContainerFixture.createContainerWithUniqueConstraintNode();

        String expected =
                """
                        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                        <ns2:databaseChangeLog xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ns2="http://www.liquibase.org/xml/ns/dbchangelog" logicalFilePath="com.onbelay/onbelay/snapshot/ddl/constraints/uniqueconstraints.xml" xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog-ext  http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.9.xsd">
                            <ns2:changeSet author="onbelay" id="uniqueconstraints">
                                <ns2:addUniqueConstraint columnNames="MY_COL,MY_COL2" constraintName="MY_FOR_UC" tableName="MY_TABLE"/>
                            </ns2:changeSet>
                        </ns2:databaseChangeLog>
                        """;

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            ChangeLogWriter writer = ChangeLogWriter
                    .newWriter(outputStream)
                    .withLogicalFilePrefix("com.onbelay");

            try {
                writer.write(container);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            String xmlGenerated = outputStream.toString();
            assertEquals(expected, xmlGenerated);
        } catch (IOException e) {
            fail("Should have thrown exception");
        }
    }


    @Test
    public void testWriteCreateIndexNode() {
        ChangeLogContainer container = ChangeLogContainerFixture.createContainerWithCreateIndexNode();

        String expected =
                """
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ns2:databaseChangeLog xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ns2="http://www.liquibase.org/xml/ns/dbchangelog" logicalFilePath="com.onbelay/onbelay/snapshot/ddl/indices/indices.xml" xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog-ext  http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.9.xsd">
    <ns2:changeSet author="onbelay" id="indices">
        <ns2:createIndex indexName="TABLE_IDX" tableName="MY_TABLE">
            <ns2:column name="MY_COLUMN" type="java.sql.Types.NUMERIC(10, 0)">
                <ns2:ignore>false</ns2:ignore>
            </ns2:column>
        </ns2:createIndex>
    </ns2:changeSet>
</ns2:databaseChangeLog>
""";

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            ChangeLogWriter writer = ChangeLogWriter
                    .newWriter(outputStream)
                    .withLogicalFilePrefix("com.onbelay");

            try {
                writer.write(container);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            String xmlGenerated = outputStream.toString();
            logger.debug(xmlGenerated);
            assertEquals(expected, xmlGenerated);
        } catch (IOException e) {
            fail("Should have thrown exception");
        }
    }


    @Test
    public void testWriteCreateSequenceNode() {
        ChangeLogContainer container = ChangeLogContainerFixture.createContainerWithCreateSequenceNode();

        String expected =
                """
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ns2:databaseChangeLog xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ns2="http://www.liquibase.org/xml/ns/dbchangelog" logicalFilePath="com.onbelay/onbelay/snapshot/ddl/sequences/sequences.xml" xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog-ext  http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.9.xsd">
    <ns2:changeSet author="onbelay" id="sequences">
        <ns2:createSequence cycle="false" incrementBy="1" sequenceName="TABLE_SQ" startValue="1"/>
    </ns2:changeSet>
</ns2:databaseChangeLog>
""";

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            ChangeLogWriter writer = ChangeLogWriter
                    .newWriter(outputStream)
                    .withLogicalFilePrefix("com.onbelay");

            try {
                writer.write(container);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            String xmlGenerated = outputStream.toString();
            logger.debug(xmlGenerated);
            assertEquals(expected, xmlGenerated);
        } catch (IOException e) {
            fail("Should have thrown exception");
        }
    }

    @Test
    public void testWriteInserts() {
        ChangeLogContainer container = ChangeLogContainerFixture.createContainerWithInsertNode();

        String expected = """
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ns2:databaseChangeLog xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ns2="http://www.liquibase.org/xml/ns/dbchangelog" logicalFilePath="com.onbelay/onbelay/snapshot/dml/INSERT_BUY_SELL_CODE.xml" xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog-ext  http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.9.xsd">
    <ns2:changeSet author="onbelay" id="INSERT_BUY_SELL_CODE">
        <ns2:insert tableName="BUY_SELL_CODE">
            <ns2:column name="CODE_CD" value="B">
                <ns2:ignore>false</ns2:ignore>
            </ns2:column>
            <ns2:column name="CODE_LABEL" value="Buy">
                <ns2:ignore>false</ns2:ignore>
            </ns2:column>
            <ns2:column name="DISPLAY_ORDER_NO" value="10">
                <ns2:ignore>false</ns2:ignore>
            </ns2:column>
        </ns2:insert>
        <ns2:insert tableName="BUY_SELL_CODE">
            <ns2:column name="CODE_CD" value="S">
                <ns2:ignore>false</ns2:ignore>
            </ns2:column>
            <ns2:column name="CODE_LABEL" value="Sell">
                <ns2:ignore>false</ns2:ignore>
            </ns2:column>
            <ns2:column name="DISPLAY_ORDER_NO" value="10">
                <ns2:ignore>false</ns2:ignore>
            </ns2:column>
        </ns2:insert>
    </ns2:changeSet>
</ns2:databaseChangeLog>
""";


        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            ChangeLogWriter writer = ChangeLogWriter
                    .newWriter(outputStream)
                    .withLogicalFilePrefix("com.onbelay");

            try {
                writer.write(container);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            String xmlGenerated = outputStream.toString();
            logger.debug(xmlGenerated);
            assertEquals(expected, xmlGenerated);
        } catch (IOException e) {
            fail("Should have thrown exception");
        }
    }

}
