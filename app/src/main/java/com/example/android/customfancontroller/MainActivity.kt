package com.example.android.customfancontroller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.free_drawing).setOnClickListener {
            gotoScreen(CanvasActivity())
        }

        findViewById<Button>(R.id.clipping).setOnClickListener {
            gotoScreen(ClippingActivity())
        }
    }

    private fun gotoScreen(screen: AppCompatActivity) {
        val intent = Intent(this, screen::class.java)
        startActivity(intent)
    }
}