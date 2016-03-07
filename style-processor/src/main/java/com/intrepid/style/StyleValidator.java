package com.intrepid.style;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeKind;

public class StyleValidator {
    public static final String  VIEW_CLASS_NAME = "android.view.View";

    public static void validateAnnotatedElement(Element element) throws ProcessingException {
        validateElementKind(element);
        validateEnclosingElementClassType(element.getEnclosingElement());
        validateAttrType(element);
    }

    /**
     * Validates that annotated element is a field
     *
     * @param element
     * @throws ProcessingException
     */
    public static void validateElementKind(Element element) throws ProcessingException {
        if (element.getKind() != ElementKind.FIELD) {
            throw new ProcessingException(element, "Only fields can be annotated with @%s",
                    Style.class.getSimpleName());
        }
    }

    /**
     * Validates that element is enclosed in a class that extends View
     *
     * @param element
     * @throws ProcessingException
     */
    public static void validateEnclosingElementClassType(Element element) throws ProcessingException {
        if (!LangModelUtils.isSubtypeOfType(element.asType(), VIEW_CLASS_NAME)) {
            throw new ProcessingException(element, "%s is not subclass of %s", element.asType().toString(),
                    VIEW_CLASS_NAME);
        }
    }

    /**
     * Validates that AttributeType argument to annotation is compatible with declared type of attribute field
     *
     * @param element
     * @throws ProcessingException
     */
    public static void validateAttrType(Element element) throws ProcessingException {
        AttributeType attrType = element.getAnnotation(Style.class).attrType();
        TypeKind kind = LangModelUtils.getTypeKind(element);
        boolean primitiveTypeMatch = (kind == TypedArrayUtils.getCompatibleTypeKind(attrType));
        boolean classTypeMatch = LangModelUtils.isSubtypeOfType(element.asType(),
                TypedArrayUtils.getPartnerClassName(attrType));
        if ((attrType == AttributeType.GUESS) || primitiveTypeMatch || classTypeMatch) {
            return;
        }
        throw new ProcessingException(element, "Attribute type %s is not compatible with declared type %s",
                attrType.toString(), kind.toString());
    }
}
