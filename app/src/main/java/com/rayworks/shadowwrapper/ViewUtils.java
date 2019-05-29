package com.rayworks.shadowwrapper;

import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;

public class ViewUtils {

    public static Drawable generateBackgroundWithShadow(
            View view,
            @ColorRes int backgroundColor,
            @DimenRes int cornerRadius,
            @ColorRes int shadowColor,
            int elevationValue,
            int shadowGravity) {
        Resources resources = view.getContext().getResources();

        float cornerRadiusValue = resources.getDimension(cornerRadius);
        int shadowColorValue = ContextCompat.getColor(view.getContext(), shadowColor);
        int backgroundColorValue = ContextCompat.getColor(view.getContext(), backgroundColor);

        float[] outerRadius = {
            cornerRadiusValue,
            cornerRadiusValue,
            cornerRadiusValue,
            cornerRadiusValue,
            cornerRadiusValue,
            cornerRadiusValue,
            cornerRadiusValue,
            cornerRadiusValue
        };

        Rect shapeDrawablePadding = new Rect();
        shapeDrawablePadding.left = elevationValue;
        shapeDrawablePadding.right = elevationValue;

        int DY;
        switch (shadowGravity) {
            case Gravity.CENTER:
                shapeDrawablePadding.top = elevationValue;
                shapeDrawablePadding.bottom = elevationValue;
                DY = 0;
                break;
            case Gravity.TOP:
                shapeDrawablePadding.top = elevationValue * 2;
                shapeDrawablePadding.bottom = elevationValue;
                DY = -1 * elevationValue / 3;
                break;
            default:
            case Gravity.BOTTOM:
                shapeDrawablePadding.top = elevationValue;
                shapeDrawablePadding.bottom = elevationValue * 2;
                DY = elevationValue / 3;
                break;
        }

        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setPadding(shapeDrawablePadding);

        Paint paint = shapeDrawable.getPaint();
        paint.setColor(backgroundColorValue);
        paint.setShadowLayer(elevationValue /*/3*/, 0, DY, shadowColorValue);

        view.setLayerType(View.LAYER_TYPE_SOFTWARE, paint);

        shapeDrawable.setShape(new RoundRectShape(outerRadius, null, null));

        // merge it to LayerDrawable and set Inset to drawable for not seeing cutting shadow
        LayerDrawable drawable = new LayerDrawable(new Drawable[] {shapeDrawable});
        int vertical = elevationValue /* * 2*/;
        int horizontal = elevationValue;
        drawable.setLayerInset(0, horizontal, vertical, horizontal, vertical);

        return drawable;
    }
}
