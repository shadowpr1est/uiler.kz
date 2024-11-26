package com.example.uilerkz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.example.uilerkz.ui.theme.UilerkzTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UilerkzTheme {
                MainScreen()
            }
        }
    }
}