package com.onbelay.liquidbtools.command;

import com.onbelay.liquidbtools.read.ChangeLogContainer;
import com.onbelay.liquidbtools.read.ChangeLogReader;
import com.onbelay.liquidbtools.write.ChangeLogWriter;
import jakarta.xml.bind.JAXBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@Profile("!test")
@Component
public class RegenerateChangeLogCmd implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger();

    @Value("${application.name:myapp}")
    private String applicationName;

    @Value("${liquidbtools.ignore.tables}")
    private String[] ignoreTableNamesList;

    @Value("${liquidbtools.ignore.tables.partialmatch}")
    private String[] ignoreTableNamesPartialMatchList;

    @Value("${liquidbtools.ignore.sequences.partialmatch}")
    private String[] ignoreSequenceNamesPartialMatchList;

    @Value("${liquidbtools.logicalfile.prefix:com.onbelay}")
    private String logicalFilePrefix;

    @Value("${liquidbtools.ignore.column.names}")
    private String[] ignoreColumnNamesList;

    @Value("${liquidbtools.ignore.column.names.bytable}")
    private String[] ignoreColumnNamesByTableList;

    @Value("${liquidbtools.db.tablespace.data.name}")
    private String dbDataTableSpaceName;

    @Value("${liquidbtools.db.tablespace.index.name}")
    private String dbIndexTableSpaceName;


    public void run(InputStream inputStream) throws Exception {
        readAndWrite(inputStream);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length < 1) {
            System.out.println("Specify the fileName");
            return;
        }
        String inputFileName = args[0];
        logger.error("processing file: " + inputFileName);
        try (InputStream inputStream = new FileInputStream(new File(inputFileName))) {
            readAndWrite(inputStream);
        } catch (FileNotFoundException e) {
            logger.error("file error", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("io error", e);
            throw new RuntimeException(e);
        } catch (JAXBException e) {
            logger.error("jaxb error", e);
            throw new RuntimeException(e);
        }

}
    private void readAndWrite(InputStream inputStream) throws JAXBException {
        HashMap<String, List<String>> ignoreColumnsByTableMap = new HashMap<>();
        if (ignoreColumnNamesByTableList != null) {
            for (String tableColumnName : ignoreColumnNamesByTableList) {
                String[] parts = tableColumnName.split("\\.");
                if (parts.length < 2)
                    throw new RuntimeException("Column names by table requires a table name prefix: tableName.columnName");
                List<String> columnNamesList = ignoreColumnsByTableMap.get(parts[0]);
                if (columnNamesList == null) {
                    columnNamesList = new ArrayList<>();
                    ignoreColumnsByTableMap.put(parts[0], columnNamesList);
                }
                columnNamesList.add(parts[1]);
            }
        }

            ChangeLogReader reader = ChangeLogReader.newReader(inputStream);
            reader.withIgnoreColumnNamesByTable(ignoreColumnsByTableMap);
            if (ignoreColumnNamesList != null)
                reader.withIgnoreColumnNamesAll(ignoreColumnNamesList);
            if (ignoreTableNamesList != null)
                reader.withIgnoreTableNames(ignoreTableNamesList);
            if (ignoreTableNamesPartialMatchList != null)
                reader.withIgnoreTableNamesPartialMatch(ignoreTableNamesPartialMatchList);
            if (ignoreSequenceNamesPartialMatchList != null)
                reader.withIgnoreSequenceNamesPartialMatch(ignoreSequenceNamesPartialMatchList);
            String dataTableSpaceName = "${tblspace_data}";
            String indexTableSpaceName = "${tblspace_data}";

            if (dbDataTableSpaceName != null)
                dataTableSpaceName = dbDataTableSpaceName;

            if (dbIndexTableSpaceName != null)
                indexTableSpaceName = dbIndexTableSpaceName;
            reader.withTableSpaceNames(
                    dataTableSpaceName,
                    indexTableSpaceName);

            ChangeLogContainer container = reader.read();
            logger.error("read file");

            ChangeLogWriter writer = ChangeLogWriter
                    .newWriter(applicationName)
                    .withLogicalFilePrefix(logicalFilePrefix);

            writer.write(container);
            logger.error("wrote file");

    }

}
