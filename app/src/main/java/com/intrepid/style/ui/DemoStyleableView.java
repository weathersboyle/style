package com.intrepid.style.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import style.AttributeType;
import style.Styleable;

public class DemoStyleableView extends FrameLayout {
    @Styleable(attrType = AttributeType.DIMENSION)
    public float width;

    @Styleable(attrType = AttributeType.BOOLEAN, defValueBoolean = true)
    public boolean fillIn;

    @Styleable(attrType = AttributeType.DRAWABLE)
    public Drawable backgroundDrawable;

    @Styleable(attrType = AttributeType.INT, defValueInt = 5)
    public Integer quantity;

    public DemoStyleableView(Context context) {
        super(context);
        init();
    }

    public DemoStyleableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithAttrs(attrs);
    }

    private void initWithAttrs(AttributeSet attrs) {
        init();
    }

    private void init() {

    }
}
