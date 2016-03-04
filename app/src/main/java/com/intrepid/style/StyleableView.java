package com.intrepid.style;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class StyleableView extends FrameLayout {
    @Style
    Object object;

    public StyleableView(Context context) {
        super(context);
    }

    public StyleableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
