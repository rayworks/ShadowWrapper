package com.rayworks.shadowlib

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout

class RoundLinerLayoutNormal @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var currElevation: Int = 0
        private set

    private var shadowRadius: Int = 0
    private var shadowColor: Int = 0
    private var shadowBkgColor: Int = Color.WHITE
    private var shadowGravity: Int = Gravity.CENTER

    private var insetHorizontal: Int = 0
    private var insetVertical: Int = 0

    private var dx: Float = 0F
    private var dy: Float = 0F

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.RoundLinerLayoutNormal)
            currElevation = ta.getDimensionPixelSize(
                R.styleable.RoundLinerLayoutNormal_rll_elevation,
                context.resources.getDimensionPixelSize(R.dimen.elevation)
            )
            shadowRadius = ta.getDimensionPixelSize(
                R.styleable.RoundLinerLayoutNormal_rll_shadow_radius,
                context.resources.getDimensionPixelSize(R.dimen.radius_corner)
            )

            shadowColor = ta.getColor(
                R.styleable.RoundLinerLayoutNormal_rll_shadow_color,
                getContext().resources.getColor(R.color.shadowColor)
            )

            shadowBkgColor = ta.getColor(
                R.styleable.RoundLinerLayoutNormal_rll_shadow_bkg_color,
                Color.WHITE
            )

            insetHorizontal =
                ta.getDimensionPixelSize(R.styleable.RoundLinerLayoutNormal_rll_inset_horizontal, currElevation)
            insetVertical =
                ta.getDimensionPixelSize(R.styleable.RoundLinerLayoutNormal_rll_inset_vertical, currElevation)

            dx = ta.getDimensionPixelSize(R.styleable.RoundLinerLayoutNormal_rll_shadow_dx, 0).toFloat()
            dy = ta.getDimensionPixelSize(R.styleable.RoundLinerLayoutNormal_rll_shadow_dy, 0).toFloat()

            ta.recycle()
        }

        applyShadowBackground()
    }

    private fun applyShadowBackground() {
        background = ViewUtils.generateBackgroundWithShadow(
            this,
            shadowBkgColor,
            shadowRadius.toFloat(),
            shadowColor,
            currElevation,
            shadowGravity,
            insetHorizontal,
            insetVertical,
            dx, dy
        )
    }

    /***
     * Updates the shadow attributes.
     *
     * @param bkgColor the color of background layer which is below the shadow
     * @param shadowRadius shadow radius
     * @param shadowColorValue color of shadow
     * @param elevationValue shadow elevation
     * @param shadowGravity gravity of shadow, see [View's Gravity](android.view.Gravity)
     * @param insetHorizontal horizontal inset for shadow drawable
     * @param insetVertical vertical inset for shadow drawable
     * @param dx shadow layer dx
     * @param dy shadow layer dy
     */
    fun updateShadowBackground(
        bkgColor: Int = shadowBkgColor,
        shadowRadius: Int = this.shadowRadius,
        shadowColorValue: Int = shadowColor,
        elevationValue: Int = currElevation,
        shadowGravity: Int = this.shadowGravity,
        insetHorizontal: Int = this.insetHorizontal,
        insetVertical: Int = this.insetVertical,
        dx: Float = this.dx,
        dy: Float = this.dy
    ) {

        this.shadowBkgColor = bkgColor
        this.shadowRadius = shadowRadius
        this.shadowColor = shadowColorValue
        this.currElevation = elevationValue
        this.shadowGravity = shadowGravity
        this.insetHorizontal = insetHorizontal
        this.insetVertical = insetVertical
        this.dx = dx
        this.dy = dy

        applyShadowBackground()
    }
}
