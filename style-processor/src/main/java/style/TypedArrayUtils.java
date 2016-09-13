package style;

import javax.lang.model.type.TypeKind;

public class TypedArrayUtils {
    public static final String STYLEABLE_RES_PREFIX = "R.styleable";
    public static final String METHOD_OBTAIN_ATTRS = Constants.METHOD_OBTAIN_ATTRS;

    public static String getPartnerClassName(AttributeType attrType) {
        return AttributeType.getClassNameMap().get(attrType);
    }

    public static TypeKind getCompatibleTypeKind(AttributeType attrType) {
        return AttributeType.getTypeKindMap().get(attrType);
    }

    public static String getTypedArrMethodName(AttributeType attrType) {
        //todo: handle case where attrType is guess
        return AttributeType.getMethodMap().get(attrType);
    }
}
