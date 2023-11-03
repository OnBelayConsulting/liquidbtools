package com.onbelay.liquidbtools.model;

import jakarta.xml.bind.annotation.XmlValue;

public abstract class MyNode {


    private String text;

    public String getText() {
        return text;
    }

    @XmlValue
    public void setText(String text) {
        this.text = text;
    }


}
