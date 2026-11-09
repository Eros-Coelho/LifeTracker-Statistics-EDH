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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            box to make it so the buttons are shown in the centre of the screen for now
//            will probably change the centering when I do these pages on future milestones
            Box (modifier = Modifier.fillMaxSize()) {
                Column( //columns with buttons linking the different activities, but only relevant ones
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Main Menu")
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, GameSetUp::class.java)
                        startActivity(intent)
                    }) {
                        Text("New Game")
                    }
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Statistics::class.java)
                        startActivity(intent)
                    }) {
                        Text("View Statistics")
                    }
                }
            }
        }
    }
}