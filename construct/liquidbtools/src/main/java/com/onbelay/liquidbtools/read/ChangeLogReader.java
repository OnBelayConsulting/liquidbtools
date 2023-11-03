package com.onbelay.liquidbtools.read;

import com.onbelay.liquidbtools.model.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import java.io.InputStream;
import java.util.*;

public class ChangeLogReader {

    private String dbDataTableSpaceName;
    private String dbIndexTableSpaceName;

    private InputStream inputStream;
    private List<String> ignoreTableNames = new ArrayList<>();
    private List<String> ignoreTableNamesPartialMatch = new ArrayList<>();
    private List<String> ignoreSequencesNamesPartialMatch = new ArrayList<>();

    private List<String> ignoreColumnNamesAll = new ArrayList<>();
    private Map<String, List<String>> ignoreColumnNamesByTableMap = new HashMap<>();

    /**
     * Create a new reader in the fluent style.
     * @param inputStream - input stream to read from
     * @return
     */
    public static ChangeLogReader newReader(InputStream inputStream) {
        return new ChangeLogReader(inputStream);
    }

    /**
     * Pass in an array of tablenames to be ignored
     * @param ignoreNamesArray
     * @return
     */
    public ChangeLogReader withIgnoreTableNames(String[] ignoreNamesArray) {
        this.ignoreTableNames = Arrays.stream(ignoreNamesArray).toList();
        return this;
    }

    public ChangeLogReader withIgnoreTableNames(List<String> ignoreNamesList) {
        this.ignoreTableNames = ignoreNamesList;
        return this;
    }


    /**
     * Pass in a list of string name to apply a partial match to - to ignore
     * @param ignoreNamesArray
     * @return
     */
    public ChangeLogReader withIgnoreTableNamesPartialMatch(String[] ignoreNamesArray) {
        this.ignoreTableNamesPartialMatch = Arrays.stream(ignoreNamesArray).toList();
        return this;
    }

    public ChangeLogReader withIgnoreTableNamesPartialMatch(List<String> ignoreNamesList) {
        this.ignoreTableNamesPartialMatch = ignoreNamesList;
        return this;
    }

    /**
     * Pass in an array of names to use in a partial match against sequence names to ignore
     * @param ignoreNamesArray
     * @return
     */
    public ChangeLogReader withIgnoreSequenceNamesPartialMatch(String[] ignoreNamesArray) {
        this.ignoreSequencesNamesPartialMatch = Arrays.stream(ignoreNamesArray).toList();
        return this;
    }
    public ChangeLogReader withIgnoreSequenceNamesPartialMatch(List<String> ignoreNamesList) {
        this.ignoreSequencesNamesPartialMatch = ignoreNamesList;
        return this;
    }

    /**
     * Pass in tablespace names to be inserted into nodes.
     * @param dbDataTableSpaceName
     * @param dbIndexTableSpaceName
     * @return
     */
    public ChangeLogReader withTableSpaceNames(
            String dbDataTableSpaceName,
            String dbIndexTableSpaceName) {
        this.dbDataTableSpaceName = dbDataTableSpaceName;
        this.dbIndexTableSpaceName = dbIndexTableSpaceName;
        return this;
    }

    /**
     * pass in a map by tableName with a list of column names to be ignored.
     * @param ignoreColumnNamesByTableMap
     * @return
     */
    public ChangeLogReader withIgnoreColumnNamesByTable(Map<String, List<String>> ignoreColumnNamesByTableMap) {
        this.ignoreColumnNamesByTableMap = ignoreColumnNamesByTableMap;
        return this;
    }

    public ChangeLogReader withIgnoreColumnNamesAll(String[] ignoreNamesArray) {
        this.ignoreColumnNamesAll =Arrays.stream(ignoreNamesArray).toList();
        return this;
    }

    public ChangeLogReader withIgnoreColumnNamesAll(List<String> ignoreNamesList) {
        this.ignoreColumnNamesAll = ignoreNamesList;
        return this;
    }


    private ChangeLogReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ChangeLogContainer read() throws JAXBException {
        ChangeLogProcessor processor = ChangeLogProcessor.newProcessor()
                .withDataTablespaceNames(dbDataTableSpaceName, dbIndexTableSpaceName)
                .withIgnoreTableNames(ignoreTableNames)
                .withIgnoreTableNamesPartialMatch(ignoreTableNamesPartialMatch)
                .withIgnoreSequenceNamesPartialMatch(ignoreSequencesNamesPartialMatch)
                .withIgnoreColumnsAll(ignoreColumnNamesAll)
                .withIgnoreColumnsByTable(ignoreColumnNamesByTableMap);


        JAXBContext context = JAXBContext.newInstance(
                DatabaseChangeLog.class,
                InsertNode.class,
                CreateViewNode.class,
                ColumnNode.class,
                CreateTableNode.class,
                CreateSequenceNode.class,
                AddForeignKeyConstraintNode.class,
                AddUniqueConstraintNode.class,
                AddPrimaryKeyNode.class,
                CreateIndexNode.class);
        DatabaseChangeLog log = (DatabaseChangeLog) context.createUnmarshaller().unmarshal(inputStream);

        for (ChangeSet changeSet : log.getChangeSets()) {
            for (MyNode node : changeSet.getNodes()) {
                processor.addNode(node);
            }
        }
        return processor.getContainer();
    }

}
