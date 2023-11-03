package com.onbelay.liquidbtools.read;

import com.onbelay.liquidbtools.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ChangeLogProcessor {
    private static final Logger logger = LogManager.getLogger();

    private  Map<Class<? extends MyNode>, Consumer<MyNode>> mapper = new HashMap<>();


    private String dbDataTablespaceName = "${tblspace_data}";
    private String dbIndexTablespaceName = "${tblspace_data}";
    private List<String> ignoreTableNames = new ArrayList<>();
    private List<String> ignoreTableNamesPartialMatch = new ArrayList<>();
    private List<String> ignoreSequencesNamesPartialMatch = new ArrayList<>();

    private List<String> ignoreColumnNamesAll = new ArrayList<>();
    private Map<String, List<String>> ignoreColumnNamesByTableMap = new HashMap<>();

    private ChangeLogContainer container = new ChangeLogContainer();

    public static ChangeLogProcessor  newProcessor() {
        return new ChangeLogProcessor();
    }

    private ChangeLogProcessor() {
        initialize();
    }

    public ChangeLogProcessor withIgnoreTableNames(
            List<String> ignoreTableNames) {

        this.ignoreTableNames = ignoreTableNames;
        return this;
    }

    public ChangeLogProcessor withIgnoreTableNamesPartialMatch(
            List<String> ignoreTableNames) {

        this.ignoreTableNamesPartialMatch = ignoreTableNames;
        return this;
    }

    public ChangeLogProcessor withIgnoreSequenceNamesPartialMatch(
            List<String> ignoreNames) {

        this.ignoreSequencesNamesPartialMatch = ignoreNames;
        return this;
    }

    public ChangeLogProcessor withDataTablespaceNames(
            String dbDataTablespaceName,
            String dbIndexTablespaceName) {
        this.dbDataTablespaceName = dbDataTablespaceName;
        this.dbIndexTablespaceName = dbIndexTablespaceName;
        return this;
    }

    public ChangeLogProcessor withIgnoreColumnsAll(List<String> columnNames) {
        this.ignoreColumnNamesAll = columnNames;
        return this;
    }
    public ChangeLogProcessor withIgnoreColumnsByTable(Map<String, List<String>> ignoreColumnNamesByTableMap) {
        this.ignoreColumnNamesByTableMap = ignoreColumnNamesByTableMap;
        return this;
    }

    protected void initialize() {

        mapper.put(CreateSequenceNode.class, tableNode -> addSequenceNode(tableNode));
        mapper.put(CreateTableNode.class, tableNode -> addTableNode(tableNode));
        mapper.put(CreateIndexNode.class, tableNode -> addIndexNode(tableNode));
        mapper.put(CreateViewNode.class, tableNode -> addViewNode(tableNode));
        mapper.put(AddForeignKeyConstraintNode.class, tableNode -> addForeignKeyConstraintsNode(tableNode));
        mapper.put(AddUniqueConstraintNode.class, tableNode -> addUniqueConstraintsNode(tableNode));
        mapper.put(AddPrimaryKeyNode.class, tableNode -> addPrimaryKeyNode(tableNode));
        mapper.put(InsertNode.class, tableNode -> addInsertNode(tableNode));

    }

    public void addNode(MyNode node) {
        mapper.get(node.getClass()).accept(node);
    }

    public ChangeLogContainer getContainer() {
        return container;
    }


    private void addInsertNode(MyNode node) {
        InsertNode insertNode = (InsertNode) node;

        if (insertNode.nameContains(ignoreTableNamesPartialMatch))
            return;

        if (insertNode.nameEquals(ignoreTableNames))
            return;

        List<String> columnNamesToIgnore = ignoreColumnNamesByTableMap.get(insertNode.getTableName());

        for (ColumnNode columnNode : insertNode.getColumnNodes()) {
            if (columnNode.nameContains(ignoreColumnNamesAll))
                columnNode.setIgnore(true);
            if (columnNamesToIgnore != null) {
                if (columnNode.nameContains(columnNamesToIgnore))
                    columnNode.setIgnore(true);
            }
        }

        insertNode.setColumnNodes(
                insertNode.getColumnNodes()
                        .stream()
                        .filter(c-> c.isIgnore() == false)
                        .collect(Collectors.toList()));

        container.addInsertNode(insertNode.getTableName(), insertNode);
    }

    private void addTableNode(MyNode node) {
        CreateTableNode createTableNode = (CreateTableNode) node;

        if (createTableNode.nameContains(ignoreTableNamesPartialMatch))
            return;

        if (createTableNode.nameEquals(ignoreTableNames))
            return;

        createTableNode.setTablespace(dbDataTablespaceName);
        List<String> columnNamesInTableToIgnore = ignoreColumnNamesByTableMap.get(((CreateTableNode) node).getTableName());

        for (ColumnNode columnNode : createTableNode.getColumnNodes()) {
            if (columnNode.nameContains(ignoreColumnNamesAll)) {
                columnNode.setIgnore(true);
                continue;
            }
            if (columnNamesInTableToIgnore != null) {
                if (columnNode.nameContains(columnNamesInTableToIgnore)) {
                    columnNode.setIgnore(true);
                    continue;
                }

            }

            if (columnNode.getConstraints() != null) {
                if (columnNode.getConstraints().isPrimaryKey())
                    columnNode.getConstraints().setPrimaryKeyTablespace(dbIndexTablespaceName);
            }
        }

        createTableNode.setColumnNodes(
                createTableNode.getColumnNodes()
                        .stream()
                        .filter(c -> c.isIgnore() == false)
                        .collect(Collectors.toList()));

        if (createTableNode.isCodeTable()) {
            container.getCodeTables().add(createTableNode);
        } else {
            container.getTables().add(createTableNode);
        }


    }


    private void addSequenceNode(MyNode node) {
        CreateSequenceNode createSequenceNode = (CreateSequenceNode) node;

        if (createSequenceNode.nameContains(ignoreSequencesNamesPartialMatch))
            return;
        container.getSequences().add(createSequenceNode);
    }

    private void addForeignKeyConstraintsNode(MyNode node) {
        AddForeignKeyConstraintNode foreignKeyConstraintNode = (AddForeignKeyConstraintNode) node;
        if (foreignKeyConstraintNode.nameEquals(ignoreTableNames))
            return;

        if (foreignKeyConstraintNode.nameContains(ignoreTableNamesPartialMatch))
            return;

        List<String> columnNamesInTableToIgnore = ignoreColumnNamesByTableMap.get(foreignKeyConstraintNode.getBaseTableName());
        String[] parts = foreignKeyConstraintNode.getBaseColumnNames().split(",");
        boolean ignoreConstraint = false;
        for (String name : parts) {
            for (String match : ignoreColumnNamesAll) {
                if (name.contains(match))
                    ignoreConstraint = true;
                break;
            }
            if (columnNamesInTableToIgnore != null) {
                for (String match : columnNamesInTableToIgnore) {
                    if (name.contains(match))
                        ignoreConstraint = true;
                    break;
                }
            }
        }

        if (ignoreConstraint == false)
            container.getForeignConstraints().add(foreignKeyConstraintNode);
    }

    private void addUniqueConstraintsNode(MyNode node) {
        AddUniqueConstraintNode uniqueConstraintNode = (AddUniqueConstraintNode) node;

        if (uniqueConstraintNode.nameEquals(ignoreTableNames))
            return;

        if (uniqueConstraintNode.nameContains(ignoreTableNamesPartialMatch))
            return;

        List<String> columnNamesInTableToIgnore = ignoreColumnNamesByTableMap.get(uniqueConstraintNode.getTableName());
        String[] parts = uniqueConstraintNode.getColumnNames().split(",");
        boolean ignoreConstraint = false;
        for (String name : parts) {
            for (String match : ignoreColumnNamesAll) {
                if (name.contains(match))
                    ignoreConstraint = true;
                break;
            }
            if (columnNamesInTableToIgnore != null) {
                for (String match : columnNamesInTableToIgnore) {
                    if (name.contains(match))
                        ignoreConstraint = true;
                    break;
                }
            }
        }

        if (ignoreConstraint == false)
            container.getUniqueConstraints().add(uniqueConstraintNode);
    }

    private void addViewNode(MyNode node) {
        CreateViewNode createViewNode = (CreateViewNode) node;
        container.getViews().add(createViewNode);
    }


    private void addPrimaryKeyNode(MyNode node) {
        AddPrimaryKeyNode primaryKeyNode = (AddPrimaryKeyNode) node;
        if (primaryKeyNode.nameContains(ignoreTableNamesPartialMatch))
            return;

        primaryKeyNode.setTablespace(dbIndexTablespaceName);
        container.getPrimaryKeys().add(primaryKeyNode);
    }

    private void addIndexNode(MyNode node) {
        CreateIndexNode indexNode = (CreateIndexNode) node;

        if (indexNode.tableNameContains(ignoreTableNamesPartialMatch))
            return;

        if (indexNode.tableNameEquals(ignoreTableNamesPartialMatch))
            return;

        List<String> columnNamesInTableToIgnore = ignoreColumnNamesByTableMap.get(indexNode.getTableName());

        for (ColumnNode columnNode : indexNode.getColumnNodes()) {
            if (columnNode.nameContains(ignoreColumnNamesAll))
                columnNode.setIgnore(true);
            if (columnNamesInTableToIgnore != null) {
                if (columnNode.nameContains(columnNamesInTableToIgnore))
                    columnNode.setIgnore(true);
            }
        }
        indexNode.setColumnNodes(
                indexNode.getColumnNodes()
                        .stream()
                        .filter(c-> c.isIgnore() == false)
                        .collect(Collectors.toList()));

        indexNode.setTablespace(dbIndexTablespaceName);
        container.getIndices().add(indexNode);
    }
}
