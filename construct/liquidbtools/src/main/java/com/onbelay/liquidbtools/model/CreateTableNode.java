package com.onbelay.liquidbtools.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="createTable")
public class CreateTableNode extends MyNode {

    private String tableName;

    private String tablespace;

    private List<ColumnNode> columns = new ArrayList<>();

    public String getTableName() {
        return tableName;
    }

    @XmlElement(name = "column")
    public List<ColumnNode> getColumnNodes() {
        return columns;
    }

    public void setColumnNodes(List<ColumnNode> columns) {
        this.columns = columns;
    }

    public void addColumnNode(ColumnNode columnNode) {
        columns.add(columnNode);
    }

    @XmlAttribute(name = "tableName")
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean nameContains(List<String> names) {
        for (String nameIn : names) {
            if (tableName.contains(nameIn))
                return true;
        }
        return false;
    }

    public boolean isCodeTable() {
        return (tableName.contains("_CODE"));
    }


    public boolean nameEquals(List<String> names) {
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
