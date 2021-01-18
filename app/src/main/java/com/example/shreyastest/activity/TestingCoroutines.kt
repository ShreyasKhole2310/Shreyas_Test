package com.example.shreyastest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shreyastest.R
import kotlinx.android.synthetic.main.activity_testing_coroutines.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestingCoroutines : AppCompatActivity() {
    val strResult1: String = "Help Me"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing_coroutines)
        btnCallCoroutines.setOnClickListener {
            CoroutineScope(Main).launch {
                val result = Testfunction("Why this")
                txtCallMe.text = result
            }
        }
    }

    suspend fun Testfunction(strTestUI: String): String {
        delay(3200)
        return txtCallMe.text.toString() + "\n$strResult1" + strTestUI
    }
}