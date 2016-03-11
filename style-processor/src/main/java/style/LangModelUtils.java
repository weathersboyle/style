package style;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class LangModelUtils {
    private static Elements elementUtils;
    private static Types typeUtils;

    public static void init(Elements elementUtils, Types typeUtils) {
        LangModelUtils.elementUtils = elementUtils;
        LangModelUtils.typeUtils = typeUtils;
    }

    /**
     * This is pared-down variant of Butterknife method
     *
     * @param type
     * @param targetType
     * @return true only if type is subtype of targetType, where targetType is basic, class type, e.g., View.
     */
    public static boolean isSubtypeOfType(TypeMirror type, String targetType) {
        /**
         * return true if type names are equivalent
         */
        if (type.toString().equals(targetType)) {
            return true;
        }
        /**
         * return false if type not a class or interface
         */
        if (type.getKind() != TypeKind.DECLARED) {
            return false;
        }
        DeclaredType declaredType = (DeclaredType) type;
        Element element = declaredType.asElement();
        if (!(element instanceof TypeElement)) {
            return false;
        }
        TypeElement typeElement = (TypeElement) element;
        return isSubtypeOfType(typeElement.getSuperclass(), targetType);
    }

    public static boolean isBoxedInt(DeclaredType type) {
        return isBoxedVariant(type, TypeKind.INT);
    }

    public static boolean isBoxedFloat(DeclaredType type) {
        return isBoxedVariant(type, TypeKind.FLOAT);
    }

    public static boolean isBoxedBoolean(DeclaredType type) {
        return isBoxedVariant(type, TypeKind.BOOLEAN);
    }

    /**
     * @param type
     * @param primitiveTypeKind
     * @return true only if type is boxed variant of type with kind equal to primitiveTypeKind
     */
    public static boolean isBoxedVariant(DeclaredType type, TypeKind primitiveTypeKind) {
        try {
            PrimitiveType primitiveType = typeUtils.unboxedType(type);
            return primitiveType.getKind() == primitiveTypeKind;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * @param element
     * @return typeKind of element
     */
    public static TypeKind getTypeKind(Element element) {
        return element.asType().getKind();
    }

    /**
     * @param element
     * @return package name of element
     */
    public static String getPackageName(Element element) {
        return elementUtils.getPackageOf(element).getQualifiedName().toString();
    }

    public static Elements getElementUtils() {
        return elementUtils;
    }

    public static Types getTypeUtils() {
        return typeUtils;
    }
}
