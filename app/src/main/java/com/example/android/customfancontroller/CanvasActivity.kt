package com.example.android.customfancontroller

import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import androidx.appcompat.app.AppCompatActivity
import com.example.android.customfancontroller.view.MyCanvasView

class CanvasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myCanvasView = MyCanvasView(this)
        myCanvasView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        myCanvasView.contentDescription = getString(R.string.canvasContentDescription)

        setContentView(myCanvasView)
    }
}