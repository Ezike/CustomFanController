package com.example.android.customfancontroller.view

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import com.example.android.customfancontroller.R
import kotlin.math.abs

private const val STROKE_WIDTH = 12f // has to be float

class MyCanvasView(context: Context) : View(context) {
    // Path representing the drawing so far
    private val drawing = Path()

    // Path representing what's currently being drawn
    private val currentPath = Path()

    private var path = Path()

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    private var currentX = 0f
    private var currentY = 0f

    private val touchTolerance: Int = ViewConfiguration.get(context).scaledTouchSlop

    private lateinit var frame: Rect

    private val backgroundColor: Int =
        ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    private val drawColor: Int =
        ResourcesCompat.getColor(resources, R.color.colorPaint, null)

    // Set up the paint with which to draw.
    private val paint: Paint = Paint().apply {
        color = drawColor
        // Smooths out edges of what is drawn without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val inset = 60
        frame = Rect(inset, inset, width - inset, height - inset)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(backgroundColor)
        // Draw the drawing so far
        canvas.drawPath(drawing, paint)
        // Draw any current squiggle
        canvas.drawPath(currentPath, paint)
        // Draw a frame around the canvas
        canvas.drawRect(frame, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    private fun touchUp() {
        // Reset the path so it doesn't get drawn again.
        drawing.addPath(currentPath)
        currentPath.reset()
        path.reset()
    }

    private fun touchMove() {
        val dx: Float = abs(motionTouchEventX - currentX)
        val dy: Float = abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            // QuadTo() adds a quadratic bezier from the last point,
            // approaching control point (x1,y1), and ending at (x2,y2).
            path.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            drawing.addPath(path)
        }
        invalidate()
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }
}