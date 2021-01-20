package com.example.shreyastest.jetPackCompose

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.setContent
import com.example.shreyastest.R
import kotlinx.android.synthetic.main.activity_main.*

class JetpackSample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                
            }
        }
    }

}