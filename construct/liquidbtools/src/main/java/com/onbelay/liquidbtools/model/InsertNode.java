package com.onbelay.liquidbtools.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="insert")
public class InsertNode extends MyNode {

    private String tableName;

    private List<ColumnNode> columns = new ArrayList<>();

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

    @XmlAttribute
    public String getTableName() {
        return tableName;
    }

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
        return (tableName.contains("_CODE_LANG"));
    }

    public boolean isCodeIgnore() {
        if (tableName.contains("_CODE")) {
            if (tableName.contains("_CODE_LANG") == false)
                return true;
        }
        return false;
    }

    public boolean nameEquals(List<String> names) {
        for (String nameIn : names) {
            if (tableName.equalsIgnoreCase(nameIn))
                return true;
        }
        return false;
    }


}
