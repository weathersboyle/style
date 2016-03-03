package com.intrepid.style;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class TypeUtils {
    /**
     * This is pared-down variant of Butterknife method
     *
     * @param elementType
     * @param targetType
     * @return true only if elementType is subtype of targetType, where targetType is basic, class type, e.g., View.
     */
    public static boolean isSubtypeOfType(TypeMirror elementType, String targetType) {
        /**
         * return true if type names are equivalent
         */
        if (elementType.toString().equals(targetType)) {
            return true;
        }
        /**
         * return false if type not a class or interface
         */
        if (elementType.getKind() != TypeKind.DECLARED) {
            return false;
        }
        DeclaredType declaredType = (DeclaredType) elementType;
        Element element = declaredType.asElement();
        if (!(element instanceof TypeElement)) {
            return false;
        }
        TypeElement typeElement = (TypeElement) element;
        return isSubtypeOfType(typeElement.getSuperclass(), targetType);
    }

    /**
     * @param element
     * @return typeKind of given element
     */
    public static TypeKind getTypeKind(Element element) {
        return element.asType().getKind();
    }
}
