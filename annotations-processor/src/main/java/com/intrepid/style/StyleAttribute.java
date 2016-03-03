package com.intrepid.style;

import javax.lang.model.type.TypeKind;

public class StyleAttribute {
    private String name;
    private TypeKind type;
    private String defaultVal;

    public StyleAttribute(String name, TypeKind type, String defaultVal) {
        this.name = name;
        this.type = type;
        this.defaultVal = defaultVal;
    }

    public String getName() {
        return name;
    }

    public TypeKind getType() {
        return type;
    }

    public String getDefaultVal() {
        return defaultVal;
    }
}
