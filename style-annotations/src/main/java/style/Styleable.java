package style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface Styleable {
    AttributeType attrType() default AttributeType.GUESS;

    int defValueInt() default Constants.DEFAULT_VALUE_INT;

    float defValueFloat() default Constants.DEFAULT_VALUE_FLOAT;

    boolean defValueBoolean() default Constants.DEFAULT_VALUE_BOOL;
}
