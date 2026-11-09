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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.*
import androidx.compose.material3.TextField

class GameSetUp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { //design choice same as main activity
            var player1Name by remember{mutableStateOf("")}
            var player2Name by remember{mutableStateOf("")}
            var player3Name by remember{mutableStateOf("")}
            var player4Name by remember{mutableStateOf("")}

            Box (modifier = Modifier.fillMaxSize()) {
                Column (
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Game Set Up")
                    TextField(
                        value = player1Name,
                        onValueChange = {player1Name = it},
                        label = {Text("Player 1")}
                    )
                    TextField(
                        value = player2Name,
                        onValueChange = {player2Name = it},
                        label = {Text("Player 2")}
                    )
                    TextField(
                        value = player3Name,
                        onValueChange = {player3Name = it},
                        label = {Text("Player 3")}
                    )
                    TextField(
                        value = player4Name,
                        onValueChange = {player4Name = it},
                        label = {Text("Player 4")}
                    )

                    Button(onClick = {
                        val intent = Intent(this@GameSetUp, LifeTracker::class.java)

                        intent.putExtra("PLAYER_1", player1Name)
                        intent.putExtra("PLAYER_2", player2Name)
                        intent.putExtra("PLAYER_3", player3Name)
                        intent.putExtra("PLAYER_4", player4Name)

                        startActivity(intent)
                    }) {
                        Text("Start")
                    }
                    Button(onClick = {
                        val intent = Intent(this@GameSetUp, MainActivity::class.java)
                        startActivity(intent)
                    }) {
                        Text("Back")
                    }
                }
            }
        }
    }
}