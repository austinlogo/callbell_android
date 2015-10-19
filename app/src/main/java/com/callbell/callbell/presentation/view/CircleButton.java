package com.callbell.callbell.presentation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by austin on 10/18/15.
 */
public class CircleButton extends Button {


    public CircleButton(Context context) {
        super(context);
    }

    public CircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (widthMeasureSpec < heightMeasureSpec)
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        else {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
    }
}
