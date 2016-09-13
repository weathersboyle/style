package style;

import java.util.EnumMap;
import java.util.Map;

import javax.lang.model.type.TypeKind;

public enum AttributeType {
    BOOLEAN(Boolean.class.getName(), TypeKind.BOOLEAN, Styleable.METHOD_GET_BOOLEAN),
    COLOR(Integer.class.getName(), TypeKind.INT, Styleable.METHOD_GET_COLOR),
    COLOR_STATE_LIST(Styleable.COLOR_STATE_LIST_CLASS_NAME, null, Styleable.METHOD_GET_COLOR_STATE_LIST),
    DIMENSION(Integer.class.getName(), TypeKind.INT, Styleable.METHOD_GET_DIMEN),
    DIMENSION_PIXEL_OFFSET(Integer.class.getName(), TypeKind.INT, Styleable.METHOD_GET_DIMEN_PIX_OFFSET),
    DIMENSION_PIXEL_SIZE(Integer.class.getName(), TypeKind.INT, Styleable.METHOD_GET_DIMEN_PIX_SIZE),
    DRAWABLE(Styleable.DRAWABLE_CLASS_NAME, null, Styleable.METHOD_GET_DRAWABLE),
    FLOAT(Float.class.getName(), TypeKind.FLOAT, Styleable.METHOD_GET_FLOAT),
    INT(Integer.class.getName(), TypeKind.INT, Styleable.METHOD_GET_INT),
    INTEGER(Integer.class.getName(), TypeKind.INT, Styleable.METHOD_GET_INTEGER),
    LAYOUT_DIMENSION(Integer.class.getName(), TypeKind.INT, Styleable.METHOD_GET_LAYOUT_DIMEN),
    NON_RESOURCE_STRING(String.class.getName(), null, Styleable.METHOD_GET_NON_RES_STRING),
    RESOURCE_ID(Integer.class.getName(), TypeKind.INT, Styleable.METHOD_GET_RES_ID),
    STRING(String.class.getName(), null, Styleable.METHOD_GET_STRING),
    TEXT(CharSequence.class.getName(), null, Styleable.METHOD_GET_TEXT),
    GUESS(null, null, null);

    private static Map<AttributeType, String> classNameMap;
    private static Map<AttributeType, TypeKind> typeKindMap;
    private static Map<AttributeType, String> methodMap;

    static {
        classNameMap = new EnumMap<>(AttributeType.class);
        typeKindMap = new EnumMap<>(AttributeType.class);
        methodMap = new EnumMap<>(AttributeType.class);
        for (AttributeType attrType : AttributeType.values()) {
            classNameMap.put(attrType, attrType.getPartnerClassName());
            typeKindMap.put(attrType, attrType.getCompatibleType());
            methodMap.put(attrType, attrType.getTypedArrMethodName());
        }
    }

    private final String partnerClassName;
    private final TypeKind compatibleType;
    private final String typedArrMethodName;

    AttributeType(String partnerClassName, TypeKind compatibleType, String typedArrMethodName) {
        this.partnerClassName = partnerClassName;
        this.compatibleType = compatibleType;
        this.typedArrMethodName = typedArrMethodName;
    }

    public static Map<AttributeType, String> getClassNameMap() {
        return classNameMap;
    }

    public static Map<AttributeType, TypeKind> getTypeKindMap() {
        return typeKindMap;
    }

    public static Map<AttributeType, String> getMethodMap() {
        return methodMap;
    }

    public String getPartnerClassName() {
        return partnerClassName;
    }

    public TypeKind getCompatibleType() {
        return compatibleType;
    }

    public String getTypedArrMethodName() {
        return typedArrMethodName;
    }
}
