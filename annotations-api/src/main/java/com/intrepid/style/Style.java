package com.intrepid.style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Style {
    String COLOR_STATE_LIST_CLASS_NAME = "android.content.res.ColorStateList";
    String DRAWABLE_CLASS_NAME = "android.graphics.drawable.Drawable";

    int DEFAULT_VALUE_INT = 0;
    float DEFAULT_VALUE_FLOAT = 0;
    boolean DEFAULT_VALUE_BOOL = false;

    AttributeType attrType() default AttributeType.GUESS;

    int defValueInt() default DEFAULT_VALUE_INT;

    float defValueFloat() default DEFAULT_VALUE_FLOAT;

    boolean defValueBool() default DEFAULT_VALUE_BOOL;
}
