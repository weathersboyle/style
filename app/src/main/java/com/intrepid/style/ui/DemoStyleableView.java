package com.intrepid.style.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import style.AttributeType;
import style.Styleable;

public class DemoStyleableView extends FrameLayout {
    @Styleable(attrType = AttributeType.DIMENSION)
    public int field1;

    @Styleable(attrType = AttributeType.BOOLEAN, defValueBoolean = true)
    public boolean field2;

    @Styleable(attrType = AttributeType.DRAWABLE)
    public Drawable field3;

    @Styleable(attrType = AttributeType.INT, defValueInt = 5)
    public Integer field4;

    public DemoStyleableView(Context context) {
        super(context);
    }

    public DemoStyleableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
