package com.intrepid.style.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.intrepid.style.AttributeType;
import com.intrepid.style.Style;

public class StyleableView extends FrameLayout {
    @Style(attrType = AttributeType.DIMENSION)
    public int field1;

    @Style(attrType = AttributeType.BOOLEAN, defValueBoolean = true)
    public boolean field2;

    @Style(attrType = AttributeType.DRAWABLE)
    public Drawable field3;

    @Style(attrType = AttributeType.INT, defValueInt = 5)
    public Integer field4;

    public StyleableView(Context context) {
        super(context);
    }

    public StyleableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
