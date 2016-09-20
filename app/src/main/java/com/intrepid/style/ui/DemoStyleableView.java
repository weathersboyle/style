package com.intrepid.style.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import style.AttributeType;
import style.Style;
import style.Styleable;

public class DemoStyleableView extends View {
    @Styleable(attrType = AttributeType.DIMENSION)
    public float radius;
    @Styleable(attrType = AttributeType.BOOLEAN, defValueBoolean = false)
    public boolean fillIn;
    @Styleable(attrType = AttributeType.COLOR, defValueInt = android.R.color.black)
    public Integer backgroundColor;
    @Styleable(attrType = AttributeType.INT, defValueInt = 5)
    public Integer quantity;

    private Paint paint = new Paint();

    public DemoStyleableView(Context context) {
        super(context);

        init();
    }

    public DemoStyleableView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initWithAttrs(attrs);
    }

    private void initWithAttrs(AttributeSet attrs) {
        Style.bind(this, attrs);

        init();
    }

    private void init() {
        paint.setColor(backgroundColor);
        paint.setStyle(fillIn ? Paint.Style.FILL : Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < quantity; i++) {
            float cx = radius * (i + 1) + (i * radius + i * 30);
            canvas.drawCircle(cx, radius, radius, paint);
        }
    }
}
