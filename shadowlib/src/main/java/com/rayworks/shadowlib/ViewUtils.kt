package com.rayworks.shadowlib

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.Gravity
import android.view.View

object ViewUtils {

    fun generateBackgroundWithShadow(
        view: View,
        backgroundColorValue: Int,
        cornerRadiusValue: Float,
        shadowColorValue: Int,
        elevationValue: Int,
        shadowGravity: Int,
        insetHorizontal: Int = elevationValue,
        insetVertical: Int = elevationValue,
        dx: Float = 0F,
        dy: Float = 0F

    ): Drawable {
        val outerRadius = floatArrayOf(
            cornerRadiusValue,
            cornerRadiusValue,
            cornerRadiusValue,
            cornerRadiusValue,
            cornerRadiusValue,
            cornerRadiusValue,
            cornerRadiusValue,
            cornerRadiusValue
        )

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

        view.setLayerType(View.LAYER_TYPE_SOFTWARE, paint)

        shapeDrawable.shape = RoundRectShape(outerRadius, null, null)

        // merge it to LayerDrawable and set Inset to drawable for not seeing cutting shadow
        val drawable = LayerDrawable(arrayOf<Drawable>(shapeDrawable))
        drawable.setLayerInset(0, insetHorizontal, insetVertical, insetHorizontal, insetVertical)

        return drawable
    }
}