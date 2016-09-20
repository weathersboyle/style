package style;

import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedHashMap;
import java.util.Map;

public class Style {
    private static final String STYLEABLE_CLASS_SUFFIX = "$$Styleable";

    private static final Map<Class, StyleableViewBinder> BINDERS = new LinkedHashMap<>();

    public static <T extends View> void bind(T target, AttributeSet attrs) {
        Class<?> targetClass = target.getClass();
        StyleableViewBinder<T> binder = getBinderForClass(targetClass);
        if (binder != null) {
            binder.bind(target, attrs);
        }
    }

    private static <T extends View> StyleableViewBinder<T> getBinderForClass(Class<?> cls) {
        StyleableViewBinder<T> binderInstance = BINDERS.get(cls);
        if (binderInstance != null) {
            return binderInstance;
        }

        String bindingClassName = cls.getName();
        try {
            Class<?> bindingClass = Class.forName(bindingClassName + STYLEABLE_CLASS_SUFFIX);
            binderInstance = (StyleableViewBinder<T>) bindingClass.newInstance();
            BINDERS.put(bindingClass, binderInstance);
        } catch (Exception e) {
            throw new RuntimeException("Unable to instantiate binding class " + bindingClassName, e);
        }

        return binderInstance;
    }
}
