package com.intrepid.style;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public class StyleValidator {
    public static final String VIEW_CLASS_NAME = "android.view.View";

    public static void validateAnnotatedElement(Element element) throws ProcessingException {
        validateElementKind(element);
        validateEnclosingElementClassType(element);
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
        if (!TypeUtils.isSubtypeOfType(element.asType(), VIEW_CLASS_NAME)) {
            throw new ProcessingException(element, "Field must belong to subclass of @%s", VIEW_CLASS_NAME);
        }
    }
}
