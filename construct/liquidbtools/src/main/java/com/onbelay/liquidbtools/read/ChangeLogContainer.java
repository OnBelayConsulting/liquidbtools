package com.onbelay.liquidbtools.read;

import com.onbelay.liquidbtools.model.*;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeLogContainer {

    private Map<String, List<InsertNode>> insertMap = new HashMap<>();

    private List<CreateTableNode> tables = new ArrayList<>();

    private List<CreateTableNode> codeTables = new ArrayList<>();

    private List<CreateIndexNode> indices = new ArrayList<>();

    private List<CreateSequenceNode> sequences = new ArrayList<>();

    private List<AddForeignKeyConstraintNode> foreignConstraints = new ArrayList<>();

    private List<AddUniqueConstraintNode> uniqueConstraints = new ArrayList<>();

    private List<AddPrimaryKeyNode> primaryKeys = new ArrayList<>();

    private List<CreateViewNode> views = new ArrayList<>();

    public List<CreateTableNode> getTables() {
        return tables;
    }

    public List<CreateIndexNode> getIndices() {
        return indices;
    }

    public List<CreateSequenceNode> getSequences() {
        return sequences;
    }

    public List<AddForeignKeyConstraintNode> getForeignConstraints() {
        return foreignConstraints;
    }

    public List<AddUniqueConstraintNode> getUniqueConstraints() {
        return uniqueConstraints;
    }

    public List<AddPrimaryKeyNode> getPrimaryKeys() {
        return primaryKeys;
    }

    public List<CreateViewNode> getViews() {
        return views;
    }

    public List<CreateTableNode> getCodeTables() {
        return codeTables;
    }

    public void addInsertNode(String tableName, InsertNode insertNode) {
        List<InsertNode> nodes = insertMap.get(tableName);
        if (nodes == null) {
            nodes = new ArrayList<>();
            insertMap.put(tableName, nodes);
        }
        nodes.add(insertNode);

    }

    public Map<String, List<InsertNode>> getInsertMap() {
        return insertMap;
    }
}
