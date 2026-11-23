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
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import kotlin.math.sqrt
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.layout.Arrangement

class GameSetUp : ComponentActivity(), SensorEventListener {

    private val threshold = 15f //threshold to detect a substantial shake
    private var lastTimeShook = 0L //keeps last time the device was shaken
    private val cooldown =
        2000L // 2 seconds cooldown to prevent shaking being detected over and over again
    private var shakeDetected by mutableStateOf(false) //keep track if a shake has been already detected or not
    private var firstPlayer by mutableStateOf("") //storing player to go first

    //    creating 4 variables with mutable state to store player names and later pass them on to the life tracker
    private var player1Name by mutableStateOf("")
    private var player2Name by mutableStateOf("")
    private var player3Name by mutableStateOf("")
    private var player4Name by mutableStateOf("")

    override fun onSensorChanged(event: SensorEvent?) {

        if (event != null) { //triggers whenever an event happens
            val x = event.values[0] //getting x, y and z axis for sensor
            val y = event.values[1]
            val z = event.values[2]

            val acceleration = sqrt(x * x + y * y + z * z) //calculating acceleration
            println("Acceleration: $acceleration")

            val currentTime = System.currentTimeMillis()

//            println("X: $x, Y: $y, Z: $z")

//            after detecting a shake, it will ignore other shake detections for 2 seconds
            if (acceleration > threshold && currentTime - lastTimeShook > cooldown && !shakeDetected) {
                lastTimeShook =
                    currentTime //updates last time shook to current time whenever a shake is detected
                shakeDetected = true //changes to shake detected if a shake is detected

                val players = listOf( //creating a list of input players
                    player1Name,
                    player2Name,
                    player3Name,
                    player4Name
                ).filter { it.isNotBlank() } //filtering it to avoid blank names

                if (players.isNotEmpty()) { //if none of the players are empty spaces, selects a random player to go first
                    firstPlayer = players.random()
                }

                println("Shaking movement detected")
                println("$firstPlayer goes first!")
            }
        }
    }

    //    no need for accuracy, so this will stay empty
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        if (accelerometer != null) {
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

//        testing to see if accelerometer is working
        if (accelerometer != null) {
            println("Accelerometer working") //it is, shows on Logcat
        } else {
            println("Not working :(")
        }

        setContent {

            var showShakeDialog by remember { mutableStateOf(false) } //variable to show pop up notification when needed

//            box to keep everything in the centre
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
//                    text fields for users to enter players' names
                    Text("Game Set Up")
                    TextField(
                        value = player1Name,
                        onValueChange = { player1Name = it },
                        label = { Text("Player 1") }
                    )
                    TextField(
                        value = player2Name,
                        onValueChange = { player2Name = it },
                        label = { Text("Player 2") }
                    )
                    TextField(
                        value = player3Name,
                        onValueChange = { player3Name = it },
                        label = { Text("Player 3") }
                    )
                    TextField(
                        value = player4Name,
                        onValueChange = { player4Name = it },
                        label = { Text("Player 4") }
                    )

                    if (shakeDetected) { //if shake is detected and there's already a firstPlayer, show the name on screen
                        if (firstPlayer.isNotBlank()) {
                            Text("$firstPlayer goes first!")
                        } else { //error message saying no player names were entered before shaking
                            Text("No player names entered!")
                        }
                    }

                    Button(onClick = {
                        val players = listOf(
                            player1Name,
                            player2Name,
                            player3Name,
                            player4Name
                        ).filter { it.isNotBlank() }

                        if (players.isNotEmpty()) {
                            firstPlayer = players.random()
                        }
                        shakeDetected = true
                    }) {
                        Text("Test Shake detection")
                    }

                    Button(onClick = {
                        shakeDetected = false
                        showShakeDialog = true
                        firstPlayer = ""
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
            if (showShakeDialog) { //showing pop up window
                AlertDialog(
                    onDismissRequest = { //dismissing request
                        showShakeDialog = false
                        shakeDetected = false
                        firstPlayer = ""
                    },
                    title = {
                        Text("Choose first player")
                    },
                    text = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            if (shakeDetected) { //if shaken and no player has been selected to go first, select a player to go first
                                if (firstPlayer.isNotBlank()) {
                                    Text("$firstPlayer goes first!")
                                } else {
                                    Text("No player names entered!") //otherwise tell the user no player names have been entered
                                }
                            } else { //shake not detected yet
                                Text("Shake your phone to choose who goes first!")
                            }

                            Button(onClick = {
                                val players = listOf(
                                    player1Name,
                                    player2Name,
                                    player3Name,
                                    player4Name
                                ).filter { it.isNotBlank() }

                                if (players.isNotEmpty()) {
                                    firstPlayer = players.random()
                                    shakeDetected = true
                                }
                            }) {
                                Text("Simulate Shaking")
                            }
                        }
                    },
                    //                    button to start the game after players names were entered and first player was chosen
                    confirmButton = {
                        Button(onClick = {
                            val intent = Intent(this@GameSetUp, LifeTracker::class.java)

                            intent.putExtra("PLAYER_1", player1Name)
                            intent.putExtra("PLAYER_2", player2Name)
                            intent.putExtra("PLAYER_3", player3Name)
                            intent.putExtra("PLAYER_4", player4Name)

                            startActivity(intent)
                        }) {
                            Text("Start the Game")
                        }
                    },
                    dismissButton = { //dismiss button, resets the
                        Button(onClick = {
                            showShakeDialog = false
                            shakeDetected = false
                            firstPlayer = ""
                        }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}