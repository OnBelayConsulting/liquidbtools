package com.onbelay.liquidbtools.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="createIndex")
public class CreateIndexNode extends MyNode {

    private String indexName;

    private String tableName;

    private String tablespace;

    private List<ColumnNode> columnNodes = new ArrayList<>();

    public String getIndexName() {
        return indexName;
    }

    @XmlAttribute
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTableName() {
        return tableName;
    }

    @XmlAttribute
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnNode> getColumnNodes() {
        return columnNodes;
    }

    @XmlElementRef
    public void setColumnNodes(List<ColumnNode> columnNodes) {
        this.columnNodes = columnNodes;
    }

    public void addColumnNode(ColumnNode columnNode) {
        columnNodes.add(columnNode);
    }


    public boolean tableNameContains(List<String> names) {
        for (String nameIn : names) {
            if (tableName.contains(nameIn))
                return true;
        }
        return false;
    }

    public boolean tableNameEquals(List<String> names) {
        for (String nameIn : names) {
            if (tableName.equalsIgnoreCase(nameIn))
                return true;
        }
        return false;
    }


    public String getTablespace() {
        return tablespace;
    }

    @XmlAttribute
    public void setTablespace(String tablespace) {
        this.tablespace = tablespace;
    }


}
