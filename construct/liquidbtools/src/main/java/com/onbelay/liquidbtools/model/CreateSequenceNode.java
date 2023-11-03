package com.onbelay.liquidbtools.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name="createSequence")
public class CreateSequenceNode extends MyNode {

    private boolean cycle;
    
    private int incrementBy;

    private String sequenceName;
    
    private int startValue;

    public boolean isCycle() {
        return cycle;
    }

    @XmlAttribute
    public void setCycle(boolean cycle) {
        this.cycle = cycle;
    }

    public int getIncrementBy() {
        return incrementBy;
    }

    @XmlAttribute
    public void setIncrementBy(int incrementBy) {
        this.incrementBy = incrementBy;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    @XmlAttribute
    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public int getStartValue() {
        return startValue;
    }

    @XmlAttribute
    public void setStartValue(int startValue) {
        this.startValue = startValue;
    }


    public boolean nameContains(List<String> names) {
        for (String nameIn : names) {
            if (sequenceName.contains(nameIn))
                return true;
        }
        return false;
    }

    public boolean nameEquals(List<String> names) {
        for (String nameIn : names) {
            if (sequenceName.equalsIgnoreCase(nameIn))
                return true;
        }
        return false;
    }


}
