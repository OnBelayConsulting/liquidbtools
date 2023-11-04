package com.onbelay.liquidbtools.model;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="changeSet")
public class ChangeSet {

    private String id;

    private String author;

    private Boolean runAlways;

    private Boolean runOnChange;

    private List<MyNode> nodes = new ArrayList<>();

    public ChangeSet() {
    }

    public ChangeSet(String author) {
        this.author = author;
    }

    public List<MyNode> getNodes() {
        return nodes;
    }

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    @XmlAttribute
    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getRunAlways() {
        return runAlways;
    }

    @XmlAttribute
    public void setRunAlways(Boolean runAlways) {
        this.runAlways = runAlways;
    }

    public Boolean getRunOnChange() {
        return runOnChange;
    }

    @XmlAttribute
    public void setRunOnChange(Boolean runOnChange) {
        this.runOnChange = runOnChange;
    }

    @XmlElementRef
    public void setNodes(List<MyNode> nodes) {
        this.nodes = nodes;
    }


    public void addNode(MyNode node) {
        nodes.add(node);
    }

}
