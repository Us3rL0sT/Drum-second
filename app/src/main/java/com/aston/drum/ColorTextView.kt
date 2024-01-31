package com.aston.drum

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ColorTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    private val colorMap = mapOf(
        Color.rgb(255, 0, 0) to "Red",
        Color.rgb(255, 127, 0) to "Orange",
        Color.rgb(255, 255, 0) to "Yellow",
        Color.rgb(0, 255, 0) to "Green",
        Color.rgb(0, 0, 255) to "Blue",
        Color.rgb(0, 191, 255) to "Cyan",
        Color.rgb(143, 0, 255) to "Purple"
    )

    fun setColor(color: Int) {
        val colorName = getColorName(color)
        text = colorName
        setTextColor(Color.BLACK)
    }

    private fun getColorName(color: Int): String {
        return colorMap[color] ?: "Unknown Color"
    }
}
