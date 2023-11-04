package com.onbelay.liquidbtools.read;

import com.onbelay.liquidbtools.model.*;
import com.onbelay.liquidbtools.test.DbScriptsSpringTestCase;
import jakarta.xml.bind.JAXBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class ChangeLogReaderTest extends DbScriptsSpringTestCase {
    private static Logger logger = LogManager.getLogger();

    @Test
    public void testRead()  {

        InputStream  xmlFileIn;

        try (InputStream inputStream = ChangeLogReaderTest.class.getClassLoader().getResourceAsStream("example.xml")) {
            ChangeLogReader reader = ChangeLogReader
                    .newReader(inputStream)
                    .withTableSpaceNames("DEFAULT_DATA", "DEFAULT_IDX");

            ChangeLogContainer result = reader.read();

            assertEquals(1, result.getTables().size());
            CreateTableNode tableNode = result.getTables().get(0);
            assertEquals("DEFAULT_DATA", tableNode.getTablespace());
            assertEquals(3, tableNode.getColumnNodes().size());

            assertEquals(false, tableNode.getColumnNodes().stream().filter(c-> c.getName().equals("ENTITY_ID")).findFirst().isEmpty());
            ColumnNode entityIdNode = tableNode.getColumnNodes().stream().filter(c-> c.getName().equals("ENTITY_ID")).findFirst().get();
            assertEquals("java.sql.Types.NUMERIC(10, 0)", entityIdNode.getType());
            assertNotNull(entityIdNode.getConstraints());
            assertTrue(entityIdNode.getConstraints().isPrimaryKey());
            assertEquals("ADDRESS_PK", entityIdNode.getConstraints().getPrimaryKeyName());
            assertFalse(entityIdNode.getConstraints().isNullable());

            assertEquals(false, tableNode.getColumnNodes().stream().filter(c-> c.getName().equals("STREET_NAME")).findFirst().isEmpty());
            ColumnNode streetNode = tableNode.getColumnNodes().stream().filter(c-> c.getName().equals("STREET_NAME")).findFirst().get();
            assertEquals("nvarchar(100)", streetNode.getType());
            assertNotNull(streetNode.getConstraints());
            assertFalse(streetNode.getConstraints().isNullable());


            assertEquals(false, tableNode.getColumnNodes().stream().filter(c-> c.getName().equals("OPTIMISTIC_LOCK_NO")).findFirst().isEmpty());

            ColumnNode lockNode = tableNode.getColumnNodes().stream().filter(c-> c.getName().equals("OPTIMISTIC_LOCK_NO")).findFirst().get();
            assertEquals("ADDRESS_OPTIMISTIC_LOCK_NO", lockNode.getDefaultValueConstraintName());
            assertEquals(0, lockNode.getDefaultValueNumeric());
            assertEquals("numeric(10, 0)", lockNode.getType());

            assertEquals(2, result.getCodeTables().size());
            assertEquals(false, result.getCodeTables().stream().filter(c-> c.getTableName().equals("DEAL_TYPE_CODE")).findFirst().isEmpty());
            assertEquals(false, result.getCodeTables().stream().filter(c-> c.getTableName().equals("BUY_SELL_CODE")).findFirst().isEmpty());


            assertEquals(1, result.getSequences().size());
            CreateSequenceNode sequenceNode = result.getSequences().get(0);
            assertEquals("ADDRESS_SQ", sequenceNode.getSequenceName());
            assertEquals(1, sequenceNode.getIncrementBy());
            assertEquals(1, sequenceNode.getStartValue());

            assertEquals(1, result.getIndices().size());
            CreateIndexNode indexNode = result.getIndices().get(0);
            assertEquals("DEFAULT_IDX", indexNode.getTablespace());
            assertEquals("DEAL_TICKET_IDX", indexNode.getIndexName());
            assertEquals("BASE_DEAL", indexNode.getTableName());
            assertEquals(1, indexNode.getColumnNodes().size());
            ColumnNode indColumnNode = indexNode.getColumnNodes().get(0);
            assertEquals("TICKET_NO", indColumnNode.getName());

            assertEquals(1, result.getUniqueConstraints().size());
            AddUniqueConstraintNode uniqueConstraintNode = result.getUniqueConstraints().get(0);
            assertEquals("ORGSK_VLDTODT_AUDIT_UX", uniqueConstraintNode.getConstraintName());
            assertEquals("ENTITY_ID, VALID_TO_DTTM", uniqueConstraintNode.getColumnNames());
            assertEquals("ORGANIZATION_AUDIT", uniqueConstraintNode.getTableName());

            assertEquals(1, result.getPrimaryKeys().size());
            AddPrimaryKeyNode primaryKeyNode = result.getPrimaryKeys().get(0);
            assertEquals("ORGANIZATION_PK", primaryKeyNode.getConstraintName());
            assertEquals("OB_ORGANIZATION", primaryKeyNode.getTableName());
            assertEquals("ENTITY_ID", primaryKeyNode.getColumnNames());
            assertEquals("DEFAULT_IDX", primaryKeyNode.getTablespace());

            assertEquals(1, result.getForeignConstraints().size());
            AddForeignKeyConstraintNode foreignKeyConstraintNode = result.getForeignConstraints().get(0);
            assertEquals("BASE_DEAL_FK01", foreignKeyConstraintNode.getConstraintName());
            assertEquals("BASE_DEAL", foreignKeyConstraintNode.getBaseTableName());
            assertEquals("DEAL_TYPE_CODE", foreignKeyConstraintNode.getBaseColumnNames());
            assertEquals("DEAL_TYPE_CODE", foreignKeyConstraintNode.getReferencedTableName());
            assertEquals("CODE_CD", foreignKeyConstraintNode.getReferencedColumnNames());
            assertEquals("NO ACTION", foreignKeyConstraintNode.getOnDelete());
            assertEquals("NO ACTION", foreignKeyConstraintNode.getOnUpdate());
            assertEquals(false, foreignKeyConstraintNode.isDeferrable());
            assertEquals(false, foreignKeyConstraintNode.isInitiallyDeferred());
            assertEquals(true, foreignKeyConstraintNode.isValidate());

            assertEquals(1, result.getViews().size());
            CreateViewNode viewNode = result.getViews().get(0);
            assertEquals("DEAL_VIEW", viewNode.getViewName());

        } catch (IOException e) {
            logger.error(e);
            fail("Threw exception");
        } catch (JAXBException k) {
            logger.error(k);
            fail("Threw exception");
        }
    }

    @Test
    public void testReadIgnoreTables()  {

        InputStream  xmlFileIn;

        try (InputStream inputStream = ChangeLogReaderTest.class.getClassLoader().getResourceAsStream("example.xml")) {
            ChangeLogReader reader = ChangeLogReader
                    .newReader(inputStream)
                    .withIgnoreTableNames(List.of("OB_ADDRESS"));
            ChangeLogContainer result = reader.read();
            assertEquals(0, result.getTables().size());
            assertEquals(2, result.getCodeTables().size());
            assertEquals(1, result.getSequences().size());
            assertEquals(1, result.getIndices().size());
            assertEquals(1, result.getUniqueConstraints().size());
            assertEquals(1, result.getForeignConstraints().size());
        } catch (IOException e) {
            logger.error(e);
            fail("Threw exception");
        } catch (JAXBException k) {
            logger.error(k);
            fail("Threw exception");
        }
    }

    @Test
    public void testReadIgnoreTablesPartialMatch()  {

        InputStream  xmlFileIn;

        try (InputStream inputStream = ChangeLogReaderTest.class.getClassLoader().getResourceAsStream("example.xml")) {
            ChangeLogReader reader = ChangeLogReader
                    .newReader(inputStream)
                    .withIgnoreTableNamesPartialMatch(List.of("_CODE"));
            ChangeLogContainer result = reader.read();
            assertEquals(1, result.getTables().size());
            assertEquals(0, result.getCodeTables().size());
            assertEquals(1, result.getSequences().size());
            assertEquals(1, result.getIndices().size());
            assertEquals(1, result.getUniqueConstraints().size());
            assertEquals(1, result.getForeignConstraints().size());
        } catch (IOException e) {
            logger.error(e);
            fail("Threw exception");
        } catch (JAXBException k) {
            logger.error(k);
            fail("Threw exception");
        }
    }


    @Test
    public void testReadIgnoreSequencesPartialMatch()  {

        InputStream  xmlFileIn;

        try (InputStream inputStream = ChangeLogReaderTest.class.getClassLoader().getResourceAsStream("example.xml")) {
            ChangeLogReader reader = ChangeLogReader
                    .newReader(inputStream)
                    .withIgnoreSequenceNamesPartialMatch(List.of("ADDRESS_SQ"));
            ChangeLogContainer result = reader.read();
            assertEquals(1, result.getTables().size());
            assertEquals(2, result.getCodeTables().size());
            assertEquals(0, result.getSequences().size());
            assertEquals(1, result.getIndices().size());
            assertEquals(1, result.getUniqueConstraints().size());
            assertEquals(1, result.getForeignConstraints().size());
        } catch (IOException e) {
            logger.error(e);
            fail("Threw exception");
        } catch (JAXBException k) {
            logger.error(k);
            fail("Threw exception");
        }
    }


    @Test
    public void testReadWithIgnoreColumnsAll()  {

        InputStream  xmlFileIn;

        try (InputStream inputStream = ChangeLogReaderTest.class.getClassLoader().getResourceAsStream("example.xml")) {
            ChangeLogReader reader = ChangeLogReader.newReader(inputStream)
                    .withIgnoreColumnNamesAll(List.of("MODIFIED"));
            ChangeLogContainer result = reader.read();
            assertEquals(1, result.getTables().size());
            assertEquals(2, result.getCodeTables().size());
            for (CreateTableNode codeTable : result.getCodeTables()) {
                assertTrue(codeTable.getColumnNodes().stream().filter(c -> c.getName().contains("MODIFIED")).findFirst().isEmpty());
            }
            assertEquals(1, result.getSequences().size());
            assertEquals(1, result.getIndices().size());
            assertEquals(1, result.getUniqueConstraints().size());
            assertEquals(1, result.getForeignConstraints().size());
        } catch (IOException e) {
            logger.error(e);
            fail("Threw exception");
        } catch (JAXBException k) {
            logger.error(k);
            fail("Threw exception");
        }
    }

    @Test
    public void testReadWithIgnoreColumnsByTable()  {

        InputStream  xmlFileIn;

        try (InputStream inputStream = ChangeLogReaderTest.class.getClassLoader().getResourceAsStream("example.xml")) {
            ChangeLogReader reader = ChangeLogReader.newReader(inputStream)
                    .withIgnoreColumnNamesByTable(
                            Map.of(
                                    "BUY_SELL_CODE",
                                    List.of("MODIFIED")));
            ChangeLogContainer result = reader.read();
            assertEquals(1, result.getTables().size());
            assertEquals(2, result.getCodeTables().size());
            CreateTableNode codeTable = result.getCodeTables().stream().filter(c-> c.getTableName().equals("BUY_SELL_CODE")).findFirst().get();
            assertTrue(codeTable.getColumnNodes().stream().filter(c -> c.getName().contains("MODIFIED")).findFirst().isEmpty());

            CreateTableNode codeDealTypeTable = result.getCodeTables().stream().filter(c-> c.getTableName().equals("DEAL_TYPE_CODE")).findFirst().get();
            assertFalse(codeDealTypeTable.getColumnNodes().stream().filter(c -> c.getName().contains("MODIFIED")).findFirst().isEmpty());

            assertEquals(1, result.getSequences().size());
            assertEquals(1, result.getIndices().size());
            assertEquals(1, result.getUniqueConstraints().size());
            assertEquals(1, result.getForeignConstraints().size());
        } catch (IOException e) {
            logger.error(e);
            fail("Threw exception");
        } catch (JAXBException k) {
            logger.error(k);
            fail("Threw exception");
        }
    }


    //@Test
    public void readInserts() {
        InputStream  xmlFileIn;

        try (InputStream inputStream = ChangeLogReaderTest.class.getClassLoader().getResourceAsStream("insertExample.xml")) {
            ChangeLogReader reader = ChangeLogReader.newReader(inputStream);
            ChangeLogContainer result = reader.read();
            assertEquals(1, result.getInsertMap().keySet().size());
            List<InsertNode> buySells = result.getInsertMap().get("BUY_SELL_CODE");
            assertEquals(2, buySells.size());

        } catch (IOException e) {
            logger.error(e);
            fail("Threw exception");
        } catch (JAXBException k) {
            logger.error(k);
            fail("Threw exception");
        }
    }

}
