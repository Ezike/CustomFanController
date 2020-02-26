package com.example.android.customfancontroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.customfancontroller.view.ClippedView

class ClippingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ClippedView(this))
    }
}