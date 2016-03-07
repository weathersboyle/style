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

    String METHOD_OBTAIN_ATTRS = "obtainStyledAttributes";
    String METHOD_GET_BOOLEAN = "getBoolean";
    String METHOD_GET_COLOR = "getColor";
    String METHOD_GET_COLOR_STATE_LIST = "getColorStateList";
    String METHOD_GET_DIMEN = "getDimension";
    String METHOD_GET_DIMEN_PIX_OFFSET = "getDimensionPixelOffset";
    String METHOD_GET_DIMEN_PIX_SIZE = "getDimensionPixelSize";
    String METHOD_GET_DRAWABLE = "getDrawable";
    String METHOD_GET_FLOAT = "getFloat";
    String METHOD_GET_INT = "getInt";
    String METHOD_GET_INTEGER = "getInteger";
    String METHOD_GET_LAYOUT_DIMEN = "getLayoutDimension";
    String METHOD_GET_NON_RES_STRING = "getNonResourceString";
    String METHOD_GET_RES_ID = "getResourceId";
    String METHOD_GET_STRING = "getString";
    String METHOD_GET_TEXT = "getText";

    int DEFAULT_VALUE_INT = 0;
    float DEFAULT_VALUE_FLOAT = 0;
    boolean DEFAULT_VALUE_BOOL = false;

    AttributeType attrType() default AttributeType.GUESS;

    int defValueInt() default DEFAULT_VALUE_INT;

    float defValueFloat() default DEFAULT_VALUE_FLOAT;

    boolean defValueBoolean() default DEFAULT_VALUE_BOOL;
}
