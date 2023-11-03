package com.onbelay.liquidbtools.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="createView")
public class CreateViewNode extends MyNode {


    private boolean fullDefinition;

    private String viewName;

    public boolean isFullDefinition() {
        return fullDefinition;
    }

    @XmlAttribute
    public void setFullDefinition(boolean fullDefinition) {
        this.fullDefinition = fullDefinition;
    }

    public String getViewName() {
        return viewName;
    }

    @XmlAttribute
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
