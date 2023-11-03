package com.onbelay.liquidbtools.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="constraints")
public class ConstraintsNode {

    private boolean nullable;

    private boolean primaryKey;

    private String primaryKeyTablespace;

    private String primaryKeyName;

    public boolean isNullable() {
        return nullable;
    }

    @XmlAttribute(name = "nullable")
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    @XmlAttribute(name = "primaryKey")
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    @XmlAttribute(name = "primaryKeyName")
    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }

    public String getPrimaryKeyTablespace() {
        return primaryKeyTablespace;
    }

    @XmlAttribute(name = "primaryKeyTablespace")
    public void setPrimaryKeyTablespace(String primaryKeyTablespace) {
        this.primaryKeyTablespace = primaryKeyTablespace;
    }
}
