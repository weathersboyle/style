package com.intrepid.style;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.type.TypeKind;

public enum AttributeType {
    BOOLEAN(Boolean.class.getName(), TypeKind.BOOLEAN),
    COLOR(Integer.class.getName(), TypeKind.INT),
    COLOR_STATE_LIST(Style.COLOR_STATE_LIST_CLASS_NAME, null),
    DIMENSION(Integer.class.getName(), TypeKind.INT),
    DIMENSION_PIXEL_OFFSET(Integer.class.getName(), TypeKind.INT),
    DIMENSION_PIXEL_SIZE(Integer.class.getName(), TypeKind.INT),
    DRAWABLE(Style.DRAWABLE_CLASS_NAME ,null),
    FLOAT(Float.class.getName(), TypeKind.FLOAT),
    INT(Integer.class.getName(), TypeKind.INT),
    INTEGER(Integer.class.getName(), TypeKind.INT),
    LAYOUT_DIMENSION(Integer.class.getName(), TypeKind.INT),
    NON_RESOURCE_STRING(String.class.getName(), null),
    RESOURCE_ID(Integer.class.getName(), TypeKind.INT),
    STRING(String.class.getName(), null),
    TEXT(CharSequence.class.getName(), null),
    TYPE(Integer.class.getName(), TypeKind.INT),
    GUESS(null, null);

    private static Map<AttributeType, String> classNameMap;
    private static Map<AttributeType, TypeKind> typeMap;

    static {
        classNameMap = new HashMap<>();
        typeMap = new HashMap<>();
        for (AttributeType attrType : AttributeType.values()) {
            classNameMap.put(attrType, attrType.getPartnerClassName());
            typeMap.put(attrType, attrType.getCompatibleType());
        }
    }

    private final String partnerClassName;
    private final TypeKind compatibleType;

    AttributeType(String partnerClassName, TypeKind compatibleType) {
        this.partnerClassName = partnerClassName;
        this.compatibleType = compatibleType;
    }

    public static Map<AttributeType, String> getClassNameMap() {
        return classNameMap;
    }

    public static Map<AttributeType, TypeKind> getTypeMap() {
        return typeMap;
    }

    public String getPartnerClassName() {
        return partnerClassName;
    }

    public TypeKind getCompatibleType() {
        return compatibleType;
    }
}
