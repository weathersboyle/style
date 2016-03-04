package com.intrepid.style;

import javax.lang.model.type.TypeKind;

public class StyleAttribute {
    private String name;
    private TypeKind type;
    private AttributeType attributeType;
    private String defValue;

    public StyleAttribute(String name, TypeKind type, AttributeType attributeType, String defValue) {
        this.name = name;
        this.type = type;
        this.attributeType = attributeType;
        this.defValue = defValue;
    }

    public String getName() {
        return name;
    }

    public TypeKind getType() {
        return type;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public String getDefValue() {
        return defValue;
    }
}
