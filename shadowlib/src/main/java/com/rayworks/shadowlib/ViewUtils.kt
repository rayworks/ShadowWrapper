package com.rayworks.shadowlib

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.Gravity
import android.view.View

object ViewUtils {
    /***
     * Applies the shadow effect
     */
    fun View.applyShadow(
        backgroundColorValue: Int,
        cornerRadiusValue: Float,
        shadowColorValue: Int,
        elevationValue: Int,
        shadowGravity: Int,
        insetLeft: Int = elevationValue,
        insetTop: Int = elevationValue,
        insetRight: Int = elevationValue,
        insetBottom: Int = elevationValue,
        dx: Float = 0F,
        dy: Float = 0F
    ) {
        background = generateBackgroundWithShadow(
            this, backgroundColorValue, cornerRadiusValue, shadowColorValue, elevationValue,
            shadowGravity, insetLeft, insetTop, insetRight, insetBottom, dx, dy
        )
    }

    /***
     * Generates a shadow drawable
     */
    private fun generateBackgroundWithShadow(
        view: View,
        backgroundColorValue: Int,
        cornerRadiusValue: Float,
        shadowColorValue: Int,
        elevationValue: Int,
        shadowGravity: Int,
        insetLeft: Int = elevationValue,
        insetTop: Int = elevationValue,
        insetRight: Int = elevationValue,
        insetBottom: Int = elevationValue,
        dx: Float = 0F,
        dy: Float = 0F

    ): Drawable {
        val outerRadius = FloatArray(8) { cornerRadiusValue }

        val shapeDrawablePadding = Rect()
        shapeDrawablePadding.left = elevationValue
        shapeDrawablePadding.right = elevationValue

        when (shadowGravity) {
            Gravity.CENTER -> {
                shapeDrawablePadding.top = elevationValue
                shapeDrawablePadding.bottom = elevationValue
            }
            Gravity.TOP -> {
                shapeDrawablePadding.top = elevationValue * 2
                shapeDrawablePadding.bottom = elevationValue
            }
            Gravity.BOTTOM -> {
                shapeDrawablePadding.top = elevationValue
                shapeDrawablePadding.bottom = elevationValue * 2
            }
            else -> {
                shapeDrawablePadding.top = elevationValue
                shapeDrawablePadding.bottom = elevationValue * 2
            }
        }

        val shapeDrawable = ShapeDrawable()
        shapeDrawable.setPadding(shapeDrawablePadding)

        val paint = shapeDrawable.paint
        paint.color = backgroundColorValue
        paint.setShadowLayer(elevationValue.toFloat(), dx, dy, shadowColorValue)

        // https://developer.android.com/guide/topics/graphics/hardware-accel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            view.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
        else
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, paint)

        // NB: Given that the RoundRectShape will be set as the view's background, you can only change its
        // dimension via the host view
        shapeDrawable.shape = RoundRectShape(outerRadius, null, null)

        // merge it to LayerDrawable and set Inset to drawable for not seeing cutting shadow
        val drawable = LayerDrawable(arrayOf<Drawable>(shapeDrawable))
        drawable.setLayerInset(0, insetLeft, insetTop, insetRight, insetBottom)

        return drawable
    }
}
