package com.onbelay.liquidbtools.model;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name="databaseChangeLog")
public class DatabaseChangeLog {

    private String logicalFilePath;

    private List<Object> any;

    private List<ChangeSet> changeSets = new ArrayList<>();

    public String getLogicalFilePath() {
        return logicalFilePath;
    }

    @XmlAttribute
    public void setLogicalFilePath(String logicalFilePath) {
        this.logicalFilePath = logicalFilePath;
    }

    public List<ChangeSet> getChangeSets() {
        return changeSets;
    }

    @XmlElement(name = "changeSet")
    public void setChangeSets(List<ChangeSet> changeSets) {
        this.changeSets = changeSets;
    }


    public void addChangeSet(ChangeSet changeSet) {
        changeSets.add(changeSet);
    }

    public List<Object> getAny() {
        return any;
    }

    @XmlAnyElement
    public void setAny(List<Object> any) {
        this.any = any;
    }
}
