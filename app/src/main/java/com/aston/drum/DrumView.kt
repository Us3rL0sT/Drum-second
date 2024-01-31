package com.aston.drum

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator


class DrumView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val drumPaint: Paint = Paint()
    private val numSegments = 7
    private var currentColorIndex: Int = 0
    private lateinit var colorTextView: ColorTextView





    init {
        drumPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        val drumRadius = (width / 2f).coerceAtMost(height / 2f)

        val angle = 360f / numSegments

        for (i in 0 until numSegments) {
            drumPaint.color = getRainbowColor(i)

            val left = centerX - drumRadius
            val top = centerY - drumRadius
            val right = centerX + drumRadius
            val bottom = centerY + drumRadius

            canvas.drawArc(RectF(left, top, right, bottom), i * angle, angle, true, drumPaint)
        }

        drawColorName()
    }

    fun getRainbowColor(position: Int): Int {
        val rainbowColors = intArrayOf(
            Color.rgb(255, 0, 0), Color.rgb(255, 127, 0),
            Color.rgb(255, 255, 0), Color.rgb(0, 255, 0),
            Color.rgb(0, 0, 255),
            Color.rgb(0, 191, 255), Color.rgb(143, 0, 255)
        )
        return rainbowColors[position % rainbowColors.size]
    }

    fun rotateDrum(callback: (Int) -> Unit) {
        val randomDegrees = (360 + Math.random() * 1440).toFloat()

        val rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", rotation + randomDegrees)
        rotationAnimator.duration = 1000
        rotationAnimator.interpolator = LinearInterpolator()
        rotationAnimator.start()

        rotationAnimator.addListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                val totalDegrees = rotation % 360
                val normalizedDegrees = if (totalDegrees < 0) totalDegrees + 360 else totalDegrees
                currentColorIndex = (numSegments - (normalizedDegrees / (360f / numSegments)).toInt()) % numSegments + 200
                val currentColor = getRainbowColor(currentColorIndex)

                callback.invoke(currentColor)
                invalidate()
            }
        })

        rotationAnimator.addListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                drawColorName()
            }
        })

        rotationAnimator.start()
    }


    fun getColorName(color: Int): String {
        val colorMap = mapOf(
            Color.rgb(255, 0, 0) to "Red",
            Color.rgb(255, 127, 0) to "Orange",
            Color.rgb(255, 255, 0) to "Yellow",
            Color.rgb(0, 255, 0) to "Green",
            Color.rgb(0, 0, 255) to "Blue",
            Color.rgb(0, 191, 255) to "Cyan",
            Color.rgb(143, 0, 255) to "Purple"
        )
        return colorMap[color] ?: "Unknown Color"
    }

    fun setColorTextView(colorTextView: ColorTextView) {
        this.colorTextView = colorTextView
    }

    private fun drawColorName() {
        colorTextView.setColor(getRainbowColor(currentColorIndex))
    }


}









