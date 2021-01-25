package com.rayworks.shadowwrapper

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.rayworks.shadowlib.ViewUtils.applyShadow
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var colors: ArrayList<Int>

    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        colors = arrayListOf(
            resources.getColor(android.R.color.holo_blue_light),
            resources.getColor(android.R.color.holo_green_light),
            resources.getColor(android.R.color.holo_red_light)
        )

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val insetBottomValue = layout_light.currElevation * 2
        val insetHorizontalValue = layout_light.currElevation / 2
        layout_light.updateShadowBackground(
            shadowColorValue = colors[0],
            shadowGravity = Gravity.BOTTOM,
            dy = (layout_light.currElevation / 2).toFloat(),
            insetBottomValue = insetBottomValue
        )

        layout_light.setOnClickListener {
            ++index
            index %= colors.size
            layout_light.updateShadowBackground(shadowColorValue = colors[index])
        }

        top_text.applyShadow(
            Color.WHITE,
            cornerRadiusValue = resources.getDimension(R.dimen.radius_corner),
            shadowColorValue = resources.getColor(R.color.shadowColor),
            elevationValue = resources.getDimensionPixelSize(R.dimen.radius),
            shadowGravity = Gravity.CENTER
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
