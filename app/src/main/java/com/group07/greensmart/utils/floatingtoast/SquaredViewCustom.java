package com.group07.greensmart.utils.floatingtoast;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by nguyenvuhuy on 23/09/2017.
 */

public class SquaredViewCustom extends View {

    public SquaredViewCustom(Context context) {
        super(context);
    }

    public SquaredViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaredViewCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}