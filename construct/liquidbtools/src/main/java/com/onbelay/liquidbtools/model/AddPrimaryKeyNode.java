package com.onbelay.liquidbtools.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name="addPrimaryKey")
public class AddPrimaryKeyNode extends MyNode{

    private String columnNames;

    private String constraintName;

    private String tableName;

    private String tablespace;

    public String getColumnNames() {
        return columnNames;
    }

    @XmlAttribute
    public void setColumnNames(String columnNames) {
        this.columnNames = columnNames;
    }

    public String getConstraintName() {
        return constraintName;
    }

    @XmlAttribute
    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public String getTableName() {
        return tableName;
    }

    @XmlAttribute
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
