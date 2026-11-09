package com.md.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.md.assignment.ui.theme.Assignment_MDTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import android.view.Menu
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.offset

class Statistics : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            design choice same as main activity
            Box (modifier = Modifier.fillMaxSize()) {
                Column (
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Statistics")
                    Button(onClick = {
                        val intent = Intent(this@Statistics, MainActivity::class.java)
                        startActivity(intent)
                    }) {
                        Text("Back to Main Menu")
                    }
                }
            }
        }
    }
}