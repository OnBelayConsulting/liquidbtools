package com.onbelay.liquidbtools.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name="addForeignKeyConstraint")
public class AddForeignKeyConstraintNode extends MyNode{
    private String baseColumnNames;
    
    private String baseTableName;
    
    private String constraintName;
    
    private boolean deferrable;
    
    private boolean initiallyDeferred;
    
    private String onDelete;
    
    private String onUpdate;
    
    private String referencedColumnNames;
    
    private String referencedTableName;
    
    private boolean validate;

    public String getBaseColumnNames() {
        return baseColumnNames;
    }

    @XmlAttribute
    public void setBaseColumnNames(String baseColumnNames) {
        this.baseColumnNames = baseColumnNames;
    }

    public String getBaseTableName() {
        return baseTableName;
    }

    @XmlAttribute
    public void setBaseTableName(String baseTableName) {
        this.baseTableName = baseTableName;
    }

    public String getConstraintName() {
        return constraintName;
    }

    @XmlAttribute
    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public boolean isDeferrable() {
        return deferrable;
    }

    @XmlAttribute
    public void setDeferrable(boolean deferrable) {
        this.deferrable = deferrable;
    }

    public boolean isInitiallyDeferred() {
        return initiallyDeferred;
    }

    @XmlAttribute
    public void setInitiallyDeferred(boolean initiallyDeferred) {
        this.initiallyDeferred = initiallyDeferred;
    }

    public String getOnDelete() {
        return onDelete;
    }

    @XmlAttribute
    public void setOnDelete(String onDelete) {
        this.onDelete = onDelete;
    }

    public String getOnUpdate() {
        return onUpdate;
    }

    @XmlAttribute
    public void setOnUpdate(String onUpdate) {
        this.onUpdate = onUpdate;
    }

    public String getReferencedColumnNames() {
        return referencedColumnNames;
    }

    @XmlAttribute
    public void setReferencedColumnNames(String referencedColumnNames) {
        this.referencedColumnNames = referencedColumnNames;
    }

    public String getReferencedTableName() {
        return referencedTableName;
    }

    @XmlAttribute
    public void setReferencedTableName(String referencedTableName) {
        this.referencedTableName = referencedTableName;
    }

    public boolean isValidate() {
        return validate;
    }

    @XmlAttribute
    public void setValidate(boolean validate) {
        this.validate = validate;
    }


    public boolean nameContains(List<String> names) {
        for (String nameIn : names) {
            if (baseTableName.contains(nameIn))
                return true;
        }
        return false;
    }


    public boolean referenceTableNameContains(List<String> names) {
        for (String nameIn : names) {
            if (referencedTableName.contains(nameIn))
                return true;
        }
        return false;
    }


    public boolean nameEquals(List<String> names) {
        for (String nameIn : names) {
            if (baseTableName.equalsIgnoreCase(nameIn))
                return true;
        }
        return false;
    }


}
