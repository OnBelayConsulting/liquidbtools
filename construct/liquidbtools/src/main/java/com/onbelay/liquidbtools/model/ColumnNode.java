package com.onbelay.liquidbtools.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name="column")
public class ColumnNode {
    private String name;
    private String type;
    private String value;
    private boolean ignore = false;

    private String defaultValueComputed;

    private Integer defaultValueNumeric;

    private String defaultValueConstraintName;

    private ConstraintsNode constraints;

    public String getName() {
        return name;
    }

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @XmlAttribute
    public void setValue(String value) {
        this.value = value;
    }

    @XmlAttribute
    public void setType(String type) {
        this.type = type;
    }

    public ConstraintsNode getConstraints() {
        return constraints;
    }

    @XmlElement(name = "constraints")
    public void setConstraints(ConstraintsNode constraints) {
        this.constraints = constraints;
    }

    public String getDefaultValueComputed() {
        return defaultValueComputed;
    }

    @XmlAttribute
    public void setDefaultValueComputed(String defaultValueComputed) {
        this.defaultValueComputed = defaultValueComputed;
    }

    public Integer getDefaultValueNumeric() {
        return defaultValueNumeric;
    }

    @XmlAttribute
    public void setDefaultValueNumeric(Integer defaultValueNumeric) {
        this.defaultValueNumeric = defaultValueNumeric;
    }

    public String getDefaultValueConstraintName() {
        return defaultValueConstraintName;
    }

    @XmlAttribute
    public void setDefaultValueConstraintName(String defaultValueConstraintName) {
        this.defaultValueConstraintName = defaultValueConstraintName;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public boolean nameContains(List<String> names) {
        for (String nameIn : names) {
            if (name.contains(nameIn))
                return true;
        }
        return false;
    }

}
