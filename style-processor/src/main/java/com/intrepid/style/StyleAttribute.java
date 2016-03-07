package com.intrepid.style;

public class StyleAttribute {
    private String name;
    private AttributeType attributeType;
    private String defValue;

    public StyleAttribute(String name, AttributeType attributeType, String defValue) {
        this.name = name;
        this.attributeType = attributeType;
        this.defValue = defValue;
    }

    public String getName() {
        return name;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public String getDefValue() {
        return defValue;
    }
}
