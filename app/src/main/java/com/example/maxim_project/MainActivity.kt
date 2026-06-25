package com.example.maxim_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.maxim_project.ui.MaximApp
import com.example.maxim_project.ui.theme.MaximTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaximTheme {
                MaximApp()
            }
        }
    }
}