package com.rayworks.shadowwrapper;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

public class RoundLinerLayoutNormal extends LinearLayout {

    private int currElevation;

    public RoundLinerLayoutNormal(Context context) {
        this(context, null);
    }

    public RoundLinerLayoutNormal(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundLinerLayoutNormal(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray ta =
                    context.obtainStyledAttributes(attrs, R.styleable.RoundLinerLayoutNormal);
            currElevation =
                    ta.getDimensionPixelSize(
                            R.styleable.RoundLinerLayoutNormal_rll_elevation,
                            context.getResources().getDimensionPixelSize(R.dimen.elevation));
            ta.recycle();
        }

        initBackground();
    }

    private void initBackground() {
        setBackground(
                ViewUtils.generateBackgroundWithShadow(
                        this,
                        android.R.color.white,
                        R.dimen.radius_corner,
                        R.color.shadowColor,
                        currElevation,
                        Gravity.CENTER));
    }
}
